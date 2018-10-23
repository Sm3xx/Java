package core;

public class Logger {
	
	public static void info(String message) {
		System.out.println("\n[INFO]     -  ["+Main.getTimestamp()+"] "+message);
	}
	
	public static void warning(String message) {
		System.out.println("\n[WARNING]  -  ["+Main.getTimestamp()+"] "+message);
	}
	
	public static void error(String message) {
		System.out.println("[ERROR]    -  ["+Main.getTimestamp()+"] "+message);
	}
	
	public static void message(String message) {
		System.out.println("\n[MESSAGE]  -  ["+Main.getTimestamp()+"] "+message);
	}
	
	public static void command(String message) {
		System.out.println("\n[COMMAND]  -  ["+Main.getTimestamp()+"] "+message);
	}
}
