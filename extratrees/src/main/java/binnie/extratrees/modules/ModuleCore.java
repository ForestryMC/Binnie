package binnie.extratrees.modules;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.VillagerRegistry;

import forestry.api.fuels.EngineBronzeFuel;
import forestry.api.fuels.FuelManager;
import forestry.api.modules.ForestryModule;
import forestry.api.recipes.ICarpenterManager;
import forestry.api.recipes.IStillManager;
import forestry.api.recipes.RecipeManagers;

import binnie.core.Binnie;
import binnie.core.Constants;
import binnie.core.Mods;
import binnie.core.liquid.ManagerLiquid;
import binnie.core.modules.BinnieModule;
import binnie.core.modules.ExtraTreesModuleUIDs;
import binnie.core.util.RecipeUtil;
import binnie.extratrees.config.ConfigurationMain;
import binnie.extratrees.items.ExtraTreeLiquid;
import binnie.extratrees.items.ExtraTreeMiscItems;
import binnie.extratrees.items.Food;
import binnie.extratrees.modules.features.ExtraTreesItems;
import binnie.extratrees.village.VillageCreationExtraTrees;

@ForestryModule(moduleID = ExtraTreesModuleUIDs.CORE, containerID = Constants.EXTRA_TREES_MOD_ID, coreModule = true, name = "Core", unlocalizedDescription = "extratrees.module.core")
public class ModuleCore extends BinnieModule {

