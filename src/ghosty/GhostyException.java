package ghosty;

public class GhostyException extends Exception {
	private String message = "An error occured in ghosty";
	
	public GhostyException() {}
	
	public GhostyException(String message) {
		this.message = message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	@Override
	public String toString() {
		return this.message;
	}
}
