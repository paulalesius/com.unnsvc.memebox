
package com.unnsvc.memebox.backup;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.unnsvc.memebox.IMemeboxContext;
import com.unnsvc.memebox.IStorageLocation;
import com.unnsvc.memebox.MemeboxException;
import com.unnsvc.memebox.config.IMemeboxConfig;
import com.unnsvc.memebox.config.MemeboxConfig;
import com.unnsvc.memebox.model.StorageLocation;

public class MemeboxBackup implements IMemeboxBackup {

	private IMemeboxContext context;

	public MemeboxBackup(IMemeboxContext context) {

		this.context = context;
	}

	@Override
	public void destroyComponent() throws MemeboxException {

	}

	@Override
	public File performBackup() throws MemeboxException {

		try {

			IMemeboxConfig config = context.getComponent(MemeboxConfig.class);
			IStorageLocation storageLocation = context.getComponent(StorageLocation.class);

			if (!config.getBackupLocation().exists()) {

				config.getBackupLocation().mkdirs();
			}

			Date now = Calendar.getInstance().getTime();
			DateFormat stampFormat = new SimpleDateFormat("yyMMddyyyyHHmmss");
			String stampStr = stampFormat.format(now);
			File backupFile = new File(config.getBackupLocation(), "backup_" + stampStr + ".zip");

			StringBuilder sb = new StringBuilder();
			sb.append("Test String");

			try (ZipOutputStream zipFile = new ZipOutputStream(new FileOutputStream(backupFile))) {

				addToZipFile(zipFile, "memebox.xml", config.serialise().getBytes());
				addToZipFile(zipFile, "memebox.properties", storageLocation.serialise());
			}

			return backupFile;
		} catch (IOException ioe) {

			throw new MemeboxException(ioe);
		}
	}

	private void addToZipFile(ZipOutputStream zipFile, String entryName, Properties props) throws IOException {

		try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			props.store(baos, null);
			addToZipFile(zipFile, entryName, baos.toByteArray());
		}
	}

	private void addToZipFile(ZipOutputStream zipFile, String entryName, byte[] data) throws IOException {

		ZipEntry e = new ZipEntry(entryName);
		zipFile.putNextEntry(e);

		zipFile.write(data, 0, data.length);
		zipFile.closeEntry();
	}
}
