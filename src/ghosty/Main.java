package ghosty;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.nio.file.WatchService;
import java.util.Scanner;

import ghosty.config.Configuration;
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
		
		
		Directory.createDirectory(baseConfigDirectory);
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

			baseConfig.set("last_directory", directory);


			baseConfig.save();

		} catch (Exception e) {}
		
		
		// Watcher
		System.out.println("Pour arrêter ce programme, tapez CTRL+C.");
		launchWatcher();


		// If configuration missing or reconfigure enabled, we must load the configuration process
		
	}


	private static void launchWatcher(Path dir) {
		
		WatchService watcher = FileSystems.getDefault().newWatchService();
		
		for (;;) {

		    // wait for key to be signaled
		    WatchKey key;
		    try {
		        key = watcher.take();
		    } catch (InterruptedException x) {
		        return;
		    }

		    for (WatchEvent<?> event: key.pollEvents()) {
		        WatchEvent.Kind<?> kind = event.kind();

		        // This key is registered only
		        // for ENTRY_CREATE events,
		        // but an OVERFLOW event can
		        // occur regardless if events
		        // are lost or discarded.
		        if (kind == OVERFLOW) {
		            continue;
		        }

		        // The filename is the
		        // context of the event.
		        WatchEvent<Path> ev = (WatchEvent<Path>)event;
		        Path filename = ev.context();

		        // Verify that the new
		        //  file is a text file.
		        try {
		            // Resolve the filename against the directory.
		            // If the filename is "test" and the directory is "foo",
		            // the resolved name is "test/foo".
		            Path child = dir.resolve(filename);
		            if (!Files.probeContentType(child).equals("text/plain")) {
		                System.err.format("New file '%s'" +
		                    " is not a plain text file.%n", filename);
		                continue;
		            }
		        } catch (IOException x) {
		            System.err.println(x);
		            continue;
		        }

		        // Email the file to the
		        //  specified email alias.
		        System.out.format("Emailing file %s%n", filename);
		        //Details left to reader....
		    }

		    // Reset the key -- this step is critical if you want to
		    // receive further watch events.  If the key is no longer valid,
		    // the directory is inaccessible so exit the loop.
		    boolean valid = key.reset();
		    if (!valid) {
		    	System.out.println("A critical error appears: the directory is not enough accessible");
		        break;
		    }
		}
		// TODO Auto-generated method stub
		
	}
	
	

}
