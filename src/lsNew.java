import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;

public class lsNew {
	private Path path;
		
	public lsNew(Path path) throws IOException {
			if (!path.toFile().isDirectory()) {
				throw new IOException("");
			}
			this.path = path;
		}
		
	public void ls() throws IOException {
			Iterator<Path> it = Files.newDirectoryStream(this.path).iterator();
			while (it.hasNext()) {
				System.out.println(it.next().toString());
			}
		}
		
	// classe interne
	
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
		
	public void ls(long n) throws IOException {
			Iterator<Path> it = Files.newDirectoryStream(this.path).iterator();

			PrefixFilter filter = new PrefixFilter(n) {
				@Override
				public boolean accept(Path entry) throws IOException {
					if (entry.toFile().length() < n) {
						return false;
					}
					return true;
				}
			};
			
			while (it.hasNext()) {
				Path file = it.next();
				if (filter.accept(file)) {
					System.out.println(file.toString());
				}
			}
		}
		
	public long sizeOfFiles() throws IOException {
			long size = 0;
			Iterator<Path> it = Files.newDirectoryStream(this.path).iterator();
			while (it.hasNext()) {
				Path file = it.next();
				if (file.toFile().isFile()) {
					size += file.toFile().length();
				}
			}
			return size;
		}
		
	public Path mostRecent() throws IOException {
			Iterator<Path> it = Files.newDirectoryStream(this.path).iterator();
			Path mostRecent = null;
			Path file;
			while (it.hasNext()) {
				file = it.next();
				if (mostRecent == null || mostRecent.toFile().lastModified() < file.toFile().lastModified()) {
					mostRecent = file;
				}
			}
			return mostRecent;
		}
		
	public void lsNew (long n) throws IOException {
			Iterator<Path> it = Files.newDirectoryStream(this.path).iterator();
	 		while (it.hasNext()) {
	 			this.applyAction(path, n, new MyAction() {
					@Override
					public void perform(Path p) throws IOException {
						if (p.toFile().length() >= n) {
							System.out.println(p.toString());
						}
					}
				});
			}
		}
		
		public class SizeAction implements MyAction {
			private long size;
			private long n;
			public SizeAction(long size, long n) {
				this.size = size;
				this.n = n;
			}
			@Override
			public void perform(Path p) throws IOException {
				if (p.toFile().length() >= n && p.toFile().isFile()) {
					this.size += p.toFile().length();
				}
			}
		}
		
		public long sizeOfFilesNew(long n) throws IOException {
			Iterator<Path> it = Files.newDirectoryStream(this.path).iterator();
			SizeAction sizeAction = new SizeAction(0, n);
			while (it.hasNext()) {
	 			this.applyAction(it.next(), n, sizeAction);
			}
			return sizeAction.size;
		}
		
		public class MostRecentAction implements MyAction {
			private Path mostRecent;
			private long n;
			public MostRecentAction(long n) {
				this.mostRecent = null;
				this.n = n;
			}
			@Override
			public void perform(Path p) throws IOException {
				if (p.toFile().length() >= n && (mostRecent == null || mostRecent.toFile().lastModified() < p.toFile().lastModified())) {
					mostRecent = p;
				}
			}
		}
		
		public Path mostRecentNew (long n) throws IOException {
			Iterator<Path> it = Files.newDirectoryStream(this.path).iterator();
			MostRecentAction mra = new MostRecentAction(n);
			while (it.hasNext()) {
				this.applyAction(it.next(), n, mra);
			}
			return mra.mostRecent;
		}
		
		public void applyAction(Path p, long n, MyAction action) throws IOException {
			action.perform(p);
		}
		
}

