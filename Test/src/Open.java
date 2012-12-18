import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.Iterator;
import java.util.List;

public class Open {
	
	public static void testDirectoryStream(String arg) throws IOException {
		
		Path jdkPath = Paths.get(arg);
		
		DirectoryStream<Path> stream = Files.newDirectoryStream(jdkPath);
		
		try {
		
		Iterator<Path> iterator = stream.iterator();
		
		while(iterator.hasNext()) {
		
				
		Path p = iterator.next();
		System.out.println(p);
		if ( Files.isDirectory(p))
			testDirectoryStream( p.toString());
		
		}
		
		} finally {
		
		stream.close();
		
		}
		
		}
	
	public static byte[] getFileContent(String stg) throws IOException {
		byte[] array = Files.readAllBytes(FileSystems.getDefault().getPath(stg));

		return array;
	}
	
	public static List<String> readFile (String stg) throws IOException{
		List<String> lignes =  Files.readAllLines(  
				  FileSystems.getDefault().getPath(stg), Charset.defaultCharset());  
				
		return lignes;
	}
	
	 public static void main (String[] args) throws IOException {
		 Path path = Paths.get("src");
		 System.out.println("Chemin absolu du fichier : " + path.toAbsolutePath());
		 System.out.println("Est-ce qu'il existe ? " + Files.exists(path));
		 System.out.println("Nom du fichier : " + path.getFileName());
		 System.out.println("Est-ce un répertoire ? " + Files.isDirectory(path));
			
		 try {
			testDirectoryStream("C:/Users/Niko/workspace/Test");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 byte[] element = getFileContent("src/Open.java");
		 for (byte array : element)
			  System.out.println(array);
		List<String> lignes = readFile( "src/Open.java");
		 for (String ligne : lignes)
			  System.out.println(ligne);
	 }
}