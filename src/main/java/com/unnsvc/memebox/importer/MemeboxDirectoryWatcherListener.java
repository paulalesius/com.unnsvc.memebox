
package com.unnsvc.memebox.importer;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

import javax.imageio.ImageIO;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.unnsvc.memebox.IMemeboxContext;
import com.unnsvc.memebox.IMetadataStore;
import com.unnsvc.memebox.MemeboxConstants;
import com.unnsvc.memebox.MemeboxException;
import com.unnsvc.memebox.config.IMemeboxConfig;
import com.unnsvc.memebox.config.IThumbnailsConfig;
import com.unnsvc.memebox.config.MemeboxConfig;
import com.unnsvc.memebox.metadata.MetadataStore;

/**
 * @TODO This class should really handoff to executor service to process them
 *       all in parallel because we perform a very slow operation to hash/store
 *       metadata/etc
 * 
 * @author noname
 *
 */
public class MemeboxDirectoryWatcherListener implements IMemeboxDirectoryWatcherListener {

	private Logger log = LoggerFactory.getLogger(getClass());
	private IMemeboxContext context;
	private IMemeboxConfig config;
	private IMetadataStore storage;

	public MemeboxDirectoryWatcherListener(IMemeboxContext context) {

		this.context = context;
		this.config = context.getComponent(MemeboxConfig.class);
		this.storage = context.getComponent(MetadataStore.class);
	}

	@Override
	public void onInitialise(boolean autoimport, Path path, ESupportedExt ext) throws MemeboxException {

		File file = path.toFile();
		String digestString = toDigestString(file);
		log.trace("On initialise " + path + " ext: " + ext + " autoimport: " + autoimport + " hash: " + digestString);

		File imageStore = config.getImageStorageLocation();
		if (!imageStore.exists()) {
			imageStore.mkdirs();
		}
		File imageStoreLocation = new File(imageStore, digestString + "." + ext.getDefaultExt());

		File thumbStore = config.getThumbnailsStorageLocation();
		if (!thumbStore.exists()) {
			thumbStore.mkdirs();
		}
		File thumbStoreLocation = new File(thumbStore, digestString + "." + MemeboxConstants.FORMAT_THUMBNAILS.toLowerCase());

		if (!imageStoreLocation.exists()) {
			try {
				log.info("Importing: " + path + " to " + imageStoreLocation);

				BufferedImage image = ImageIO.read(file);

				/**
				 * @TODO make a data structure that can handle native types?
				 */
				storage.setProperty(digestString, MemeboxConstants.META_ORIGINAL_NAME, file.getName());
				storage.setProperty(digestString, MemeboxConstants.META_ORIGINAL_WIDTH, image.getWidth() + "");
				storage.setProperty(digestString, MemeboxConstants.META_ORIGINAL_HEIGHT, image.getHeight() + "");

				FileUtils.copyFile(path.toFile(), imageStoreLocation);
				IThumbnailsConfig thumbConfig = config.getThumbnailsConfig();

				BufferedImage thumbnail = getScaledImage(image, thumbConfig.getWidth(), thumbConfig.getHeight());
				storage.setProperty(digestString, MemeboxConstants.META_THUMBNAIL_WIDTH, thumbConfig.getWidth() + "");
				storage.setProperty(digestString, MemeboxConstants.META_THUMBNAIL_HEIGHT, thumbConfig.getHeight() + "");
				ImageIO.write(thumbnail, MemeboxConstants.FORMAT_THUMBNAILS, thumbStoreLocation);

			} catch (IOException ioe) {
				throw new MemeboxException(ioe);
			}
		} else {

			log.warn("Exists already: " + imageStoreLocation);
			/**
			 * It exists in already in store so we just remove the original?
			 * This is a dangerous operation so think carefully until production
			 */
		}
	}

	@Override
	public void onEntryModify(boolean autoimport, Path path, ESupportedExt ext) throws MemeboxException {

		log.trace("On modify " + path + " ext: " + ext + " autoimport: " + autoimport);
	}

	private String toDigestString(File file) throws MemeboxException {

		try (InputStream is = new FileInputStream(file)) {

			return DigestUtils.sha1Hex(is);
		} catch (IOException e) {

			throw new MemeboxException(e);
		}
	}

	/**
	 * @TODO preserve aspect
	 * 
	 * @param srcImg
	 * @param w
	 * @param h
	 * @return
	 */
	private BufferedImage getScaledImage(BufferedImage srcImg, int w, int h) {

		BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = resizedImg.createGraphics();

		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2.drawImage(srcImg, 0, 0, w, h, null);
		g2.dispose();

		return resizedImg;
	}

}
