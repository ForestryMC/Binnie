package binnie.core.liquid;

import binnie.core.BinnieCore;
import binnie.core.Mods;
import binnie.genetics.item.GeneticsItems;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;

import javax.annotation.Nullable;

public enum FluidContainerType {
	CAPSULE,
	REFARACTORY,
	CAN,
	GLASS,
	CYLINDER;

	@Nullable
	ItemFluidContainer item;

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
		return BinnieCore.proxy.localise("item.container." + this.name().toLowerCase());
	}

	public boolean isActive() {
		return this.getEmpty() != null;
	}

	public ItemStack getEmpty() {
		switch (this) {
			case CAN: {
				return Mods.Forestry.stack("can");
			}
			case CAPSULE: {
				return Mods.Forestry.stack("capsule");
			}
			case GLASS: {
				return new ItemStack(Items.GLASS_BOTTLE, 1, 0);
			}
			case REFARACTORY: {
				return Mods.Forestry.stack("refractory");
			}
			case CYLINDER: {
				return GeneticsItems.Cylinder.get(1);
			}
			default: {
				throw new IllegalArgumentException("Unknown container: " + this);
			}
		}
	}

	@Nullable
	public ItemStack getFilled(Fluid fluid) {
		ItemStack stack = getEmpty();
		if (stack != null) {
			stack = stack.copy();
 			IFluidHandler fluidHandler = FluidUtil.getFluidHandler(stack);
			if (fluidHandler != null) {
				int fill = fluidHandler.fill(new FluidStack(fluid, Integer.MAX_VALUE), true);
				if (fill > 0) {
					return stack;
				}
			}
		}
		return null;
	}

	public void registerContainerData(final IFluidType fluid) {
		if (!this.isActive()) {
			return;
		}
		ItemStack filled = getFilled(FluidRegistry.getFluid(fluid.getIdentifier().toLowerCase()));
		if(filled == null){
			filled = item.getContainer(fluid);
		}
		ItemStack empty = this.getEmpty();
		if (filled == null || empty == null || fluid.get(1000) == null) {
			return;
		}
		final FluidContainerRegistry.FluidContainerData data = new FluidContainerRegistry.FluidContainerData(fluid.get(1000), filled, empty);
		FluidContainerRegistry.registerFluidContainer(data);
	}
}
