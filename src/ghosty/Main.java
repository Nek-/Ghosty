package ghosty;

import javax.swing.SwingUtilities;


public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	    // We must call the window later to avoid problems with events
	    // http://docs.oracle.com/javase/tutorial/uiswing/concurrency/dispatch.html
	    SwingUtilities.invokeLater(new Runnable() {
	    	public void run() {
	    		GhostyWindow W = new GhostyWindow();
	    	}
    	});
	    
	    // Time for tests
	}

}
