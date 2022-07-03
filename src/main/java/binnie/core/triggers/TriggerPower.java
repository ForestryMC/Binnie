package binnie.core.triggers;

import binnie.core.machines.Machine;
import binnie.core.machines.power.IPoweredMachine;

public class TriggerPower {
    public static TriggerData powerNone(Object tile) {
        double percent = getPercentage(tile);
        return new TriggerData(BinnieTrigger.triggerPowerNone, percent < 0.05000000074505806);
    }

    public static TriggerData powerLow(Object tile) {
        double percent = getPercentage(tile);
        return new TriggerData(BinnieTrigger.triggerPowerLow, percent < 0.3499999940395355);
    }

    public static TriggerData powerMedium(Object tile) {
        double percent = getPercentage(tile);
        return new TriggerData(
                BinnieTrigger.triggerPowerMedium, percent >= 0.3499999940395355 && percent <= 0.6499999761581421);
    }

    public static TriggerData powerHigh(Object tile) {
        double percent = getPercentage(tile);
        return new TriggerData(BinnieTrigger.triggerPowerHigh, percent > 0.6499999761581421);
    }

    public static TriggerData powerFull(Object tile) {
        double percent = getPercentage(tile);
        return new TriggerData(BinnieTrigger.triggerPowerFull, percent > 0.949999988079071);
    }

    private static double getPercentage(Object tile) {
        IPoweredMachine process = Machine.getInterface(IPoweredMachine.class, tile);
        if (process != null) {
            return (double)
                    (process.getInterface().getEnergy() / process.getInterface().getCapacity());
        }
        return 0.0;
    }
}
