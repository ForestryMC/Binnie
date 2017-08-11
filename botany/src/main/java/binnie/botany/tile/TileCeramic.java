package binnie.botany.tile;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

import binnie.botany.api.genetics.EnumFlowerColor;
import binnie.botany.modules.ModuleCeramic;

public class TileCeramic extends TileEntity {
	protected EnumFlowerColor color = EnumFlowerColor.Black;

	public EnumFlowerColor getColor() {
		return color;
	}

	public void setColor(EnumFlowerColor color) {
		this.color = color;
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setInteger("Color", color.getFlowerColorAllele().getID());
		return super.writeToNBT(compound);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		color = EnumFlowerColor.VALUES[compound.getInteger("Color")];
	}

	public ItemStack getStack() {
		return getStack(1);
	}

	public ItemStack getStack(int amount) {
		int ID = 0;
		if (color != null) {
			ID = color.getFlowerColorAllele().getID();
		}
		return new ItemStack(ModuleCeramic.ceramic, amount, ID);
	}
}
