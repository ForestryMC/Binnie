package binnie.core.machines.power;

import forestry.api.core.INbtReadable;
import forestry.api.core.INbtWritable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;

import javax.annotation.Nullable;

public class TankInfo implements INbtReadable, INbtWritable {
	@Nullable
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

	public static TankInfo[] get(final ITankMachine machine) {
		final TankInfo[] info = new TankInfo[machine.getTanks().length];
		for (int i = 0; i < info.length; ++i) {
			info[i] = new TankInfo(machine.getTanks()[i]);
		}
		return info;
	}

	public float getAmount() {
		return (this.liquid == null) ? 0.0f : this.liquid.amount;
	}

	public float getCapacity() {
		return this.capacity;
	}

	/*public IIcon getIcon() {
		return this.liquid.getFluid().getStillIcon();
	}*/

	public boolean isEmpty() {
		return this.liquid == null;
	}

	public String getName() {
		return (this.liquid == null) ? "" : this.liquid.getFluid().getLocalizedName(this.liquid);
	}

	@Override
	public void readFromNBT(final NBTTagCompound nbt) {
		this.capacity = nbt.getInteger("capacity");
		if (nbt.hasKey("liquid")) {
			this.liquid = FluidStack.loadFluidStackFromNBT(nbt.getCompoundTag("liquid"));
		}
	}

	@Override
	public NBTTagCompound writeToNBT(final NBTTagCompound nbt) {
		nbt.setInteger("capacity", (int) this.getCapacity());
		if (this.liquid == null) {
			return nbt;
		}
		final NBTTagCompound tag = new NBTTagCompound();
		this.liquid.writeToNBT(tag);
		nbt.setTag("liquid", tag);
		return nbt;
	}
}
