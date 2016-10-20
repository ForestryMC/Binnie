// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.machines.power;

import cpw.mods.fml.common.Optional;
import cofh.api.energy.IEnergyHandler;
import ic2.api.energy.tile.IEnergySink;

@Optional.Interface(iface = "ic2.api.energy.tile.IEnergySink", modid = "IC2")
public interface IPoweredMachine extends IEnergySink, IEnergyHandler
{
	PowerInfo getPowerInfo();

	PowerInterface getInterface();
}
