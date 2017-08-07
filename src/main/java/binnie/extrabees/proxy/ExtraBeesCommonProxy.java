package binnie.extrabees.proxy;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.text.translation.I18n;

import net.minecraftforge.fml.common.registry.ForgeRegistries;

import binnie.extrabees.genetics.ExtraBeeDefinition;

public class ExtraBeesCommonProxy {

	public void registerBeeModel(ExtraBeeDefinition type) {
	}

	public Block registerBlock(Block block) {
		ForgeRegistries.BLOCKS.register(block);
		return block;
	}

	public Item registerItem(Item item) {
		ForgeRegistries.ITEMS.register(item);
		return item;
	}

	@SuppressWarnings("deprecation")
	public String localise(String s) {
		return I18n.translateToLocal("extrabees." + s);
	}

	public String localiseWithOutPrefix(String s) {
		return I18n.translateToLocal(s);
	}

	public void registerModel(Item item, int meta) {
	}
}
