package com.nari.util;

import java.security.NoSuchAlgorithmException;

/**
 * @author 陈建国
 *
 * @创建时间:2009-12-14 上午10:02:27
 *
 * @类描述: 密码工具类
 *	
 */
public class PasswordUtils {

	/**
	 * @描述 二进制转字符串
	 * @param b
	 * @return
	 */
	private static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
		}
		return hs.toUpperCase();
	}
	
	/**
	 * @描述 MD5加密 不可逆
	 * @param md5input
	 * @return
	 */
	public static String md5(String md5input) {
		byte[] input = md5input.getBytes();
		java.security.MessageDigest alg;
		try {
			alg = java.security.MessageDigest
					.getInstance("MD5"); // or "SHA-1"
			alg.update(input);
			byte[] digest = alg.digest();
			return byte2hex(digest);
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
		
	}
}
