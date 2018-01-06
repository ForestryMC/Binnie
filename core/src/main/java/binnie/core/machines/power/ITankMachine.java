package binnie.core.machines.power;

import javax.annotation.Nullable;

import net.minecraft.util.EnumFacing;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.IFluidHandler;

import binnie.core.machines.inventory.IValidatedTankContainer;
import binnie.core.machines.inventory.TankSlot;

public interface ITankMachine extends IValidatedTankContainer {
	TankInfo[] getTankInfos();

	IFluidTank[] getTanks();

	/**
	 * @deprecated use {@link #addTank(int, ResourceLocation, int)}
	 */
	@Deprecated
	TankSlot addTank(final int index, final String name, final int capacity);

	TankSlot addTank(final int index, final ResourceLocation name, final int capacity);

	IFluidTank getTank(final int index);

	@Nullable
	TankSlot getTankSlot(final int index);

	@Nullable
	IFluidHandler getHandler(@Nullable EnumFacing from);

	@Nullable
	IFluidHandler getHandler(int[] targetTanks);
}
