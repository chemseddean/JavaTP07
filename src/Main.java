import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
	public static void main(String[] args) throws IOException, InterruptedException {
		
		lsNew dir = new lsNew(Paths.get("test"));
		dir.ls();
		System.out.println(dir.sizeOfFiles());
		System.out.println(dir.mostRecent());
		
	}

}