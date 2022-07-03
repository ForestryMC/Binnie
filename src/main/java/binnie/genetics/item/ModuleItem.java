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
                GeneticsItems.DNADye.get(8), Items.glowstone_dust, new ItemStack(Items.dye, 1, 5));

        GameRegistry.addRecipe(new ShapedOreRecipe(
                GeneticsItems.LaboratoryCasing.get(1),
                new Object[] {"iii", "iYi", "iii", 'i', "ingotIron", 'Y', Mods.forestry.item("sturdyMachine")}));

        GameRegistry.addRecipe(new ShapelessOreRecipe(
                GeneticsItems.DNADye.get(2), new Object[] {"dyePurple", "dyeMagenta", "dyePink"}));

        GameRegistry.addRecipe(new ShapelessOreRecipe(
                GeneticsItems.FluorescentDye.get(2), new Object[] {"dyeOrange", "dyeYellow", "dustGlowstone"}));

        GameRegistry.addRecipe(new ShapelessOreRecipe(
                GeneticsItems.GrowthMedium.get(2), new Object[] {new ItemStack(Items.dye, 1, 15), Items.sugar}));

        GameRegistry.addRecipe(new ShapedOreRecipe(
                GeneticsItems.EmptySequencer.get(1),
                new Object[] {" p ", "iGi", " p ", 'i', "ingotGold", 'G', Blocks.glass_pane, 'p', Items.paper}));

        GameRegistry.addRecipe(new ShapedOreRecipe(
                GeneticsItems.EmptySerum.get(1),
                new Object[] {" g ", " G ", "GGG", 'g', "ingotGold", 'G', Blocks.glass_pane}));

        GameRegistry.addRecipe(new ShapedOreRecipe(
                GeneticsItems.EmptyGenome.get(1),
                new Object[] {"sss", "sss", "sss", 's', GeneticsItems.EmptySerum.get(1)}));

        GameRegistry.addRecipe(new ShapedOreRecipe(
                GeneticsItems.Cylinder.get(8), new Object[] {" g ", "g g", "   ", 'g', Blocks.glass_pane}));

        GameRegistry.addRecipe(new ShapedOreRecipe(GeneticsItems.IntegratedCircuit.get(1), new Object[] {
            "l g",
            " c ",
            "g l",
            'c',
            Mods.forestry.stack("chipsets", 1, 1),
            'l',
            new ItemStack(Items.dye, 1, 4),
            'g',
            "dustGlowstone"
        }));

        GameRegistry.addRecipe(new ShapedOreRecipe(GeneticsItems.IntegratedCircuit.get(1), new Object[] {
            "g l",
            " c ",
            "l g",
            'c',
            Mods.forestry.stack("chipsets", 1, 1),
            'l',
            new ItemStack(Items.dye, 1, 4),
            'g',
            "dustGlowstone"
        }));
        GameRegistry.addRecipe(new ShapedOreRecipe(GeneticsItems.IntegratedCasing.get(1), new Object[] {
            "ccc", "cdc", "ccc", 'c', GeneticsItems.IntegratedCircuit.get(1), 'd', GeneticsItems.LaboratoryCasing.get(1)
        }));

        GameRegistry.addRecipe(new ShapedOreRecipe(
                GeneticsItems.IntegratedCPU.get(1),
                new Object[] {"ccc", "cdc", "ccc", 'c', GeneticsItems.IntegratedCircuit.get(1), 'd', Items.diamond}));

        RecipeManagers.carpenterManager.addRecipe(
                100,
                Binnie.Liquid.getLiquidStack("water", 2000),
                null,
                new ItemStack(Genetics.database),
                "X#X",
                "YEY",
                "RDR",
                '#',
                Blocks.glass_pane,
                'X',
                Items.diamond,
                'Y',
                Items.diamond,
                'R',
                Items.redstone,
                'D',
                Items.ender_eye,
                'E',
                Blocks.obsidian);

        GameRegistry.addSmelting(Genetics.itemSequencer, GeneticsItems.EmptySequencer.get(1), 0.0f);
        GameRegistry.addSmelting(Genetics.itemSerum, GeneticsItems.EmptySerum.get(1), 0.0f);
        GameRegistry.addSmelting(Genetics.itemSerumArray, GeneticsItems.EmptyGenome.get(1), 0.0f);

        // TODONE refactor refactor refactor this!
        // CombatZAK up to the task!
        Item beealyzer = Mods.forestry.item("beealyzer");
        Item treealyzer = Mods.forestry.item("treealyzer");

        // This bit prevents a crash if the Forestry butterflies module is disabled. You can use whatever placeholder
        // item you but a diamond seems reasonable
        // theoretically similar checks could be performed for Apiculture and Arboriculture; but I will leave that to
        // the mod owner to decide
        Item flutterlyzer = BinnieCore.isLepidopteryActive()
                ? Mods.forestry.item("flutterlyzer") // grab the forestry flutterlyzer ONLY IF lepidopterology is active
                : Items.diamond; // otherwise just replace it with another diamond

        ItemStack circuit = GeneticsItems.IntegratedCircuit.get(1); // get a reference to Integrated circuit
        Item diamond = Items.diamond; // save a reference to diamond

        ItemStack analyst =
                new ItemStack(Genetics.analyst); // save a reference to the analyst, will make the next bit easier

        // the following code is a sufficient replacement for the 3 nested loops that preceded it; arguably this is
        // still
        // more trouble than it's worth. A single recipe is probably fine, with the analyzers in a specific arrangement,
        // but I will leave it to the mod owner to make that call

        // there are literally only six permutations for this recipe, so let's just write them all out and save
        // ourselves
        // some cycles rather than spinning through an n^3 loop wasting most of the combinations.
        // expand the first one out for readability, but the following five are compressed to a single line
        GameRegistry.addShapedRecipe(
                analyst,
                " b ",
                "tcf",
                " d ",
                'b',
                beealyzer,
                't',
                treealyzer,
                'f',
                flutterlyzer,
                'c',
                circuit,
                'd',
                diamond);

        GameRegistry.addShapedRecipe(
                analyst,
                " b ",
                "fct",
                " d ",
                'b',
                beealyzer,
                't',
                treealyzer,
                'f',
                flutterlyzer,
                'c',
                circuit,
                'd',
                diamond);
        GameRegistry.addShapedRecipe(
                analyst,
                " t ",
                "bcf",
                " d ",
                'b',
                beealyzer,
                't',
                treealyzer,
                'f',
                flutterlyzer,
                'c',
                circuit,
                'd',
                diamond);
        GameRegistry.addShapedRecipe(
                analyst,
                " t ",
                "fcb",
                " d ",
                'b',
                beealyzer,
                't',
                treealyzer,
                'f',
                flutterlyzer,
                'c',
                circuit,
                'd',
                diamond);
        GameRegistry.addShapedRecipe(
                analyst,
                " f ",
                "bct",
                " d ",
                'b',
                beealyzer,
                't',
                treealyzer,
                'f',
                flutterlyzer,
                'c',
                circuit,
                'd',
                diamond);
        GameRegistry.addShapedRecipe(
                analyst,
                " f ",
                "tcb",
                " d ",
                'b',
                beealyzer,
                't',
                treealyzer,
                'f',
                flutterlyzer,
                'c',
                circuit,
                'd',
                diamond);

        // get some references to dictionary/databases for the various modules; these names are very inconsistent for
        // like items.
        // should be refactored in their respective modules.
        ItemStack registry = new ItemStack(Genetics.registry);
        Item beeDb = ExtraBees.dictionary;
        Item treeDb = ExtraTrees.itemDictionary;
        Item flwrDb = Botany.database;

        // do a check to ensure that the butterfly module is enabled before using the butterfly dictionary.
        // a similar check could be done for arboriculture and apiculture as well, but that is left to the mod owner
        Item lepiDb = BinnieCore.isLepidopteryActive()
                ? ExtraTrees.itemDictionaryLepi
                : Items.diamond; // just use a diamond if Butterflies is disabled
        // going to use the circuit reference above as well

        // as above, there are only 24 permutations here; let's find a way to just get those recipes rather than trying
        // 256 combinations in an n^4 loop. This will help performance and maybe be a bit more maintainable.
        if (BinnieCore.isBotanyActive() && BinnieCore.isExtraBeesActive() && BinnieCore.isExtraTreesActive()) {
            // 24 full recipe lines is a bit much to maintain; so we'll use a loop here, but we're only going
            // to run it 24 times. Start by making an array of all 24 valid dictionary permutations...
            String[] permutations = new String[] {
                "btlf", "btfl", "bltf", "blft", "bftl", "bflt",
                "tblf", "tbfl", "tlbf", "tlfb", "tfbl", "tflb",
                "lbtf", "lbft", "ltbf", "ltfb", "lfbt", "lftb",
                "fbtl", "fblt", "ftbl", "ftlb", "flbt", "fltb"
            };

            // now go through each of the permutations and create a recipe
            for (String p : permutations) {
                GameRegistry.addShapedRecipe(
                        registry, // adding the recipe with registry as output
                        // this line is the 3-string arrangement, with the characters pulled from the current
                        // permutation
                        String.format(" %c ", p.charAt(0)),
                        String.format("%cc%c", p.charAt(1), p.charAt(2)),
                        String.format(" %c ", p.charAt(3)),
                        'b',
                        beeDb,
                        't',
                        treeDb,
                        'l',
                        lepiDb,
                        'f',
                        flwrDb,
                        'c',
                        circuit);
            }
        }
    }
}
