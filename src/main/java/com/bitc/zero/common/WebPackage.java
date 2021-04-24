package com.bitc.zero.common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class WebPackage {
	
	/**
	  SHA-256 (HASH)
	 */
	public static String Encrypt256(String value) {
		StringBuffer sb = new StringBuffer();
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-256");
			md.update(value.getBytes());

			byte[] msgb = md.digest();

			for (int i = 0; i < msgb.length; i++) {
				sb.append(Integer.toString((msgb[i] & 0xff) + 0x100, 16).substring(1));
			}

			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
	}
}
