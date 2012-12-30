package ghosty.files;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * Class who manage a file
 *
 */
public class File {
	/**
	 * path of the file
	 */
	private Path path;

	/**
	 * Construct a File manager
	 * @param String path
	 */
	public File(String path) {
		this.path = Paths.get(path);
	}

	/**
	 * Construct a File manager
	 * @param Path path
	 */
	public File(Path path) {
		this.path = path;
	}
	
	/**
	 * Get a scanner who send file by part
	 * 
	 * @return
	 */
	public Scanner getScanner() {
		InputStream is = null;
		return new Scanner(is);
	}
	
	/**
	 * Return the complete file as bytes
	 * 
	 * @return
	 * @throws IOException 
	 */
	public byte[] getBytes() throws IOException {
		byte[] array = Files.readAllBytes(this.path);

		return array;
	}
	
	/**
	 * Return the complete file as InputStream
	 * 
	 * @return
	 * @throws IOException 
	 */
	public InputStream getInputStream() throws IOException{
		InputStream in = Files.newInputStream(this.path);
		return in;	
	}

	/**
	 * Return the complete file as InputStream
	 * 
	 * @return
	 * @throws IOException
	 */
	public OutputStream getOutputStream() throws IOException{
		OutputStream out = Files.newOutputStream(this.path);
		return out;
	}
}
