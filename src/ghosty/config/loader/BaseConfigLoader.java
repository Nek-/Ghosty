package ghosty.config.loader;

import ghosty.config.utils.ConfigMapList;

import java.util.Scanner;

/**
 * This loader load configuration files formatted like that:
 * key: value
 * if there is a second ":" in the line, it will be ignored
 *
 */
public class BaseConfigLoader implements ConfigLoaderInterface {
	
	/**
	 * Load a file configuration to set it in a ConfigMapList
	 * 
	 * @param path
	 * @return
	 */
	public ConfigMapList load(String path) {
		// Should be load from a file
		String config = "";
		ConfigMapList res = new ConfigMapList();
		
		Scanner sc = new Scanner(config);
		String[] tmp;
		
		while(sc.hasNextLine()) {
			tmp = this.parseLine(sc.nextLine());
			
			res.put(tmp[0], tmp[1]);
		}
		
		return res;
	}
	
	/**
	 * Parse a line of configuration
	 * 
	 * @param s
	 * @return
	 */
	private String[] parseLine(String s) {
		String[] res = s.split(":", 2);
		
		for (int i = 0; i < 2; i++) {
			res[i].trim();
		}
		
		return res;
	}
}
