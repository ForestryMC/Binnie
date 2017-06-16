package binnie.botany.gardening;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import binnie.Binnie;

public class ItemWeed extends ItemBlock {
	public ItemWeed(Block block) {
		super(block);
		this.setHasSubtypes(true);
		this.hasSubtypes = true;
	}

	@Override
	public String getItemStackDisplayName(final ItemStack stack) {
		return Binnie.LANGUAGE.localise("botany.plant." + BlockPlant.Type.values()[stack.getItemDamage()].getName());
	}

	@Override
	public int getMetadata(int damage) {
		return damage;
	}
}
