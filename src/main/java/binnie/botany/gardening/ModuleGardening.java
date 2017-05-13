package binnie.botany.gardening;

import binnie.botany.Botany;
import binnie.botany.CreativeTabBotany;
import binnie.botany.api.EnumAcidity;
import binnie.botany.api.EnumMoisture;
import binnie.botany.api.EnumSoilType;
import binnie.botany.ceramic.*;
import binnie.botany.ceramic.brick.BlockCeramicBrick;
import binnie.botany.ceramic.brick.ItemCeramicBrick;
import binnie.botany.ceramic.brick.TileCeramicBrick;
import binnie.botany.farm.CircuitGarden;
import binnie.botany.flower.ItemInsulatedTube;
import binnie.botany.genetics.EnumFlowerColor;
import binnie.botany.items.BotanyItems;
import binnie.botany.items.ItemClay;
import binnie.botany.items.ItemPigment;
import binnie.core.BinnieCore;
import binnie.core.IInitializable;
import binnie.core.Mods;
import binnie.core.block.TileEntityMetadata;
import binnie.core.item.ItemMisc;
import binnie.extratrees.carpentry.ItemDesign;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.RecipeSorter;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.util.HashMap;
import java.util.Map;

public class ModuleGardening implements IInitializable {
	public static final HashMap<ItemStack, Integer> queuedAcidFertilisers = new HashMap<>();
	public static final HashMap<ItemStack, Integer> queuedAlkalineFertilisers = new HashMap<>();
	public static final HashMap<ItemStack, Integer> queuedNutrientFertilisers = new HashMap<>();

	@Override
	public void preInit() {
		Botany.plant = new BlockPlant();
		Botany.proxy.registerBlock(Botany.plant, new ItemWeed(Botany.plant));
		Botany.soil = new BlockSoil(EnumSoilType.SOIL, "soil", false);
		Botany.proxy.registerBlock(Botany.soil, new ItemSoil(Botany.soil));
		Botany.loam = new BlockSoil(EnumSoilType.LOAM, "loam", false);
		Botany.proxy.registerBlock(Botany.loam, new ItemSoil(Botany.loam));
		Botany.flowerbed = new BlockSoil(EnumSoilType.FLOWERBED, "flowerbed", false);
		Botany.proxy.registerBlock(Botany.flowerbed, new ItemSoil(Botany.flowerbed));
		Botany.soilNoWeed = new BlockSoil(EnumSoilType.SOIL, "soil_no_weed", true);
		Botany.proxy.registerBlock(Botany.soilNoWeed, new ItemSoil(Botany.soilNoWeed));
		Botany.loamNoWeed = new BlockSoil(EnumSoilType.LOAM, "loam_no_weed", true);
		Botany.proxy.registerBlock(Botany.loamNoWeed, new ItemSoil(Botany.loamNoWeed));
		Botany.flowerbedNoWeed = new BlockSoil(EnumSoilType.FLOWERBED, "flowerbed_no_weed", true);
		Botany.proxy.registerBlock(Botany.flowerbedNoWeed, new ItemSoil(Botany.flowerbedNoWeed));
		Botany.soilMeter = new ItemSoilMeter();
		Botany.proxy.registerItem(Botany.soilMeter);
		Botany.insulatedTube = new ItemInsulatedTube();
		Botany.proxy.registerItem(Botany.insulatedTube);
		Botany.trowelWood = new ItemTrowel(Item.ToolMaterial.WOOD, "wood");
		Botany.proxy.registerItem(Botany.trowelWood);
		Botany.trowelStone = new ItemTrowel(Item.ToolMaterial.STONE, "stone");
		Botany.proxy.registerItem(Botany.trowelStone);
		Botany.trowelIron = new ItemTrowel(Item.ToolMaterial.IRON, "iron");
		Botany.proxy.registerItem(Botany.trowelIron);
		Botany.trowelDiamond = new ItemTrowel(Item.ToolMaterial.DIAMOND, "diamond");
		Botany.proxy.registerItem(Botany.trowelDiamond);
		Botany.trowelGold = new ItemTrowel(Item.ToolMaterial.GOLD, "gold");
		Botany.proxy.registerItem(Botany.trowelGold);
		Botany.misc = new ItemMisc(CreativeTabBotany.instance, BotanyItems.values());
		Botany.proxy.registerItem(Botany.misc);
		Botany.pigment = new ItemPigment();
		Botany.proxy.registerItem(Botany.pigment);
		Botany.clay = new ItemClay();
		Botany.proxy.registerItem(Botany.clay);
		Botany.ceramic = new BlockCeramic();
		Botany.proxy.registerBlock(Botany.ceramic, new ItemCeramic(Botany.ceramic));
		BinnieCore.getBinnieProxy().registerTileEntity(TileCeramic.class, "botany.tile.ceramic");
//		BinnieCore.proxy.registerCustomItemRenderer(Item.getItemFromBlock(Botany.ceramic), new ItemMetadataRenderer());
		Botany.stained = new BlockStainedGlass();
		Botany.proxy.registerBlock(Botany.stained, new ItemStainedGlass(Botany.stained));
//		BinnieCore.proxy.registerCustomItemRenderer(Item.getItemFromBlock(Botany.stained), new ItemMetadataRenderer());
		Botany.ceramicTile = new BlockCeramicPatterned();
		Botany.proxy.registerBlock(Botany.ceramicTile, new ItemDesign(Botany.ceramicTile));
		Botany.ceramicBrick = new BlockCeramicBrick();
		Botany.proxy.registerBlock(Botany.ceramicBrick, new ItemCeramicBrick(Botany.ceramicBrick));
		BinnieCore.getBinnieProxy().registerTileEntity(TileCeramicBrick.class, "botany.tile.ceramicBrick");
//		BinnieCore.proxy.registerCustomItemRenderer(Item.getItemFromBlock(Botany.ceramicTile), new MultipassItemRenderer());
//		BinnieCore.proxy.registerCustomItemRenderer(Item.getItemFromBlock(Botany.ceramicBrick), new MultipassItemRenderer());
	}

