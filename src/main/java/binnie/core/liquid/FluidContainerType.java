package binnie.core.liquid;

import binnie.core.BinnieCore;
import binnie.core.Mods;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;

import javax.annotation.Nullable;

public enum FluidContainerType {
	CAPSULE,
	REFRACTORY,
	CAN,
	GLASS,
	CYLINDER;

	@Nullable
	private ItemFluidContainer item;

	public int getMaxStackSize() {
		return 16;
	}
	
	public static FluidContainerType[] getBinnieContainers(){
		return new FluidContainerType[]{GLASS, CYLINDER};
	}
	
	public String getName() {
		return this.name().toLowerCase();
	}

	public String getDisplayName() {
		return BinnieCore.getBinnieProxy().localise("item.container." + this.name().toLowerCase());
	}

	@Nullable
	public ItemFluidContainer getItem() {
		return item;
	}

	public void setItem(ItemFluidContainer item) {
		this.item = item;
	}
	
	public ItemStack get(int amount){
		if (item == null){
			throw new IllegalArgumentException("Null container item: " + this);
		}
		return new ItemStack(item, amount);
	}

	public ItemStack getEmpty() {
		switch (this) {
			case CAN: {
				return Mods.Forestry.stack("can");
			}
			case CAPSULE: {
				return Mods.Forestry.stack("capsule");
			}
			case CYLINDER:
			case GLASS: {
				if (item != null) {
					return new ItemStack(item);
				} else {
					throw new IllegalArgumentException("Null container item: " + this);
				}
			}
			case REFRACTORY: {
				return Mods.Forestry.stack("refractory");
			}
			default: {
				throw new IllegalArgumentException("Unknown container: " + this);
			}
		}
	}

	public ItemStack getFilled(Fluid fluid) {
		ItemStack stack = getEmpty();
		stack = stack.copy();
		IFluidHandler fluidHandler = FluidUtil.getFluidHandler(stack);
		if (fluidHandler != null) {
			int fill = fluidHandler.fill(new FluidStack(fluid, Integer.MAX_VALUE), true);
			if (fill > 0) {
				return stack;
			}
		}
		throw new IllegalStateException("Could not fill fluid handler for container: " + this);
	}
}
