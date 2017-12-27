package binnie.core.machines.inventory;

import binnie.core.Constants;
import binnie.core.ModId;
import net.minecraft.nbt.NBTTagCompound;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.IFluidTank;

import binnie.core.util.I18N;

public class TankSlot extends BaseSlot<FluidStack> {
	private final FluidTank tank;

	@Deprecated
	public TankSlot(final int index, final String name, final int capacity) {
		this(index, new ResourceLocation(Constants.CORE_MOD_ID, "gui.tank." + name), capacity);
	}

	public TankSlot(final int index, final ResourceLocation name, final int capacity) {
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

	public FluidTank getTank() {
		return this.tank;
	}

	@Override
	public String getName() {
		Validator<FluidStack> validator = getValidator();
		if (validator != null) {
			return I18N.localise(ModId.CORE, "gui.tank.validated", validator.getTooltip());
		}
		if (this.unlocLocation == null) {
			return "";
		}
		return I18N.localise(this.unlocLocation);
	}
}
