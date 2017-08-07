package binnie.extrabees.items;

import javax.annotation.Nonnull;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import net.minecraftforge.client.model.ModelLoader;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.extrabees.blocks.type.EnumHiveType;

public class ItemBeehive extends ItemBlock implements IItemModelProvider {

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
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if (this.isInCreativeTab(tab)) {
			for (int i = 0; i < EnumHiveType.values().length; ++i) {
				items.add(new ItemStack(this, 1, i));
			}
		}
	}

	@Override
	public String getItemStackDisplayName(final ItemStack itemStack) {
		return EnumHiveType.values()[itemStack.getItemDamage()].toString() + " Hive";
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerModel(Item item) {
		int i = 0;
		for(EnumHiveType type : EnumHiveType.values()){
			ModelLoader.setCustomModelResourceLocation(item, i, new ModelResourceLocation("extrabees:hive", "type=" + type.getName()));
			i++;
		}
	}
}
