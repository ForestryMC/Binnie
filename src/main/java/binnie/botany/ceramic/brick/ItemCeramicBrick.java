package binnie.botany.ceramic.brick;

import forestry.core.items.IColoredItem;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemCeramicBrick extends ItemBlock implements IColoredItem {
	public ItemCeramicBrick(BlockCeramicBrick block) {
		super(block);
	}

	@Override
	public int getMetadata(final int metadata) {
		return metadata;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int getColorFromItemstack(ItemStack stack, int tintIndex) {
		CeramicBrickPair ceramic = new CeramicBrickPair(stack.getItemDamage());
		if (tintIndex == 1) {
			return ceramic.colorFirst.getColor(false);
		} else if (tintIndex == 2) {
			return ceramic.colorSecond.getColor(false);
		}
		return 0xffffff;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getItemStackDisplayName(ItemStack itemStack) {
		return new CeramicBrickPair(itemStack.getItemDamage()).getName();
	}
}
