
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

import com.unnsvc.memebox.model.StorageLocation;
import com.unnsvc.memebox.preferences.IMemeboxPreferences;
import com.unnsvc.memebox.preferences.MemeboxPreferenceReader;
import com.unnsvc.memebox.ui.MainFrame;

public class Main {

	private static Logger log = LoggerFactory.getLogger(Main.class);

	public static void main(String... args) throws Exception {

		log.info("Initializing application");

		File prefs = new File("src/test/resources/memebox.xml");

		SwingUtilities.invokeLater(new Runnable() {

			public void run() {

				try {
					final IMemeboxContext context = initialise(prefs);
					MemeboxUtils.configureLookAndFeel();

					MainFrame frame = new MainFrame(context);
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

							context.destroy();
						}
					});

					frame.setVisible(true);

				} catch (Throwable throwable) {

					log.error("Epic fail", throwable);
				}
			}
		});
	}

	private static IMemeboxContext initialise(File prefsLocation) throws ParserConfigurationException, SAXException, IOException, MemeboxException {

		IMemeboxPreferences prefs = MemeboxPreferenceReader.readPreferences(prefsLocation);
		StorageLocation location = new StorageLocation(prefs.getDatabase());

		int procs = Runtime.getRuntime().availableProcessors();
		int threads = procs < 4 ? 4 : procs;
		MemeboxThreadPool executor = new MemeboxThreadPool(threads);
		MemeboxContext memeboxContext = new MemeboxContext();

		memeboxContext.addComponent(executor);
		memeboxContext.addComponent(prefs);
		memeboxContext.addComponent(location);

		return memeboxContext;
	}
}
