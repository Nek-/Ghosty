package ghosty;

import java.io.IOException;
import java.util.Scanner;

import ghosty.config.Configuration;
import ghosty.config.MissingConfigurationException;
import ghosty.config.loader.LoadException;
import ghosty.config.loader.XmlConfigLoader;
import ghosty.config.utils.ConfigMapList;
import ghosty.files.Directory;
import ghosty.utils.Parameters;
import ghosty.utils.ParametersManager;

import javax.swing.SwingUtilities;

/**
 * Main class of the project Ghost
 * 
 * Type of calls:
 *   - "java ghosty --no-gui --reconfigure"
 *
 */
public class Main {

	public static ParametersManager pm;
	
	public static boolean hasGui = false;
	
	public static String baseConfigDirectory;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		pm = new ParametersManager(args);
		hasGui = pm.has(Parameters.GUI);
		baseConfigDirectory = System.getProperty("user.home") + System.getProperty("file.separator") + ".Ghosty";
		
		if(hasGui) {
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
		
		try {
			Directory.testCreateDirectory(baseConfigDirectory);
		} catch (Exception e) {}
		String lastDirectory = null;
		String directory = null;
		try {
			Configuration baseConfig = new Configuration(baseConfigDirectory +  System.getProperty("file.separator") + "base.xml");
			if (baseConfig.exists() && baseConfig.has("last_directory")) {

				lastDirectory = baseConfig.get("last_directory");
			}
			Scanner in = new Scanner(System.in);
			do {
				System.out.print("Choisissez un dossier");
				if(lastDirectory != null) {
					System.out.print(" (Laissez vide pour \"" + lastDirectory + "\") ");
				}
				System.out.println(": ");
				
				
				directory = in.nextLine();
				
				if(directory.equals("") && lastDirectory != null) {
					directory = lastDirectory;
				}
				
			} while (directory == null);
			
			baseConfig.set("lastDirectory", directory);
			
			System.out.println("Directory: " + directory);
			System.out.println("Config : " + baseConfig.get("lastDirectory"));
			
			baseConfig.save();

		} catch (Exception e) {
			System.out.println("Exception");
		}


		// If configuration missing or reconfigure enabled, we must load the configuration process
		
	}

}
