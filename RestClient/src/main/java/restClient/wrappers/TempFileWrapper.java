package restClient.wrappers;

import java.io.File;
import java.io.IOException;

public class TempFileWrapper {
	
	private File file = null;
	
	public void initializeFile() throws IOException {
		this.file = File.createTempFile("xml", ".xml");
	}
	public String getPath() {
		return file.getPath();
	}
	public File getFile() {
		return this.file;
	}
	public boolean delete() {
		return file.delete();
	}
}
