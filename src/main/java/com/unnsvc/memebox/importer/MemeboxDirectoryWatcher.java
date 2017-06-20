
package com.unnsvc.memebox.importer;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.unnsvc.memebox.IMemeboxContext;
import com.unnsvc.memebox.MemeboxException;
import com.unnsvc.memebox.config.IMemeboxConfig;
import com.unnsvc.memebox.config.MemeboxConfig;
import com.unnsvc.memebox.config.WatchLocation;

/**
 * @TODO fix these back and forth Path-File conversions
 * @author noname
 *
 */
public class MemeboxDirectoryWatcher extends Thread implements IMemeboxDirectoryWatcher {

	private Logger log = LoggerFactory.getLogger(getClass());
	private IMemeboxContext context;
	private AtomicBoolean executing;
	private IMemeboxDirectoryWatcherListener watchListener;

	public MemeboxDirectoryWatcher(IMemeboxContext context, IMemeboxDirectoryWatcherListener watchListener) {

		this.context = context;
		this.executing = new AtomicBoolean(false);
		this.watchListener = watchListener;
		start();
	}

	public void run() {

		try {
			log.info("Starting watch service");

			runInitialise();
			runMainLoop();
		} catch (MemeboxException | InterruptedException | IOException ioe) {

			throw new RuntimeException(ioe);
		}
	}

	private void runMainLoop() throws IOException, InterruptedException {

		IMemeboxConfig prefs = context.getComponent(MemeboxConfig.class);
		try (WatchService watchService = FileSystems.getDefault().newWatchService()) {

			for (WatchLocation watchLocation : prefs.getWatchLocations()) {

				if (watchLocation.getLocation().exists()) {

					log.trace("Watching: " + watchLocation);
					Path path = watchLocation.getLocation().toPath();
					path.register(watchService, ENTRY_CREATE, ENTRY_MODIFY, ENTRY_DELETE);
				} else {

					log.warn("Watch location not available: " + watchLocation);
				}
			}

			if (!prefs.getWatchLocations().isEmpty()) {

				executing.set(true);

				/**
				 * @TODO while loop on something abortable so we can close this
				 *       component
				 */
				while (executing.get()) {

					WatchKey key = watchService.poll(1, TimeUnit.SECONDS);
					if (key != null) {
						for (WatchEvent<?> watchEvent : key.pollEvents()) {

							Kind<?> kind = watchEvent.kind();
							if (kind == StandardWatchEventKinds.OVERFLOW) {
								// invalid or something
								continue;
							} else if (kind == StandardWatchEventKinds.ENTRY_CREATE || kind == StandardWatchEventKinds.ENTRY_MODIFY) {

								try {
									Path path = (Path) watchEvent.context();

									// @TODO don't convert paths back and forth
									String extSuffix = getFileExtension(path.toFile());
									if (extSuffix != null) {

										ESupportedExt ext = ESupportedExt.fromExtension(extSuffix);
										watchListener.onEntryModify(getAutoimportAttributeForPath(prefs, path), path, ext);
									}
								} catch (MemeboxException me) {

									log.error("Error processing file change notification, this is a bug, please report", me);
								}
							}

							// process event

							// end
							if (key.reset()) {

								continue;
							}
						}
					} else {
						// No watch directories or something like that, so
						// we make the loop block to spare cycles
						synchronized (this) {

							this.wait(3000);
						}
					}
				}
			}
		}
	}

	/**
	 * @TODO I don't like checking like this for each individual file as it
	 *       becomes expensive for hundreds of thousands of files
	 */
	private boolean getAutoimportAttributeForPath(IMemeboxConfig prefs, Path path) {

		String pathFile = path.toFile().getAbsolutePath();
		for (WatchLocation watchLocation : prefs.getWatchLocations()) {

			String location = watchLocation.getLocation().getAbsolutePath();
			if (pathFile.startsWith(location)) {

				return watchLocation.getAutoimport();
			}
		}
		return false;
	}

	/**
	 * Here we want to search thought the watched folders and evaluate the items
	 * 
	 * @throws MemeboxException
	 */
	private void runInitialise() throws MemeboxException {

		IMemeboxConfig prefs = context.getComponent(MemeboxConfig.class);
		for (WatchLocation watchLocation : prefs.getWatchLocations()) {

			if (watchLocation.getLocation().isDirectory()) {
				runInitialise(watchLocation.getAutoimport(), watchLocation.getLocation());
			}
		}
		// 
		context.flushComponents();
	}

	private void runInitialise(boolean autoimport, File watchLocation) throws MemeboxException {

		for (File contained : watchLocation.listFiles()) {
			log.trace("Try " + watchLocation);

			if (contained.isFile()) {

				String extSuffix = getFileExtension(contained);
				if (extSuffix != null) {

					log.trace("Watcher found " + contained + " ext: " + extSuffix);

					ESupportedExt ext = ESupportedExt.fromExtension(extSuffix);
					watchListener.onInitialise(autoimport, contained.toPath(), ext);
				}
			} else if (contained.isDirectory()) {

				runInitialise(autoimport, contained);
			}
		}
	}

	@Override
	public void destroyComponent() throws MemeboxException {

		log.info("Destroying");
		executing.set(false);
		try {
			join(1000);
		} catch (InterruptedException ie) {

			throw new MemeboxException(ie);
		}
	}

	private String getFileExtension(File file) {

		String name = file.getName();
		int atPosition = name.lastIndexOf(".");
		if (atPosition == -1) {

			return null;
		} else {

			return name.substring(atPosition + 1).toLowerCase();
		}
	}

	@Override
	public void flushComponent(IMemeboxConfig config) {

	}

}
