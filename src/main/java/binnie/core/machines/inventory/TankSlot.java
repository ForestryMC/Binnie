// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.machines.inventory;

import binnie.core.BinnieCore;
import binnie.Binnie;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidStack;

public class TankSlot extends BaseSlot<FluidStack>
{
	public static final String NameJuice = "Juice Tank";
	public static final String NameWater = "Water Tank";
	public static String NameCupboard;
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
	public void writeToNBT(final NBTTagCompound nbttagcompound) {
		if (this.getContent() != null) {
			this.getContent().writeToNBT(nbttagcompound);
		}
	}

	@Override
	public FluidStack getContent() {
		return this.tank.getFluid();
	}

	@Override
	public void setContent(final FluidStack itemStack) {
		this.tank.setFluid(itemStack);
	}

	public IFluidTank getTank() {
		return this.tank;
	}

	@Override
	public String getName() {
		return Binnie.Language.localise(BinnieCore.instance, "gui.slot." + this.unlocName);
	}

	static {
		TankSlot.NameCupboard = "Cupboard Slot";
	}
}
