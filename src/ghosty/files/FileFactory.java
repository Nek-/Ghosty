package ghosty.files;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

public class FileFactory {
	
	private static FileFactory that;
	
	private HashMap<Path, File> files;
	
	public static FileFactory getInstance() {
		if(that == null) {
			that = new FileFactory();
		}
		return that;
	}
	
	private FileFactory() {
		this.files = new HashMap<Path, File>();
	}
	
	public File getFile(String str) {
		Path path = Paths.get(str);

		return this.getFile(path);
	}
	
	public File getFile(Path path) {
		if(this.files.containsKey(path)) {
			return this.files.get(path);
		}

		File res = new File(path);
		this.files.put(path, res);

		return res;
	}
}
