package ghosty.files;

import java.util.HashMap;

public class FileFactory {
	
	private static FileFactory that;
	
	private HashMap<String, File> files;
	
	public static FileFactory getInstance() {
		if(that == null) {
			that = new FileFactory();
		}
		return that;
	}
	
	private FileFactory() {
		this.files = new HashMap<String, File>();
	}
	
	public File getFile(String path) {
		if(this.files.containsKey(path)) {
			return this.files.get(path);
		}

		File res = new File(path);
		this.files.put(path, res);

		return res;
	}
}
