package binnie.core.machines.inventory;

import net.minecraft.nbt.NBTTagCompound;

import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.IFluidTank;

import binnie.core.util.I18N;

public class TankSlot extends BaseSlot<FluidStack> {
	private FluidTank tank;

	public TankSlot(final int index, final String name, final int capacity) {
		super(index, name);
		this.tank = new FluidTank(capacity);
	}

	@Override
	public void readFromNBT(final NBTTagCompound nbttagcompound) {
		final FluidStack liquid = FluidStack.loadFluidStackFromNBT(nbttagcompound);
		this.setContent(liquid);
	}

	@Override
	public NBTTagCompound writeToNBT(final NBTTagCompound nbttagcompound) {
		if (this.getContent() != null) {
			this.getContent().writeToNBT(nbttagcompound);
		}
		return nbttagcompound;
	}

	@Override
	public FluidStack getContent() {
		return this.tank.getFluid();
	}

	public void setContent(final FluidStack itemStack) {
		this.tank.setFluid(itemStack);
	}

	public IFluidTank getTank() {
		return this.tank;
	}

	@Override
	public String getName() {
		return I18N.localise("binniecore.gui.slot." + this.unlocName);
	}
}
