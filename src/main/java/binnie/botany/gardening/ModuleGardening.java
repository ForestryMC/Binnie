package binnie.botany.gardening;

import binnie.Binnie;
import binnie.botany.Botany;
import binnie.botany.CreativeTabBotany;
import binnie.botany.api.EnumAcidity;
import binnie.botany.api.EnumMoisture;
import binnie.botany.api.EnumSoilType;
import binnie.botany.ceramic.BlockCeramic;
import binnie.botany.ceramic.BlockCeramicBrick;
import binnie.botany.ceramic.BlockCeramicPatterned;
import binnie.botany.ceramic.BlockStained;
import binnie.botany.ceramic.CeramicTileRecipe;
import binnie.botany.ceramic.PigmentRecipe;
import binnie.botany.farm.CircuitGarden;
import binnie.botany.flower.ItemInsulatedTube;
import binnie.botany.genetics.EnumFlowerColor;
import binnie.botany.items.BotanyItems;
import binnie.botany.items.ItemClay;
import binnie.botany.items.ItemPigment;
import binnie.core.BinnieCore;
import binnie.core.IInitializable;
import binnie.core.Mods;
import binnie.core.block.ItemMetadata;
import binnie.core.block.ItemMetadataRenderer;
import binnie.core.block.MultipassItemRenderer;
import binnie.core.block.TileEntityMetadata;
import cpw.mods.fml.common.registry.GameRegistry;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.RecipeSorter;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class ModuleGardening implements IInitializable {
    public static HashMap<ItemStack, Integer> queuedAcidFertilisers = new HashMap<>();
    public static HashMap<ItemStack, Integer> queuedAlkalineFertilisers = new HashMap<>();
    public static HashMap<ItemStack, Integer> queuedNutrientFertilisers = new HashMap<>();

    @Override
    public void preInit() {
        Botany.plant = new BlockPlant();
        GameRegistry.registerBlock(Botany.plant, ItemWeed.class, "plant");

        Botany.soil = new BlockSoil(EnumSoilType.SOIL, "soil", false);
        GameRegistry.registerBlock(Botany.soil, ItemSoil.class, "soil");

        Botany.loam = new BlockSoil(EnumSoilType.LOAM, "loam", false);
        GameRegistry.registerBlock(Botany.loam, ItemSoil.class, "loam");

        Botany.flowerbed = new BlockSoil(EnumSoilType.FLOWERBED, "flowerbed", false);
        GameRegistry.registerBlock(Botany.flowerbed, ItemSoil.class, "flowerbed");

        Botany.soilNoWeed = new BlockSoil(EnumSoilType.SOIL, "soilNoWeed", true);
        GameRegistry.registerBlock(Botany.soilNoWeed, ItemSoil.class, "soilNoWeed");

        Botany.loamNoWeed = new BlockSoil(EnumSoilType.LOAM, "loamNoWeed", true);
        GameRegistry.registerBlock(Botany.loamNoWeed, ItemSoil.class, "loamNoWeed");

        Botany.flowerbedNoWeed = new BlockSoil(EnumSoilType.FLOWERBED, "flowerbedNoWeed", true);
        GameRegistry.registerBlock(Botany.flowerbedNoWeed, ItemSoil.class, "flowerbedNoWeed");

        Botany.soilMeter = new ItemSoilMeter();
        Botany.insulatedTube = new ItemInsulatedTube();
        Botany.trowelWood = new ItemTrowel(Item.ToolMaterial.WOOD, "Wood");
        Botany.trowelStone = new ItemTrowel(Item.ToolMaterial.STONE, "Stone");
        Botany.trowelIron = new ItemTrowel(Item.ToolMaterial.IRON, "Iron");
        Botany.trowelDiamond = new ItemTrowel(Item.ToolMaterial.EMERALD, "Diamond");
        Botany.trowelGold = new ItemTrowel(Item.ToolMaterial.GOLD, "Gold");
        Botany.misc = Binnie.Item.registerMiscItems(BotanyItems.values(), CreativeTabBotany.instance);
        Botany.pigment = new ItemPigment();
        Botany.clay = new ItemClay();

        Botany.ceramic = new BlockCeramic();
        GameRegistry.registerBlock(Botany.ceramic, ItemMetadata.class, "ceramic");
        BinnieCore.proxy.registerCustomItemRenderer(Item.getItemFromBlock(Botany.ceramic), new ItemMetadataRenderer());

        Botany.stained = new BlockStained();
        GameRegistry.registerBlock(Botany.stained, ItemMetadata.class, "stained");
        BinnieCore.proxy.registerCustomItemRenderer(Item.getItemFromBlock(Botany.stained), new ItemMetadataRenderer());

        Botany.ceramicTile = new BlockCeramicPatterned();
        GameRegistry.registerBlock(Botany.ceramicTile, ItemMetadata.class, "ceramicPattern");
        BinnieCore.proxy.registerCustomItemRenderer(
                Item.getItemFromBlock(Botany.ceramicTile), new MultipassItemRenderer());

        Botany.ceramicBrick = new BlockCeramicBrick();
        GameRegistry.registerBlock(Botany.ceramicBrick, ItemMetadata.class, "ceramicBrick");
        BinnieCore.proxy.registerCustomItemRenderer(
                Item.getItemFromBlock(Botany.ceramicBrick), new MultipassItemRenderer());
    }

    @Override
    public void init() {
        OreDictionary.registerOre("pigment", Botany.pigment);
        RecipeSorter.register("botany:ceramictile", CeramicTileRecipe.class, RecipeSorter.Category.SHAPED, "");
        RecipeSorter.register("botany:pigment", PigmentRecipe.class, RecipeSorter.Category.SHAPED, "");
        ItemStack yellow = new ItemStack(Blocks.yellow_flower, 1);
        ItemStack red = new ItemStack(Blocks.red_flower, 1);
        ItemStack blue = new ItemStack(Blocks.red_flower, 1, 7);

        for (boolean manual : new boolean[] {true, false}) {
            for (boolean fertilised : new boolean[] {true, false}) {
                for (EnumMoisture moist : EnumMoisture.values()) {
                    ItemStack icon =
                            (moist == EnumMoisture.DRY) ? yellow : ((moist == EnumMoisture.NORMAL) ? red : blue);
                    int insulate = 2 - moist.ordinal();
                    if (fertilised) {
                        insulate += 3;
                    }
                    new CircuitGarden(
                            moist,
                            null,
                            manual,
                            fertilised,
                            new ItemStack(Botany.insulatedTube, 1, 128 * insulate),
                            icon);
                    new CircuitGarden(
                            moist,
                            EnumAcidity.ACID,
                            manual,
                            fertilised,
                            new ItemStack(Botany.insulatedTube, 1, 1 + 128 * insulate),
                            icon);
                    new CircuitGarden(
                            moist,
                            EnumAcidity.NEUTRAL,
                            manual,
                            fertilised,
                            new ItemStack(Botany.insulatedTube, 1, 2 + 128 * insulate),
                            icon);
                    new CircuitGarden(
                            moist,
                            EnumAcidity.ALKALINE,
                            manual,
                            fertilised,
                            new ItemStack(Botany.insulatedTube, 1, 3 + 128 * insulate),
                            icon);
                }
            }
        }
    }

    @Override
    public void postInit() {
        GameRegistry.addRecipe(new CeramicTileRecipe());
        for (int mat = 0; mat < 4; ++mat) {
            for (int insulate = 0; insulate < 6; ++insulate) {
                ItemStack tubes = new ItemStack(Botany.insulatedTube, 2, mat + 128 * insulate);
                ItemStack insulateStack = ItemInsulatedTube.getInsulateStack(tubes);
                ItemStack forestryTube = new ItemStack(Mods.forestry.item("thermionicTubes"), 1, mat);
                GameRegistry.addShapelessRecipe(tubes, forestryTube, forestryTube, insulateStack);
            }
        }

        GameRegistry.addShapelessRecipe(
                new ItemStack(Blocks.dirt), new ItemStack(Botany.soil, 1, OreDictionary.WILDCARD_VALUE));
        GameRegistry.addShapelessRecipe(
                new ItemStack(Blocks.dirt), new ItemStack(Botany.loam, 1, OreDictionary.WILDCARD_VALUE));
        GameRegistry.addShapelessRecipe(
                new ItemStack(Blocks.dirt), new ItemStack(Botany.flowerbed, 1, OreDictionary.WILDCARD_VALUE));
        GameRegistry.addShapelessRecipe(
                new ItemStack(Blocks.dirt), new ItemStack(Botany.soilNoWeed, 1, OreDictionary.WILDCARD_VALUE));
        GameRegistry.addShapelessRecipe(
                new ItemStack(Blocks.dirt), new ItemStack(Botany.loamNoWeed, 1, OreDictionary.WILDCARD_VALUE));
        GameRegistry.addShapelessRecipe(
                new ItemStack(Blocks.dirt), new ItemStack(Botany.flowerbedNoWeed, 1, OreDictionary.WILDCARD_VALUE));

        GameRegistry.addRecipe(new ShapedOreRecipe(
                Botany.trowelWood,
                new Object[] {"d  ", " x ", "  s", 'd', Blocks.dirt, 's', "stickWood", 'x', "plankWood"}));
        GameRegistry.addRecipe(new ShapedOreRecipe(
                Botany.trowelStone,
                new Object[] {"d  ", " x ", "  s", 'd', Blocks.dirt, 's', "stickWood", 'x', "cobblestone"}));
        GameRegistry.addRecipe(new ShapedOreRecipe(
                Botany.trowelIron,
                new Object[] {"d  ", " x ", "  s", 'd', Blocks.dirt, 's', "stickWood", 'x', "ingotIron"}));
        GameRegistry.addRecipe(new ShapedOreRecipe(
                Botany.trowelGold,
                new Object[] {"d  ", " x ", "  s", 'd', Blocks.dirt, 's', "stickWood", 'x', "ingotGold"}));
        GameRegistry.addRecipe(new ShapedOreRecipe(
                Botany.trowelDiamond,
                new Object[] {"d  ", " x ", "  s", 'd', Blocks.dirt, 's', "stickWood", 'x', "gemDiamond"}));
        GameRegistry.addRecipe(new ShapedOreRecipe(
                Botany.soilMeter,
                new Object[] {" gg", " rg", "i  ", 'g', "ingotGold", 'r', "dustRedstone", 'i', "ingotIron"}));
        GameRegistry.addShapelessRecipe(
                BotanyItems.Weedkiller.get(4),
                new ItemStack(Items.spider_eye),
                new ItemStack(Items.wheat_seeds),
                new ItemStack(Items.wheat_seeds),
                new ItemStack(Items.wheat_seeds));

        GameRegistry.addShapelessRecipe(BotanyItems.AshPowder.get(4), Mods.forestry.stack("ash"));
        GameRegistry.addShapelessRecipe(BotanyItems.MulchPowder.get(4), Mods.forestry.stack("mulch"));
        GameRegistry.addShapelessRecipe(BotanyItems.CompostPowder.get(4), Mods.forestry.stack("fertilizerBio"));
        GameRegistry.addShapelessRecipe(BotanyItems.FertiliserPowder.get(4), Mods.forestry.stack("fertilizerCompound"));
        GameRegistry.addShapelessRecipe(BotanyItems.PulpPowder.get(4), Mods.forestry.stack("woodPulp"));
        GameRegistry.addRecipe(new ShapelessOreRecipe(BotanyItems.SulphurPowder.get(4), new Object[] {"dustSulphur"}));

        GameRegistry.addRecipe(new ShapelessOreRecipe(
                new ItemStack(Botany.pigment, 2, EnumFlowerColor.BLACK.ordinal()),
                new Object[] {"pigment", "pigment", "dyeBlack"}));

        ModuleGardening.queuedAcidFertilisers.put(BotanyItems.SulphurPowder.get(1), 1);
        ModuleGardening.queuedAcidFertilisers.put(BotanyItems.MulchPowder.get(1), 1);
        ModuleGardening.queuedAcidFertilisers.put(new ItemStack(GameRegistry.findItem("Forestry", "mulch")), 2);

        for (ItemStack stack : OreDictionary.getOres("dustSulfur")) {
            ModuleGardening.queuedAcidFertilisers.put(stack, 2);
        }

        ModuleGardening.queuedAlkalineFertilisers.put(BotanyItems.AshPowder.get(1), 1);
        ModuleGardening.queuedAlkalineFertilisers.put(BotanyItems.PulpPowder.get(1), 1);
        ModuleGardening.queuedAlkalineFertilisers.put(new ItemStack(GameRegistry.findItem("Forestry", "ash")), 2);
        ModuleGardening.queuedAlkalineFertilisers.put(new ItemStack(GameRegistry.findItem("Forestry", "woodPulp")), 2);
        ModuleGardening.queuedNutrientFertilisers.put(BotanyItems.CompostPowder.get(1), 1);
        ModuleGardening.queuedNutrientFertilisers.put(BotanyItems.FertiliserPowder.get(1), 1);
        ModuleGardening.queuedNutrientFertilisers.put(
                new ItemStack(GameRegistry.findItem("Forestry", "fertilizerBio")), 2);
        ModuleGardening.queuedNutrientFertilisers.put(
                new ItemStack(GameRegistry.findItem("Forestry", "fertilizerCompound")), 2);

        for (Map.Entry<ItemStack, Integer> entry : ModuleGardening.queuedAcidFertilisers.entrySet()) {
            addAcidFertiliser(entry.getKey(), entry.getValue());
        }

        for (Map.Entry<ItemStack, Integer> entry : ModuleGardening.queuedAlkalineFertilisers.entrySet()) {
            addAlkalineFertiliser(entry.getKey(), entry.getValue());
        }

        for (Map.Entry<ItemStack, Integer> entry : ModuleGardening.queuedNutrientFertilisers.entrySet()) {
            addNutrientFertiliser(entry.getKey(), entry.getValue());
        }

        GameRegistry.addRecipe(
                BotanyItems.Mortar.get(6), " c ", "cgc", " c ", 'c', Items.clay_ball, 'g', Blocks.gravel);

        for (EnumFlowerColor c : EnumFlowerColor.values()) {
            ItemStack clay = new ItemStack(Botany.clay, 1, c.ordinal());
            ItemStack pigment = new ItemStack(Botany.pigment, 1, c.ordinal());
            GameRegistry.addShapelessRecipe(clay, Items.clay_ball, Items.clay_ball, Items.clay_ball, pigment);
            GameRegistry.addSmelting(clay, TileEntityMetadata.getItemStack(Botany.ceramic, c.ordinal()), 0.0f);
            ItemStack glass = TileEntityMetadata.getItemStack(Botany.stained, c.ordinal());
            glass.stackSize = 4;
            GameRegistry.addShapedRecipe(glass, " g ", "gpg", " g ", 'g', Blocks.glass, 'p', pigment);
        }

        GameRegistry.addRecipe(new PigmentRecipe());
    }

    private ItemStack getStack(int type, int pH, int moisture) {
        if (type < 0 || type > 2 || pH < 0 || pH > 2 || moisture < 0 || moisture > 2) {
            return null;
        }
        return new ItemStack(
                Gardening.getSoilBlock(EnumSoilType.values()[type]),
                1,
                BlockSoil.getMeta(EnumAcidity.values()[pH], EnumMoisture.values()[moisture]));
    }

    private void addAcidFertiliser(ItemStack stack, int strengthMax) {
        if (stack == null) {
            return;
        }

        Gardening.fertiliserAcid.put(stack, strengthMax);
        for (int moisture = 0; moisture < 3; ++moisture) {
            for (int pH = 0; pH < 3; ++pH) {
                for (int type = 0; type < 3; ++type) {
                    int numOfBlocks = strengthMax * strengthMax;
                    for (int strength = 1; strength < strengthMax; ++strength) {
                        ItemStack start = getStack(type, pH, moisture);
                        ItemStack end = getStack(type, pH - strength, moisture);
                        if (end != null) {
                            end.stackSize = numOfBlocks;
                            Object[] stacks = new Object[numOfBlocks + 1];
                            for (int i = 0; i < numOfBlocks; ++i) {
                                stacks[i] = start;
                            }
                            stacks[numOfBlocks] = stack.copy();
                            GameRegistry.addShapelessRecipe(end, stacks);
                        }
                        numOfBlocks /= 2;
                    }
                }
            }
        }
    }

    private void addAlkalineFertiliser(ItemStack stack, int strengthMax) {
        if (stack == null) {
            return;
        }

        Gardening.fertiliserAlkaline.put(stack, strengthMax);
        for (int moisture = 0; moisture < 3; ++moisture) {
            for (int pH = 0; pH < 3; ++pH) {
                for (int type = 0; type < 3; ++type) {
                    int numOfBlocks = strengthMax * strengthMax;
                    for (int strength = 1; strength < strengthMax; ++strength) {
                        ItemStack start = getStack(type, pH, moisture);
                        ItemStack end = getStack(type, pH + strength, moisture);
                        if (end != null) {
                            end.stackSize = numOfBlocks;
                            Object[] stacks = new Object[numOfBlocks + 1];
                            for (int i = 0; i < numOfBlocks; ++i) {
                                stacks[i] = start;
                            }
                            stacks[numOfBlocks] = stack.copy();
                            GameRegistry.addShapelessRecipe(end, stacks);
                        }
                        numOfBlocks /= 2;
                    }
                }
            }
        }
    }

    private void addNutrientFertiliser(ItemStack stack, int strengthMax) {
        if (stack == null) {
            return;
        }

        Gardening.fertiliserNutrient.put(stack, strengthMax);
        for (int moisture = 0; moisture < 3; ++moisture) {
            for (int pH = 0; pH < 3; ++pH) {
                for (int type = 0; type < 3; ++type) {
                    int numOfBlocks = strengthMax * strengthMax;
                    for (int strength = 1; strength < strengthMax; ++strength) {
                        ItemStack start = getStack(type, pH, moisture);
                        ItemStack end = getStack(type + strength, pH, moisture);
                        if (end != null) {
                            end.stackSize = numOfBlocks;
                            Object[] stacks = new Object[numOfBlocks + 1];
                            for (int i = 0; i < numOfBlocks; ++i) {
                                stacks[i] = start;
                            }
                            stacks[numOfBlocks] = stack.copy();
                            GameRegistry.addShapelessRecipe(end, stacks);
                        }
                        numOfBlocks /= 2;
                    }
                }
            }
        }
    }
}
