package binnie.extrabees.items;

import binnie.extrabees.blocks.type.EnumHiveType;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import javax.annotation.Nonnull;

public class ItemBeehive extends ItemBlock {

	@SuppressWarnings("all")
	public ItemBeehive(@Nonnull final Block block) {
		super(block);
		setRegistryName(block.getRegistryName());
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
		this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
	}

	@Override
	public int getMetadata(final int i) {
		return i;
	}

	@Override
	public void getSubItems(final Item itemIn, final CreativeTabs tab, final NonNullList<ItemStack> subItems) {
		for (int i = 0; i < EnumHiveType.values().length; ++i) {
			subItems.add(new ItemStack(this, 1, i));
		}
	}

	@Override
	public String getItemStackDisplayName(final ItemStack itemStack) {
		return EnumHiveType.values()[itemStack.getItemDamage()].toString() + " Hive";
	}
}
