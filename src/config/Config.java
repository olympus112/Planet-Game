package config;

public interface Config {
	public String get(String key);
	public void set(String key, String value);
	
	public int getInt(String key);
	public void setInt(String key, int value);
	
	public double getDouble(String key);
	public void setDouble(String key, double value);
}
