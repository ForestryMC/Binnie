package binnie.genetics.item;

import com.google.common.base.Preconditions;

import javax.annotation.Nullable;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import net.minecraftforge.fml.common.registry.GameRegistry;

import forestry.api.recipes.RecipeManagers;

import binnie.core.Binnie;
import binnie.core.Constants;
import binnie.core.modules.BotanyModuleUIDs;
import binnie.botany.modules.ModuleDatabase;
import binnie.core.BinnieCore;
import binnie.core.IInitializable;
import binnie.core.Mods;
import binnie.core.integration.extrabees.ExtraBeesIntegration;
import binnie.core.item.ItemMisc;
import binnie.core.liquid.FluidContainerType;
import binnie.core.liquid.ManagerLiquid;
import binnie.core.resource.BinnieSprite;
import binnie.core.util.RecipeUtil;
import binnie.extratrees.modules.ExtraTreesModuleUIDs;
import binnie.extratrees.modules.ModuleMothDatabase;
import binnie.extratrees.modules.ModuleTreeDatabase;
import binnie.genetics.CreativeTabGenetics;
import binnie.genetics.Genetics;
import binnie.core.modules.ModuleManager;

public class ModuleItems implements IInitializable {
	public static BinnieSprite iconNight;
	public static BinnieSprite iconDaytime;
	public static BinnieSprite iconAllDay;
	public static BinnieSprite iconRain;
	public static BinnieSprite iconNoRain;
	public static BinnieSprite iconSky;
	public static BinnieSprite iconNoSky;
	public static BinnieSprite iconFire;
	public static BinnieSprite iconNoFire;
	public static BinnieSprite iconAdd;
	public static BinnieSprite iconArrow;
	public static BinnieSprite iconAdd0;
	public static BinnieSprite iconArrow0;
	public static BinnieSprite iconAdd1;
	public static BinnieSprite iconArrow1;
	public ItemSerum itemSerum;
	public ItemSequence itemSequencer;
	public ItemDatabase database;
	public ItemAnalyst analyst;
	public Item registry;
	public Item masterRegistry;
	public ItemSerumArray itemSerumArray = null;
	@Nullable
	private Item itemGenetics;

	@Override
	public void preInit() {
		itemSerum = new ItemSerum();
		Genetics.proxy.registerItem(itemSerum);
		itemSerumArray = new ItemSerumArray();
		Genetics.proxy.registerItem(itemSerumArray);

		database = new ItemDatabase();
		Genetics.proxy.registerItem(database);
		analyst = new ItemAnalyst();
		Genetics.proxy.registerItem(analyst);
		registry = new ItemRegistry();
		Genetics.proxy.registerItem(registry);
		masterRegistry = new ItemMasterRegistry();
		Genetics.proxy.registerItem(masterRegistry);
		itemGenetics = new ItemMisc(CreativeTabGenetics.instance, GeneticsItems.values());
		Genetics.proxy.registerItem(itemGenetics);
		itemSequencer = new ItemSequence();
		Genetics.proxy.registerItem(itemSequencer);

		Binnie.LIQUID.createLiquids(GeneticLiquid.values());

		ModuleItems.iconNight = Binnie.RESOURCE.getItemSprite(BinnieCore.getInstance(), "gui/analyst.night");
		ModuleItems.iconDaytime = Binnie.RESOURCE.getItemSprite(BinnieCore.getInstance(), "gui/analyst.day");
		ModuleItems.iconAllDay = Binnie.RESOURCE.getItemSprite(BinnieCore.getInstance(), "gui/analyst.allday");
		ModuleItems.iconRain = Binnie.RESOURCE.getItemSprite(BinnieCore.getInstance(), "gui/analyst.rain");
		ModuleItems.iconNoRain = Binnie.RESOURCE.getItemSprite(BinnieCore.getInstance(), "gui/analyst.norain");
		ModuleItems.iconSky = Binnie.RESOURCE.getItemSprite(BinnieCore.getInstance(), "gui/analyst.sky");
		ModuleItems.iconNoSky = Binnie.RESOURCE.getItemSprite(BinnieCore.getInstance(), "gui/analyst.nosky");
		ModuleItems.iconFire = Binnie.RESOURCE.getItemSprite(BinnieCore.getInstance(), "gui/analyst.fire");
		ModuleItems.iconNoFire = Binnie.RESOURCE.getItemSprite(BinnieCore.getInstance(), "gui/analyst.nofire");
		ModuleItems.iconAdd = Binnie.RESOURCE.getItemSprite(BinnieCore.getInstance(), "gui/analyst.add");
		ModuleItems.iconArrow = Binnie.RESOURCE.getItemSprite(BinnieCore.getInstance(), "gui/analyst.arrow");
		ModuleItems.iconAdd0 = Binnie.RESOURCE.getItemSprite(BinnieCore.getInstance(), "gui/analyst.add.0");
		ModuleItems.iconArrow0 = Binnie.RESOURCE.getItemSprite(BinnieCore.getInstance(), "gui/analyst.arrow.0");
		ModuleItems.iconAdd1 = Binnie.RESOURCE.getItemSprite(BinnieCore.getInstance(), "gui/analyst.add.1");
		ModuleItems.iconArrow1 = Binnie.RESOURCE.getItemSprite(BinnieCore.getInstance(), "gui/analyst.arrow.1");
	}

	public Item getItemGenetics() {
		Preconditions.checkState(itemGenetics != null);
		return itemGenetics;
	}

