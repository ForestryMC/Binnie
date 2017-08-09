package binnie.extrabees.items;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import net.minecraftforge.client.model.ModelLoader;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.extrabees.alveary.BlockAlveary;
import binnie.extrabees.alveary.EnumAlvearyLogicType;

public class ItemBlockAlveary extends ItemBlock implements IItemModelProvider {
	public ItemBlockAlveary(Block block) {
		super(block);
		setRegistryName(block.getRegistryName());
		setHasSubtypes(true);
	}
	
	@Override
	public int getMetadata(int damage) {
		return damage;
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return ((BlockAlveary) block).getUnlocalizedName(stack.getMetadata());
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerModel(Item item) {
		int i = 0;
		for(EnumAlvearyLogicType type : EnumAlvearyLogicType.values()){
			ModelLoader.setCustomModelResourceLocation(item, i, new ModelResourceLocation("extrabees:alveary", "type=" + type.getName()));
			i++;
		}
	}
}
