package binnie.core.machines.inventory;

import binnie.Binnie;
import binnie.core.BinnieCore;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.IFluidTank;

public class TankSlot extends BaseSlot<FluidStack> {
	public static String NameJuice = "Juice Tank";
	public static String NameWater = "Water Tank";
	public static String NameCupboard = "Cupboard Slot";
	private FluidTank tank;

	public TankSlot(int index, String name, int capacity) {
		super(index, name);
		tank = new FluidTank(capacity);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound) {
		FluidStack liquid = FluidStack.loadFluidStackFromNBT(nbttagcompound);
		setContent(liquid);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound) {
		FluidStack content = getContent();
		if (content != null) {
			content.writeToNBT(nbttagcompound);
		}
	}

	@Override
	public FluidStack getContent() {
		return tank.getFluid();
	}

	@Override
	public void setContent(FluidStack itemStack) {
		tank.setFluid(itemStack);
	}

	public IFluidTank getTank() {
		return tank;
	}

	@Override
	public String getName() {
		return Binnie.I18N.localise(BinnieCore.instance, "gui.slot." + unlocName);
	}
}
