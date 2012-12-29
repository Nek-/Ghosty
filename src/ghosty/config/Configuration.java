package ghosty.config;

import ghosty.config.loader.ConfigLoaderInterface;
import ghosty.config.loader.LoadException;
import ghosty.config.loader.XmlConfigLoader;
import ghosty.config.saver.ConfigSaverInterface;
import ghosty.config.saver.SaveException;
import ghosty.config.saver.XmlConfigSaver;
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
	 * Help for save the configuration to a file
	 */
	private ConfigSaverInterface saver;
	
	/**
	 * Construct the configuration manager
	 * 
	 * @param path
	 * @throws LoadException 
	 */
	public Configuration(String path) throws LoadException {
		this.path   = path;
		this.loader = new XmlConfigLoader();
		this.saver  = new XmlConfigSaver();

		this.load();
	}
	
	/**
	 * Get an option from the loaded configuration
	 * 
	 * @param option
	 * @return
	 * @throws MissingConfigurationException
	 */
	public String get(String option) throws MissingConfigurationException {
		if(this.config.containsKey(option)){

			return this.config.get(option);
		}
		throw new MissingConfigurationException("The option " + option + " do not exists in the configuration file " + this.path + ".");
	}
	
	/**
	 * Set an option on the configuration
	 * 
	 * @param option
	 * @param value
	 */
	public void set(String option, String value) {
		this.config.put(option, value);
	}
	
	public boolean has(String option) {
		return this.config.containsKey(option);
	}
	
	
	/**
	 * Check if the configDir/config.txt exists
	 * 
	 * @return
	 */
	public boolean exists() {
		return !this.config.isEmpty();
	}
	
	/**
	 * Load the configuration
	 * simply use the loader
	 * @throws LoadException 
	 */
	private void load() throws LoadException {
		this.config = this.loader.load(this.path);
	}
	
	public void save() throws SaveException {
		this.saver.save(this.path, this.config);
	}
	

	@Override
	public Iterator iterator() {
		
		return this.config.iterator();
	}
}
