package binnie.extrabees.proxy;

import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;

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

	public String localise(String s) {
		return "extrabees." + s;
	}

	public String localiseWithOutPrefix(String s) {
		return s;
	}

	public void registerModel(Item item, int meta) {
	}
}
