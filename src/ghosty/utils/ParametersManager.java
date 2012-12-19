package ghosty.utils;

/**
 * Manage Ghosty Parameters
 *
 */
public class ParametersManager {
	
	/**
	 * Program arguments
	 */
	private String[] args;
	
	/**
	 * Set the manager
	 * @param args
	 */
	public ParametersManager(String[] args) {
		this.args = args;
	}
	
	/**
	 * Check if it's specified to have a GUI
	 * @return
	 */
	public boolean hasGui() {
		boolean res = false;
		
		for(int i=0; i < this.args.length; i++) {
			if(this.args[i].equals("--gui")) {
				res = true;
			}
		}
		
		return res;
	}
}
