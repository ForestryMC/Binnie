package binnie.core.util;

import net.minecraft.nbt.NBTTagCompound;

import net.minecraftforge.fluids.FluidStack;

public class FluidStackUtil {

	public static String toString(FluidStack fluidStack){
		if(fluidStack == null){
			return "null";
		}
		NBTTagCompound tag = fluidStack.tag;
		return fluidStack.getFluid().getName() + ":" + fluidStack.amount + (tag != null ? ":" + fluidStack.tag : "");
	}
}
