package ghosty.md5;


import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;

import ghosty.config.ConfigFactory;
import ghosty.config.Configuration;
import ghosty.config.MissingConfigurationException;
import ghosty.config.loader.LoadException;
import ghosty.config.saver.SaveException;
import ghosty.files.FileFactory;

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
	 * @throws LoadException 
	 * @throws CheckException 
	 * @throws SaveException 
	 */
	public static String[] check(Path[] files, ConfigFactory factory) throws LoadException, CheckException, SaveException {
		ArrayList<String> changed = new ArrayList<String>();
		Configuration config = factory.getConfiguration(configFilename);
		String hash = null;
		String newHash = null;

		for(int i=0; i < files.length; i++) {
			try {
				hash = config.get(files[i].toString());

			} catch(MissingConfigurationException e) {
				hash = "";
			}
			
			try {
				newHash = hash(FileFactory.getInstance().getFile(files[i]).getBytes());
				changed.add(newHash);
			} catch (IOException e) {
				throw new CheckException("Impossible to load the file \"" + files[i] + "\"");
			}
			
			if(!hash.equals(newHash)) {
				config.set(files[i].toString(), newHash);
			}
			
		}
		
		// TODO: Check for files to remove
		// Probleme: impossible d'itérer sur des HashMap
		
		
		config.save();
		return Arrays.copyOf(changed.toArray(), changed.size(), String[].class);

	}
	
	public static String hash(byte[] input) {
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
