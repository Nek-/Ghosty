package ghosty.config;

public class MissingConfigurationException extends Exception {

	private String message;
	
	public MissingConfigurationException() {
		this.message = "The specified option is not available in configuration";
	}

	public MissingConfigurationException(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return this.message;
	}
}
