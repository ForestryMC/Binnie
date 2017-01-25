package binnie.genetics.item;

import binnie.Binnie;
import binnie.botany.Botany;
import binnie.core.BinnieCore;
import binnie.core.IInitializable;
import binnie.core.Mods;
import binnie.core.item.ItemMisc;
import binnie.core.resource.BinnieSprite;
import binnie.extrabees.ExtraBees;
import binnie.extratrees.ExtraTrees;
import binnie.genetics.CreativeTabGenetics;
import binnie.genetics.Genetics;
import forestry.api.recipes.RecipeManagers;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class ModuleItem implements IInitializable {
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

	@Override
	public void preInit() {
		Genetics.itemSerum = new ItemSerum();
		Genetics.itemSerumArray = new ItemSerumArray();
		Genetics.itemSequencer = new ItemSequence();
		Genetics.setItemGenetics(new ItemMisc(CreativeTabGenetics.instance, GeneticsItems.values()));
		Genetics.proxy.registerItem(Genetics.itemSerum);
		Genetics.proxy.registerItem(Genetics.itemSerumArray);
		Genetics.proxy.registerItem(Genetics.itemSequencer);
		Genetics.proxy.registerItem(Genetics.getItemGenetics());

		Genetics.database = new ItemDatabase();
		Genetics.analyst = new ItemAnalyst();
		Genetics.registry = new ItemRegistry();
		Genetics.masterRegistry = new ItemMasterRegistry();
		Genetics.proxy.registerItem(Genetics.database);
		Genetics.proxy.registerItem(Genetics.analyst);
		Genetics.proxy.registerItem(Genetics.registry);
		Genetics.proxy.registerItem(Genetics.masterRegistry);

		Binnie.LIQUID.createLiquids(GeneticLiquid.values());
	}

	@Override
	public void init() {
		ModuleItem.iconNight = Binnie.RESOURCE.getItemSprite(BinnieCore.instance, "gui/analyst.night");
		ModuleItem.iconDaytime = Binnie.RESOURCE.getItemSprite(BinnieCore.instance, "gui/analyst.day");
		ModuleItem.iconAllDay = Binnie.RESOURCE.getItemSprite(BinnieCore.instance, "gui/analyst.allday");
		ModuleItem.iconRain = Binnie.RESOURCE.getItemSprite(BinnieCore.instance, "gui/analyst.rain");
		ModuleItem.iconNoRain = Binnie.RESOURCE.getItemSprite(BinnieCore.instance, "gui/analyst.norain");
		ModuleItem.iconSky = Binnie.RESOURCE.getItemSprite(BinnieCore.instance, "gui/analyst.sky");
		ModuleItem.iconNoSky = Binnie.RESOURCE.getItemSprite(BinnieCore.instance, "gui/analyst.nosky");
		ModuleItem.iconFire = Binnie.RESOURCE.getItemSprite(BinnieCore.instance, "gui/analyst.fire");
		ModuleItem.iconNoFire = Binnie.RESOURCE.getItemSprite(BinnieCore.instance, "gui/analyst.nofire");
		ModuleItem.iconAdd = Binnie.RESOURCE.getItemSprite(BinnieCore.instance, "gui/analyst.add");
		ModuleItem.iconArrow = Binnie.RESOURCE.getItemSprite(BinnieCore.instance, "gui/analyst.arrow");
		ModuleItem.iconAdd0 = Binnie.RESOURCE.getItemSprite(BinnieCore.instance, "gui/analyst.add.0");
		ModuleItem.iconArrow0 = Binnie.RESOURCE.getItemSprite(BinnieCore.instance, "gui/analyst.arrow.0");
		ModuleItem.iconAdd1 = Binnie.RESOURCE.getItemSprite(BinnieCore.instance, "gui/analyst.add.1");
		ModuleItem.iconArrow1 = Binnie.RESOURCE.getItemSprite(BinnieCore.instance, "gui/analyst.arrow.1");
	}

	@Override
	public void postInit() {
		Item itemGenetics = Genetics.getItemGenetics();
		if (itemGenetics != null) {
			GameRegistry.addShapelessRecipe(GeneticsItems.DNADye.get(8),
					Items.GLOWSTONE_DUST, new ItemStack(Items.DYE, 1, 5));
			GameRegistry.addRecipe(new ShapedOreRecipe(GeneticsItems.LaboratoryCasing.get(itemGenetics, 1),
					"iii", "iYi", "iii",
					'i', "ingotIron", 'Y', Mods.Forestry.item("sturdyMachine")));
			GameRegistry.addRecipe(new ShapelessOreRecipe(GeneticsItems.DNADye.get(itemGenetics, 2),
					"dyePurple", "dyeMagenta", "dyePink"));
			GameRegistry.addRecipe(new ShapelessOreRecipe(GeneticsItems.FluorescentDye.get(itemGenetics, 2),
					"dyeOrange", "dyeYellow", "dustGlowstone"));
			GameRegistry.addRecipe(new ShapelessOreRecipe(GeneticsItems.GrowthMedium.get(itemGenetics, 2),
					new ItemStack(Items.DYE, 1, 15), Items.SUGAR));
			GameRegistry.addRecipe(new ShapedOreRecipe(GeneticsItems.EmptySequencer.get(itemGenetics, 1),
					" p ", "iGi", " p ",
					'i', "ingotGold", 'G', Blocks.GLASS_PANE, 'p', Items.PAPER));
			GameRegistry.addRecipe(new ShapedOreRecipe(GeneticsItems.EmptySerum.get(itemGenetics, 1),
					" g ", " G ", "GGG",
					'g', "ingotGold", 'G', Blocks.GLASS_PANE));
			GameRegistry.addRecipe(new ShapedOreRecipe(GeneticsItems.EmptyGenome.get(itemGenetics, 1),
					"sss", "sss", "sss",
					's', GeneticsItems.EmptySerum.get(itemGenetics, 1)));
			GameRegistry.addRecipe(new ShapedOreRecipe(GeneticsItems.Cylinder.get(itemGenetics, 8),
					" g ", "g g", "   ",
					'g', Blocks.GLASS_PANE));
			GameRegistry.addRecipe(new ShapedOreRecipe(GeneticsItems.IntegratedCircuit.get(itemGenetics, 1),
					"l g", " c ", "g l",
					'c', Mods.Forestry.stack("chipsets", 1, 1), 'l', new ItemStack(Items.DYE, 1, 4), 'g', "dustGlowstone"));
			GameRegistry.addRecipe(new ShapedOreRecipe(GeneticsItems.IntegratedCircuit.get(itemGenetics, 1),
					"g l", " c ", "l g",
					'c', Mods.Forestry.stack("chipsets", 1, 1), 'l', new ItemStack(Items.DYE, 1, 4), 'g', "dustGlowstone"));
			GameRegistry.addRecipe(new ShapedOreRecipe(GeneticsItems.IntegratedCasing.get(itemGenetics, 1),
					"ccc", "cdc", "ccc",
					'c', GeneticsItems.IntegratedCircuit.get(itemGenetics, 1), 'd', GeneticsItems.LaboratoryCasing.get(itemGenetics, 1)));
			GameRegistry.addRecipe(new ShapedOreRecipe(GeneticsItems.IntegratedCPU.get(itemGenetics, 1),
					"ccc", "cdc", "ccc",
					'c', GeneticsItems.IntegratedCircuit.get(itemGenetics, 1), 'd', Items.DIAMOND));


			RecipeManagers.carpenterManager.addRecipe(100, Binnie.LIQUID.getFluidStack("water", 2000), null,
					new ItemStack(Genetics.database),
					"X#X", "YEY", "RDR",
					'#', Blocks.GLASS_PANE, 'X', Items.DIAMOND, 'Y', Items.DIAMOND, 'R', Items.REDSTONE, 'D', Items.ENDER_EYE, 'E', Blocks.OBSIDIAN);
			GameRegistry.addSmelting(Genetics.itemSequencer, GeneticsItems.EmptySequencer.get(itemGenetics, 1), 0.0f);
			GameRegistry.addSmelting(Genetics.itemSerum, GeneticsItems.EmptySerum.get(itemGenetics, 1), 0.0f);
			GameRegistry.addSmelting(Genetics.itemSerumArray, GeneticsItems.EmptyGenome.get(itemGenetics, 1), 0.0f);

			GameRegistry.addShapedRecipe(new ItemStack(Genetics.analyst),
					" c ", "cac", " d ",
					'c', GeneticsItems.IntegratedCircuit.get(itemGenetics, 1),
					'a', Mods.Forestry.item("portableAlyzer"),
					'd', new ItemStack(Items.DIAMOND));

			final Item[] dbs = {ExtraBees.dictionary, ExtraTrees.items().itemDictionary, ExtraTrees.items().itemDictionaryLepi, Botany.database};
			if (BinnieCore.isBotanyActive() && BinnieCore.isExtraBeesActive() && BinnieCore.isExtraTreesActive()) {
				for (final Item a2 : dbs) {
					for (final Item b2 : dbs) {
						for (final Item c2 : dbs) {
							for (final Item d : dbs) {
								if (a2 != b2 && a2 != c2 && a2 != d && b2 != c2 && b2 != d && c2 != d) {
									GameRegistry.addShapedRecipe(new ItemStack(Genetics.registry), new Object[]{" b ", "fct", " l ", 'c', GeneticsItems.IntegratedCircuit.get(itemGenetics, 1), 'b', a2, 't', b2, 'f', c2, 'l', d});
								}
							}
						}
					}
				}
			}
		}
	}
}
