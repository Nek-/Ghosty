package ghosty.config;

import ghosty.config.loader.LoadException;

public class ConfigFactory {
	
	private final String configFolder = ".config";
	private String mainFolder;
	
	public ConfigFactory (String mainFolder) {
		this.mainFolder = mainFolder;
	}
	
	public Configuration getConfiguration(String file) throws LoadException {
		Configuration config = new Configuration(mainFolder + "/" + configFolder + "/" + file);
		
		return config;
	}
}
