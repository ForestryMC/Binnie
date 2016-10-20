// 
// Decompiled by Procyon v0.5.30
// 

package binnie.extrabees.worldgen;

import net.minecraft.item.ItemStack;
import java.util.List;
import net.minecraft.item.Item;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

public class ItemBeehive extends ItemBlock
{
	public ItemBeehive(final Block block) {
		super(block);
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
		this.setCreativeTab(CreativeTabs.tabBlock);
	}

	@Override
	public int getMetadata(final int i) {
		return i;
	}

	@Override
	public void getSubItems(final Item par1, final CreativeTabs par2CreativeTabs, final List itemList) {
		for (int i = 0; i < 4; ++i) {
			itemList.add(new ItemStack(this, 1, i));
		}
	}

	@Override
	public String getItemStackDisplayName(final ItemStack itemStack) {
		return EnumHiveType.values()[itemStack.getItemDamage()].toString() + " Hive";
	}
}
