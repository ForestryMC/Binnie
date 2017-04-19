package binnie.core.triggers;

import binnie.core.machines.Machine;
import binnie.core.machines.power.IPoweredMachine;

public class TriggerPower {
	public static TriggerData powerNone(final Object tile) {
		double percent = getPercentage(tile);
		return new TriggerData(BinnieTrigger.triggerPowerNone, percent < 0.05000000074505806);
	}

	public static TriggerData powerLow(final Object tile) {
		double percent = getPercentage(tile);
		return new TriggerData(BinnieTrigger.triggerPowerLow, percent < 0.3499999940395355);
	}

	public static TriggerData powerMedium(final Object tile) {
		double percent = getPercentage(tile);
		return new TriggerData(BinnieTrigger.triggerPowerMedium, percent >= 0.3499999940395355 && percent <= 0.6499999761581421);
	}

	public static TriggerData powerHigh(final Object tile) {
		double percent = getPercentage(tile);
		return new TriggerData(BinnieTrigger.triggerPowerHigh, percent > 0.6499999761581421);
	}

	public static TriggerData powerFull(final Object tile) {
		double percent = getPercentage(tile);
		return new TriggerData(BinnieTrigger.triggerPowerFull, percent > 0.949999988079071);
	}

	private static double getPercentage(final Object tile) {
		final IPoweredMachine process = Machine.getInterface(IPoweredMachine.class, tile);
		if (process != null) {
			return (double) (process.getInterface().getEnergy() / process.getInterface().getCapacity());
		}
		return 0.0;
	}
}
