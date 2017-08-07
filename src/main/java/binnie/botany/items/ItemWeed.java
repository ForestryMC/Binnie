package binnie.botany.items;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import binnie.botany.blocks.PlantType;
import binnie.core.util.I18N;

public class ItemWeed extends ItemBlock {
	public ItemWeed(Block block) {
		super(block);
		setHasSubtypes(true);
		hasSubtypes = true;
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		return I18N.localise("botany.plant." + PlantType.values()[stack.getItemDamage()].getName());
	}

	@Override
	public int getMetadata(int damage) {
		return damage;
	}
}
