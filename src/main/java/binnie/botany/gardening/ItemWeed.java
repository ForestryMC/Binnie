package binnie.botany.gardening;

import binnie.core.util.I18N;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

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
