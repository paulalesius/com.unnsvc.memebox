
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

import com.unnsvc.memebox.model.IPersistenceManager;
import com.unnsvc.memebox.model.PersistenceManager;
import com.unnsvc.memebox.preferences.IMemeboxPreferences;
import com.unnsvc.memebox.preferences.MemeboxPreferenceReader;
import com.unnsvc.memebox.ui.MainFrame;

public class Main {

	private static Logger log = LoggerFactory.getLogger(MainFrame.class);

	public static void main(String... args) throws Exception {

		SwingUtilities.invokeLater(new Runnable() {

			public void run() {

				try {
					final IMemeboxContext context = initialise();
					MemeboxUtils.configureLookAndFeel();

					MainFrame frame = new MainFrame(context);
					frame.setTitle("MemeBox");
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

	private static IMemeboxContext initialise() throws ParserConfigurationException, SAXException, IOException {

		IMemeboxPreferences prefs = MemeboxPreferenceReader.readPreferences(new File("src/test/resources/memebox.xml"));

		System.setProperty("derby.system.home", prefs.getDatabase().getAbsolutePath());

		IPersistenceManager persistence = PersistenceManager.INSTANCE;
		return new MemeboxContext(prefs, persistence);
	}
}
