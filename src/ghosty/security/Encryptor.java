package ghosty.security;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
 * Hardcore part
 *
 */
public class Encryptor {

	private RSAPublicKey publicKey;

	private RSAPrivateKey privateKey;
	
	private Cipher cipher;
	
	private int keySize;

	/**
	 * Main constructor
	 * Init the RSA key to 2048 bytes
	 * @throws Exception 
	 */
	public Encryptor () throws Exception {
		this(2048);
	}

	/**
	 * Main constructor
	 * Take the key size as parameter
	 * 
	 * @param size
	 */
	public Encryptor(int size) throws Exception {
		this.keySize = size;
		KeyPair pair = null;
		// pair of keys generation
		try {
			KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
			keyPairGen.initialize(this.keySize);
			pair = keyPairGen.generateKeyPair();
		} catch (Exception e) {
			System.out.println(e);
		}
		
		
		// Getting keys
		this.publicKey = (RSAPublicKey) pair.getPublic();
		this.privateKey = (RSAPrivateKey) pair.getPrivate();

		this.cipher = Cipher.getInstance("RSA");
	}
	
	/**
	 * Take an array of bytes and return an array of this bytes encoded
	 * with the public and the private key
	 * 
	 * @param content
	 * @return
	 * @throws InvalidKeyException 
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 */
	public byte[] crypt(byte[] content) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		
		
		this.cipher.init(Cipher.ENCRYPT_MODE, this.publicKey);
		
		return this.cipher.doFinal(content);
	}
	
	/**
	 * Crypt a large byte array using a passkey
	 * 
	 * @param content
	 * @param passkey
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public byte[] cryptBigArray(byte[] content, byte[] passkey) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		
		
		Key key = new SecretKeySpec(passkey,"Blowfish");
		Cipher cipher=Cipher.getInstance("Blowfish");
		cipher.init(Cipher.ENCRYPT_MODE,key);

		return cipher.doFinal(content);
	}
	

	/**
	 * Decrypt a large byte array using a passkey
	 * 
	 * @param content
	 * @param passkey
	 * @return
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 */
	public byte[] decryptBigArray(byte[] content, byte[] passkey) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
		
		//byte[] keyBytes = this.decrypt(passkey);
		
		
		Key key = new SecretKeySpec(passkey,"Blowfish");
		Cipher cipher=Cipher.getInstance("Blowfish");
		cipher.init(Cipher.DECRYPT_MODE,key);
		
		return cipher.doFinal(content);
	}
	
	
	public byte[] decrypt(byte[] content) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		this.cipher.init(Cipher.DECRYPT_MODE, this.privateKey);
		
		return this.cipher.doFinal(content);
	}
	
	//* Testing!
	public static void main(String[] args) {

		String message = "Hello this is me ! I'm a super long text for testing max limit of this algorism !";
		try {
			Encryptor cryptosor = new Encryptor();
			
			byte[] crypted = cryptosor.crypt(message.getBytes("UTF-8"));
			
			String decrypted;
			
			decrypted = new String(cryptosor.decrypt(crypted), "UTF-8");
			System.out.println("Decrypted message : " + decrypted);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	//*/
}
