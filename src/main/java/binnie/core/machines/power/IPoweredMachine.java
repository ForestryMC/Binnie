package binnie.core.machines.power;

import cofh.api.energy.*;
import cpw.mods.fml.common.*;
import ic2.api.energy.tile.*;

@Optional.Interface(iface = "ic2.api.energy.tile.IEnergySink", modid = "IC2")
public interface IPoweredMachine extends IEnergySink, IEnergyHandler {
	PowerInfo getPowerInfo();

	PowerInterface getInterface();
}
