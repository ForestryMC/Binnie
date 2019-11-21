package binnie.core.machines.power;

import buildcraft.api.mj.IMjConnector;
import buildcraft.api.mj.IMjPassiveProvider;
import buildcraft.api.mj.IMjReadable;
import buildcraft.api.mj.IMjReceiver;
import buildcraft.api.mj.IMjRedstoneReceiver;
import forestry.core.config.Constants;
import ic2.api.energy.tile.IEnergySink;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.common.Optional;

@Optional.InterfaceList({
	@Optional.Interface(iface = "ic2.api.energy.tile.IEnergySink", modid = "ic2"),
	@Optional.Interface(iface = "buildcraft.api.mj.IMjReceiver", modid = Constants.BCLIB_MOD_ID),
	@Optional.Interface(iface = "buildcraft.api.mj.IMjConnector", modid = Constants.BCLIB_MOD_ID),
	@Optional.Interface(iface = "buildcraft.api.mj.IMjReadable", modid = Constants.BCLIB_MOD_ID),
	@Optional.Interface(iface = "buildcraft.api.mj.IMjRedstoneReceiver", modid = Constants.BCLIB_MOD_ID),
	@Optional.Interface(iface = "buildcraft.api.mj.IMjPassiveProvider", modid = Constants.BCLIB_MOD_ID),
})
public interface IPoweredMachine extends IEnergyStorage, IEnergySink, IMjReceiver, IMjConnector, IMjReadable, IMjRedstoneReceiver, IMjPassiveProvider {
	PowerInfo getPowerInfo();

	PowerInterface getInterface();

	@Override
	boolean canReceive();
}