	@Override
	public void postInit() {
		RecipeUtil recipeUtil = new RecipeUtil(Constants.GENETICS_MOD_ID);
		recipeUtil.addShapelessRecipe("dna_dye_from_glowstone", GeneticsItems.DNADye.get(8),
			Items.GLOWSTONE_DUST, new ItemStack(Items.DYE, 1, 5)
		);
		recipeUtil.addRecipe("laboratory_casing", GeneticsItems.LaboratoryCasing.get(itemGenetics, 1),
			"iii",
			"iYi",
			"iii",
			'i', "ingotIron", 'Y', Mods.Forestry.item("sturdy_machine")
		);
		recipeUtil.addShapelessRecipe("dna_dye", GeneticsItems.DNADye.get(itemGenetics, 2), "dyePurple", "dyeMagenta", "dyePink");
		recipeUtil.addShapelessRecipe("fluorescent_dye", GeneticsItems.FluorescentDye.get(itemGenetics, 2), "dyeOrange", "dyeYellow", "dustGlowstone");
		recipeUtil.addShapelessRecipe("growth_medium", GeneticsItems.GrowthMedium.get(itemGenetics, 2), new ItemStack(Items.DYE, 1, 15), Items.SUGAR);
		recipeUtil.addRecipe("empty_sequencer", GeneticsItems.EmptySequencer.get(itemGenetics, 1),
			" p ",
			"iGi",
			" p ",
			'i', "ingotGold", 'G', Blocks.GLASS_PANE, 'p', Items.PAPER
		);
		recipeUtil.addRecipe("empty_serum", GeneticsItems.EMPTY_SERUM.get(itemGenetics, 1),
			" g ",
			" G ",
			"GGG",
			'g', "ingotGold", 'G', Blocks.GLASS_PANE
		);
		recipeUtil.addRecipe("empty_genome", GeneticsItems.EMPTY_GENOME.get(itemGenetics, 1),
			"sss",
			"sss",
			"sss",
			's', GeneticsItems.EMPTY_SERUM.get(itemGenetics, 1)
		);
		recipeUtil.addRecipe("cylinder", FluidContainerType.CYLINDER.get(8),
			" g ",
			"g g",
			"   ",
			'g', Blocks.GLASS_PANE
		);
		recipeUtil.addRecipe("integrated_circuit", GeneticsItems.IntegratedCircuit.get(itemGenetics, 1),
			"l g",
			" c ",
			"g l",
			'c', Mods.Forestry.stack("chipsets", 1, 1), 'l', new ItemStack(Items.DYE, 1, 4), 'g', "dustGlowstone"
		);
		recipeUtil.addRecipe("integrated_casing", GeneticsItems.IntegratedCasing.get(itemGenetics, 1),
			"ccc",
			"cdc",
			"ccc",
			'c', GeneticsItems.IntegratedCircuit.get(itemGenetics, 1), 'd', GeneticsItems.LaboratoryCasing.get(itemGenetics, 1)
		);
		recipeUtil.addRecipe("integrated_cpu", GeneticsItems.IntegratedCPU.get(itemGenetics, 1),
			"ccc",
			"cdc",
			"ccc",
			'c', GeneticsItems.IntegratedCircuit.get(itemGenetics, 1), 'd', Items.DIAMOND
		);


		RecipeManagers.carpenterManager.addRecipe(100, Binnie.LIQUID.getFluidStack(ManagerLiquid.WATER, 2000), ItemStack.EMPTY,
			new ItemStack(database),
			"X#X",
			"YEY",
			"RDR",
			'#', Blocks.GLASS_PANE, 'X', Items.DIAMOND, 'Y', Items.DIAMOND, 'R', Items.REDSTONE, 'D', Items.ENDER_EYE, 'E', Blocks.OBSIDIAN
		);
		GameRegistry.addSmelting(itemSequencer, GeneticsItems.EmptySequencer.get(itemGenetics, 1), 0.0f);
		GameRegistry.addSmelting(itemSerum, GeneticsItems.EMPTY_SERUM.get(itemGenetics, 1), 0.0f);
		GameRegistry.addSmelting(itemSerumArray, GeneticsItems.EMPTY_GENOME.get(itemGenetics, 1), 0.0f);

		recipeUtil.addRecipe("analyst", new ItemStack(analyst),
			" c ",
			"cac",
			" d ",
			'c', GeneticsItems.IntegratedCircuit.get(itemGenetics, 1), 'a', Mods.Forestry.item("portable_alyzer"), 'd', new ItemStack(Items.DIAMOND)
		);

		// TODO: make this crazy list of recipes into one custom recipe
		Item[] databases = {ExtraBeesIntegration.dictionary, ModuleTreeDatabase.itemDictionary, ModuleMothDatabase.itemDictionaryLepi, ModuleDatabase.database};
		if (ModuleManager.isModuleEnabled(Constants.BOTANY_MOD_ID, BotanyModuleUIDs.DATABASE) && BinnieCore.isExtraBeesActive() && ModuleManager.isModuleEnabled(Constants.EXTRA_TREES_MOD_ID, ExtraTreesModuleUIDs.TREE_DATABASE) && ModuleManager.isModuleEnabled(Constants.EXTRA_TREES_MOD_ID, ExtraTreesModuleUIDs.MOTH_DATABASE)) {
			int recipeNum = 0;
			for (Item databaseA : databases) {
				for (Item databaseB : databases) {
					for (Item databaseC : databases) {
						for (Item databaseD : databases) {
							if (databaseA != databaseB && databaseA != databaseC && databaseA != databaseD && databaseB != databaseC && databaseB != databaseD && databaseC != databaseD) {
								recipeUtil.addRecipe("registry_" + recipeNum, new ItemStack(registry),
									" b ",
									"fct",
									" l ", 'c', GeneticsItems.IntegratedCircuit.get(itemGenetics, 1), 'b', databaseA, 't', databaseB, 'f', databaseC, 'l', databaseD
								);
								recipeNum++;
							}
						}
					}
				}
			}
		}
	}
}
