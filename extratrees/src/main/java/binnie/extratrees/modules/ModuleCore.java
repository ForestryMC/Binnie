package binnie.extratrees.modules;

import com.google.common.collect.ImmutableSet;

import java.util.Set;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.VillagerRegistry;

import forestry.api.core.Tabs;
import forestry.api.fuels.EngineBronzeFuel;
import forestry.api.fuels.FuelManager;
import forestry.api.modules.ForestryModule;
import forestry.api.recipes.ICarpenterManager;
import forestry.api.recipes.IStillManager;
import forestry.api.recipes.RecipeManagers;

import binnie.core.Binnie;
import binnie.core.Constants;
import binnie.core.Mods;
import binnie.core.item.ItemMisc;
import binnie.core.liquid.ManagerLiquid;
import binnie.core.modules.BlankModule;
import binnie.core.modules.ExtraTreesModuleUIDs;
import binnie.core.util.RecipeUtil;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.blocks.BlockHops;
import binnie.extratrees.config.ConfigurationMain;
import binnie.extratrees.items.ExtraTreeItems;
import binnie.extratrees.items.ExtraTreeLiquid;
import binnie.extratrees.items.Food;
import binnie.extratrees.items.ItemETFood;
import binnie.extratrees.items.ItemHammer;
import binnie.extratrees.items.ItemHops;
import binnie.extratrees.village.VillageCreationExtraTrees;

@ForestryModule(moduleID = ExtraTreesModuleUIDs.CORE, containerID = Constants.EXTRA_TREES_MOD_ID, coreModule = true, name = "Core", unlocalizedDescription = "extratrees.module.core")
public class ModuleCore extends BlankModule {
	public static ItemMisc itemMisc;
	public static Item itemFood;
	public static Item itemHammer;
	public static Item itemDurableHammer;
	public static Item itemHops;

	public static BlockHops hops;

	public ModuleCore() {
		super("forestry", "core");
	}

	@Override
	public Set<ResourceLocation> getDependencyUids() {
		return ImmutableSet.of(new ResourceLocation("forestry", "arboriculture"));
	}

	@Override
	public void registerItemsAndBlocks() {
		itemMisc = new ItemMisc(Tabs.tabArboriculture, ExtraTreeItems.values());
		ExtraTrees.proxy.registerItem(itemMisc);

		Binnie.LIQUID.createLiquids(ExtraTreeLiquid.values());
		itemFood = new ItemETFood();
		ExtraTrees.proxy.registerItem(itemFood);

		itemHammer = new ItemHammer(false);
		ExtraTrees.proxy.registerItem(itemHammer);
		itemDurableHammer = new ItemHammer(true);
		ExtraTrees.proxy.registerItem(itemDurableHammer);

		hops = new BlockHops();
		ExtraTrees.proxy.registerBlock(hops);

		itemHops = new ItemHops(hops, Blocks.FARMLAND);
		ExtraTrees.proxy.registerItem(itemHops);

		OreDictionary.registerOre("pulpWood", ExtraTreeItems.SAWDUST.get(1));
		Food.registerOreDictionary();
		OreDictionary.registerOre("cropApple", Items.APPLE);
		OreDictionary.registerOre("cropHops", itemHops);
		OreDictionary.registerOre("seedWheat", Items.WHEAT_SEEDS);
		OreDictionary.registerOre("seedWheat", ExtraTreeItems.GRAIN_WHEAT.get(1));
		OreDictionary.registerOre("seedBarley", ExtraTreeItems.GRAIN_BARLEY.get(1));
		OreDictionary.registerOre("seedCorn", ExtraTreeItems.GRAIN_CORN.get(1));
		OreDictionary.registerOre("seedRye", ExtraTreeItems.GRAIN_RYE.get(1));
		OreDictionary.registerOre("seedRoasted", ExtraTreeItems.GRAIN_ROASTED.get(1));
	}

	@Override
	public void preInit() {
		if (ConfigurationMain.hopeField) {
			VillageCreationExtraTrees.registerVillageComponents();
		}
	}

