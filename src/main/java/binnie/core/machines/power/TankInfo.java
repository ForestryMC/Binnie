// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.machines.power;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.FluidStack;
import forestry.api.core.INBTTagable;

public class TankInfo implements INBTTagable
{
	public FluidStack liquid;
	private float capacity;

	public TankInfo(final IFluidTank tank) {
		this.capacity = 0.0f;
		this.capacity = tank.getCapacity();
		this.liquid = tank.getFluid();
	}

	public TankInfo() {
		this.capacity = 0.0f;
	}

	public float getAmount() {
		return (this.liquid == null) ? 0.0f : this.liquid.amount;
	}

	public float getCapacity() {
		return this.capacity;
	}

	public boolean isEmpty() {
		return this.liquid == null;
	}

	public IIcon getIcon() {
		return this.liquid.getFluid().getStillIcon();
	}

	public String getName() {
		return (this.liquid == null) ? "" : this.liquid.getFluid().getLocalizedName();
	}

	@Override
	public void readFromNBT(final NBTTagCompound nbt) {
		this.capacity = nbt.getInteger("capacity");
		if (nbt.hasKey("liquid")) {
			this.liquid = FluidStack.loadFluidStackFromNBT(nbt.getCompoundTag("liquid"));
		}
	}

	@Override
	public void writeToNBT(final NBTTagCompound nbt) {
		nbt.setInteger("capacity", (int) this.getCapacity());
		if (this.liquid == null) {
			return;
		}
		final NBTTagCompound tag = new NBTTagCompound();
		this.liquid.writeToNBT(tag);
		nbt.setTag("liquid", tag);
	}

	public static TankInfo[] get(final ITankMachine machine) {
		final TankInfo[] info = new TankInfo[machine.getTanks().length];
		for (int i = 0; i < info.length; ++i) {
			info[i] = new TankInfo(machine.getTanks()[i]);
		}
		return info;
	}
}
