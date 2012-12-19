package ghosty.files;

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
}
