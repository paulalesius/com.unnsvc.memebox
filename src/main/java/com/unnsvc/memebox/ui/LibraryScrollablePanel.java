
package com.unnsvc.memebox.ui;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

import com.unnsvc.memebox.IMemeboxContext;
import com.unnsvc.memebox.MemeboxException;

/**
 * @TODO implement, this is a dummy implementation for PoC
 * @author noname
 *
 */
public class LibraryScrollablePanel implements ListModel<ImageIcon> {

	private int nr = 100;
	private ImageIcon dummy;
	private ImageIcon[] listData;

	public LibraryScrollablePanel(IMemeboxContext context) throws MemeboxException {

		listData = new ImageIcon[nr];
		for (int i = 0; i < nr; i++) {
			ImageIcon icon = createImageIcon("/META-INF/icons/dummy_128x128.jpg", "HERE IS ICON");
			// Image scaled = getScaledImage(icon.getImage(), 300, 300);
			// ImageIcon scaledIcon = new ImageIcon(scaled);
			JLabel l = new JLabel("HERE IS ICON", icon, JLabel.CENTER);
			l.setSize(300, 300);
			listData[i] = icon;
		}
	}

	private ImageIcon createImageIcon(String path, String description) throws MemeboxException {

		if (dummy == null) {
			try (InputStream inStream = getClass().getResourceAsStream(path)) {

				BufferedImage image = ImageIO.read(inStream);
				if (image != null) {

					Image resized = image.getScaledInstance(300, 300, Image.SCALE_SMOOTH);

					dummy = new ImageIcon(resized, description);
				} else {
					throw new MemeboxException("Not found: " + path);
				}
			} catch (IOException ioe) {

				throw new MemeboxException(ioe);
			}
		}

		return dummy;
	}

	/**
	 * This seemed much faster than BufferedImage.getScaledInstance
	 * 
	 * @param srcImg
	 * @param w
	 * @param h
	 * @return
	 */
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
	public ImageIcon getElementAt(int index) {

		return listData[index];
	}

	@Override
	public void addListDataListener(ListDataListener l) {

	}

	@Override
	public void removeListDataListener(ListDataListener l) {

	}
}
