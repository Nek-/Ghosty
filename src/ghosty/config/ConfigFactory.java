package ghosty.config;

import ghosty.config.loader.LoadException;
import ghosty.files.Directory;

public class ConfigFactory {
	
	private final String configFolder = ".config";
	private String mainFolder;
	
	public ConfigFactory (String mainFolder) {
		this.mainFolder = mainFolder;
		Directory.createDirectory(mainFolder + System.getProperty("file.separator") + this.configFolder);
	}
	
	public Configuration getConfiguration(String file) throws LoadException {
		Configuration config = new Configuration(mainFolder + "/" + configFolder + "/" + file);
		
		return config;
	}
}
