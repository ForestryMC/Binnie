package binnie.core.liquid;

import binnie.core.BinnieCore;
import binnie.core.Mods;
import binnie.genetics.item.GeneticsItems;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;

import javax.annotation.Nullable;

public enum FluidContainer {
	Capsule,
	Refractory,
	Can,
	Glass,
	Cylinder;

	//	IIcon bottle;
//	IIcon contents;
	ItemFluidContainer item;

	public int getMaxStackSize() {
		return 16;
	}

//	@SideOnly(Side.CLIENT)
//	public void updateIcons(final IIconRegister register) {
//		this.bottle = BinnieCore.proxy.getIcon(register, (this == FluidContainer.Cylinder) ? "binniecore" : "forestry", "liquids/" + this.toString().toLowerCase() + ".bottle");
//		this.contents = BinnieCore.proxy.getIcon(register, (this == FluidContainer.Cylinder) ? "binniecore" : "forestry", "liquids/" + this.toString().toLowerCase() + ".contents");
//	}
//
//	public IIcon getBottleIcon() {
//		return this.bottle;
//	}
//
//	public IIcon getContentsIcon() {
//		return this.contents;
//	}

	public String getName() {
		return BinnieCore.proxy.localise("item.container." + this.name().toLowerCase());
	}

	public boolean isActive() {
		return this.getEmpty() != null;
	}

	public ItemStack getEmpty() {
		switch (this) {
			case Can: {
				return Mods.Forestry.stack("can");
			}
			case Capsule: {
				return Mods.Forestry.stack("capsule");
			}
			case Glass: {
				return new ItemStack(Items.GLASS_BOTTLE, 1, 0);
			}
			case Refractory: {
				return Mods.Forestry.stack("refractory");
			}
			case Cylinder: {
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
		final ItemStack filled = this.item.getContainer(fluid);
		final ItemStack empty = this.getEmpty();
		if (filled == null || empty == null || fluid.get(1000) == null) {
			return;
		}
		final FluidContainerRegistry.FluidContainerData data = new FluidContainerRegistry.FluidContainerData(fluid.get(1000), filled, empty);
		FluidContainerRegistry.registerFluidContainer(data);
	}
}
