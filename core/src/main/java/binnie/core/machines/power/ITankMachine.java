package binnie.core.machines.power;

import javax.annotation.Nullable;

import net.minecraft.util.EnumFacing;

import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.IFluidHandler;

import binnie.core.machines.inventory.IValidatedTankContainer;
import binnie.core.machines.inventory.TankSlot;

public interface ITankMachine extends IValidatedTankContainer {
	TankInfo[] getTankInfos();

	IFluidTank[] getTanks();

	TankSlot addTank(final int index, final String p1, final int p2);

	IFluidTank getTank(final int index);

	@Nullable
	TankSlot getTankSlot(final int index);

	@Nullable
	IFluidHandler getHandler(@Nullable EnumFacing from);
}
