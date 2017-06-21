
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
import com.unnsvc.memebox.metadata.MetadataStore;
import com.unnsvc.memebox.ui.MainFrame;

public class Main {

	private Logger log = LoggerFactory.getLogger(Main.class);
	public IMemeboxContext context;

	public static void main(String... args) throws Exception {

		IDistributionConfiguration distConfig = new DistributionConfiguration();
		File configLocation = new File(distConfig.getProperty(DistributionConfiguration.PROP_DEFAULT_CONFIG));

		if (!configLocation.exists()) {
			throw new MemeboxException("memebox.xml not found: " + configLocation);
		}

		Main main = new Main();
		main.startup(distConfig, configLocation);
	}

	public void startup(IDistributionConfiguration distConfig, File configLocation) throws Exception {

		log.info("Starting MemeBox - " + distConfig.getProperty(DistributionConfiguration.PROP_PROFILE));

		SwingUtilities.invokeAndWait(new Runnable() {

			public void run() {

				try {
					log.info("Starting application with config: " + configLocation);

					context = configureContext(distConfig, configLocation);
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

					new Exception("Epic fail", throwable);
				}
			}
		});
	}

	public IMemeboxContext getContext() {

		return context;
	}

	/**
	 * Ordering in how we put components in context is important here because
	 * components initialised later will want to have other components in
	 * context, another option is SwingUtilities.invokeLater but this is
	 * probably a bad idea
	 * 
	 * @param distributionProps
	 */
	private IMemeboxContext configureContext(IDistributionConfiguration distConfig, File configLocation)
			throws ParserConfigurationException, SAXException, IOException, MemeboxException {

		MemeboxContext memeboxContext = new MemeboxContext(distConfig);

		MemeboxConfigReader configReader = new MemeboxConfigReader(configLocation);
		IMemeboxConfig prefs = configReader.readConfiguration();
		memeboxContext.addComponent(prefs);

		MetadataStore location = new MetadataStore(prefs.getDatabaseFile());
		memeboxContext.addComponent(location);

		int procs = Runtime.getRuntime().availableProcessors();
		int threads = procs < 4 ? 4 : procs;
		MemeboxThreadPool executor = new MemeboxThreadPool(threads);
		memeboxContext.addComponent(executor);

		MemeboxDirectoryWatcher directoryWatcher = new MemeboxDirectoryWatcher(memeboxContext, new MemeboxDirectoryWatcherListener(memeboxContext));
		memeboxContext.addComponent(directoryWatcher);

		return memeboxContext;
	}
}
