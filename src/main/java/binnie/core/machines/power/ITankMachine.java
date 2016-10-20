// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.machines.power;

import binnie.core.machines.inventory.TankSlot;
import net.minecraftforge.fluids.IFluidTank;
import binnie.core.machines.inventory.IValidatedTankContainer;
import net.minecraftforge.fluids.IFluidHandler;

public interface ITankMachine extends IFluidHandler, IValidatedTankContainer
{
	TankInfo[] getTankInfos();

	IFluidTank[] getTanks();

	TankSlot addTank(final int p0, final String p1, final int p2);

	IFluidTank getTank(final int p0);

	TankSlot getTankSlot(final int p0);
}
