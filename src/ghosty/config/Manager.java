package ghosty.config;

import ghosty.config.loader.BaseConfigLoader;

import java.util.HashMap;

public class Manager {
	
	/**
	 * Store the configuration directory
	 */
	private String configDir;
	
	/**
	 * Help for loading the configuration from the file
	 */
	private BaseConfigLoader loader;
	
	/**
	 * Store all the configuration
	 */
	private HashMap<String, String> config;
	
	/**
	 * Construct the configuration manager
	 * 
	 * @param path
	 */
	public Manager(String path) {
		this.configDir = path;
		this.loader = new BaseConfigLoader();
		
		if (this.exists())
			this.load();
		else
			this.create();
	}
	
	public String get(String option) {
		return this.config.get(option);
	}
	
	
	/**
	 * Check if the configDir/config.txt exists
	 * 
	 * @return
	 */
	private boolean exists() {
		return false;
	}
	
	/**
	 * Load the configuration,
	 * simply use the loader
	 */
	private void load() {
		this.config = this.loader.load(this.configDir + "/config.txt");
	}
	
	/**
	 * Create a new configuration from nothing
	 */
	private void create() {
		
	}
}
