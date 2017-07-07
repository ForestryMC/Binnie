package binnie.extratrees.machines.brewery;

import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import net.minecraftforge.fluids.FluidStack;

import forestry.api.core.INbtWritable;

public class BreweryCrafting implements INbtWritable {
	@Nullable
	public FluidStack inputFluid;
	@Nullable
	public ItemStack[] inputGrains;
	public ItemStack ingredient;
	public ItemStack yeast;

	public BreweryCrafting( @Nullable final FluidStack inputFluid, final ItemStack ingredient, @Nullable final ItemStack[] inputGrains, final ItemStack yeast) {
		this.inputFluid = inputFluid;
		this.inputGrains = ((inputGrains == null) ? new ItemStack[3] : inputGrains);
		this.ingredient = ingredient;
		this.yeast = yeast;
	}

	public static BreweryCrafting create(final NBTTagCompound nbt) {
		FluidStack inputFluid = FluidStack.loadFluidStackFromNBT(nbt.getCompoundTag("fluid"));
		ItemStack ingredient = new ItemStack(nbt.getCompoundTag("ingr"));
		ItemStack[] inputGrains = new ItemStack[]{
			new ItemStack(nbt.getCompoundTag("in1")),
			new ItemStack(nbt.getCompoundTag("in2")),
			new ItemStack(nbt.getCompoundTag("in3"))
		};
		ItemStack yeast = new ItemStack(nbt.getCompoundTag("yeast"));
		return new BreweryCrafting(inputFluid, ingredient, inputGrains, yeast);
	}
	
	public boolean hasInputGrainsEmpty(){
		return inputGrains == null || (isEmptyStack(inputGrains[0]) && isEmptyStack(inputGrains[1]) && isEmptyStack(inputGrains[2]));
	}

	private boolean isEmptyStack(ItemStack stack){
		return stack == null || stack.isEmpty();
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

	private NBTTagCompound getNBT(final ItemStack ingr) {
		if (ingr.isEmpty()) {
			return new NBTTagCompound();
		}
		final NBTTagCompound nbt = new NBTTagCompound();
		ingr.writeToNBT(nbt);
		return nbt;
	}
}
