package fsm.oned.comm.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.stereotype.Component;

@Component("jjCipher")
public class JJCipher {
	private static final String JJCIPHER_KEY = "SklOSklOIFNZU1RFTQ==";
	private static final String JJCIPHER_ENC_TYPE = "Qmxvd2Zpc2g=";
	
	public String doEncrypt(String msg) throws Exception {
		String tmsg = "";
		tmsg = msg;
		int d = 8 - tmsg.length() % 8;
		for (int j = 0; j < d; j++) {
			tmsg = tmsg + " ";
		}
		String keyText = doDecodeBase64(JJCIPHER_KEY);
		String encType = doDecodeBase64(JJCIPHER_ENC_TYPE);
		
		SecretKey key = new SecretKeySpec(keyText.getBytes(), encType);
		Cipher c = Cipher.getInstance(encType);
		c.init(1, key);
		byte[] ciphertext = c.doFinal(tmsg.getBytes());
		String outval = Base64.encode(ciphertext);
		return outval.trim();
	}
		
	public String doDecrypt(String msg) throws Exception {
		String keyText = doDecodeBase64(JJCIPHER_KEY);
		String encType = doDecodeBase64(JJCIPHER_ENC_TYPE);
		
		SecretKey key = new SecretKeySpec(keyText.getBytes(), encType);
		Cipher c = Cipher.getInstance(encType);
		c.init(2, key);
		byte[] inputByte = Base64.decode(msg);
		
		byte[] decryptText = c.doFinal(inputByte);
		
		String outval = new String(decryptText);
		return outval.trim();
	}
		
	public String doEncodeBase64(String msg) throws Exception {
		String output="";
		output = Base64.encode(msg.getBytes("utf-8"));
		return output;
	}
	
	public String doDecodeBase64(String msg) throws Exception {
		String output="";
		output = new String(Base64.decode(msg), "UTF-8");
		return output.trim();
	}
}