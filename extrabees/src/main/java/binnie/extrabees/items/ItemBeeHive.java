package binnie.extrabees.items;

import binnie.extrabees.blocks.type.EnumHiveType;
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

import javax.annotation.Nonnull;

public class ItemBeeHive extends ItemBlock implements IItemModelProvider {

	public ItemBeeHive(@Nonnull final Block block) {
		super(block);
		setRegistryName(block.getRegistryName());
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
		this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
	}

	@Override
	public int getMetadata(int damage) {
		return damage;
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if (this.isInCreativeTab(tab)) {
			for (EnumHiveType type : EnumHiveType.values()) {
				items.add(new ItemStack(this, 1, type.getMeta()));
			}
		}
	}

	@Override
	public String getTranslationKey(ItemStack stack) {
		EnumHiveType type = EnumHiveType.getHiveTypeForMeta(stack.getItemDamage());
		return "extrabees.block.hive." + type.getName();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerModel(Item item) {
		for (EnumHiveType type : EnumHiveType.values()) {
			ModelLoader.setCustomModelResourceLocation(item, type.getMeta(), new ModelResourceLocation("extrabees:hive", "type=" + type.getName()));
		}
	}
}
