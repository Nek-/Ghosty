package ghosty.utils;

import java.util.ArrayList;

/**
 * Manage Ghosty Parameters
 *
 */
public class ParametersManager {
	
	/**
	 * Program arguments
	 */
	private String[] args;
	
	private ArrayList<Parameters> parameters;
	
	/**
	 * Set the manager
	 * @param args
	 */
	public ParametersManager(String[] args) {
		this.args = args;
		this.parameters = new ArrayList<Parameters>();
		
		this.build();
	}
	
	private void build() {
		for(int i = 0; i < this.args.length; i++) {
			if (this.args[i].equals("--gui")) {
				this.parameters.add(Parameters.GUI);
			} else if(this.args[i].equals("-c") || this.args[i].equals("--re-configure")) {
				this.parameters.add(Parameters.RECONFIGURE);
			}
		}
		
	}
	
	public boolean has(Parameters p) {

		return  this.parameters.contains(p);
	}

	/**
	 * Check if it's specified to have a GUI
	 * @return
	 * @deprecated
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
