package one.show.common;

import org.apache.commons.codec.binary.Base64;

public class Base64Util {

	private static byte[] Base64Decode(String base64String) {
		if (base64String == null) return null;

		return Base64.decodeBase64(base64String.getBytes());
	}

	public static String Base64Encode(byte[] originBytes) {
		if (originBytes == null) return null;

		return Base64.encodeBase64String(originBytes);
	}

}
