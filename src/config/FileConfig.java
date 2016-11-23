package config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public interface FileConfig extends Config{
	public void refresh() throws InvalidConfigException, FileNotFoundException, IOException;
	public void flush() throws IOException;
	void flush(File flushTo) throws IOException;
}
