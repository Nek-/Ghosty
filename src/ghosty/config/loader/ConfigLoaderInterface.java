package ghosty.config.loader;

import ghosty.config.utils.ConfigMapList;

public interface ConfigLoaderInterface {
	
	/**
	 * Load something from a file
	 * 
	 * @param path
	 * @return
	 * @throws LoadException 
	 */
	public ConfigMapList load(String path) throws LoadException;
}
