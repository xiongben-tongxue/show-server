package one.show.common;

import java.security.Key;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
/**
 * DES加解密
 * @author Haliaeetus leucocephalus
 *
 */
public class DESUtil {
	
	private static String DEFAULTKEY = "DEFAULTKEY";
	
	private Cipher encryptCipher = null;
	
	private Cipher decryptCipher = null;
	
	/** 
	  * 将byte数组转换为表示16进制值的字符串， 如：byte[]{8,18}转换为：0813， 和public static byte[] 
	  * hexStr2ByteArr(String strIn) 互为可逆的转换过程 
	  *  
	  * @param arrB 需要转换的byte数组 
	  * @return 转换后的字符串 
	  * @throws Exception 本方法不处理任何异常，所有异常全部抛出 
	  */ 
	public static String byteArr2HexStr(byte[] arrB) throws Exception{
		int iLen = arrB.length;  
		// 每个byte用两个字符才能表示，所以字符串的长度是数组长度的两倍  
		StringBuffer sb = new StringBuffer(iLen * 2);  
		for(int i=0;i<iLen;i++){  
			int intTmp = arrB[i];  
			// 把负数转换为正数  
			while (intTmp < 0) {  
				intTmp = intTmp + 256;  
			}  
			// 小于0F的数需要在前面补0  
			if (intTmp < 16) {  
				sb.append("0");  
			}  
			sb.append(Integer.toString(intTmp, 16));  
		}  
		return sb.toString();
	}
	/** 
	  * 将表示16进制值的字符串转换为byte数组， 和public static String byteArr2HexStr(byte[] arrB) 
	  * 互为可逆的转换过程 
	  *  
	  * @param strI 需要转换的字符串 
	  * @return 转换后的byte数组 
	  * @throws Exception 本方法不处理任何异常，所有异常全部抛出
	  */  
	public static byte[] hexStr2ByteArr(String str) throws Exception{ 
		byte[] arrB = str.getBytes();  
		int iLen = arrB.length;  
		  
		// 两个字符表示一个字节，所以字节数组长度是字符串长度除以2  
		byte[] arrOut = new byte[iLen / 2];  
		for(int i=0;i<iLen;i=i + 2){  
			String strTmp = new String(arrB, i, 2);  
		    arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);  
		}  
		return arrOut;
	}
	/** 
	  * 指定密钥构造方法 
	  * @throws Exception
	  */ 
	public DESUtil() throws Exception{
		this(DEFAULTKEY);
	}
	/** 
	  * 指定密钥构造方法 
	  * @param keyStr 指定的密钥
	  * @throws Exception 
	  */ 
	public DESUtil(String keyStr) throws Exception{
		Security.addProvider(new com.sun.crypto.provider.SunJCE());
		Key key = getKey(keyStr.getBytes());

		encryptCipher = Cipher.getInstance("DES");
		encryptCipher.init(Cipher.ENCRYPT_MODE, key);
		
		decryptCipher = Cipher.getInstance("DES");
		decryptCipher.init(Cipher.DECRYPT_MODE, key);
		
	}
	/** 
	  * 加密字节数组 
	  * @param arrB 需加密的字节数组 
	  * @return 加密后的字节数组 
	  * @throws Exception 
	  */
	public byte[] encrypt(byte[] arr) throws Exception{
		return encryptCipher.doFinal(arr);
	}
	/** 
	  * 加密字符串
	  * @param strIn 需加密的字符串 
	  * @return 加密后的字符串 
	  * @throws Exception 
	  */
	public String encrypt(String str) throws Exception{
		return byteArr2HexStr(encrypt(str.getBytes()));
	}
	/** 
	  * 解密字节数组
	  * @param arrB 需解密的字节数组 
	  * @return 解密后的字节数组 
	  * @throws Exception 
	  */
	public byte[] decrypt(byte[] arr) throws Exception{
		return decryptCipher.doFinal(arr);
	}
	/** 
	  * 解密字符串 
	  * @param strIn 需解密的字符串 
	  * @return 解密后的字符串 
	  * @throws Exception 
	  */
	public String decrypt(String str) throws Exception{
		return new String(decrypt(hexStr2ByteArr(str)));
	}
	/** 
	  * 从指定字符串生成密钥，密钥所需的字节数组长度为8位 不足8位时后面补0，超出8位只取前8位  
	  * @param arrBTmp 构成该字符串的字节数组 
	  * @return 生成的密钥 
	  * @throws java.lang.Exception 
	  */
	private Key getKey(byte[] arrBTmp) throws Exception{
		//创建一个空的8位字节数组（默认值为0）
		byte[] arrB = new byte[8];
		//将原始字节数组转换为8位
		for(int i=0;i<arrBTmp.length && i<arrB.length;i++){
			arrB[i] = arrBTmp[i];
		}
		//生成密钥
		Key key = new SecretKeySpec(arrB, "DES");
		return key;
	}
	
	
	/**
	 * 创建weipai token, token: uid|time
	 * @param uid	用户id
	 * @return
	 * @throws Exception
	 */
	public String encryptToken(String uid) throws Exception{
		int now = (int)(System.currentTimeMillis()/1000);
		String token = encrypt(uid+"|"+now);
		return token;
	}
	
	
	
	public static void main(String[] args){
		try{
			DESUtil du = new DESUtil();
			

			String token = du.encryptToken("741624247214022656");

			System.out.println("加密："+token);
			
			System.out.println("解密："+du.decrypt(token));
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
