package binnie.botany.ceramic.brick;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

import binnie.botany.Botany;
import binnie.botany.genetics.EnumFlowerColor;
import binnie.core.block.TileEntityMetadata;

public class TileCeramicBrick extends TileEntity {
	private EnumFlowerColor colorFirst = EnumFlowerColor.Black;
	private EnumFlowerColor colorSecond = EnumFlowerColor.White;

	public void setColors(EnumFlowerColor colorFirst, EnumFlowerColor colorSecond) {
		this.colorFirst = colorFirst;
		this.colorSecond = colorSecond;
	}

	public boolean hasTwoColors() {
		return CeramicBrickType.VALUES[getBlockMetadata()].canDouble() && colorSecond != colorFirst;
	}

	public ItemStack getStack(int i) {
		ItemStack s = TileEntityMetadata.getItemStack(Botany.gardening().ceramicBrick, ordinal());
		s.setCount(i);
		return s;
	}

	public int ordinal() {
		return colorFirst.ordinal() + colorSecond.ordinal() * 256 + getBlockMetadata() * 256 * 256;
	}

	public CeramicBrickPair pair() {
		return new CeramicBrickPair(colorFirst, colorSecond, CeramicBrickType.VALUES[getBlockMetadata()]);
	}

	public EnumFlowerColor getColorFirst() {
		return colorFirst;
	}

	public EnumFlowerColor getColorSecond() {
		return colorSecond;
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setInteger("First", colorFirst.ordinal());
		compound.setInteger("Second", colorSecond.ordinal());
		return super.writeToNBT(compound);
	}

	@Override
	public NBTTagCompound getUpdateTag() {
		return writeToNBT(new NBTTagCompound());
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		if (compound.hasKey("First")) {
			colorFirst = EnumFlowerColor.VALUES[compound.getInteger("First")];
		}

		if (compound.hasKey("Second")) {
			colorSecond = EnumFlowerColor.VALUES[compound.getInteger("Second")];
		}
	}
}
