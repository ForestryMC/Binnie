package binnie.botany.ceramic;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.core.items.IColoredItem;

import binnie.botany.genetics.EnumFlowerColor;
import binnie.core.block.ItemMetadata;
import binnie.core.block.TileEntityMetadata;

public class ItemStainedGlass extends ItemMetadata implements IColoredItem {
	public ItemStainedGlass(Block block) {
		super(block);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int getColorFromItemstack(ItemStack stack, int tintIndex) {
		EnumFlowerColor color = EnumFlowerColor.get(TileEntityMetadata.getItemDamage(stack));
		return color.getFlowerColorAllele().getColor(false);
	}
}
