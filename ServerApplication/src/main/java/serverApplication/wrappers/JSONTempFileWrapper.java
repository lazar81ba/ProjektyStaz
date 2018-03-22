package serverApplication.wrappers;

import java.io.File;
import java.io.IOException;

public class JSONTempFileWrapper {
	
	private File file = null;
	
	public void initializeFile(String path) throws IOException {
		File filepath = new File(path);
		this.file = File.createTempFile("json", ".json",filepath);
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
