package binnie.core.triggers;

import binnie.core.machines.Machine;
import binnie.core.machines.power.IPoweredMachine;

public class TriggerPower {
	private TriggerPower() {
	}

	public static TriggerData powerNone(Object tile) {
		return new TriggerData(BinnieTrigger.triggerPowerNone, getPercentage(tile) < 0.05000000074505806);
	}

	public static TriggerData powerLow(Object tile) {
		return new TriggerData(BinnieTrigger.triggerPowerLow, getPercentage(tile) < 0.3499999940395355);
	}

	public static TriggerData powerMedium(Object tile) {
		double percentage = getPercentage(tile);
		return new TriggerData(BinnieTrigger.triggerPowerMedium, percentage >= 0.3499999940395355 && percentage <= 0.6499999761581421);
	}

	public static TriggerData powerHigh(Object tile) {
		return new TriggerData(BinnieTrigger.triggerPowerHigh, getPercentage(tile) > 0.6499999761581421);
	}

	public static TriggerData powerFull(Object tile) {
		return new TriggerData(BinnieTrigger.triggerPowerFull, getPercentage(tile) > 0.949999988079071);
	}

	private static double getPercentage(Object tile) {
		IPoweredMachine process = Machine.getInterface(IPoweredMachine.class, tile);
		if (process != null) {
			return (float)process.getInterface().getEnergy() / (float)process.getInterface().getCapacity();
		}
		return 0.0;
	}
}
