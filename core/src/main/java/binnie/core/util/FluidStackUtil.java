package binnie.core.util;

import javax.annotation.Nullable;
import java.util.Collection;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import net.minecraft.util.NonNullList;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;

public class FluidStackUtil {

	public static String toString(FluidStack fluidStack){
		if(fluidStack == null){
			return "null";
		}
		NBTTagCompound tag = fluidStack.tag;
		return fluidStack.getFluid().getName() + ':' + fluidStack.amount + (tag != null ? ":" + fluidStack.tag : "");
	}

	public static NonNullList<FluidStack> removeEqualFluids(Collection<FluidStack> fluidsStacks) {
		NonNullList<FluidStack> dedupedFluidStacks = NonNullList.create();
		for (FluidStack fluidStack : fluidsStacks) {
			if (!containsEqualFluid(dedupedFluidStacks, fluidStack)) {
				dedupedFluidStacks.add(fluidStack);
			}
		}
		return dedupedFluidStacks;
	}

	public static boolean containsEqualFluid(NonNullList<FluidStack> fluidStacks, FluidStack query) {
		for (FluidStack fluidStack : fluidStacks) {
			if (fluidStack.isFluidEqual(query)) {
				return true;
			}
		}
		return false;
	}

	@Nullable
	public static ItemStack getContainer(FluidStack fluidStack) {
		ItemStack[] containers = {
			new ItemStack(Items.GLASS_BOTTLE),
			new ItemStack(Items.BUCKET)
		};

		for (ItemStack container : containers) {
			IFluidHandlerItem fluidHandler = FluidUtil.getFluidHandler(container);
			if (fluidHandler != null && fluidHandler.fill(fluidStack, true) > 0) {
				return fluidHandler.getContainer();
			}
		}
		return null;
	}
}
