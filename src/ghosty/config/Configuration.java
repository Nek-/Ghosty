package ghosty.config;

import ghosty.config.loader.ConfigLoaderInterface;
import ghosty.config.loader.LoadException;
import ghosty.config.loader.XmlConfigLoader;
import ghosty.config.utils.ConfigMapList;

import java.util.Iterator;

public class Configuration implements Iterable {
	
	/**
	 * Store the configuration directory
	 */
	private String path;
	
	/**
	 * Help for loading the configuration from the file
	 */
	private ConfigLoaderInterface loader;
	
	/**
	 * Store all the configuration
	 */
	private ConfigMapList config;
	
	/**
	 * Construct the configuration manager
	 * 
	 * @param path
	 * @throws LoadException 
	 */
	public Configuration(String path) throws LoadException {
		this.path = path;
		this.loader = new XmlConfigLoader();

		if (this.exists())
			this.load();
		else
			this.create();
	}
	
	public String get(String option) throws MissingConfigurationException {
		if(this.config.containsKey(option)){

			return this.config.get(option);
		}
		throw new MissingConfigurationException("The option " + option + " do not exists in the configuration file " + this.path + ".");
	}
	
	
	/**
	 * Check if the configDir/config.txt exists
	 * 
	 * @return
	 */
	private boolean exists() {
		return !this.config.isEmpty();
	}
	
	/**
	 * Load the configuration,
	 * simply use the loader
	 * @throws LoadException 
	 */
	private void load() throws LoadException {
		this.config = this.loader.load(this.path);
	}
	
	/**
	 * Create a new configuration from nothing
	 */
	private void create() {
		
	}

	@Override
	public Iterator iterator() {
		
		return this.config.iterator();
	}
}
