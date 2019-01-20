package binnie.extrabees.init;

import net.minecraft.item.ItemBlock;

import net.minecraftforge.oredict.OreDictionary;

import forestry.api.core.Tabs;

import binnie.core.item.ItemMisc;
import binnie.extrabees.ExtraBees;
import binnie.extrabees.items.ItemBeeHive;
import binnie.extrabees.items.ItemDatabaseBee;
import binnie.extrabees.items.ItemHoneyComb;
import binnie.extrabees.items.ItemHoneyCrystal;
import binnie.extrabees.items.ItemHoneyDrop;
import binnie.extrabees.items.ItemPropolis;
import binnie.extrabees.items.types.EnumHoneyComb;
import binnie.extrabees.items.types.ExtraBeeItems;
import binnie.extrabees.modules.ModuleCore;

public final class ItemRegister {

	public static void preInitItems() {
		ModuleCore.honeyCrystal = new ItemHoneyCrystal();
		ExtraBees.proxy.registerItem(ModuleCore.honeyCrystal);
		ModuleCore.honeyDrop = new ItemHoneyDrop();
		ExtraBees.proxy.registerItem(ModuleCore.honeyDrop);
		ModuleCore.comb = new ItemHoneyComb();
		ExtraBees.proxy.registerItem(ModuleCore.comb);
		ModuleCore.propolis = new ItemPropolis();
		ExtraBees.proxy.registerItem(ModuleCore.propolis);
		for (EnumHoneyComb comb : EnumHoneyComb.values()) {
			if (comb.isActive()) {
				ExtraBees.proxy.registerModel(ModuleCore.comb, comb.ordinal());
			}
		}

		ItemBeeHive itemBeeHive = new ItemBeeHive(ModuleCore.hive);
		ItemBlock ectoplasm = new ItemBlock(ModuleCore.ectoplasm);
		ectoplasm.setRegistryName(ModuleCore.ectoplasm.getRegistryName());
		ItemMisc<ExtraBeeItems> itemMisc = ModuleCore.itemMisc = new ItemMisc<>(Tabs.tabApiculture, ExtraBeeItems.values(), "misc");
		ModuleCore.dictionaryBees = new ItemDatabaseBee();

		ExtraBees.proxy.registerItem(ectoplasm);
		ExtraBees.proxy.registerModel(ectoplasm, 0);
		ExtraBees.proxy.registerItem(itemBeeHive);
		ExtraBees.proxy.registerItem(itemMisc);
		ExtraBees.proxy.registerItem(ModuleCore.dictionaryBees);


		OreDictionary.registerOre("dyeRed", ExtraBeeItems.RED_DYE.stack(1));
		OreDictionary.registerOre("dyeYellow", ExtraBeeItems.YELLOW_DYE.stack(1));
		OreDictionary.registerOre("dyeBlue", ExtraBeeItems.BLUE_DYE.stack(1));
		OreDictionary.registerOre("dyeGreen", ExtraBeeItems.GREEN_DYE.stack(1));
		OreDictionary.registerOre("dyeBlack", ExtraBeeItems.BLACK_DYE.stack(1));
		OreDictionary.registerOre("dyeWhite", ExtraBeeItems.WHITE_DYE.stack(1));
		OreDictionary.registerOre("dyeBrown", ExtraBeeItems.BROWN_DYE.stack(1));

		OreDictionary.registerOre("binnie_database", ModuleCore.dictionaryBees);
	}
}
