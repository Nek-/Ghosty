package ghosty.md5;


import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import ghosty.config.ConfigFactory;
import ghosty.config.Configuration;
import ghosty.config.MissingConfigurationException;

/**
 * Load a file and check
 *
 */
public class Checker {
	
	private static final String configFilename = "check";
	
	/**
	 * 
	 * @param files
	 * @param configDir
	 * @return String[] paths of files who change
	 */
	public static String[] check(String[] files, ConfigFactory factory) {
		ArrayList<String> changed = new ArrayList<String>();
		Configuration config = factory.getConfiguration(configFilename);
		String hash;
		String newHash;
		
		for(int i=0; i < files.length; i++) {
			try {
				hash = config.get(files[i]);
				
				// TODO get from the file
				newHash = hash(files[i].getBytes());
				
				if(hash != newHash) {
					changed.add(files[i]);
				}

			} catch(MissingConfigurationException e) {
				changed.add(files[i]);
			}
		}
		
		// TODO: Check for files to remove
		// Probleme: impossible d'itérer sur des HashMap
		
		
		
		return files;
		
	}
	
	private static String hash(byte[] input) {
		String md5 = "";
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			
			digest.update(input);
			md5 = new BigInteger(1, digest.digest()).toString(16);
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return md5;
	}
	
}
