package binnie.genetics.item;

import binnie.Binnie;
import binnie.botany.Botany;
import binnie.core.BinnieCore;
import binnie.core.IInitializable;
import binnie.core.Mods;
import binnie.core.liquid.ItemFluidContainer;
import binnie.core.resource.BinnieIcon;
import binnie.extrabees.ExtraBees;
import binnie.extratrees.ExtraTrees;
import binnie.genetics.Genetics;
import binnie.genetics.GeneticsCreativeTab;
import cpw.mods.fml.common.registry.GameRegistry;
import forestry.api.recipes.RecipeManagers;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class ModuleItem implements IInitializable {
	public static BinnieIcon iconNight;
	public static BinnieIcon iconDaytime;
	public static BinnieIcon iconAllDay;
	public static BinnieIcon iconRain;
	public static BinnieIcon iconNoRain;
	public static BinnieIcon iconSky;
	public static BinnieIcon iconNoSky;
	public static BinnieIcon iconFire;
	public static BinnieIcon iconNoFire;
	public static BinnieIcon iconAdd;
	public static BinnieIcon iconArrow;
	public static BinnieIcon iconAdd0;
	public static BinnieIcon iconArrow0;
	public static BinnieIcon iconAdd1;
	public static BinnieIcon iconArrow1;

	@Override
	public void preInit() {
		Genetics.itemSerum = new ItemSerum();
		Genetics.itemSerumArray = new ItemSerumArray();
		Genetics.itemSequencer = new ItemSequence();
		Genetics.itemGenetics = Binnie.Item.registerMiscItems(GeneticsItems.values(), GeneticsCreativeTab.instance);
		Genetics.database = new ItemDatabase();
		Genetics.analyst = new ItemAnalyst();
		Genetics.registry = new ItemRegistry();
		Genetics.masterRegistry = new ItemMasterRegistry();
		Binnie.Liquid.createLiquids(GeneticLiquid.values(), ItemFluidContainer.LiquidGenetics);
	}

	@Override
	public void init() {
		ModuleItem.iconNight = Binnie.Resource.getItemIcon(BinnieCore.instance, "gui/analyst.night");
		ModuleItem.iconDaytime = Binnie.Resource.getItemIcon(BinnieCore.instance, "gui/analyst.day");
		ModuleItem.iconAllDay = Binnie.Resource.getItemIcon(BinnieCore.instance, "gui/analyst.allday");
		ModuleItem.iconRain = Binnie.Resource.getItemIcon(BinnieCore.instance, "gui/analyst.rain");
		ModuleItem.iconNoRain = Binnie.Resource.getItemIcon(BinnieCore.instance, "gui/analyst.norain");
		ModuleItem.iconSky = Binnie.Resource.getItemIcon(BinnieCore.instance, "gui/analyst.sky");
		ModuleItem.iconNoSky = Binnie.Resource.getItemIcon(BinnieCore.instance, "gui/analyst.nosky");
		ModuleItem.iconFire = Binnie.Resource.getItemIcon(BinnieCore.instance, "gui/analyst.fire");
		ModuleItem.iconNoFire = Binnie.Resource.getItemIcon(BinnieCore.instance, "gui/analyst.nofire");
		ModuleItem.iconAdd = Binnie.Resource.getItemIcon(BinnieCore.instance, "gui/analyst.add");
		ModuleItem.iconArrow = Binnie.Resource.getItemIcon(BinnieCore.instance, "gui/analyst.arrow");
		ModuleItem.iconAdd0 = Binnie.Resource.getItemIcon(BinnieCore.instance, "gui/analyst.add.0");
		ModuleItem.iconArrow0 = Binnie.Resource.getItemIcon(BinnieCore.instance, "gui/analyst.arrow.0");
		ModuleItem.iconAdd1 = Binnie.Resource.getItemIcon(BinnieCore.instance, "gui/analyst.add.1");
		ModuleItem.iconArrow1 = Binnie.Resource.getItemIcon(BinnieCore.instance, "gui/analyst.arrow.1");
	}

	@Override
	public void postInit() {
		GameRegistry.addShapelessRecipe(
			GeneticsItems.DNADye.get(8),
			Items.glowstone_dust,
			new ItemStack(Items.dye, 1, 5)
		);

		GameRegistry.addRecipe(new ShapedOreRecipe(
			GeneticsItems.LaboratoryCasing.get(1),
			new Object[]{
				"iii", "iYi", "iii",
				'i', "ingotIron",
				'Y', Mods.forestry.item("sturdyMachine")
			}
		));

		GameRegistry.addRecipe(new ShapelessOreRecipe(
			GeneticsItems.DNADye.get(2),
			new Object[]{
				"dyePurple",
				"dyeMagenta",
				"dyePink"
			}
		));

		GameRegistry.addRecipe(new ShapelessOreRecipe(
			GeneticsItems.FluorescentDye.get(2),
			new Object[]{
				"dyeOrange",
				"dyeYellow",
				"dustGlowstone"
			}
		));

		GameRegistry.addRecipe(new ShapelessOreRecipe(
			GeneticsItems.GrowthMedium.get(2),
			new Object[]{
				new ItemStack(Items.dye, 1, 15),
				Items.sugar
			}
		));

		GameRegistry.addRecipe(new ShapedOreRecipe(
			GeneticsItems.EmptySequencer.get(1),
			new Object[]{
				" p ", "iGi", " p ",
				'i', "ingotGold",
				'G', Blocks.glass_pane,
				'p', Items.paper
			}
		));

		GameRegistry.addRecipe(new ShapedOreRecipe(
			GeneticsItems.EmptySerum.get(1),
			new Object[]{
				" g ", " G ", "GGG",
				'g', "ingotGold",
				'G', Blocks.glass_pane
			}
		));

		GameRegistry.addRecipe(new ShapedOreRecipe(
			GeneticsItems.EmptyGenome.get(1),
			new Object[]{
				"sss", "sss", "sss",
				's', GeneticsItems.EmptySerum.get(1)
			}
		));

		GameRegistry.addRecipe(new ShapedOreRecipe(
			GeneticsItems.Cylinder.get(8),
			new Object[]{
				" g ", "g g", "   ",
				'g', Blocks.glass_pane
			}
		));

		GameRegistry.addRecipe(new ShapedOreRecipe(
			GeneticsItems.IntegratedCircuit.get(1),
			new Object[]{
				"l g", " c ", "g l",
				'c', Mods.forestry.stack("chipsets", 1, 1),
				'l', new ItemStack(Items.dye, 1, 4),
				'g', "dustGlowstone"
			}
		));

		GameRegistry.addRecipe(new ShapedOreRecipe(
			GeneticsItems.IntegratedCircuit.get(1),
			new Object[]{
				"g l", " c ", "l g",
				'c', Mods.forestry.stack("chipsets", 1, 1),
				'l', new ItemStack(Items.dye, 1, 4),
				'g', "dustGlowstone"
			}
		));
		GameRegistry.addRecipe(new ShapedOreRecipe(
			GeneticsItems.IntegratedCasing.get(1),
			new Object[]{
				"ccc", "cdc", "ccc",
				'c', GeneticsItems.IntegratedCircuit.get(1),
				'd', GeneticsItems.LaboratoryCasing.get(1)
			}
		));

		GameRegistry.addRecipe(new ShapedOreRecipe(
			GeneticsItems.IntegratedCPU.get(1),
			new Object[]{
				"ccc", "cdc", "ccc",
				'c', GeneticsItems.IntegratedCircuit.get(1),
				'd', Items.diamond
			}
		));

		RecipeManagers.carpenterManager.addRecipe(
			100,
			Binnie.Liquid.getLiquidStack("water", 2000),
			null,
			new ItemStack(Genetics.database),
			"X#X", "YEY", "RDR", 
			'#', Blocks.glass_pane,
			'X', Items.diamond,
			'Y', Items.diamond,
			'R', Items.redstone,
			'D', Items.ender_eye,
			'E', Blocks.obsidian
		);

		GameRegistry.addSmelting(Genetics.itemSequencer, GeneticsItems.EmptySequencer.get(1), 0.0f);
		GameRegistry.addSmelting(Genetics.itemSerum, GeneticsItems.EmptySerum.get(1), 0.0f);
		GameRegistry.addSmelting(Genetics.itemSerumArray, GeneticsItems.EmptyGenome.get(1), 0.0f);

		// TODO refactor refactor refactor this!
		Item[] arr$;
		Item[] lyzers = arr$ = new Item[]{
			Mods.forestry.item("beealyzer"),
			Mods.forestry.item("treealyzer"),
			Mods.forestry.item("flutterlyzer")
		};
		for (Item a : arr$) {
			for (Item b : lyzers) {
				for (Item c : lyzers) {
					if (a != b && a != c && b != c) {
						GameRegistry.addShapedRecipe(
							new ItemStack(Genetics.analyst),
							" b ", "fct", " d ",
							'c', GeneticsItems.IntegratedCircuit.get(1),
							'b', a,
							't', b,
							'f', c,
							'd', new ItemStack(Items.diamond)
						);
					}
				}
			}
		}

		Item[] dbs = {ExtraBees.dictionary, ExtraTrees.itemDictionary, ExtraTrees.itemDictionaryLepi, Botany.database};
		if (BinnieCore.isBotanyActive() && BinnieCore.isExtraBeesActive() && BinnieCore.isExtraTreesActive()) {
			for (Item a2 : dbs) {
				for (Item b2 : dbs) {
					for (Item c2 : dbs) {
						for (Item d : dbs) {
							if (a2 != b2 && a2 != c2 && a2 != d && b2 != c2 && b2 != d && c2 != d) {
								GameRegistry.addShapedRecipe(
									new ItemStack(Genetics.registry),
									" b ", "fct", " l ",
									'c', GeneticsItems.IntegratedCircuit.get(1),
									'b', a2,
									't', b2,
									'f', c2,
									'l', d
								);
							}
						}
					}
				}
			}
		}
	}
}