	@Override
	public void init() {
		OreDictionary.registerOre("pigment", Botany.pigment);
		RecipeSorter.register("botany:ceramictile", CeramicTileRecipe.class, RecipeSorter.Category.SHAPED, "");
		RecipeSorter.register("botany:pigment", PigmentRecipe.class, RecipeSorter.Category.SHAPED, "");
		final ItemStack yellow = new ItemStack(Blocks.YELLOW_FLOWER, 1);
		final ItemStack red = new ItemStack(Blocks.RED_FLOWER, 1);
		final ItemStack blue = new ItemStack(Blocks.RED_FLOWER, 1, 7);
		for (boolean manual : new boolean[]{true, false}) {
			for (boolean fertilised : new boolean[]{true, false}) {
				for (EnumMoisture moist : EnumMoisture.values()) {
					ItemStack icon;
					if (moist == EnumMoisture.Dry) {
						icon = yellow;
					} else if (moist == EnumMoisture.Normal) {
						icon = red;
					} else {
						icon = blue;
					}
					int insulate = 2 - moist.ordinal();
					if (fertilised) {
						insulate += 3;
					}
					new CircuitGarden(moist, null, manual, fertilised, new ItemStack(Botany.insulatedTube, 1, 0 + 128 * insulate), icon);
					new CircuitGarden(moist, EnumAcidity.Acid, manual, fertilised, new ItemStack(Botany.insulatedTube, 1, 1 + 128 * insulate), icon);
					new CircuitGarden(moist, EnumAcidity.Neutral, manual, fertilised, new ItemStack(Botany.insulatedTube, 1, 2 + 128 * insulate), icon);
					new CircuitGarden(moist, EnumAcidity.Alkaline, manual, fertilised, new ItemStack(Botany.insulatedTube, 1, 3 + 128 * insulate), icon);
				}
			}
		}
	}

