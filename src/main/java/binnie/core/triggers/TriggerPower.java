package binnie.core.triggers;

public class TriggerPower {
	/*public static TriggerData powerNone(final Object tile) {
		return new TriggerData(BinnieTrigger.triggerPowerNone, getPercentage(tile) < 0.05000000074505806);
	}

	public static TriggerData powerLow(final Object tile) {
		return new TriggerData(BinnieTrigger.triggerPowerLow, getPercentage(tile) < 0.3499999940395355);
	}

	public static TriggerData powerMedium(final Object tile) {
		final double p = getPercentage(tile);
		return new TriggerData(BinnieTrigger.triggerPowerMedium, p >= 0.3499999940395355 && p <= 0.6499999761581421);
	}

	public static TriggerData powerHigh(final Object tile) {
		final double p = getPercentage(tile);
		return new TriggerData(BinnieTrigger.triggerPowerHigh, getPercentage(tile) > 0.6499999761581421);
	}

	public static TriggerData powerFull(final Object tile) {
		final double p = getPercentage(tile);
		return new TriggerData(BinnieTrigger.triggerPowerFull, getPercentage(tile) > 0.949999988079071);
	}

	private static double getPercentage(final Object tile) {
		final IPoweredMachine process = Machine.getInterface(IPoweredMachine.class, tile);
		if (process != null) {
			final double percentage = process.getInterface().getEnergy() / process.getInterface().getCapacity();
			return percentage;
		}
		return 0.0;
	}*/
}
