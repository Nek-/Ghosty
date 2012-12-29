package ghosty.config.loader;

import ghosty.GhostyException;

public class LoadException extends GhostyException {
	public LoadException(String file) {
		this.setMessage("An error occured when we tried to load the config file \"" + file +"\".");
	}
}
