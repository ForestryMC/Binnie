package binnie.extratrees.genetics;

import binnie.core.resource.BinnieIcon;

public enum FruitSprite //implements IIconProvider
{
	Tiny,
	Small,
	Average,
	Large,
	Larger,
	Pear;

	BinnieIcon icon;

	public short getIndex() {
		return (short) (this.ordinal() + 4200);
	}

//	@Override
//	@SideOnly(Side.CLIENT)
//	public IIcon getIcon(final short texUID) {
//		final int index = texUID - 4200;
//		if (index >= 0 && index < values().length) {
//			return values()[index].icon.getIcon();
//		}
//		return null;
//	}
//
//	@Override
//	@SideOnly(Side.CLIENT)
//	public void registerIcons(final IIconRegister register) {
//		this.icon = Binnie.Resource.getBlockIcon(ExtraTrees.instance, "fruit/" + this.toString().toLowerCase());
//	}
}
