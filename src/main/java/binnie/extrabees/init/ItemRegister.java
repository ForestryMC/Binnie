package binnie.extrabees.init;

import binnie.extrabees.ExtraBees;
import binnie.extrabees.alveary.BlockAlveary;
import binnie.extrabees.alveary.EnumAlvearyLogicType;
import binnie.extrabees.blocks.type.EnumHiveType;
import binnie.extrabees.items.ItemBeehive;
import binnie.extrabees.items.ItemHoneyComb;
import binnie.extrabees.items.ItemHoneyCrystal;
import binnie.extrabees.items.ItemHoneyCrystalEmpty;
import binnie.extrabees.items.ItemHoneyDrop;
import binnie.extrabees.items.ItemMiscProduct;
import binnie.extrabees.items.ItemPropolis;
import binnie.extrabees.items.types.EnumHiveFrame;
import binnie.extrabees.items.types.EnumHoneyComb;
import binnie.extrabees.items.types.ExtraBeeItems;
import forestry.api.core.Tabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

public final class ItemRegister {

	public static void preInitItems() {
		registerProducts();
		registerMisc();
		registerOreDict();
	}

	@SuppressWarnings("all")
	private static void registerMisc() {
		Item i = GameRegistry.register(new ItemBlock(ExtraBees.alveary) {

			@Override
			public int getMetadata(int damage) {
				return damage;
			}

			@Override
			public String getUnlocalizedName(ItemStack stack) {
				return ((BlockAlveary) block).getUnlocalizedName(stack.getMetadata());
			}

			@Override
			public boolean getHasSubtypes() {
				return true;
			}
		}.setRegistryName(ExtraBees.alveary.getRegistryName()));
		for (int j = 0; j < EnumAlvearyLogicType.VALUES.length; j++) {
			ExtraBees.proxy.registerModel(i, j);
		}
		i = GameRegistry.register(new ItemBeehive(ExtraBees.hive));
		for (int j = 0; j < EnumHiveType.values().length; j++) {
			ExtraBees.proxy.registerModel(i, j);
		}
		ExtraBees.proxy.registerModel(GameRegistry.register(new ItemBlock(ExtraBees.ectoplasm).setRegistryName(ExtraBees.ectoplasm.getRegistryName())), 0);
		ExtraBees.itemMisc = ExtraBees.proxy.registerItem(new ItemMiscProduct(Tabs.tabApiculture, ExtraBeeItems.values()));
		for (final EnumHiveFrame frame : EnumHiveFrame.values()) {
			ExtraBees.proxy.registerItem(frame.getItem());
		}
	}

	private static void registerProducts() {
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
			if (c.isActive()) {
				ExtraBees.proxy.registerModel(ExtraBees.comb, c.ordinal());
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
