
package com.unnsvc.memebox;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private JTabbedPane tabbedPane;

	public MainFrame() {

	}

	public static void main(String... args) throws Exception {

		MemeboxUtils.configureLookAndFeel();

		SwingUtilities.invokeLater(new Runnable() {

			public void run() {

				MainFrame frame = new MainFrame();

				// remove this later
				frame.setSize(new Dimension(1024, 768));

				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setLayout(new BorderLayout());
				frame.configureTabs();
				// frame.pack();
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
			}
		});
	}

	private void configureTabs() {

		tabbedPane = new JTabbedPane();

		JComponent mainTab = createMainTab();
		tabbedPane.addTab("Memebox", mainTab);

		JComponent optionsTab = createOptionsTab();
		tabbedPane.addTab("Options", optionsTab);

		setLayout(new BorderLayout());
		add(tabbedPane);
	}

	private JComponent createMainTab() {

		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		// weight to 100% so we fill width with gbc.fill
		gbc.weightx = 1;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.CENTER;

		JTextField searchField = new JTextField();
		searchField.setMinimumSize(searchField.getPreferredSize());
		// @TODO grey out on focus
		searchField.setBackground(Color.WHITE);
		searchField.setEditable(true);
		searchField.setText("test");
		panel.add(searchField, gbc);

		gbc.gridy++;
		JButton button = new JButton();
		button.setText("Button");
		panel.add(button, gbc);

		return panel;
	}

	private JComponent createOptionsTab() {

		return new JPanel(new GridBagLayout());
	}
}
