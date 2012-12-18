package ghosty.files;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.LinkedList;


public class Manager {
	public LinkedList<String> getFilesFromPath(String path)  throws IOException {
		
		LinkedList<String> list = new LinkedList<String>();
		Path jdkPath = Paths.get(path);
		DirectoryStream<Path> stream = Files.newDirectoryStream(jdkPath);
		try {
		
		Iterator<Path> iterator = stream.iterator();
		
		while(iterator.hasNext()) {
		
			Path p = iterator.next();
			
			if (!p.toString().equals(".ghosty")){
				list.add(p.toString());
				if (Files.isDirectory(p))
					getFilesFromPath(p.toString());
				}
		
		
			}
		
		} finally {
		
		stream.close();
		
		}
		return new LinkedList<String>();
	}
	
	public Byte[] getFileContent(Path path) {
		Byte[] array = null;
		try {
			Files.readAllBytes(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return array;
	}
	
	public LinkedList readFile ( Path path){
		
	}
	
}
