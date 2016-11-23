package config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class KeyValueConfig implements FileConfig {
	boolean dirty = false;
	File configFile;
	public Map<String, String> keyValuePairs = new HashMap<String, String>();
	
	public KeyValueConfig(File configFile) throws InvalidConfigException, IOException{
		this.configFile = configFile;
		refresh();
	}
	@Override public void refresh() throws InvalidConfigException, IOException{
		FileReader configReader = new FileReader(configFile);
		BufferedReader reader = new BufferedReader(configReader);
		
		Map<String, String> newKeyValuePairs = new HashMap<String, String>();
		for(int lineNum = 1; reader.ready(); lineNum++){
			String line = reader.readLine();
			if(!line.startsWith("#") && line.trim().length() != 0){
				String[] keyValuePair = line.split(":", 2);
				if(keyValuePair.length != 2)
					throw new InvalidConfigException(configFile.getName(), lineNum, line.length() - 1, "No key/Value separator");
				
				newKeyValuePairs.put(keyValuePair[0].trim(), keyValuePair[1].trim());
			}
		}
		this.keyValuePairs = newKeyValuePairs;
		configReader.close();
		reader.close();
		this.dirty = false;
	}
	@Override public void flush(File flushTo) throws IOException{
		StringBuffer buffer = new StringBuffer(keyValuePairs.size() * 20); // estimated size per line
		keyValuePairs.forEach((key, value) -> {
			buffer.append(key + ": " + value + "\n");
		});
		FileWriter fw = new FileWriter(configFile);
		fw.write(buffer.toString());
		fw.flush();
		fw.close();
	}
	@Override public void flush() throws IOException{
		if(dirty)
			flush(configFile);
		this.dirty = false;
	}
	
	@Override public String get(String key){
		return keyValuePairs.get(key);
	}
	@Override public void set(String key, String value){
		keyValuePairs.put(key, value);
		dirty = true;
	}
	
	@Override public int getInt(String key) throws NumberFormatException {
		return Integer.parseInt(get(key));
	}
	@Override public void setInt(String key, int value){
		keyValuePairs.put(key, "" + value);
		dirty = true;
	}
	@Override public double getDouble(String key) throws NumberFormatException {
		return Double.parseDouble(get(key));
	}
	@Override public void setDouble(String key, double value){
		keyValuePairs.put(key, "" + value);
		dirty = true;
	}
}
