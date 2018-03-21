package binnie.extrabees.proxy;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

import net.minecraftforge.fml.common.registry.ForgeRegistries;
import binnie.extrabees.ExtraBees;

public class ExtraBeesCommonProxy {
	public Block registerBlock(Block block) {
		ForgeRegistries.BLOCKS.register(block);
		return block;
	}

	public Item registerItem(Item item) {
		ForgeRegistries.ITEMS.register(item);
		return item;
	}

	public String localise(final String s) {
		return String.format("%s.%s", ExtraBees.MODID, s);
	}

	public String localiseWithOutPrefix(final String s) {
		return s;
	}

	public void registerModel(Item item, int meta) {
	}
}
