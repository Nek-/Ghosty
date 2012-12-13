package ghosty.config.loader;

import java.util.HashMap;

public class BaseConfigLoader {
	
	/**
	 * Load a file configuration to set it in a hashmap
	 * @param path
	 * @return
	 */
	public HashMap<String, String> load(String path) {
		return new HashMap<String, String>();
	}
}
