package binnie.extratrees.carpentry;

import net.minecraft.item.ItemStack;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.core.items.IColoredItem;

import binnie.core.block.ItemMetadata;
import binnie.core.api.block.TileEntityMetadata;
import binnie.extratrees.modules.ModuleCarpentry;

public class ItemDesign extends ItemMetadata implements IColoredItem {

	public ItemDesign(BlockDesign block) {
		super(block);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int getColorFromItemstack(ItemStack stack, int tintIndex) {
		DesignBlock block = ModuleCarpentry.getDesignBlock(((BlockDesign) this.block).getDesignSystem(), TileEntityMetadata.getItemDamage(stack));
		if (tintIndex > 0) {
			return block.getSecondaryColour();
		}
		return block.getPrimaryColour();
	}
}
