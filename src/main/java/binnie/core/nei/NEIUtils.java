package binnie.core.nei;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.*;

public class NEIUtils {
    public static FluidStack getFluidStack(ItemStack stack) {
        if (stack != null) {
            FluidStack fluidStack = null;
            if (stack.getItem() instanceof IFluidContainerItem) {
                fluidStack = ((IFluidContainerItem) stack.getItem()).getFluid(stack);
            }

            if (fluidStack == null) {
                fluidStack = FluidContainerRegistry.getFluidForFilledItem(stack);
            }

            if (fluidStack == null && Block.getBlockFromItem(stack.getItem()) instanceof IFluidBlock) {
                Fluid fluid = ((IFluidBlock) Block.getBlockFromItem(stack.getItem())).getFluid();
                if (fluid != null) {
                    fluidStack = new FluidStack(fluid, 1000);
                }
            }

            return fluidStack;
        } else {
            return null;
        }
    }

    public static boolean areFluidsSameType(FluidStack fluidStack1, FluidStack fluidStack2) {
        if (fluidStack1 != null && fluidStack2 != null) {
            return fluidStack1.getFluid() == fluidStack2.getFluid();
        } else {
            return false;
        }
    }
}
