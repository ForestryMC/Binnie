package binnie.extrabees.proxy;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class ExtraBeesCommonProxy {
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
