package fsm.oned.comm.util;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fsm.oned.comm.exception.UserException;

/*
* com_mchn_user 사용자 패스워드 암호화
*/
public class CamtCrypto {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CamtCrypto.class);
	
	/**
	 * 파일암호화에 쓰이는 버퍼 크기 지정
	 */
	private final int kBufferSize = 8192;

	private java.security.Key key = null;

	// public static final String defaultkeyfileurl =
	// "D:/JEUS5.0/camtdefaultkey.key"; //로컬
	private final String defaultkeyfileurl = EgovProperties.RELATIVE_PATH_PREFIX.substring(0, EgovProperties.RELATIVE_PATH_PREFIX.lastIndexOf("classes/"))+"/cryptoKey/camtdefaultkey.key";

	// public static final String defaultkeyfileurl =
	// "/JEUS/jeus5/camtdefaultkey.key"; //운영서버

	/**
	 * 비밀키 생성메소드
	 * 
	 * @return void
	 * @exception java.io.IOException,NoSuchAlgorithmException
	 */
	private java.io.File makekey() throws java.io.IOException, NoSuchAlgorithmException {
		return makekey(defaultkeyfileurl);
	}

	private java.io.File makekey(String filename) throws java.io.IOException, NoSuchAlgorithmException {
		java.io.File tempfile = new java.io.File(".", filename);
		javax.crypto.KeyGenerator generator = javax.crypto.KeyGenerator.getInstance("DES");
		generator.init(new java.security.SecureRandom());
		java.security.Key key = generator.generateKey();
		java.io.ObjectOutputStream out = new java.io.ObjectOutputStream(new java.io.FileOutputStream(tempfile));
		out.writeObject(key);
		out.close();
		return tempfile;
	}

	/**
	 * 지정된 비밀키를 가지고 오는 메서드
	 * 
	 * @return Key 비밀키 클래스
	 * @exception Exception
	 */
	private java.security.Key getKey() throws Exception {
		if (key != null) {
			return key;
		} else {
			return getKey(defaultkeyfileurl);
		}
	}

	private java.security.Key getKey(String fileurl) throws Exception {
		if (key == null) {
			java.io.File file = new java.io.File(fileurl);
			if (!file.exists()) {
				file = makekey();
			}
			if (file.exists()) {
				try (java.io.ObjectInputStream in = new java.io.ObjectInputStream(new java.io.FileInputStream(fileurl))) {
					key = (java.security.Key) in.readObject();
				} catch (IOException e) {
					throw new Exception("관리자에게 문의바랍니다.", e);
				}
				
			} else {
				throw new Exception("암호키객체를 생성할 수 없습니다.");
			}
		}
		return key;
	}

	/**
	 * 문자열 대칭 암호화
	 * 
	 * @param ID
	 *            비밀키 암호화를 희망하는 문자열
	 * @return String 암호화된 ID
	 * @exception Exception
	 */
	public String encrypt(String ID) {
		if (ID == null || ID.length() == 0) {
			return "";
		}
		try {
			javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance("DES/ECB/PKCS5Padding");
			cipher.init(javax.crypto.Cipher.ENCRYPT_MODE, getKey());
			String amalgam = ID;
	
			byte[] inputBytes1 = amalgam.getBytes("UTF8");
			byte[] outputBytes1 = cipher.doFinal(inputBytes1);
			sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
			
			return encoder.encode(outputBytes1);
		}catch(NullPointerException e) {
			LOGGER.error("ID 암호화 중 에러 발생", e);
			return null;
		}catch(Exception e) {
			LOGGER.error("ID 암호화 중 에러 발생", e);
			return null;
		}

	}

	/**
	 * 문자열 대칭 복호화
	 * 
	 * @param codedID
	 *            비밀키 복호화를 희망하는 문자열
	 * @return String 복호화된 ID
	 * @exception Exception
	 */
	public String decrypt(String codedID) throws Exception {
		if (codedID == null || codedID.length() == 0) {
			return "";
		}
		try {
			javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance("DES/ECB/PKCS5Padding");
			cipher.init(javax.crypto.Cipher.DECRYPT_MODE, getKey());
			sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
	
			byte[] inputBytes1 = decoder.decodeBuffer(codedID);
			byte[] outputBytes2 = cipher.doFinal(inputBytes1);
	
			String strResult = new String(outputBytes2, "UTF8");
			return strResult;
		}catch(NullPointerException e) {
			LOGGER.error("ID 복호화 중 에러 발생", e);
			return null;
		}catch(Exception e) {
			LOGGER.error("ID 복호화 중 에러 발생", e);
			return null;
		}
	}

	/**
	 * 파일 대칭 암호화
	 * 
	 * @param infile
	 *            암호화을 희망하는 파일명
	 * @param outfile
	 *            암호화된 파일명
	 * @exception Exception
	 */
	public void encryptFile(String infile, String outfile) throws Exception {
		javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance("DES/ECB/PKCS5Padding");
		cipher.init(javax.crypto.Cipher.ENCRYPT_MODE, getKey());

		try (java.io.FileInputStream in = new java.io.FileInputStream(infile);
			 java.io.FileOutputStream fileOut = new java.io.FileOutputStream(outfile);
			 javax.crypto.CipherOutputStream out = new javax.crypto.CipherOutputStream(fileOut, cipher)) {

			 byte[] buffer = new byte[kBufferSize];
			 int length;
			 while ((length = in.read(buffer)) != -1) {
				 	out.write(buffer, 0, length);
			 }
		} catch (IOException e) {
        throw new Exception("관리자에게 문의바랍니다.", e);
		}
	}

	/**
	 * 파일 대칭 복호화
	 * 
	 * @param infile
	 *            복호화을 희망하는 파일명
	 * @param outfile
	 *            복호화된 파일명
	 * @exception Exception
	 */
	public void decryptFile(String infile, String outfile) throws Exception {
		javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance("DES/ECB/PKCS5Padding");
		cipher.init(javax.crypto.Cipher.DECRYPT_MODE, getKey());

		try (java.io.FileInputStream in = new java.io.FileInputStream(infile);
			 java.io.FileOutputStream fileOut = new java.io.FileOutputStream(outfile);
			 javax.crypto.CipherOutputStream out = new javax.crypto.CipherOutputStream(fileOut, cipher)) {
			
			 byte[] buffer = new byte[kBufferSize];
		     int length;
		     while ((length = in.read(buffer)) != -1) {
			        out.write(buffer, 0, length);
		     }
		}catch (IOException e) {
		throw new Exception("관리자에게 문의바랍니다.", e);
		}
	}

}