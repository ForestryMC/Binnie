package binnie.botany.gardening;

import binnie.botany.Botany;
import binnie.botany.CreativeTabBotany;
import binnie.botany.api.EnumAcidity;
import binnie.botany.api.EnumMoisture;
import binnie.botany.api.EnumSoilType;
import binnie.botany.api.IGardeningManager;
import binnie.botany.ceramic.BlockCeramic;
import binnie.botany.ceramic.BlockCeramicPatterned;
import binnie.botany.ceramic.BlockStainedGlass;
import binnie.botany.ceramic.CeramicTileRecipe;
import binnie.botany.ceramic.ItemCeramic;
import binnie.botany.ceramic.ItemStainedGlass;
import binnie.botany.ceramic.PigmentRecipe;
import binnie.botany.ceramic.TileCeramic;
import binnie.botany.ceramic.brick.BlockCeramicBrick;
import binnie.botany.ceramic.brick.ItemCeramicBrick;
import binnie.botany.ceramic.brick.TileCeramicBrick;
import binnie.botany.core.BotanyCore;
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
		// TODO migrate static into BotanyItems
		Botany.plant = new BlockPlant();
		Botany.soil = new BlockSoil(EnumSoilType.SOIL, "soil", false);
		Botany.loam = new BlockSoil(EnumSoilType.LOAM, "loam", false);
		Botany.flowerbed = new BlockSoil(EnumSoilType.FLOWERBED, "flowerbed", false);
		Botany.soilNoWeed = new BlockSoil(EnumSoilType.SOIL, "soil_no_weed", true);
		Botany.loamNoWeed = new BlockSoil(EnumSoilType.LOAM, "loam_no_weed", true);
		Botany.flowerbedNoWeed = new BlockSoil(EnumSoilType.FLOWERBED, "flowerbed_no_weed", true);
		Botany.soilMeter = new ItemSoilMeter();
		Botany.insulatedTube = new ItemInsulatedTube();
		Botany.trowelWood = new ItemTrowel(Item.ToolMaterial.WOOD, "wood");
		Botany.trowelStone = new ItemTrowel(Item.ToolMaterial.STONE, "stone");
		Botany.trowelIron = new ItemTrowel(Item.ToolMaterial.IRON, "iron");
		Botany.trowelDiamond = new ItemTrowel(Item.ToolMaterial.DIAMOND, "diamond");
		Botany.trowelGold = new ItemTrowel(Item.ToolMaterial.GOLD, "gold");
		Botany.misc = new ItemMisc(CreativeTabBotany.instance, BotanyItems.values());
		Botany.pigment = new ItemPigment();
		Botany.clay = new ItemClay();
		Botany.ceramic = new BlockCeramic();
		Botany.stained = new BlockStainedGlass();
		Botany.ceramicTile = new BlockCeramicPatterned();
		Botany.ceramicBrick = new BlockCeramicBrick();

		Botany.proxy.registerBlock(Botany.plant, new ItemWeed(Botany.plant));
		Botany.proxy.registerBlock(Botany.soil, new ItemSoil(Botany.soil));
		Botany.proxy.registerBlock(Botany.loam, new ItemSoil(Botany.loam));
		Botany.proxy.registerBlock(Botany.flowerbed, new ItemSoil(Botany.flowerbed));
		Botany.proxy.registerBlock(Botany.soilNoWeed, new ItemSoil(Botany.soilNoWeed));
		Botany.proxy.registerBlock(Botany.loamNoWeed, new ItemSoil(Botany.loamNoWeed));
		Botany.proxy.registerBlock(Botany.flowerbedNoWeed, new ItemSoil(Botany.flowerbedNoWeed));
		Botany.proxy.registerItem(Botany.soilMeter);
		Botany.proxy.registerItem(Botany.insulatedTube);
		Botany.proxy.registerItem(Botany.trowelWood);
		Botany.proxy.registerItem(Botany.trowelStone);
		Botany.proxy.registerItem(Botany.trowelIron);
		Botany.proxy.registerItem(Botany.trowelDiamond);
		Botany.proxy.registerItem(Botany.trowelGold);
		Botany.proxy.registerItem(Botany.misc);
		Botany.proxy.registerItem(Botany.pigment);
		Botany.proxy.registerItem(Botany.clay);
		Botany.proxy.registerBlock(Botany.ceramic, new ItemCeramic(Botany.ceramic));
		Botany.proxy.registerBlock(Botany.stained, new ItemStainedGlass(Botany.stained));
		Botany.proxy.registerBlock(Botany.ceramicTile, new ItemDesign(Botany.ceramicTile));
		Botany.proxy.registerBlock(Botany.ceramicBrick, new ItemCeramicBrick(Botany.ceramicBrick));

		BinnieCore.getBinnieProxy().registerTileEntity(TileCeramic.class, "botany.tile.ceramic");
		//BinnieCore.proxy.registerCustomItemRenderer(Item.getItemFromBlock(Botany.ceramic), new ItemMetadataRenderer());
		//BinnieCore.proxy.registerCustomItemRenderer(Item.getItemFromBlock(Botany.stained), new ItemMetadataRenderer());
		BinnieCore.getBinnieProxy().registerTileEntity(TileCeramicBrick.class, "botany.tile.ceramicBrick");
		//BinnieCore.proxy.registerCustomItemRenderer(Item.getItemFromBlock(Botany.ceramicTile), new MultipassItemRenderer());
		//BinnieCore.proxy.registerCustomItemRenderer(Item.getItemFromBlock(Botany.ceramicBrick), new MultipassItemRenderer());

		OreDictionary.registerOre("pigment", Botany.pigment);
		OreDictionary.registerOre("toolTrowel", Botany.trowelWood);
		OreDictionary.registerOre("toolTrowel", Botany.trowelStone);
		OreDictionary.registerOre("toolTrowel", Botany.trowelIron);
		OreDictionary.registerOre("toolTrowel", Botany.trowelGold);
		OreDictionary.registerOre("toolTrowel", Botany.trowelDiamond);
	}

	@Override
	public void init() {
		RecipeSorter.register("botany:ceramictile", CeramicTileRecipe.class, RecipeSorter.Category.SHAPED, "");
		RecipeSorter.register("botany:pigment", PigmentRecipe.class, RecipeSorter.Category.SHAPED, "");
		ItemStack yellow = new ItemStack(Blocks.YELLOW_FLOWER, 1);
		ItemStack red = new ItemStack(Blocks.RED_FLOWER, 1);
		ItemStack blue = new ItemStack(Blocks.RED_FLOWER, 1, 7);
		for (boolean manual : new boolean[]{true, false}) {
			for (boolean fertilised : new boolean[]{true, false}) {
				for (EnumMoisture moist : EnumMoisture.values()) {
					ItemStack icon;
					if (moist == EnumMoisture.DRY) {
						icon = yellow;
					} else if (moist == EnumMoisture.NORMAL) {
						icon = red;
					} else {
						icon = blue;
					}
					int insulate = 2 - moist.ordinal();
					if (fertilised) {
						insulate += 3;
					}
					new CircuitGarden(moist, null, manual, fertilised, new ItemStack(Botany.insulatedTube, 1, 128 * insulate), icon);
					new CircuitGarden(moist, EnumAcidity.ACID, manual, fertilised, new ItemStack(Botany.insulatedTube, 1, 1 + 128 * insulate), icon);
					new CircuitGarden(moist, EnumAcidity.NEUTRAL, manual, fertilised, new ItemStack(Botany.insulatedTube, 1, 2 + 128 * insulate), icon);
					new CircuitGarden(moist, EnumAcidity.ALKALINE, manual, fertilised, new ItemStack(Botany.insulatedTube, 1, 3 + 128 * insulate), icon);
				}
			}
		}
	}

	@Override
	public void postInit() {
		IGardeningManager gardening = BotanyCore.getGardening();
		GameRegistry.addRecipe(new CeramicTileRecipe());
		for (int mat = 0; mat < 4; ++mat) {
			for (int insulate = 0; insulate < 6; ++insulate) {
				ItemStack tubes = new ItemStack(Botany.insulatedTube, 2, mat + 128 * insulate);
				ItemStack insulateStack = ItemInsulatedTube.getInsulateStack(tubes);
				ItemStack forestryTube = new ItemStack(Mods.Forestry.item("thermionic_tubes"), 1, mat);
				GameRegistry.addShapelessRecipe(tubes, forestryTube, forestryTube, insulateStack);
			}
		}

		GameRegistry.addRecipe(new ShapedOreRecipe(
			Botany.trowelWood,
			"d  ", " x ", "  s",
			'd', Blocks.DIRT,
			's', "stickWood",
			'x', "plankWood")
		);

		GameRegistry.addRecipe(new ShapedOreRecipe(
			Botany.trowelStone,
			"d  ", " x ", "  s",
			'd', Blocks.DIRT,
			's', "stickWood",
			'x', "cobblestone"
		));

		GameRegistry.addRecipe(new ShapedOreRecipe(
			Botany.trowelIron,
			"d  ", " x ", "  s",
			'd', Blocks.DIRT,
			's', "stickWood",
			'x', "ingotIron"
		));

		GameRegistry.addRecipe(new ShapedOreRecipe(
			Botany.trowelGold,
			"d  ", " x ", "  s",
			'd', Blocks.DIRT,
			's', "stickWood",
			'x', "ingotGold"
		));

		GameRegistry.addRecipe(new ShapedOreRecipe(
			Botany.trowelDiamond,
			"d  ", " x ", "  s",
			'd', Blocks.DIRT,
			's', "stickWood",
			'x', "gemDiamond"
		));

		GameRegistry.addRecipe(new ShapedOreRecipe(
			Botany.soilMeter,
			" gg", " rg", "i  ",
			'g', "ingotGold",
			'r', "dustRedstone",
			'i', "ingotIron"
		));

		GameRegistry.addShapelessRecipe(
			BotanyItems.Weedkiller.get(4),
			new ItemStack(Items.SPIDER_EYE),
			new ItemStack(Items.WHEAT_SEEDS),
			new ItemStack(Items.WHEAT_SEEDS),
			new ItemStack(Items.WHEAT_SEEDS)
		);

		GameRegistry.addShapelessRecipe(
			BotanyItems.AshPowder.get(4),
			Mods.Forestry.stack("ash")
		);

		GameRegistry.addShapelessRecipe(
			BotanyItems.MulchPowder.get(4),
			Mods.Forestry.stack("mulch")
		);

		GameRegistry.addShapelessRecipe(
			BotanyItems.CompostPowder.get(4),
			Mods.Forestry.stack("fertilizer_bio")
		);

		GameRegistry.addShapelessRecipe(
			BotanyItems.FertiliserPowder.get(4),
			Mods.Forestry.stack("fertilizer_compound")
		);

		GameRegistry.addShapelessRecipe(
			BotanyItems.PulpPowder.get(4),
			Mods.Forestry.stack("wood_pulp")
		);

		GameRegistry.addRecipe(new ShapelessOreRecipe(
			BotanyItems.SulphurPowder.get(4),
			"dustSulphur"
		));

		GameRegistry.addRecipe(new ShapelessOreRecipe(
			new ItemStack(Botany.pigment, 2, EnumFlowerColor.Black.ordinal()),
			"pigment", "pigment", "dyeBlack"
		));

		gardening.registerAcidFertiliser(BotanyItems.SulphurPowder.get(1), 1);
		gardening.registerAcidFertiliser(BotanyItems.MulchPowder.get(1), 1);
		gardening.registerAcidFertiliser(Mods.Forestry.stack("mulch"), 2);
		for (ItemStack stack : OreDictionary.getOres("dustSulfur")) {
			gardening.registerAcidFertiliser(stack, 2);
		}

		gardening.registerAlkalineFertiliser(BotanyItems.AshPowder.get(1), 1);
		gardening.registerAlkalineFertiliser(BotanyItems.PulpPowder.get(1), 1);
		gardening.registerAlkalineFertiliser(Mods.Forestry.stack("ash"), 2);
		gardening.registerAlkalineFertiliser(Mods.Forestry.stack("wood_pulp"), 2);
		gardening.registerNutrientFertiliser(BotanyItems.CompostPowder.get(1), 1);
		gardening.registerNutrientFertiliser(BotanyItems.FertiliserPowder.get(1), 1);
		gardening.registerNutrientFertiliser(Mods.Forestry.stack("fertilizer_bio"), 2);
		gardening.registerNutrientFertiliser(Mods.Forestry.stack("fertilizer_compound"), 2);

		for (Map.Entry<ItemStack, Integer> entry : ModuleGardening.queuedAcidFertilisers.entrySet()) {
			addAcidFertiliserRecipe(entry.getKey(), entry.getValue());
		}
		for (Map.Entry<ItemStack, Integer> entry : ModuleGardening.queuedAlkalineFertilisers.entrySet()) {
			addAlkalineFertiliserRecipe(entry.getKey(), entry.getValue());
		}
		for (Map.Entry<ItemStack, Integer> entry : ModuleGardening.queuedNutrientFertilisers.entrySet()) {
			addNutrientFertiliserRecipe(entry.getKey(), entry.getValue());
		}

		GameRegistry.addRecipe(BotanyItems.Mortar.get(6), " c ", "cgc", " c ", 'c', Items.CLAY_BALL, 'g', Blocks.GRAVEL);
		for (EnumFlowerColor c : EnumFlowerColor.values()) {
			ItemStack clay = new ItemStack(Botany.clay, 1, c.ordinal());
			ItemStack pigment = new ItemStack(Botany.pigment, 1, c.ordinal());
			GameRegistry.addShapelessRecipe(clay, Items.CLAY_BALL, Items.CLAY_BALL, Items.CLAY_BALL, pigment);
			GameRegistry.addSmelting(clay, TileEntityMetadata.getItemStack(Botany.ceramic, c.ordinal()), 0.0f);
			ItemStack glass = TileEntityMetadata.getItemStack(Botany.stained, c.ordinal());
			glass.setCount(4);

			GameRegistry.addShapedRecipe(
				glass,
				" g ", "gpg", " g ",
				'g', Blocks.GLASS,
				'p', pigment
			);
		}
		GameRegistry.addRecipe(new PigmentRecipe());
	}

	private ItemStack getStack(int type, int pH, int moisture) {
		if (type >= 0 && type <= 2 && pH >= 0 && pH <= 2 && moisture >= 0 && moisture <= 2) {
			return new ItemStack(BotanyCore.getGardening().getSoilBlock(EnumSoilType.values()[type]), 1, BlockSoil.getMeta(EnumAcidity.values()[pH], EnumMoisture.values()[moisture]));
		}
		return ItemStack.EMPTY;
	}

	private void addAcidFertiliserRecipe(ItemStack stack, int strengthMax) {
		for (int moisture = 0; moisture < 3; ++moisture) {
			for (int pH = 0; pH < 3; ++pH) {
				for (int type = 0; type < 3; ++type) {
					int numOfBlocks = strengthMax * strengthMax;
					for (int strength = 1; strength < strengthMax; ++strength) {
						ItemStack start = getStack(type, pH, moisture);
						ItemStack end = getStack(type, pH - strength, moisture);
						if (!start.isEmpty() && !end.isEmpty()) {
							end.setCount(numOfBlocks);
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

	private void addAlkalineFertiliserRecipe(ItemStack stack, int strengthMax) {
		for (int moisture = 0; moisture < 3; ++moisture) {
			for (int pH = 0; pH < 3; ++pH) {
				for (int type = 0; type < 3; ++type) {
					int numOfBlocks = strengthMax * strengthMax;
					for (int strength = 1; strength < strengthMax; ++strength) {
						ItemStack start = getStack(type, pH, moisture);
						ItemStack end = getStack(type, pH + strength, moisture);
						if (!end.isEmpty()) {
							end.setCount(numOfBlocks);
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

	private void addNutrientFertiliserRecipe(ItemStack stack, int strengthMax) {
		for (int moisture = 0; moisture < 3; ++moisture) {
			for (int pH = 0; pH < 3; ++pH) {
				for (int type = 0; type < 3; ++type) {
					int numOfBlocks = strengthMax * strengthMax;
					for (int strength = 1; strength < strengthMax; ++strength) {
						ItemStack start = getStack(type, pH, moisture);
						ItemStack end = getStack(type + strength, pH, moisture);
						if (!end.isEmpty()) {
							end.setCount(numOfBlocks);
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
