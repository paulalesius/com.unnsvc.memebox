
package com.unnsvc.memebox.ui;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.ExecutorService;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

import com.unnsvc.memebox.IMemeboxContext;
import com.unnsvc.memebox.IStorageLocation;
import com.unnsvc.memebox.MemeboxException;
import com.unnsvc.memebox.MemeboxThreadPool;
import com.unnsvc.memebox.storage.StorageLocation;

/**
 * @TODO implement, this is a dummy implementation for PoC
 * @author noname
 *
 */
public class LibraryScrollablePanel implements ListModel<ImageIcon> {

	private ImageIcon dummy;
	private ImageIcon[] listData;
	private IMemeboxContext context;

	public LibraryScrollablePanel(IMemeboxContext context) throws MemeboxException {

		ExecutorService executor = context.getComponent(MemeboxThreadPool.class);
		IStorageLocation storageLocation = context.getComponent(StorageLocation.class);
		Map<String, Map<String, String>> metadata = storageLocation.getMetadata();
		listData = new ImageIcon[metadata.size()];
		this.context = context;

		for (int i = 0; i < metadata.size(); i++) {

			/**
			 * For each image, create a dummy thumbnail and a worker thread that
			 * loads the image in the background
			 */
			ImageIcon icon = createImageIcon("/META-INF/icons/dummy_128x128.jpg", "HERE IS ICON");
			listData[i] = icon;

			executor.submit(new LibraryThumbnailWorker(context, this));
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

	@Override
	public int getSize() {

		return context.getComponent(StorageLocation.class).getMetadata().size();
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

/**
 * This seemed faster than BufferedImage.getScaledInstance
 * 
 * @param srcImg
 * @param w
 * @param h
 * @return
 */
// private Image getScaledImage(Image srcImg, int w, int h) {
//
// BufferedImage resizedImg = new BufferedImage(w, h,
// BufferedImage.TYPE_INT_ARGB);
// Graphics2D g2 = resizedImg.createGraphics();
//
// g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
// RenderingHints.VALUE_INTERPOLATION_BILINEAR);
// g2.drawImage(srcImg, 0, 0, w, h, null);
// g2.dispose();
//
// return resizedImg;
// }