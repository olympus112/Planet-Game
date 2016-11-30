package config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public interface FileConfig extends Config {
	void refresh() throws InvalidConfigException, IOException;
	void flush() throws IOException;
	void flush(File flushTo) throws IOException;
}
