
package com.unnsvc.memebox.ui;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

import com.unnsvc.memebox.MemeboxException;

/**
 * @TODO implement, this is a dummy implementation for PoC
 * @author noname
 *
 */
public class LibraryScrollablePanel implements ListModel {

	private static final long serialVersionUID = 1L;
	private int nr = 100;
	private Object[] listData;

	public LibraryScrollablePanel() throws MemeboxException {

		listData = new Object[nr];
		for (int i = 0; i < nr; i++) {
			ImageIcon icon = createImageIcon("/META-INF/icons/dummy640x480.jpg", "An icon");
			Image scaled = getScaledImage(icon.getImage(), 300, 300);
			ImageIcon scaledIcon = new ImageIcon(scaled);
			JLabel l = new JLabel("HERE IS ICON", icon, JLabel.CENTER);
			l.setSize(300, 300);
			listData[i] = l;
		}
	}

	private ImageIcon createImageIcon(String path, String description) throws MemeboxException {

		URL imgURL = getClass().getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL, description);
		} else {
			throw new MemeboxException("Not found: " + path);
		}
	}

	private Image getScaledImage(Image srcImg, int w, int h) {

		BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = resizedImg.createGraphics();

		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2.drawImage(srcImg, 0, 0, w, h, null);
		g2.dispose();

		return resizedImg;
	}

	@Override
	public int getSize() {

		return 100;
	}

	@Override
	public Object getElementAt(int index) {

		return listData[index];
	}

	@Override
	public void addListDataListener(ListDataListener l) {

	}

	@Override
	public void removeListDataListener(ListDataListener l) {

	}
}
