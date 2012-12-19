package ghosty;

import ghosty.utils.ParametersManager;

import javax.swing.SwingUtilities;


public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		ParametersManager pm = new ParametersManager(args);
		
		if(pm.hasGui()) {
			Main.loadGui();
		} else {
			Main.loadShell();
		}
	    
	}
	
	public static void loadGui() {

	    // We must call the window later to avoid problems with events
	    // http://docs.oracle.com/javase/tutorial/uiswing/concurrency/dispatch.html
	    SwingUtilities.invokeLater(new Runnable() {
	    	public void run() {
	    		GhostyWindow W = new GhostyWindow();
	    	}
    	});
	}
	

	public static void loadShell() {

		System.out.println("***********************************");
		System.out.println("*        Welcome to Ghosty !      *");
		System.out.println("***********************************");
		
	}

}
