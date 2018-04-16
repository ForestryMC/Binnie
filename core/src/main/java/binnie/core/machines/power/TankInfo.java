package binnie.core.machines.power;

import javax.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;

import net.minecraft.nbt.NBTTagCompound;

import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;

import forestry.api.core.INbtReadable;
import forestry.api.core.INbtWritable;

public class TankInfo implements INbtReadable, INbtWritable {
	@Nullable
	private FluidStack liquid;
	private float capacity;

	public TankInfo(final IFluidTank tank) {
		this.capacity = tank.getCapacity();
		this.liquid = tank.getFluid();
	}

	public TankInfo() {
		this.capacity = 0.0f;
	}

	public static TankInfo[] get(final ITankMachine machine) {
		IFluidTank[] fluidTanks = machine.getTanks();
		TankInfo[] info = new TankInfo[fluidTanks.length];
		for (int i = 0; i < info.length; ++i) {
			info[i] = new TankInfo(fluidTanks[i]);
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
		return (this.liquid == null) ? StringUtils.EMPTY : this.liquid.getFluid().getLocalizedName(this.liquid);
	}

	private static final String NBT_KEY_CAPACITY = "capacity";
	private static final String NBT_KEY_LIQUID = "liquid";

	@Override
	public void readFromNBT(final NBTTagCompound nbt) {
		this.capacity = nbt.getInteger(NBT_KEY_CAPACITY);
		if (nbt.hasKey(NBT_KEY_LIQUID)) {
			this.liquid = FluidStack.loadFluidStackFromNBT(nbt.getCompoundTag(NBT_KEY_LIQUID));
		}
	}

	@Override
	public NBTTagCompound writeToNBT(final NBTTagCompound nbt) {
		nbt.setInteger(NBT_KEY_CAPACITY, (int) this.getCapacity());
		if (this.liquid == null) {
			return nbt;
		}
		final NBTTagCompound tag = new NBTTagCompound();
		this.liquid.writeToNBT(tag);
		nbt.setTag(NBT_KEY_LIQUID, tag);
		return nbt;
	}

	@Nullable
	public FluidStack getLiquid() {
		return liquid;
	}
}
