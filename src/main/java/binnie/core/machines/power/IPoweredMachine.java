package binnie.core.machines.power;

import net.minecraftforge.fml.common.Optional;

@Optional.Interface(iface = "ic2.api.energy.tile.IEnergySink", modid = "IC2")
//TODO ic2 and RF energy
public interface IPoweredMachine //extends /*IEnergySink, IEnergyHandler, IEnergyReceiver, IEnergyProvider*/
{
    PowerInfo getPowerInfo();

    PowerInterface getInterface();
}
