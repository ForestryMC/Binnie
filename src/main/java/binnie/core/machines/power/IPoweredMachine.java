package binnie.core.machines.power;

import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.common.Optional;

@Optional.Interface(iface = "ic2.api.energy.tile.IEnergySink", modid = "IC2")
//TODO ic2 energy
public interface IPoweredMachine extends IEnergyStorage/*IEnergySink*/ {
	PowerInfo getPowerInfo();

	PowerInterface getInterface();
}
