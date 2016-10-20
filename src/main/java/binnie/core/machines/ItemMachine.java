// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.machines;

import net.minecraft.item.ItemStack;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

public class ItemMachine extends ItemBlock
{
	private IBlockMachine associatedBlock;

	public ItemMachine(final Block block) {
		super(block);
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
		this.associatedBlock = (IBlockMachine) block;
	}

	@Override
	public int getMetadata(final int i) {
		return i;
	}

	@Override
	public String getItemStackDisplayName(final ItemStack itemstack) {
		return this.associatedBlock.getMachineName(itemstack.getItemDamage());
	}
}
