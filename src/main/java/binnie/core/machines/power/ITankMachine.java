package binnie.core.machines.power;

import binnie.core.machines.inventory.IValidatedTankContainer;
import binnie.core.machines.inventory.TankSlot;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.IFluidHandler;

public interface ITankMachine extends IValidatedTankContainer {
	TankInfo[] getTankInfos();

	IFluidTank[] getTanks();

	TankSlot addTank(final int p0, final String p1, final int p2);

	IFluidTank getTank(final int p0);

	TankSlot getTankSlot(final int p0);
	
	IFluidHandler getHandler(EnumFacing from);
}
