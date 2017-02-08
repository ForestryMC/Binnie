package binnie.extratrees.machines.brewery;

import forestry.api.core.INbtReadable;
import forestry.api.core.INbtWritable;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;

public class BreweryCrafting implements INbtWritable {
	@Nullable
	public FluidStack inputFluid;
	public ItemStack[] inputGrains;
	@Nullable
	public ItemStack ingredient;
	@Nullable
	public ItemStack yeast;

	public BreweryCrafting(@Nullable final FluidStack inputFluid, @Nullable final ItemStack ingredient, @Nullable final ItemStack[] inputGrains, final @Nullable ItemStack yeast) {
		this.inputFluid = inputFluid;
		this.inputGrains = ((inputGrains == null) ? new ItemStack[3] : inputGrains);
		this.ingredient = ingredient;
		this.yeast = yeast;
	}

	public static BreweryCrafting create(final NBTTagCompound nbt) {
		FluidStack inputFluid = FluidStack.loadFluidStackFromNBT(nbt.getCompoundTag("fluid"));
		ItemStack ingredient = new ItemStack(nbt.getCompoundTag("ingr"));
		ItemStack[] inputGrains = new ItemStack[] {
				new ItemStack(nbt.getCompoundTag("in1")),
				new ItemStack(nbt.getCompoundTag("in2")),
				new ItemStack(nbt.getCompoundTag("in3"))
		};
		ItemStack yeast = new ItemStack(nbt.getCompoundTag("yeast"));
		return new BreweryCrafting(inputFluid, ingredient, inputGrains, yeast);
	}

	@Override
	public NBTTagCompound writeToNBT(final NBTTagCompound nbt) {
		if (this.inputFluid != null) {
			final NBTTagCompound fluidTag = new NBTTagCompound();
			this.inputFluid.writeToNBT(fluidTag);
			nbt.setTag("fluid", fluidTag);
		}
		nbt.setTag("ingr", this.getNBT(this.ingredient));
		nbt.setTag("in1", this.getNBT(this.inputGrains[0]));
		nbt.setTag("in2", this.getNBT(this.inputGrains[1]));
		nbt.setTag("in3", this.getNBT(this.inputGrains[2]));
		nbt.setTag("yeast", this.getNBT(this.yeast));
		return nbt;
	}

	private NBTTagCompound getNBT(@Nullable final ItemStack ingr) {
		if (ingr == null) {
			return new NBTTagCompound();
		}
		final NBTTagCompound nbt = new NBTTagCompound();
		ingr.writeToNBT(nbt);
		return nbt;
	}
}
