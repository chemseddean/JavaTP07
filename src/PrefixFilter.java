import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Path;

public class PrefixFilter implements DirectoryStream.Filter<Path> {
	private long n;
	
	public PrefixFilter(long n) {
		this.n = n;
	}
	
	@Override
	public boolean accept(Path entry) throws IOException {
		if (entry.toFile().length() < this.n) {
			return false;
		}
		return true;
	}

}