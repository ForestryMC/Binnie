package binnie.core.util;

import java.text.DecimalFormat;

public final class TimeUtil {
	private TimeUtil() {

	}

	//TODO: localise
	public static String getTimeString(int time) {
		int seconds = time / 20;
		int minutes = seconds / 60;
		int hours = minutes / 60;
		DecimalFormat df = new DecimalFormat("#.0");
		if (hours > 1) {
			return df.format(hours) + " hours";
		}
		if (minutes > 1) {
			return df.format(minutes) + " min.";
		}
		return df.format(seconds) + " sec.";
	}

	public static String getMCDayString(float time) {
		float seconds = time / 20.0f;
		float minutes = seconds / 60.0f;
		float days = minutes / 20.0f;
		DecimalFormat df = new DecimalFormat("#.#");
		// TODO: move this key to core
		return I18N.localise("genetics.gui.analyst.biology.mcDays", df.format(days));
	}
}
