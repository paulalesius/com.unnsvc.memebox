
package com.unnsvc.memebox.ui;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.unnsvc.memebox.IMemeboxContext;
import com.unnsvc.memebox.IStorageLocation;
import com.unnsvc.memebox.MemeboxConstants;
import com.unnsvc.memebox.MemeboxException;
import com.unnsvc.memebox.MemeboxThreadPool;
import com.unnsvc.memebox.config.IMemeboxConfig;
import com.unnsvc.memebox.config.MemeboxConfig;
import com.unnsvc.memebox.storage.StorageLocation;

/**
 * @TODO implement, this is a dummy implementation for PoC
 * @author noname
 *
 */
public class LibraryScrollablePanel implements ListModel<ImageIcon> {

	private Logger log = LoggerFactory.getLogger(getClass());
	private ImageIcon dummy;
	private ImageIcon[] listData;
	private IMemeboxContext context;

	public LibraryScrollablePanel(IMemeboxContext context) throws MemeboxException {

		ExecutorService executor = context.getComponent(MemeboxThreadPool.class);
		IMemeboxConfig config = context.getComponent(MemeboxConfig.class);
		IStorageLocation storageLocation = context.getComponent(StorageLocation.class);
		listData = new ImageIcon[storageLocation.getMetadata().size()];
		this.context = context;
		
		Map<String, Map<String, String>> metadata = new HashMap<String, Map<String, String>>();
		log.debug("Metadata size: " + metadata.size());

		int idx = 0;
		for (String hash : metadata.keySet()) {

			/**
			 * Configure the default thumb in the library, and have execution
			 * threads load the thumbs in the background
			 */
			ImageIcon icon = createImageIcon("/META-INF/icons/dummy_128x128.jpg", "HERE IS ICON");
			listData[idx] = icon;

			File thumbLocation = new File(config.getThumbnailsStorageLocation(), hash + "." + MemeboxConstants.FORMAT_THUMBNAILS.toLowerCase());
			executor.submit(new LibraryThumbnailWorker(context, this, thumbLocation, idx));

			idx++;
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
