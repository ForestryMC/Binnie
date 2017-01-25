package binnie.extratrees.item;

import binnie.Binnie;
import binnie.core.BinnieCore;
import binnie.core.IInitializable;
import binnie.core.Mods;
import binnie.core.item.ItemMisc;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.block.EnumETLog;
import forestry.api.core.Tabs;
import forestry.api.fuels.EngineBronzeFuel;
import forestry.api.fuels.FuelManager;
import forestry.api.recipes.RecipeManagers;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class ModuleItems implements IInitializable {
	
	public Item itemDictionary;
	public Item itemDictionaryLepi;
	public ItemMisc itemMisc;
	public Item itemFood;
	public Item itemHammer;
	public Item itemDurableHammer;
	
	@Override
	public void preInit() {
		itemMisc = new ItemMisc(Tabs.tabArboriculture, ExtraTreeItems.values());
		ExtraTrees.proxy.registerItem(itemMisc);

		itemDictionary = new ItemArboristDatabase();
		ExtraTrees.proxy.registerItem(itemDictionary);

		if (BinnieCore.isLepidopteryActive()) {
			itemDictionaryLepi = new ItemMothDatabase();
			ExtraTrees.proxy.registerItem(itemDictionaryLepi);
		}
		Binnie.LIQUID.createLiquids(ExtraTreeLiquid.values());
		itemFood = new ItemETFood();
		ExtraTrees.proxy.registerItem(itemFood);

		itemHammer = new ItemHammer(false);
		ExtraTrees.proxy.registerItem(itemHammer);
		itemDurableHammer = new ItemHammer(true);
		ExtraTrees.proxy.registerItem(itemDurableHammer);
	}

	@Override
	public void init() {

		OreDictionary.registerOre("pulpWood", ExtraTreeItems.Sawdust.get(1));
		Food.registerOreDictionary();
		OreDictionary.registerOre("cropApple", Items.APPLE);
		OreDictionary.registerOre("cropHops", ExtraTreeItems.Hops.get(1));
		OreDictionary.registerOre("seedWheat", Items.WHEAT_SEEDS);
		OreDictionary.registerOre("seedWheat", ExtraTreeItems.GrainWheat.get(1));
		OreDictionary.registerOre("seedBarley", ExtraTreeItems.GrainBarley.get(1));
		OreDictionary.registerOre("seedCorn", ExtraTreeItems.GrainCorn.get(1));
		OreDictionary.registerOre("seedRye", ExtraTreeItems.GrainRye.get(1));
		OreDictionary.registerOre("seedRoasted", ExtraTreeItems.GrainRoasted.get(1));
		
		OreDictionary.registerOre("gearWood", ExtraTreeItems.ProvenGear.get(1));

		Food.CRABAPPLE.addJuice(10, 150, 10);
		Food.ORANGE.addJuice(10, 400, 15);
		Food.KUMQUAT.addJuice(10, 300, 10);
		Food.LIME.addJuice(10, 300, 10);
		Food.WILD_CHERRY.addOil(20, 50, 5);
		Food.SOUR_CHERRY.addOil(20, 50, 3);
		Food.BLACK_CHERRY.addOil(20, 50, 5);
		Food.Blackthorn.addJuice(10, 50, 5);
		Food.CHERRY_PLUM.addJuice(10, 100, 60);
		Food.ALMOND.addOil(20, 80, 5);
		Food.APRICOT.addJuice(10, 150, 40);
		Food.GRAPEFRUIT.addJuice(10, 500, 15);
		Food.PEACH.addJuice(10, 150, 40);
		Food.SATSUMA.addJuice(10, 300, 10);
		Food.BUDDHA_HAND.addJuice(10, 400, 15);
		Food.CITRON.addJuice(10, 400, 15);
		Food.FINGER_LIME.addJuice(10, 300, 10);
		Food.KEY_LIME.addJuice(10, 300, 10);
		Food.MANDERIN.addJuice(10, 400, 10);
		Food.NECTARINE.addJuice(10, 150, 40);
		Food.POMELO.addJuice(10, 300, 10);
		Food.TANGERINE.addJuice(10, 300, 10);
		Food.PEAR.addJuice(10, 300, 20);
		Food.SAND_PEAR.addJuice(10, 200, 10);
		Food.HAZELNUT.addOil(20, 150, 5);
		Food.BUTTERNUT.addOil(20, 180, 5);
		Food.BEECHNUT.addOil(20, 100, 4);
		Food.PECAN.addOil(29, 50, 2);
		Food.BANANA.addJuice(10, 100, 30);
		Food.RED_BANANA.addJuice(10, 100, 30);
		Food.PLANTAIN.addJuice(10, 100, 40);
		Food.BRAZIL_NUT.addOil(20, 20, 2);
		Food.FIG.addOil(20, 50, 3);
		Food.ACORN.addOil(20, 50, 3);
		Food.ELDERBERRY.addJuice(10, 100, 5);
		Food.OLIVE.addOil(20, 50, 3);
		Food.GINGKO_NUT.addOil(20, 50, 5);
		Food.COFFEE.addOil(15, 20, 2);
		Food.OSANGE_ORANGE.addJuice(10, 300, 15);
		Food.CLOVE.addOil(10, 25, 2);
		Food.COCONUT.addOil(20, 300, 25);
		Food.CASHEW.addJuice(10, 150, 15);
		Food.AVACADO.addJuice(10, 300, 15);
		Food.NUTMEG.addOil(20, 50, 10);
		Food.ALLSPICE.addOil(20, 50, 10);
		Food.CHILLI.addJuice(10, 100, 10);
		Food.STAR_ANISE.addOil(20, 100, 10);
		Food.MANGO.addJuice(10, 400, 20);
		Food.STARFRUIT.addJuice(10, 300, 10);
		Food.CANDLENUT.addJuice(20, 50, 10);
	}

	@Override
	public void postInit() {
		ModuleItems items = ExtraTrees.items();
		
		CraftingManager.getInstance().getRecipeList().add(new ShapedOreRecipe(new ItemStack(items.itemDurableHammer, 1, 0), "wiw", " s ", " s ", 'w', Blocks.OBSIDIAN, 'i', Items.GOLD_INGOT, 's', Items.STICK));
		CraftingManager.getInstance().getRecipeList().add(new ShapedOreRecipe(new ItemStack(items.itemHammer, 1, 0), "wiw", " s ", " s ", 'w', "plankWood", 'i', Items.IRON_INGOT, 's', Items.STICK));
		CraftingManager.getInstance().getRecipeList().add(new ShapedOreRecipe(ExtraTreeItems.Yeast.get(8), " m ", "mbm", 'b', Items.BREAD, 'm', Blocks.BROWN_MUSHROOM));
		CraftingManager.getInstance().getRecipeList().add(new ShapedOreRecipe(ExtraTreeItems.LagerYeast.get(8), "mbm", " m ", 'b', Items.BREAD, 'm', Blocks.BROWN_MUSHROOM));
		GameRegistry.addRecipe(ExtraTreeItems.GrainWheat.get(5), " s ", "sss", " s ", 's', Items.WHEAT_SEEDS);
		GameRegistry.addRecipe(ExtraTreeItems.GrainBarley.get(3), " s ", "s  ", " s ", 's', ExtraTreeItems.GrainWheat.get(1));
		GameRegistry.addRecipe(ExtraTreeItems.GrainCorn.get(3), " s ", "  s", " s ", 's', ExtraTreeItems.GrainWheat.get(1));
		GameRegistry.addRecipe(ExtraTreeItems.GrainRye.get(3), "   ", "s s", " s ", 's', ExtraTreeItems.GrainWheat.get(1));
		GameRegistry.addRecipe(ExtraTreeItems.Hops.get(3), " s ", "sps", " s ", 's', Items.WHEAT_SEEDS, 'p', Items.APPLE);
		GameRegistry.addRecipe(ExtraTreeItems.ProvenGear.get(1), " s ", "s s", " s ", 's', Mods.Forestry.stack("oakStick"));
		GameRegistry.addRecipe(ExtraTreeItems.GlassFitting.get(6), "s s", " i ", "s s", 'i', Items.IRON_INGOT, 's', Items.STICK);
		GameRegistry.addSmelting(ExtraTreeItems.GrainWheat.get(1), ExtraTreeItems.GrainRoasted.get(1), 0.0f);
		GameRegistry.addSmelting(ExtraTreeItems.GrainRye.get(1), ExtraTreeItems.GrainRoasted.get(1), 0.0f);
		GameRegistry.addSmelting(ExtraTreeItems.GrainCorn.get(1), ExtraTreeItems.GrainRoasted.get(1), 0.0f);
		GameRegistry.addSmelting(ExtraTreeItems.GrainBarley.get(1), ExtraTreeItems.GrainRoasted.get(1), 0.0f);
		try {
			final Item minium = (Item) Class.forName("com.pahimar.ee3.lib.ItemIds").getField("miniumShard").get(null);
			CraftingManager.getInstance().getRecipeList().add(new ShapelessOreRecipe(Food.PAPAYIMAR.get(1), minium, "cropPapaya"));
		} catch (Exception ex) {
		}
		RecipeManagers.carpenterManager.addRecipe(100, Binnie.LIQUID.getFluidStack("water", 2000), null, new ItemStack(items.itemDictionary), "X#X", "YEY", "RDR", '#', Blocks.GLASS_PANE, 'X', Items.GOLD_INGOT, 'Y', "ingotCopper", 'R', Items.REDSTONE, 'D', Items.DIAMOND, 'E', Items.EMERALD);
		RecipeManagers.carpenterManager.addRecipe(100, Binnie.LIQUID.getFluidStack("water", 2000), null, new ItemStack(items.itemDictionaryLepi), "X#X", "YEY", "RDR", '#', Blocks.GLASS_PANE, 'X', Items.GOLD_INGOT, 'Y', "ingotBronze", 'R', Items.REDSTONE, 'D', Items.DIAMOND, 'E', Items.EMERALD);
		RecipeManagers.stillManager.addRecipe(25, ExtraTreeLiquid.Resin.get(5), ExtraTreeLiquid.Turpentine.get(3));
		RecipeManagers.carpenterManager.addRecipe(25, ExtraTreeLiquid.Turpentine.get(50), null, items.itemMisc.getStack(ExtraTreeItems.WoodWax, 4), "x", 'x', Mods.Forestry.stack("beeswax"));
		if (Binnie.LIQUID.getFluidStack("Creosote Oil", 100) != null) {
			RecipeManagers.carpenterManager.addRecipe(25, Binnie.LIQUID.getFluidStack("Creosote Oil", 50), null, items.itemMisc.getStack(ExtraTreeItems.WoodWax, 1), "x", 'x', Mods.Forestry.stack("beeswax"));
		}
		for (final FluidContainerRegistry.FluidContainerData data : FluidContainerRegistry.getRegisteredFluidContainerData()) {
			if (data.fluid.isFluidEqual(Binnie.LIQUID.getFluidStack("water", 0)) && data.fluid.amount == 1000) {
				CraftingManager.getInstance().addRecipe(Mods.Forestry.stack("mulch"), " b ", "bwb", " b ", 'b', ExtraTreeItems.Bark.get(1), 'w', data.filledContainer.copy());
			}
		}
		FuelManager.bronzeEngineFuel.put(ExtraTreeLiquid.Sap.get(1).getFluid(), new EngineBronzeFuel(ExtraTreeLiquid.Sap.get(1).getFluid(), 20, 10000, 1));
		FuelManager.bronzeEngineFuel.put(ExtraTreeLiquid.Resin.get(1).getFluid(), new EngineBronzeFuel(ExtraTreeLiquid.Resin.get(1).getFluid(), 30, 10000, 1));
		for (EnumETLog log : EnumETLog.values()) {
			log.addRecipe();
		}
	}
}
