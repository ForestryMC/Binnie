package binnie.core.modules;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;

import binnie.core.Constants;

public class Log {
	public static void trace(String message, Object... params) {
		log(Level.TRACE, message, params);
	}

	public static void debug(String message, Object... params) {
		log(Level.DEBUG, message, params);
	}

	public static void info(String message, Object... params) {
		log(Level.INFO, message, params);
	}

	public static void warning(String message, Object... params) {
		log(Level.WARN, message, params);
	}

	public static void error(String message, Object... params) {
		log(Level.ERROR, message, params);
	}

	private static void log(Level logLevel, String message, Object... params) {
		LogManager.getLogger(Constants.CORE_MOD_ID).log(logLevel, message, params);
	}
}
