package ghosty.files;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;

/**
 * Class who manage a directory
 *
 */
public class Directory {
	private String path;
	
	public Directory(String path) {
		this.path = path;
	}
	
	public LinkedList<String> getFiles () {
		return new LinkedList<String>();
	}
	
	public static void testCreateDirectory(String directory) throws IOException {
		Path monRepertoire = Paths.get(directory);
		Path file = Files.createDirectory(monRepertoire);
	}
}
