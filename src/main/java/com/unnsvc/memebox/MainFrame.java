
package com.unnsvc.memebox;

import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	public MainFrame() {

	}

	public static void main(String... args) throws Exception {

		MemeboxUtils.configureLookAndFeel();

		SwingUtilities.invokeLater(new Runnable() {

			public void run() {

				MainFrame frame = new MainFrame();
				frame.setSize(new Dimension(1024, 768));
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.configureTabs();
				frame.setVisible(true);
			}
		});
	}

	private void configureTabs() {

		JTabbedPane tabbedPane = new JTabbedPane();
		JComponent mainTab = createMainTab();
		tabbedPane.addTab("Memebox", mainTab);
		JComponent optionsTab = createOptionsTab();
		tabbedPane.addTab("Options", optionsTab);
		add(tabbedPane);
	}

	private JComponent createOptionsTab() {

		return new JPanel();
	}

	private JComponent createMainTab() {

		return new JPanel();
	}
}
