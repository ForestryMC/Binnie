// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.machines.base;

import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.IFluidTank;
import binnie.core.machines.inventory.TankSlot;
import binnie.core.machines.power.TankInfo;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.common.util.ForgeDirection;
import binnie.core.machines.power.ITankMachine;

class DefaultTankContainer implements ITankMachine
{
	@Override
	public int fill(final ForgeDirection from, final FluidStack resource, final boolean doFill) {
		return 0;
	}

	@Override
	public FluidStack drain(final ForgeDirection from, final int maxDrain, final boolean doDrain) {
		return null;
	}

	@Override
	public TankInfo[] getTankInfos() {
		return new TankInfo[0];
	}

	@Override
	public boolean isTankReadOnly(final int tank) {
		return false;
	}

	@Override
	public boolean isLiquidValidForTank(final FluidStack liquid, final int tank) {
		return false;
	}

	@Override
	public TankSlot addTank(final int index, final String name, final int capacity) {
		return null;
	}

	@Override
	public IFluidTank getTank(final int index) {
		return null;
	}

	@Override
	public TankSlot getTankSlot(final int slot) {
		return null;
	}

	@Override
	public FluidStack drain(final ForgeDirection from, final FluidStack resource, final boolean doDrain) {
		return null;
	}

	@Override
	public boolean canFill(final ForgeDirection from, final Fluid fluid) {
		return false;
	}

	@Override
	public boolean canDrain(final ForgeDirection from, final Fluid fluid) {
		return false;
	}

	@Override
	public FluidTankInfo[] getTankInfo(final ForgeDirection from) {
		return new FluidTankInfo[0];
	}

	@Override
	public IFluidTank[] getTanks() {
		return new IFluidTank[0];
	}
}
