package binnie.core.machines.power;

import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.common.Optional;

import ic2.api.energy.tile.IEnergySink;

@Optional.Interface(iface = "ic2.api.energy.tile.IEnergySink", modid = "ic2")
public interface IPoweredMachine extends IEnergyStorage, IEnergySink {
	PowerInfo getPowerInfo();

	PowerInterface getInterface();
}
