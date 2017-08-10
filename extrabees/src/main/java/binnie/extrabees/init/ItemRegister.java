package binnie.extrabees.init;

import net.minecraft.init.Items;
import net.minecraft.item.ItemBlock;

import net.minecraftforge.oredict.OreDictionary;

import forestry.api.core.Tabs;

import binnie.extrabees.ExtraBees;
import binnie.extrabees.items.ItemBeehive;
import binnie.extrabees.items.ItemBlockAlveary;
import binnie.extrabees.items.ItemHoneyComb;
import binnie.extrabees.items.ItemHoneyCrystal;
import binnie.extrabees.items.ItemHoneyDrop;
import binnie.extrabees.items.ItemMiscProduct;
import binnie.extrabees.items.ItemPropolis;
import binnie.extrabees.items.types.EnumHiveFrame;
import binnie.extrabees.items.types.EnumHoneyComb;
import binnie.extrabees.items.types.ExtraBeeItems;

public final class ItemRegister {

	public static void preInitItems() {
		registerProducts();
		registerMisc();
		registerOreDict();
	}

	@SuppressWarnings("all")
	private static void registerMisc() {
		ItemBlockAlveary itemAlveary = new ItemBlockAlveary(ExtraBees.alveary);
		ItemBeehive itemBeehive = new ItemBeehive(ExtraBees.hive);
		ItemBlock ectoplasm = new ItemBlock(ExtraBees.ectoplasm);
		ectoplasm.setRegistryName(ExtraBees.ectoplasm.getRegistryName());
		ItemMiscProduct itemMisc = ExtraBees.itemMisc = new ItemMiscProduct(Tabs.tabApiculture, ExtraBeeItems.values());
		
		ExtraBees.proxy.registerItem(ectoplasm);
		ExtraBees.proxy.registerModel(ectoplasm, 0);
		ExtraBees.proxy.registerItem(itemAlveary);
		ExtraBees.proxy.registerItem(itemBeehive);
		ExtraBees.proxy.registerItem(itemMisc);
		for (final EnumHiveFrame frame : EnumHiveFrame.values()) {
			ExtraBees.proxy.registerItem(frame.getItem());
		}
	}

	private static void registerProducts() {
		ExtraBees.honeyCrystal = new ItemHoneyCrystal();
		ExtraBees.honeyDrop = new ItemHoneyDrop();
		ExtraBees.comb = new ItemHoneyComb();
		ExtraBees.propolis = new ItemPropolis();

		ExtraBees.proxy.registerItem(ExtraBees.honeyCrystal);
		ExtraBees.proxy.registerItem(ExtraBees.comb);
		ExtraBees.proxy.registerItem(ExtraBees.honeyDrop);
		ExtraBees.proxy.registerItem(ExtraBees.propolis);
		for (EnumHoneyComb comb : EnumHoneyComb.values()) {
			if (comb.isActive()) {
				ExtraBees.proxy.registerModel(ExtraBees.comb, comb.ordinal());
			}
		}
	}

	private static void registerOreDict() {
		OreDictionary.registerOre("ingotIron", Items.IRON_INGOT);
		OreDictionary.registerOre("ingotGold", Items.GOLD_INGOT);
		OreDictionary.registerOre("gemDiamond", Items.DIAMOND);

		OreDictionary.registerOre("dyeRed", ExtraBeeItems.RedDye.get(1));
		OreDictionary.registerOre("dyeYellow", ExtraBeeItems.YellowDye.get(1));
		OreDictionary.registerOre("dyeBlue", ExtraBeeItems.BlueDye.get(1));
		OreDictionary.registerOre("dyeGreen", ExtraBeeItems.GreenDye.get(1));
		OreDictionary.registerOre("dyeBlack", ExtraBeeItems.BlackDye.get(1));
		OreDictionary.registerOre("dyeWhite", ExtraBeeItems.WhiteDye.get(1));
		OreDictionary.registerOre("dyeBrown", ExtraBeeItems.BrownDye.get(1));

		OreDictionary.registerOre("gearWood", ExtraBeeItems.ScentedGear.get(1));
	}
}
