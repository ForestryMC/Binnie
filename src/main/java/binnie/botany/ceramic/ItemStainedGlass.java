package binnie.botany.ceramic;

import binnie.botany.genetics.EnumFlowerColor;
import binnie.core.block.ItemMetadata;
import binnie.core.block.TileEntityMetadata;
import forestry.core.items.IColoredItem;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemStainedGlass extends ItemMetadata implements IColoredItem {
	public ItemStainedGlass(Block block) {
		super(block);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int getColorFromItemstack(ItemStack stack, int tintIndex) {
		return EnumFlowerColor.get(TileEntityMetadata.getItemDamage(stack)).getColor(false);
	}
}
