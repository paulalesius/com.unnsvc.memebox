
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
import com.unnsvc.memebox.config.MemeboxConfigurationReader;
import com.unnsvc.memebox.importer.MemeboxDirectoryWatcher;
import com.unnsvc.memebox.importer.MemeboxDirectoryWatcherListener;
import com.unnsvc.memebox.model.StorageLocation;
import com.unnsvc.memebox.ui.MainFrame;

public class Main {

	private static Logger log = LoggerFactory.getLogger(Main.class);
	public static IMemeboxContext context;

	public static void main(String... args) throws Exception {

		log.info("Initializing application");

		File prefs = new File("src/test/resources/memebox.xml");

		SwingUtilities.invokeLater(new Runnable() {

			public void run() {

				try {
					context = initialise(prefs);
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

					synchronized (prefs) {

						log.error("Initialized");
						prefs.notifyAll();
					}
				}
			}
		});

		/**
		 * Block main until initialisation is complete
		 */
		synchronized (prefs) {
			
			prefs.wait();
		}
	}

	private static IMemeboxContext initialise(File prefsLocation) throws ParserConfigurationException, SAXException, IOException, MemeboxException {

		IMemeboxConfig prefs = MemeboxConfigurationReader.readPreferences(prefsLocation);
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
