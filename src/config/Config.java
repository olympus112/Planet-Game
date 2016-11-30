package config;

public interface Config {
	String get(String key);
	void set(String key, String value);
	
	int getInt(String key);
	void setInt(String key, int value);
	
	double getDouble(String key);
	void setDouble(String key, double value);
}
