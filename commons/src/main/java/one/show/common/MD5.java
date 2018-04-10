package one.show.common;

import java.security.MessageDigest;

public class MD5 {
	/**
	 * 编码
	 * @param sText 原始文本
	 * @param sCharsetName 编码名称
	 */
	public static String Encode(String sText, String sCharsetName) {
		String s = null;
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9','a', 'b', 'c', 'd', 'e', 'f' };
		try {
			byte[] source = sText.getBytes(sCharsetName);

			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(source);
			byte tmp[] = md.digest();

			char str[] = new char[16 * 2];
			int k = 0;
			for (int i = 0; i < 16; i++) {
				byte byte0 = tmp[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}

			s = new String(str);
		} catch (Exception e){
			
		}
		return s;
	}
	
	
	/** 32位加密
	 * @param plainText
	 * @return
	 */
	public final static String md5(String plainText) {

		// 返回字符串
		String md5Str = null;
		try {
		// 操作字符串
		StringBuffer buf = new StringBuffer();


		MessageDigest md = MessageDigest.getInstance("MD5");

		// 添加要进行计算摘要的信息,使用 plainText 的 byte 数组更新摘要。
		md.update(plainText.getBytes());

		// 计算出摘要,完成哈希计算。
		byte b[] = md.digest();
		int i;

		for (int offset = 0; offset < b.length; offset++) {

		i = b[offset];

		if (i < 0) {
		i += 256;
		}

		if (i < 16) {
		buf.append("0");
		}

		// 将整型 十进制 i 转换为16位，用十六进制参数表示的无符号整数值的字符串表示形式。
		buf.append(Integer.toHexString(i));

		}

		// 32位的加密
		md5Str = buf.toString();

		// 16位的加密
		// md5Str = buf.toString().md5Strstring(8,24);

		} catch (Exception e) {
		e.printStackTrace();
		}
		return md5Str;
	}


		
	public static void main(String[] args) {
		System.out.println(md5("123456"));
	}

}