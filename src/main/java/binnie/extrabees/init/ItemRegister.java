package binnie.extrabees.init;

import binnie.extrabees.ExtraBees;
import binnie.extrabees.products.*;
import net.minecraft.init.Items;
import net.minecraftforge.oredict.OreDictionary;

/**
 * Created by Elec332 on 12-5-2017.
 */
public final class ItemRegister {

	public static void preInitItems(){
		registerProducts();
	}

	private static void registerProducts(){
		ExtraBees.honeyCrystal = new ItemHoneyCrystal("honey_crystal");
		ExtraBees.honeyCrystalEmpty = new ItemHoneyCrystalEmpty("honey_crystal_empty");
		ExtraBees.honeyDrop = new ItemHoneyDrop();
		ExtraBees.comb = new ItemHoneyComb();
		ExtraBees.propolis = new ItemPropolis();

		ExtraBees.proxy.registerItem(ExtraBees.honeyCrystal);
		ExtraBees.proxy.registerItem(ExtraBees.honeyCrystalEmpty);
		ExtraBees.proxy.registerItem(ExtraBees.comb);
		ExtraBees.proxy.registerItem(ExtraBees.honeyDrop);
		ExtraBees.proxy.registerItem(ExtraBees.propolis);
		ExtraBees.proxy.registerModel(ExtraBees.honeyCrystal, 0);
		ExtraBees.proxy.registerModel(ExtraBees.honeyCrystalEmpty, 0);
		for (EnumHoneyComb c : EnumHoneyComb.values()) {
			if (c.isActive())
				ExtraBees.proxy.registerModel(ExtraBees.comb, c.ordinal());
		}


		OreDictionary.registerOre("ingotIron", Items.IRON_INGOT);
		OreDictionary.registerOre("ingotGold", Items.GOLD_INGOT);
		OreDictionary.registerOre("gemDiamond", Items.DIAMOND);
	}

}
