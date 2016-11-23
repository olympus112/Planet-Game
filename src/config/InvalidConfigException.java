package config;

public class InvalidConfigException extends Exception {
	private String fileName, reason;
	private int line, col;
	
	public InvalidConfigException(String fileName, int line, int col, String reason){
		this.fileName = fileName;
		this.line = line;
		this.col = col;
		this.reason = reason;
	}
	public String toString(){
		return String.format("An error occurred in file %s at line %d:%d\n%s", fileName, line, col, reason);
	}
}
