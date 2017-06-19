
package com.unnsvc.memebox.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.unnsvc.memebox.IMemeboxComponent;
import com.unnsvc.memebox.IMemeboxContext;
import com.unnsvc.memebox.MemeboxException;

public class MainFrame extends JFrame implements IMemeboxComponent {

	private static final long serialVersionUID = 1L;
	private Logger log = LoggerFactory.getLogger(MainFrame.class);
	private IMemeboxContext context;

	private JTabbedPane tabbedPane;

	public MainFrame(IMemeboxContext context) {

		this.context = context;
	}

	public void createUi() throws MemeboxException {

		tabbedPane = new JTabbedPane();

		JComponent mainTab = createMainTab();
		tabbedPane.addTab("Library", mainTab);

		JComponent configurationTab = createConfigurationTab();
		tabbedPane.addTab("Configuration", configurationTab);

		setLayout(new BorderLayout());
		add(tabbedPane, BorderLayout.CENTER);

		JComponent statusBar = createStatusBar();
		add(statusBar, BorderLayout.SOUTH);
	}

	private JComponent createStatusBar() {

		JPanel statusPanel = new JPanel();
		statusPanel.setLayout(new BorderLayout());
		statusPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));

		JLabel statusLabel = new JLabel("Loading...");
		Border border = statusLabel.getBorder();
		Border margin = new EmptyBorder(5, 5, 5, 5);
		statusLabel.setBorder(new CompoundBorder(border, margin));
		statusPanel.add(statusLabel, BorderLayout.CENTER);

		MemeboxProgressBar progressBar = new MemeboxProgressBar();
		progressBar.setIndeterminate(true);
		progressBar.setStringPainted(false);
		statusPanel.add(progressBar, BorderLayout.EAST);
		context.addComponent(progressBar);

		return statusPanel;
	}

	private JComponent createMainTab() throws MemeboxException {

		JPanel panel = new JPanel(new BorderLayout());

		JComponent searchFieldComponent = createSearchFieldComponent();
		panel.add(searchFieldComponent, BorderLayout.NORTH);

		JComponent libraryContent = createLibraryContent();
		panel.add(libraryContent, BorderLayout.CENTER);

		return panel;
	}

	private JComponent createLibraryContent() throws MemeboxException {

		LibraryScrollablePanel libraryData = new LibraryScrollablePanel();
		JList<ImageIcon> list = new JList<ImageIcon>(libraryData);
		// @TODO a more pleasing lighter dark or something
		Color color = UIManager.getColor("Panel.background");
		list.setBackground(color);

		list.setLayoutOrientation(JList.VERTICAL_WRAP);
		JScrollPane scrollPane = new JScrollPane(list);

		list.addComponentListener(new ComponentAdapter() {

			@Override
			public void componentResized(ComponentEvent e) {

				fixRowCountForVisibleColumns(list);
			}
		});

		// after GUI is created
		SwingUtilities.invokeLater(new Runnable() {

			public void run() {

				fixRowCountForVisibleColumns(list);
			}
		});

		return scrollPane;
	}

	private JComponent createSearchFieldComponent() {

		String placeholderText = "Filter tags/metadata/exif, use regex ...";

		JTextField searchField = new JTextField();
		searchField.setMinimumSize(searchField.getPreferredSize());
		// @TODO grey out on focus
		searchField.setBackground(Color.WHITE);
		searchField.setForeground(Color.GRAY);
		searchField.setEditable(true);
		searchField.setText(placeholderText);
		searchField.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {

				if (searchField.getText().equals(placeholderText)) {
					searchField.setText(null);
				}
				searchField.setForeground(null);
			}

			@Override
			public void focusLost(FocusEvent e) {

				if (searchField.getText().isEmpty()) {
					searchField.setText(placeholderText);
				}
				searchField.setForeground(Color.GRAY);
			}
		});
		return searchField;
	}

	/**
	 * 
	 * @param list
	 */
	private static void fixRowCountForVisibleColumns(JList list) {

		int nCols = computeVisibleColumnCount(list);
		int nItems = list.getModel().getSize();

		// Compute the number of rows that will result in the desired number of
		// columns
		int nRows = nItems / nCols;
		if (nItems % nCols > 0)
			nRows++;
		list.setVisibleRowCount(nRows);
	}

	/**
	 * 
	 * @param list
	 * @return
	 */
	private static int computeVisibleColumnCount(JList list) {

		// It's assumed here that all cells have the same width. This method
		// could be modified if this assumption is false. If there was cell
		// padding, it would have to be accounted for here as well.
		int cellWidth = list.getCellBounds(0, 0).width;
		int width = list.getVisibleRect().width;
		return width / cellWidth;
	}

	private JComponent createConfigurationTab() {

		return new JPanel(new GridBagLayout());
	}

	@Override
	public String getIdentifier() {

		return MainFrame.class.getName();
	}
}
