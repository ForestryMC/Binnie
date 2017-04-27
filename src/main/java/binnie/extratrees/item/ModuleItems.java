// 
// Decompiled by Procyon v0.5.30
// 

package binnie.extratrees.item;

import binnie.extratrees.block.ILogType;
import forestry.api.fuels.EngineBronzeFuel;
import forestry.api.fuels.FuelManager;
import net.minecraftforge.fluids.FluidContainerRegistry;
import binnie.core.item.ItemMisc;
import forestry.api.recipes.RecipeManagers;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import net.minecraft.item.Item;
import binnie.core.Mods;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.init.Items;
import net.minecraftforge.oredict.OreDictionary;
import binnie.core.liquid.ItemFluidContainer;
import binnie.core.BinnieCore;
import binnie.extratrees.ExtraTrees;
import forestry.api.core.Tabs;
import binnie.Binnie;
import binnie.core.IInitializable;

public class ModuleItems implements IInitializable
{
	@Override
	public void preInit() {
		ExtraTrees.itemMisc = Binnie.Item.registerMiscItems(ExtraTreeItems.values(), Tabs.tabArboriculture);
		ExtraTrees.itemDictionary = new ItemDictionary();
		if (BinnieCore.isLepidopteryActive()) {
			ExtraTrees.itemDictionaryLepi = new ItemMothDatabase();
		}
		Binnie.Liquid.createLiquids(ExtraTreeLiquid.values(), ItemFluidContainer.LiquidExtraTree);
		ExtraTrees.itemFood = new ItemFood();

		ExtraTrees.itemHammer = new ItemHammer(false);
		ExtraTrees.itemDurableHammer = new ItemHammer(true);

	}

	@Override
	public void init() {

		OreDictionary.registerOre("pulpWood", ExtraTreeItems.Sawdust.get(1));
		Food.registerOreDictionary();
		OreDictionary.registerOre("cropApple", Items.apple);
		OreDictionary.registerOre("cropHops", ExtraTreeItems.Hops.get(1));
		OreDictionary.registerOre("seedWheat", Items.wheat_seeds);
		OreDictionary.registerOre("seedWheat", ExtraTreeItems.GrainWheat.get(1));
		OreDictionary.registerOre("seedBarley", ExtraTreeItems.GrainBarley.get(1));
		OreDictionary.registerOre("seedCorn", ExtraTreeItems.GrainCorn.get(1));
		OreDictionary.registerOre("seedRye", ExtraTreeItems.GrainRye.get(1));
		OreDictionary.registerOre("seedRoasted", ExtraTreeItems.GrainRoasted.get(1));

		Food.Crabapple.addJuice(10, 150, 10);
		Food.Orange.addJuice(10, 400, 15);
		Food.Kumquat.addJuice(10, 300, 10);
		Food.Lime.addJuice(10, 300, 10);
		Food.WildCherry.addOil(20, 50, 5);
		Food.SourCherry.addOil(20, 50, 3);
		Food.BlackCherry.addOil(20, 50, 5);
		Food.Blackthorn.addJuice(10, 50, 5);
		Food.CherryPlum.addJuice(10, 100, 60);
		Food.Almond.addOil(20, 80, 5);
		Food.Apricot.addJuice(10, 150, 40);
		Food.Grapefruit.addJuice(10, 500, 15);
		Food.Peach.addJuice(10, 150, 40);
		Food.Satsuma.addJuice(10, 300, 10);
		Food.BuddhaHand.addJuice(10, 400, 15);
		Food.Citron.addJuice(10, 400, 15);
		Food.FingerLime.addJuice(10, 300, 10);
		Food.KeyLime.addJuice(10, 300, 10);
		Food.Manderin.addJuice(10, 400, 10);
		Food.Nectarine.addJuice(10, 150, 40);
		Food.Pomelo.addJuice(10, 300, 10);
		Food.Tangerine.addJuice(10, 300, 10);
		Food.Pear.addJuice(10, 300, 20);
		Food.SandPear.addJuice(10, 200, 10);
		Food.Hazelnut.addOil(20, 150, 5);
		Food.Butternut.addOil(20, 180, 5);
		Food.Beechnut.addOil(20, 100, 4);
		Food.Pecan.addOil(29, 50, 2);
		Food.Banana.addJuice(10, 100, 30);
		Food.RedBanana.addJuice(10, 100, 30);
		Food.Plantain.addJuice(10, 100, 40);
		Food.BrazilNut.addOil(20, 20, 2);
		Food.Fig.addOil(20, 50, 3);
		Food.Acorn.addOil(20, 50, 3);
		Food.Elderberry.addJuice(10, 100, 5);
		Food.Olive.addOil(20, 50, 3);
		Food.GingkoNut.addOil(20, 50, 5);
		Food.Coffee.addOil(15, 20, 2);
		Food.OsangeOrange.addJuice(10, 300, 15);
		Food.Clove.addOil(10, 25, 2);
		Food.Coconut.addOil(20, 300, 25);
		Food.Cashew.addJuice(10, 150, 15);
		Food.Avacado.addJuice(10, 300, 15);
		Food.Nutmeg.addOil(20, 50, 10);
		Food.Allspice.addOil(20, 50, 10);
		Food.Chilli.addJuice(10, 100, 10);
		Food.StarAnise.addOil(20, 100, 10);
		Food.Mango.addJuice(10, 400, 20);
		Food.Starfruit.addJuice(10, 300, 10);
		Food.Candlenut.addJuice(20, 50, 10);
	}

