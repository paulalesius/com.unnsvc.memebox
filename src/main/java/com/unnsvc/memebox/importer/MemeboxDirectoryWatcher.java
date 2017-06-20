
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
import com.unnsvc.memebox.preferences.IMemeboxPreferences;
import com.unnsvc.memebox.preferences.MemeboxPreferences;

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
		} catch (InterruptedException | IOException ioe) {

			throw new RuntimeException(ioe);
		}
	}

	private void runMainLoop() throws IOException, InterruptedException {

		IMemeboxPreferences prefs = context.getComponent(MemeboxPreferences.class);
		try (WatchService watchService = FileSystems.getDefault().newWatchService()) {

			for (File watchLocation : prefs.getWatchLocations()) {

				if (watchLocation.exists()) {

					Path path = watchLocation.toPath();
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

								Path path = (Path) watchEvent.context();

								// @TODO don't convert paths back and forth
								String extSuffix = getFileExtension(path.toFile());
								if (extSuffix != null) {

									ESupportedExt ext = ESupportedExt.fromExtension(extSuffix);
									watchListener.onEntryModify(path, ext);
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
	 * Here we want to search thought the watched folders and evaluate the items
	 */
	private void runInitialise() {

		IMemeboxPreferences prefs = context.getComponent(MemeboxPreferences.class);
		for (File watchLocation : prefs.getWatchLocations()) {

			if (watchLocation.isDirectory()) {
				runInitialise(watchLocation);
			}
		}
	}

	private void runInitialise(File watchLocation) {

		for (File contained : watchLocation.listFiles()) {

			if (executing.get()) {
				if (contained.isFile()) {

					String extSuffix = getFileExtension(contained);
					if (extSuffix != null) {

						ESupportedExt ext = ESupportedExt.fromExtension(extSuffix);
						watchListener.onInitialise(contained.toPath(), ext);
					}
				} else if (contained.isDirectory()) {

					runInitialise(contained);
				}
			} else {

				// stopped execution so we abort the recursive
				break;
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

			return name.substring(atPosition + 1);
		}
	}

}
