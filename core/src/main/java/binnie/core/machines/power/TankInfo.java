package binnie.core.machines.power;

import javax.annotation.Nullable;

import net.minecraft.nbt.NBTTagCompound;

import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;

import forestry.api.core.INbtReadable;
import forestry.api.core.INbtWritable;

public class TankInfo implements INbtReadable, INbtWritable {
	@Nullable
	private FluidStack liquid;
	private float capacity;

	public TankInfo(IFluidTank tank) {
		this.capacity = 0.0f;
		this.capacity = tank.getCapacity();
		this.liquid = tank.getFluid();
	}

	public TankInfo() {
		this.capacity = 0.0f;
	}

	public static TankInfo[] get(ITankMachine machine) {
		TankInfo[] info = new TankInfo[machine.getTanks().length];
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

	public boolean isEmpty() {
		return this.liquid == null;
	}

	public String getName() {
		return (this.liquid == null) ? "" : this.liquid.getFluid().getLocalizedName(this.liquid);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		this.capacity = nbt.getInteger("capacity");
		if (nbt.hasKey("liquid")) {
			this.liquid = FluidStack.loadFluidStackFromNBT(nbt.getCompoundTag("liquid"));
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		nbt.setInteger("capacity", (int) this.getCapacity());
		if (this.liquid == null) {
			return nbt;
		}
		NBTTagCompound tag = new NBTTagCompound();
		this.liquid.writeToNBT(tag);
		nbt.setTag("liquid", tag);
		return nbt;
	}

	@Nullable
	public FluidStack getLiquid() {
		return liquid;
	}
}