	@Override
	public void postInit() {
		CraftingManager.getInstance().getRecipeList().add(new ShapedOreRecipe(new ItemStack(ExtraTrees.itemDurableHammer, 1, 0), new Object[] { "wiw", " s ", " s ", 'w', Blocks.obsidian, 'i', Items.gold_ingot, 's', Items.stick }));
		CraftingManager.getInstance().getRecipeList().add(new ShapedOreRecipe(new ItemStack(ExtraTrees.itemHammer, 1, 0), new Object[] { "wiw", " s ", " s ", 'w', "plankWood", 'i', Items.iron_ingot, 's', Items.stick }));
		CraftingManager.getInstance().getRecipeList().add(new ShapedOreRecipe(ExtraTreeItems.Yeast.get(8), new Object[] { " m ", "mbm", 'b', Items.bread, 'm', Blocks.brown_mushroom }));
		CraftingManager.getInstance().getRecipeList().add(new ShapedOreRecipe(ExtraTreeItems.LagerYeast.get(8), new Object[] { "mbm", " m ", 'b', Items.bread, 'm', Blocks.brown_mushroom }));
		GameRegistry.addRecipe(
			ExtraTreeItems.GrainWheat.get(5),
			" s ", "sss", " s ",
			's', Items.wheat_seeds
		);
		GameRegistry.addRecipe(new ShapedOreRecipe(
			ExtraTreeItems.GrainBarley.get(3), new Object[] {
				false,
				" s ", "s  ", " s ",
				's', ExtraTreeItems.GrainWheat.get(1)
			}
		));
		GameRegistry.addRecipe(new ShapedOreRecipe(
			ExtraTreeItems.GrainCorn.get(3), new Object[] {
				false,
				" s ", "  s", " s ",
				's', ExtraTreeItems.GrainWheat.get(1)
			}
		));
		GameRegistry.addRecipe(ExtraTreeItems.GrainRye.get(3), new Object[] { "   ", "s s", " s ", 's', ExtraTreeItems.GrainWheat.get(1) });
		GameRegistry.addRecipe(ExtraTreeItems.Hops.get(3), new Object[] { " s ", "sps", " s ", 's', Items.wheat_seeds, 'p', Items.apple });
		GameRegistry.addRecipe(ExtraTreeItems.ProvenGear.get(1), new Object[] { " s ", "s s", " s ", 's', Mods.Forestry.stack("oakStick") });
		GameRegistry.addRecipe(ExtraTreeItems.GlassFitting.get(6), new Object[] { "s s", " i ", "s s", 'i', Items.iron_ingot, 's', Items.stick });
		GameRegistry.addSmelting(ExtraTreeItems.GrainWheat.get(1), ExtraTreeItems.GrainRoasted.get(1), 0.0f);
		GameRegistry.addSmelting(ExtraTreeItems.GrainRye.get(1), ExtraTreeItems.GrainRoasted.get(1), 0.0f);
		GameRegistry.addSmelting(ExtraTreeItems.GrainCorn.get(1), ExtraTreeItems.GrainRoasted.get(1), 0.0f);
		GameRegistry.addSmelting(ExtraTreeItems.GrainBarley.get(1), ExtraTreeItems.GrainRoasted.get(1), 0.0f);
		try {
			final Item minium = (Item) Class.forName("com.pahimar.ee3.lib.ItemIds").getField("miniumShard").get(null);
			CraftingManager.getInstance().getRecipeList().add(new ShapelessOreRecipe(Food.Papayimar.get(1), new Object[] { minium, "cropPapaya" }));
		} catch (Exception ex) {
		}
		RecipeManagers.carpenterManager.addRecipe(100, Binnie.Liquid.getLiquidStack("water", 2000), (ItemStack) null, new ItemStack(ExtraTrees.itemDictionary), new Object[] { "X#X", "YEY", "RDR", '#', Blocks.glass_pane, 'X', Items.gold_ingot, 'Y', "ingotCopper", 'R', Items.redstone, 'D', Items.diamond, 'E', Items.emerald });
		RecipeManagers.carpenterManager.addRecipe(100, Binnie.Liquid.getLiquidStack("water", 2000), (ItemStack) null, new ItemStack(ExtraTrees.itemDictionaryLepi), new Object[] { "X#X", "YEY", "RDR", '#', Blocks.glass_pane, 'X', Items.gold_ingot, 'Y', "ingotBronze", 'R', Items.redstone, 'D', Items.diamond, 'E', Items.emerald });
		RecipeManagers.stillManager.addRecipe(25, ExtraTreeLiquid.Resin.get(5), ExtraTreeLiquid.Turpentine.get(3));
		RecipeManagers.carpenterManager.addRecipe(25, ExtraTreeLiquid.Turpentine.get(50), (ItemStack) null, ((ItemMisc) ExtraTrees.itemMisc).getStack(ExtraTreeItems.WoodWax, 4), new Object[] { "x", 'x', Mods.Forestry.stack("beeswax") });
		if (Binnie.Liquid.getLiquidStack("Creosote Oil", 100) != null) {
			RecipeManagers.carpenterManager.addRecipe(25, Binnie.Liquid.getLiquidStack("Creosote Oil", 50), (ItemStack) null, ((ItemMisc) ExtraTrees.itemMisc).getStack(ExtraTreeItems.WoodWax, 1), new Object[] { "x", 'x', Mods.Forestry.stack("beeswax") });
		}
		for (final FluidContainerRegistry.FluidContainerData data : FluidContainerRegistry.getRegisteredFluidContainerData()) {
			if (data.fluid.isFluidEqual(Binnie.Liquid.getLiquidStack("water", 0)) && data.fluid.amount == 1000) {
				CraftingManager.getInstance().addRecipe(Mods.Forestry.stack("mulch"), new Object[] { " b ", "bwb", " b ", 'b', ExtraTreeItems.Bark.get(1), 'w', data.filledContainer.copy() });
			}
		}
		FuelManager.bronzeEngineFuel.put(ExtraTreeLiquid.Sap.get(1).getFluid(), new EngineBronzeFuel(ExtraTreeLiquid.Sap.get(1).getFluid(), 20, 10000, 1));
		FuelManager.bronzeEngineFuel.put(ExtraTreeLiquid.Resin.get(1).getFluid(), new EngineBronzeFuel(ExtraTreeLiquid.Resin.get(1).getFluid(), 30, 10000, 1));
		for (final ILogType.ExtraTreeLog log : ILogType.ExtraTreeLog.values()) {
			log.addRecipe();
		}
	}
}
