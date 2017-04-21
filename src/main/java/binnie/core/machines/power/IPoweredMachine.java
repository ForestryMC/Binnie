package binnie.core.machines.power;

import cofh.api.energy.IEnergyHandler;
import cpw.mods.fml.common.Optional;
import ic2.api.energy.tile.IEnergySink;

@Optional.Interface(iface = "ic2.api.energy.tile.IEnergySink", modid = "IC2")
public interface IPoweredMachine extends IEnergySink, IEnergyHandler {
	PowerInfo getPowerInfo();

	PowerInterface getInterface();
}
