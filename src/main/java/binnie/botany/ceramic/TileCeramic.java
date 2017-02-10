package binnie.botany.ceramic;

import binnie.botany.Botany;
import binnie.botany.genetics.EnumFlowerColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileCeramic extends TileEntity {
	protected EnumFlowerColor color = EnumFlowerColor.Black;
	
	public void setColor(EnumFlowerColor color) {
		this.color = color;
	}
	
	public EnumFlowerColor getColor() {
		return color;
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setInteger("Color", color.getID());
		return super.writeToNBT(compound);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		color = EnumFlowerColor.VALUES[compound.getInteger("Color")];
	}
	
	public ItemStack getStack(){
		return getStack(1);
	}
	
	public ItemStack getStack(int amount){
		int ID = 0;
		if(color != null){
			ID = color.getID();
		}
		return new ItemStack(Botany.ceramic, amount, ID);
		
	}
	
}
