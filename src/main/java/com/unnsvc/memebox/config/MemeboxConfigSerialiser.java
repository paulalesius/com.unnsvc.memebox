
package com.unnsvc.memebox.config;

public class MemeboxConfigSerialiser {

	public static final String NL = System.getProperty("line.separator");
	private IMemeboxConfig config;

	public MemeboxConfigSerialiser(IMemeboxConfig config) {

		this.config = config;
	}

	private String serialiseConfig(IMemeboxConfig config) {

		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append(NL);
		sb.append(
				"<memebox xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns=\"urn:memebox\" xsi:schemaLocation=\"urn:memebox http://schema.unnsvc.com/memebox/memebox.xsd\">")
				.append(NL);

		sb.append("\t<storage location=\"" + config.getStorageLocation() + "\" />").append(NL);
		sb.append("\t<database location=\"" + config.getDatabaseFile() + "\" />").append(NL);
		sb.append("\t<backup location=\"" + config.getBackupLocation() + "\" />").append(NL);

		for (WatchLocation watchLocation : config.getWatchLocations()) {

			sb.append("\t<watch location=\"" + watchLocation.getLocation() + "\" autoimport=\"" + watchLocation.getAutoimport() + "\" />").append(NL);
		}

		sb.append("</memebox>").append(NL);
		return sb.toString();
	}

	public String getSerialised() {

		return serialiseConfig(config);
	}

}
