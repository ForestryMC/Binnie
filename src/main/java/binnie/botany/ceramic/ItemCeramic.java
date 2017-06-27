package binnie.botany.ceramic;

import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.core.items.IColoredItem;

import binnie.botany.genetics.EnumFlowerColor;
import binnie.core.util.I18N;

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
		EnumFlowerColor color = EnumFlowerColor.get(stack.getItemDamage());
		return color.getFlowerColorAllele().getColor(false);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getItemStackDisplayName(ItemStack itemStack) {
		EnumFlowerColor color = EnumFlowerColor.get(itemStack.getItemDamage());
		return I18N.localise("botany.ceramic.name", color.getDisplayName());
	}
}
