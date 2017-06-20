
package com.unnsvc.memebox;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import com.unnsvc.memebox.config.IMemeboxConfig;
import com.unnsvc.memebox.config.MemeboxConfigReader;
import com.unnsvc.memebox.importer.MemeboxDirectoryWatcher;
import com.unnsvc.memebox.importer.MemeboxDirectoryWatcherListener;
import com.unnsvc.memebox.model.StorageLocation;
import com.unnsvc.memebox.ui.MainFrame;

public class Main {

	private Logger log = LoggerFactory.getLogger(Main.class);
	public IMemeboxContext context;

	public static void main(String... args) throws Exception {

		File configLocation = new File("memebox.xml");

		if (!configLocation.exists()) {

			// this is from the build environment during testing
			configLocation = new File("target/test-classes/memebox.xml");
		}

		Main main = new Main();
		main.startup(configLocation);
	}

	public void startup(File configLocation) throws Exception {

		log.info("Starting application with config: " + configLocation);

		SwingUtilities.invokeLater(new Runnable() {

			public void run() {

				try {
					context = configureContext(configLocation);
					MemeboxUtils.configureLookAndFeel();

					MainFrame frame = new MainFrame(context);
					context.addComponent(frame);
					frame.setTitle("MemeBox is a memebox");

					// remove this later
					frame.setPreferredSize(new Dimension(1024, 768));
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame.setLayout(new BorderLayout());

					frame.createUi();
					frame.pack();
					frame.setLocationRelativeTo(null);

					frame.addWindowListener(new WindowAdapter() {

						@Override
						public void windowClosing(WindowEvent e) {

							log.debug("Destroying main frame");
							context.destroy();
						}
					});

					frame.setVisible(true);

					((MemeboxContext) context).debugContext();

				} catch (Throwable throwable) {

					log.error("Epic fail", throwable);
				} finally {

					synchronized (configLocation) {

						log.error("Initialized");
						configLocation.notifyAll();
					}
				}
			}
		});

		/**
		 * Block main until initialisation is complete
		 */
		synchronized (configLocation) {

			configLocation.wait();
		}
	}

	public IMemeboxContext getContext() {

		return context;
	}

	private IMemeboxContext configureContext(File prefsLocation) throws ParserConfigurationException, SAXException, IOException, MemeboxException {

		IMemeboxConfig prefs = MemeboxConfigReader.readPreferences(prefsLocation);
		StorageLocation location = new StorageLocation(prefs.getDatabase());

		int procs = Runtime.getRuntime().availableProcessors();
		int threads = procs < 4 ? 4 : procs;
		MemeboxThreadPool executor = new MemeboxThreadPool(threads);
		MemeboxContext memeboxContext = new MemeboxContext();
		MemeboxDirectoryWatcher directoryWatcher = new MemeboxDirectoryWatcher(memeboxContext, new MemeboxDirectoryWatcherListener(memeboxContext));

		memeboxContext.addComponent(executor);
		memeboxContext.addComponent(prefs);
		memeboxContext.addComponent(location);
		memeboxContext.addComponent(directoryWatcher);

		return memeboxContext;
	}
}
