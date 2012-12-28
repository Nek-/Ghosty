package ghosty.nico;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class Test{

public static void testCreateDirectory() throws IOException {
	Path monRepertoire = Paths.get("C:/temp/mon_repertoire");
	Path file = Files.createDirectory(monRepertoire);
}
}