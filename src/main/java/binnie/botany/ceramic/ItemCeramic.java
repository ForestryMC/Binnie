package binnie.botany.ceramic;

import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.core.items.IColoredItem;

import binnie.botany.genetics.EnumFlowerColor;

public class ItemCeramic extends ItemBlock implements IColoredItem {
	public ItemCeramic(BlockCeramic block) {
		super(block);
	}

	@Override
	public int getMetadata(final int metadata) {
		return metadata;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int getColorFromItemstack(ItemStack stack, int tintIndex) {
		return EnumFlowerColor.get(stack.getMetadata()).getFlowerColorAllele().getColor(false);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getItemStackDisplayName(ItemStack itemStack) {
		return EnumFlowerColor.get(itemStack.getItemDamage()).getFlowerColorAllele().getColorName() + " " + I18n.translateToLocal("tile.botany.ceramic.name").trim();
	}
}