	@Override
	public void doInit() {
		if (ConfigurationMain.hopeField) {
			VillageCreationExtraTrees villageHandler = new VillageCreationExtraTrees();
			VillagerRegistry villagerRegistry = VillagerRegistry.instance();
			villagerRegistry.registerVillageCreationHandler(villageHandler);
		}

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
		Food.AVOCADO.addJuice(10, 300, 15);
		Food.NUTMEG.addOil(20, 50, 10);
		Food.ALLSPICE.addOil(20, 50, 10);
		Food.CHILLI.addJuice(10, 100, 10);
		Food.STAR_ANISE.addOil(20, 100, 10);
		Food.MANGO.addJuice(10, 400, 20);
		Food.STARFRUIT.addJuice(10, 300, 10);
		Food.CANDLENUT.addJuice(20, 50, 10);
		Food.CRANBERRY.addJuice( 10, 50, 5);

		RecipeUtil recipeUtil = new RecipeUtil(Constants.EXTRA_TREES_MOD_ID);
		MinecraftForge.addGrassSeed(new ItemStack(itemHops), 5);

		recipeUtil.addRecipe("durable_hammer", new ItemStack(itemDurableHammer, 1, 0), "wiw", " s ", " s ", 'w', Blocks.OBSIDIAN, 'i', Items.GOLD_INGOT, 's', Items.STICK);
		recipeUtil.addRecipe("hammer", new ItemStack(itemHammer, 1, 0), "wiw", " s ", " s ", 'w', "plankWood", 'i', Items.IRON_INGOT, 's', Items.STICK);
		recipeUtil.addRecipe("yeast", ExtraTreeItems.YEAST.get(8), " m ", "mbm", 'b', Items.BREAD, 'm', Blocks.BROWN_MUSHROOM);
		recipeUtil.addRecipe("lager_yeast", ExtraTreeItems.LAGER_YEAST.get(8), "mbm", " m ", 'b', Items.BREAD, 'm', Blocks.BROWN_MUSHROOM);
		recipeUtil.addRecipe("grain_wheat", ExtraTreeItems.GRAIN_WHEAT.get(5), " s ", "sss", " s ", 's', Items.WHEAT_SEEDS);
		recipeUtil.addRecipe("grain_barley", ExtraTreeItems.GRAIN_BARLEY.get(3), false, " s ", "s  ", " s ", 's', ExtraTreeItems.GRAIN_WHEAT.get(1));
		recipeUtil.addRecipe("grain_corn", ExtraTreeItems.GRAIN_CORN.get(3), false, " s ", "  s", " s ", 's', ExtraTreeItems.GRAIN_WHEAT.get(1));
		recipeUtil.addRecipe("grain_rye", ExtraTreeItems.GRAIN_RYE.get(3), "   ", "s s", " s ", 's', ExtraTreeItems.GRAIN_WHEAT.get(1));
		recipeUtil.addRecipe("proven_gear", ExtraTreeItems.PROVEN_GEAR.get(1), " s ", "s s", " s ", 's', Mods.Forestry.stack("oak_stick"));
		recipeUtil.addRecipe("glass_fitting", ExtraTreeItems.GLASS_FITTING.get(6), "s s", " i ", "s s", 'i', Items.IRON_INGOT, 's', Items.STICK);
		GameRegistry.addSmelting(ExtraTreeItems.GRAIN_WHEAT.get(1), ExtraTreeItems.GRAIN_ROASTED.get(1), 0.0f);
		GameRegistry.addSmelting(ExtraTreeItems.GRAIN_RYE.get(1), ExtraTreeItems.GRAIN_ROASTED.get(1), 0.0f);
		GameRegistry.addSmelting(ExtraTreeItems.GRAIN_CORN.get(1), ExtraTreeItems.GRAIN_ROASTED.get(1), 0.0f);
		GameRegistry.addSmelting(ExtraTreeItems.GRAIN_BARLEY.get(1), ExtraTreeItems.GRAIN_ROASTED.get(1), 0.0f);
		try {
			final Item minium = (Item) Class.forName("com.pahimar.ee3.lib.ItemIds").getField("minium_shard").get(null);
			recipeUtil.addShapelessRecipe("papayimar", Food.PAPAYIMAR.get(1), minium, "cropPapaya");
		} catch (Exception ignored) {
		}
		ICarpenterManager carpenterManager = RecipeManagers.carpenterManager;
		IStillManager stillManager = RecipeManagers.stillManager;
		stillManager.addRecipe(25, ExtraTreeLiquid.Resin.get(5), ExtraTreeLiquid.Turpentine.get(3));
		carpenterManager.addRecipe(25, ExtraTreeLiquid.Turpentine.get(50), ItemStack.EMPTY, itemMisc.getStack(ExtraTreeItems.WOOD_WAX, 4), "x", 'x', Mods.Forestry.stack("beeswax"));
		FluidStack creosoteOil = Binnie.LIQUID.getFluidStack(ManagerLiquid.CREOSOTE, 50);
		if (creosoteOil != null) {
			carpenterManager.addRecipe(25, creosoteOil, ItemStack.EMPTY, itemMisc.getStack(ExtraTreeItems.WOOD_WAX, 1), "x", 'x', Mods.Forestry.stack("beeswax"));
		}

		FuelManager.bronzeEngineFuel.put(ExtraTreeLiquid.Sap.get(1).getFluid(), new EngineBronzeFuel(ExtraTreeLiquid.Sap.get(1).getFluid(), 20, 10000, 1));
		FuelManager.bronzeEngineFuel.put(ExtraTreeLiquid.Resin.get(1).getFluid(), new EngineBronzeFuel(ExtraTreeLiquid.Resin.get(1).getFluid(), 30, 10000, 1));
	}
}