	@Override
	public void postInit() {
		GameRegistry.addRecipe(new CeramicTileRecipe());
		for (int mat = 0; mat < 4; ++mat) {
			for (int insulate = 0; insulate < 6; ++insulate) {
				final ItemStack tubes = new ItemStack(Botany.insulatedTube, 2, mat + 128 * insulate);
				final ItemStack insulateStack = ItemInsulatedTube.getInsulateStack(tubes);
				final ItemStack forestryTube = new ItemStack(Mods.Forestry.item("thermionic_tubes"), 1, mat);
				GameRegistry.addShapelessRecipe(tubes, forestryTube, forestryTube, insulateStack);
			}
		}
		GameRegistry.addRecipe(new ShapedOreRecipe(Botany.trowelWood, "d  ", " x ", "  s", 'd', Blocks.DIRT, 's', "stickWood", 'x', "plankWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(Botany.trowelStone, "d  ", " x ", "  s", 'd', Blocks.DIRT, 's', "stickWood", 'x', "cobblestone"));
		GameRegistry.addRecipe(new ShapedOreRecipe(Botany.trowelIron, "d  ", " x ", "  s", 'd', Blocks.DIRT, 's', "stickWood", 'x', "ingotIron"));
		GameRegistry.addRecipe(new ShapedOreRecipe(Botany.trowelGold, "d  ", " x ", "  s", 'd', Blocks.DIRT, 's', "stickWood", 'x', "ingotGold"));
		GameRegistry.addRecipe(new ShapedOreRecipe(Botany.trowelDiamond, "d  ", " x ", "  s", 'd', Blocks.DIRT, 's', "stickWood", 'x', "gemDiamond"));
		GameRegistry.addRecipe(new ShapedOreRecipe(Botany.soilMeter, " gg", " rg", "i  ", 'g', "ingotGold", 'r', "dustRedstone", 'i', "ingotIron"));
		GameRegistry.addShapelessRecipe(BotanyItems.Weedkiller.get(4), new ItemStack(Items.SPIDER_EYE), new ItemStack(Items.WHEAT_SEEDS), new ItemStack(Items.WHEAT_SEEDS), new ItemStack(Items.WHEAT_SEEDS));
		GameRegistry.addShapelessRecipe(BotanyItems.AshPowder.get(4), Mods.Forestry.stack("ash"));
		GameRegistry.addShapelessRecipe(BotanyItems.MulchPowder.get(4), Mods.Forestry.stack("mulch"));
		GameRegistry.addShapelessRecipe(BotanyItems.CompostPowder.get(4), Mods.Forestry.stack("fertilizer_bio"));
		GameRegistry.addShapelessRecipe(BotanyItems.FertiliserPowder.get(4), Mods.Forestry.stack("fertilizer_compound"));
		GameRegistry.addShapelessRecipe(BotanyItems.PulpPowder.get(4), Mods.Forestry.stack("wood_pulp"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(BotanyItems.SulphurPowder.get(4), "dustSulphur"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Botany.pigment, 2, EnumFlowerColor.Black.ordinal()), "pigment", "pigment", "dyeBlack"));
		ModuleGardening.queuedAcidFertilisers.put(BotanyItems.SulphurPowder.get(1), 1);
		ModuleGardening.queuedAcidFertilisers.put(BotanyItems.MulchPowder.get(1), 1);
		ModuleGardening.queuedAcidFertilisers.put(Mods.Forestry.stack("mulch"), 2);
		for (final ItemStack stack : OreDictionary.getOres("dustSulfur")) {
			ModuleGardening.queuedAcidFertilisers.put(stack, 2);
		}
		ModuleGardening.queuedAlkalineFertilisers.put(BotanyItems.AshPowder.get(1), 1);
		ModuleGardening.queuedAlkalineFertilisers.put(BotanyItems.PulpPowder.get(1), 1);
		ModuleGardening.queuedAlkalineFertilisers.put(Mods.Forestry.stack("ash"), 2);
		ModuleGardening.queuedAlkalineFertilisers.put(Mods.Forestry.stack("wood_pulp"), 2);
		ModuleGardening.queuedNutrientFertilisers.put(BotanyItems.CompostPowder.get(1), 1);
		ModuleGardening.queuedNutrientFertilisers.put(BotanyItems.FertiliserPowder.get(1), 1);
		ModuleGardening.queuedNutrientFertilisers.put(Mods.Forestry.stack("fertilizer_bio"), 2);
		ModuleGardening.queuedNutrientFertilisers.put(Mods.Forestry.stack("fertilizer_compound"), 2);
		for (final Map.Entry<ItemStack, Integer> entry : ModuleGardening.queuedAcidFertilisers.entrySet()) {
			this.addAcidFertiliser(entry.getKey(), entry.getValue());
		}
		for (final Map.Entry<ItemStack, Integer> entry : ModuleGardening.queuedAlkalineFertilisers.entrySet()) {
			this.addAlkalineFertiliser(entry.getKey(), entry.getValue());
		}
		for (final Map.Entry<ItemStack, Integer> entry : ModuleGardening.queuedNutrientFertilisers.entrySet()) {
			this.addNutrientFertiliser(entry.getKey(), entry.getValue());
		}
		GameRegistry.addRecipe(BotanyItems.Mortar.get(6), " c ", "cgc", " c ", 'c', Items.CLAY_BALL, 'g', Blocks.GRAVEL);
		for (final EnumFlowerColor c : EnumFlowerColor.values()) {
			final ItemStack clay = new ItemStack(Botany.clay, 1, c.ordinal());
			final ItemStack pigment = new ItemStack(Botany.pigment, 1, c.ordinal());
			GameRegistry.addShapelessRecipe(clay, Items.CLAY_BALL, Items.CLAY_BALL, Items.CLAY_BALL, pigment);
			GameRegistry.addSmelting(clay, TileEntityMetadata.getItemStack(Botany.ceramic, c.ordinal()), 0.0f);
			final ItemStack glass = TileEntityMetadata.getItemStack(Botany.stained, c.ordinal());
			glass.setCount(4);
			GameRegistry.addShapedRecipe(glass, " g ", "gpg", " g ", 'g', Blocks.GLASS, 'p', pigment);
		}
		GameRegistry.addRecipe(new PigmentRecipe());
	}

	private ItemStack getStack(final int type, final int pH, final int moisture) {
		if (type >= 0 && type <= 2 && pH >= 0 && pH <= 2 && moisture >= 0 && moisture <= 2) {
			return new ItemStack(Gardening.getSoilBlock(EnumSoilType.values()[type]), 1, BlockSoil.getMeta(EnumAcidity.values()[pH], EnumMoisture.values()[moisture]));
		}
		return ItemStack.EMPTY;
	}

	private void addAcidFertiliser(final ItemStack stack, final int strengthMax) {
		Gardening.fertiliserAcid.put(stack, strengthMax);
		for (int moisture = 0; moisture < 3; ++moisture) {
			for (int pH = 0; pH < 3; ++pH) {
				for (int type = 0; type < 3; ++type) {
					int numOfBlocks = strengthMax * strengthMax;
					for (int strength = 1; strength < strengthMax; ++strength) {
						final ItemStack start = this.getStack(type, pH, moisture);
						final ItemStack end = this.getStack(type, pH - strength, moisture);
						if (!start.isEmpty() && !end.isEmpty()) {
							end.setCount(numOfBlocks);
							final Object[] stacks = new Object[numOfBlocks + 1];
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

	private void addAlkalineFertiliser(final ItemStack stack, final int strengthMax) {
		Gardening.fertiliserAlkaline.put(stack, strengthMax);
		for (int moisture = 0; moisture < 3; ++moisture) {
			for (int pH = 0; pH < 3; ++pH) {
				for (int type = 0; type < 3; ++type) {
					int numOfBlocks = strengthMax * strengthMax;
					for (int strength = 1; strength < strengthMax; ++strength) {
						final ItemStack start = this.getStack(type, pH, moisture);
						final ItemStack end = this.getStack(type, pH + strength, moisture);
						if (!end.isEmpty()) {
							end.setCount(numOfBlocks);
							final Object[] stacks = new Object[numOfBlocks + 1];
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

	private void addNutrientFertiliser(final ItemStack stack, final int strengthMax) {
		Gardening.fertiliserNutrient.put(stack, strengthMax);
		for (int moisture = 0; moisture < 3; ++moisture) {
			for (int pH = 0; pH < 3; ++pH) {
				for (int type = 0; type < 3; ++type) {
					int numOfBlocks = strengthMax * strengthMax;
					for (int strength = 1; strength < strengthMax; ++strength) {
						final ItemStack start = this.getStack(type, pH, moisture);
						final ItemStack end = this.getStack(type + strength, pH, moisture);
						if (!end.isEmpty()) {
							end.setCount(numOfBlocks);
							final Object[] stacks = new Object[numOfBlocks + 1];
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