	public ModuleCore() {
		super("forestry", "arboriculture");
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent(priority = EventPriority.LOW)
	public void onRegisterItem(RegistryEvent.Register<Item> event) {
		OreDictionary.registerOre("cropApple", Items.APPLE);
		OreDictionary.registerOre("seedWheat", Items.WHEAT_SEEDS);
		if (ExtraTreesItems.FOOD.hasItem()) {
			Food.registerOreDictionary();
		}
		if (ExtraTreesItems.HOPS_ITEM.hasItem()) {
			OreDictionary.registerOre("cropHops", ExtraTreesItems.HOPS_ITEM.item());
		}
		if (ExtraTreesItems.MISC.hasItem()) {
			OreDictionary.registerOre("pulpWood", ExtraTreeMiscItems.SAWDUST.stack(1));

			OreDictionary.registerOre("seedWheat", ExtraTreeMiscItems.GRAIN_WHEAT.stack(1));
			OreDictionary.registerOre("seedBarley", ExtraTreeMiscItems.GRAIN_BARLEY.stack(1));
			OreDictionary.registerOre("seedCorn", ExtraTreeMiscItems.GRAIN_CORN.stack(1));
			OreDictionary.registerOre("seedRye", ExtraTreeMiscItems.GRAIN_RYE.stack(1));
			OreDictionary.registerOre("seedRoasted", ExtraTreeMiscItems.GRAIN_ROASTED.stack(1));
		}
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
		Food.AVACADO.addJuice(10, 300, 15);
		Food.NUTMEG.addOil(20, 50, 10);
		Food.ALLSPICE.addOil(20, 50, 10);
		Food.CHILLI.addJuice(10, 100, 10);
		Food.STAR_ANISE.addOil(20, 100, 10);
		Food.MANGO.addJuice(10, 400, 20);
		Food.STARFRUIT.addJuice(10, 300, 10);
		Food.CANDLENUT.addJuice(20, 50, 10);

		RecipeUtil recipeUtil = new RecipeUtil(Constants.EXTRA_TREES_MOD_ID);
		MinecraftForge.addGrassSeed(ExtraTreesItems.HOPS_ITEM.stack(), 5);

		recipeUtil.addRecipe("durable_hammer", ExtraTreesItems.DURABLE_HAMMER.stack(), "wiw", " s ", " s ", 'w', Blocks.OBSIDIAN, 'i', Items.GOLD_INGOT, 's', Items.STICK);
		recipeUtil.addRecipe("hammer", ExtraTreesItems.HAMMER.stack(), "wiw", " s ", " s ", 'w', "plankWood", 'i', Items.IRON_INGOT, 's', Items.STICK);
		recipeUtil.addRecipe("yeast", ExtraTreeMiscItems.YEAST.stack(8), " m ", "mbm", 'b', Items.BREAD, 'm', Blocks.BROWN_MUSHROOM);
		recipeUtil.addRecipe("lager_yeast", ExtraTreeMiscItems.LAGER_YEAST.stack(8), "mbm", " m ", 'b', Items.BREAD, 'm', Blocks.BROWN_MUSHROOM);
		recipeUtil.addRecipe("grain_wheat", ExtraTreeMiscItems.GRAIN_WHEAT.stack(5), " s ", "sss", " s ", 's', Items.WHEAT_SEEDS);
		recipeUtil.addRecipe("grain_barley", ExtraTreeMiscItems.GRAIN_BARLEY.stack(3), false, " s ", "s  ", " s ", 's', ExtraTreeMiscItems.GRAIN_WHEAT.stack(1));
		recipeUtil.addRecipe("grain_corn", ExtraTreeMiscItems.GRAIN_CORN.stack(3), false, " s ", "  s", " s ", 's', ExtraTreeMiscItems.GRAIN_WHEAT.stack(1));
		recipeUtil.addRecipe("grain_rye", ExtraTreeMiscItems.GRAIN_RYE.stack(3), "   ", "s s", " s ", 's', ExtraTreeMiscItems.GRAIN_WHEAT.stack(1));
		recipeUtil.addRecipe("proven_gear", ExtraTreeMiscItems.PROVEN_GEAR.stack(1), " s ", "s s", " s ", 's', Mods.Forestry.stack("oak_stick"));
		recipeUtil.addRecipe("glass_fitting", ExtraTreeMiscItems.GLASS_FITTING.stack(6), "s s", " i ", "s s", 'i', Items.IRON_INGOT, 's', Items.STICK);
		GameRegistry.addSmelting(ExtraTreeMiscItems.GRAIN_WHEAT.stack(1), ExtraTreeMiscItems.GRAIN_ROASTED.stack(1), 0.0f);
		GameRegistry.addSmelting(ExtraTreeMiscItems.GRAIN_RYE.stack(1), ExtraTreeMiscItems.GRAIN_ROASTED.stack(1), 0.0f);
		GameRegistry.addSmelting(ExtraTreeMiscItems.GRAIN_CORN.stack(1), ExtraTreeMiscItems.GRAIN_ROASTED.stack(1), 0.0f);
		GameRegistry.addSmelting(ExtraTreeMiscItems.GRAIN_BARLEY.stack(1), ExtraTreeMiscItems.GRAIN_ROASTED.stack(1), 0.0f);
		try {
			Item minium = (Item) Class.forName("com.pahimar.ee3.lib.ItemIds").getField("minium_shard").get(null);
			recipeUtil.addShapelessRecipe("papayimar", Food.PAPAYIMAR.stack(1), minium, "cropPapaya");
		} catch (Exception ignored) {
		}
		ICarpenterManager carpenterManager = RecipeManagers.carpenterManager;
		IStillManager stillManager = RecipeManagers.stillManager;
		stillManager.addRecipe(25, ExtraTreeLiquid.RESIN.stack(5), ExtraTreeLiquid.TURPENTINE.stack(3));
		carpenterManager.addRecipe(25, ExtraTreeLiquid.TURPENTINE.stack(50), ItemStack.EMPTY, ExtraTreeMiscItems.WOOD_WAX.stack(4), "x", 'x', Mods.Forestry.stack("beeswax"));
		FluidStack creosoteOil = Binnie.LIQUID.getFluidStack(ManagerLiquid.CREOSOTE, 50);
		if (creosoteOil != null) {
			carpenterManager.addRecipe(25, creosoteOil, ItemStack.EMPTY, ExtraTreeMiscItems.WOOD_WAX.stack(1), "x", 'x', Mods.Forestry.stack("beeswax"));
		}

		FuelManager.bronzeEngineFuel.put(ExtraTreeLiquid.SAP.stack(1).getFluid(), new EngineBronzeFuel(ExtraTreeLiquid.SAP.stack(1).getFluid(), 20, 10000, 1));
		FuelManager.bronzeEngineFuel.put(ExtraTreeLiquid.RESIN.stack(1).getFluid(), new EngineBronzeFuel(ExtraTreeLiquid.RESIN.stack(1).getFluid(), 30, 10000, 1));
	}
}
