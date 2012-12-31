package ghosty.md5;


import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

import ghosty.config.ConfigFactory;
import ghosty.config.Configuration;
import ghosty.config.MissingConfigurationException;
import ghosty.config.loader.LoadException;
import ghosty.config.saver.SaveException;
import ghosty.config.utils.ConfigMapListIterator;
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
	public static HashMap<CheckType, String[]> check(Path[] files, ConfigFactory factory) throws LoadException, CheckException, SaveException {
		HashMap<CheckType, String[]> map = new HashMap<CheckType, String[]>();
		
		ArrayList<String> changed = new ArrayList<String>();
		ArrayList<String> deleted = new ArrayList<String>();
		ArrayList<String> added = new ArrayList<String>();
		
		Configuration config = factory.getConfiguration(configFilename);
		String hash = null;
		String newHash = null;

		for(int i=0; i < files.length; i++) {

			try {
				newHash = hash(FileFactory.getInstance().getFile(files[i]).getBytes());
			} catch (IOException e1) {
				throw new CheckException("Impossible to load the file \"" + files[i] + "\"");
			}

			try {
				hash = config.get(files[i].toString());

			} catch(MissingConfigurationException e) {
				added.add(files[i].toString());
				continue;
			}

			if(!hash.equals(newHash)) {
				config.set(files[i].toString(), newHash);
				changed.add(newHash);
			}

		}
		
		map.put(CheckType.MODIFIED, Arrays.copyOf(changed.toArray(), changed.size(), String[].class));
		map.put(CheckType.ADDED, Arrays.copyOf(added.toArray(), added.size(), String[].class));
		
		
		ConfigMapListIterator it = (ConfigMapListIterator) config.iterator();
		
		String key;
		boolean exists;
		while(it.hasNext()) {
			key = it.nextKey();
			exists = false;
			
			for(int i = 0; !exists && i < files.length; i++) {
				if(key.equals(files[i].toString())) {
					exists = true;
				}
			}
			if(!exists) {
				deleted.add(key);
			}
		}
		
		map.put(CheckType.DELETED, Arrays.copyOf(deleted.toArray(), deleted.size(), String[].class));
		
		
		config.save();
		return map;

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
