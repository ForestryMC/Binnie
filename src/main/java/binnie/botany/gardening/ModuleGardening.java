package binnie.botany.gardening;

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

import net.minecraftforge.fml.common.registry.GameRegistry;

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

public class ModuleGardening implements IInitializable {
	public static final HashMap<ItemStack, Integer> queuedAcidFertilisers = new HashMap<>();
	public static final HashMap<ItemStack, Integer> queuedAlkalineFertilisers = new HashMap<>();
	public static final HashMap<ItemStack, Integer> queuedNutrientFertilisers = new HashMap<>();

	public BlockPlant plant;
	public ItemTrowel trowelWood;
	public ItemTrowel trowelStone;
	public ItemTrowel trowelIron;
	public ItemTrowel trowelDiamond;
	public ItemTrowel trowelGold;
	public BlockSoil soil;
	public BlockSoil loam;
	public BlockSoil flowerbed;
	public BlockSoil soilNoWeed;
	public BlockSoil loamNoWeed;
	public BlockSoil flowerbedNoWeed;
	public ItemInsulatedTube insulatedTube;
	public ItemSoilMeter soilMeter;
	public ItemMisc misc;
	public ItemPigment pigment;
	public ItemClay clay;
	public BlockCeramic ceramic;
	public BlockCeramicPatterned ceramicTile;
	public BlockStainedGlass stained;
	public BlockCeramicBrick ceramicBrick;

	@Override
	public void preInit() {
		plant = new BlockPlant();
		soil = new BlockSoil(EnumSoilType.SOIL, "soil", false);
		loam = new BlockSoil(EnumSoilType.LOAM, "loam", false);
		flowerbed = new BlockSoil(EnumSoilType.FLOWERBED, "flowerbed", false);
		soilNoWeed = new BlockSoil(EnumSoilType.SOIL, "soil_no_weed", true);
		loamNoWeed = new BlockSoil(EnumSoilType.LOAM, "loam_no_weed", true);
		flowerbedNoWeed = new BlockSoil(EnumSoilType.FLOWERBED, "flowerbed_no_weed", true);
		soilMeter = new ItemSoilMeter();
		insulatedTube = new ItemInsulatedTube();
		trowelWood = new ItemTrowel(Item.ToolMaterial.WOOD, "wood");
		trowelStone = new ItemTrowel(Item.ToolMaterial.STONE, "stone");
		trowelIron = new ItemTrowel(Item.ToolMaterial.IRON, "iron");
		trowelDiamond = new ItemTrowel(Item.ToolMaterial.DIAMOND, "diamond");
		trowelGold = new ItemTrowel(Item.ToolMaterial.GOLD, "gold");
		misc = new ItemMisc(CreativeTabBotany.instance, BotanyItems.values());
		pigment = new ItemPigment();
		clay = new ItemClay();
		ceramic = new BlockCeramic();
		stained = new BlockStainedGlass();
		ceramicTile = new BlockCeramicPatterned();
		ceramicBrick = new BlockCeramicBrick();

		Botany.proxy.registerBlock(plant, new ItemWeed(plant));
		Botany.proxy.registerBlock(soil, new ItemSoil(soil));
		Botany.proxy.registerBlock(loam, new ItemSoil(loam));
		Botany.proxy.registerBlock(flowerbed, new ItemSoil(flowerbed));
		Botany.proxy.registerBlock(soilNoWeed, new ItemSoil(soilNoWeed));
		Botany.proxy.registerBlock(loamNoWeed, new ItemSoil(loamNoWeed));
		Botany.proxy.registerBlock(flowerbedNoWeed, new ItemSoil(flowerbedNoWeed));
		Botany.proxy.registerItem(soilMeter);
		Botany.proxy.registerItem(insulatedTube);
		Botany.proxy.registerItem(trowelWood);
		Botany.proxy.registerItem(trowelStone);
		Botany.proxy.registerItem(trowelIron);
		Botany.proxy.registerItem(trowelDiamond);
		Botany.proxy.registerItem(trowelGold);
		Botany.proxy.registerItem(misc);
		Botany.proxy.registerItem(pigment);
		Botany.proxy.registerItem(clay);
		Botany.proxy.registerBlock(ceramic, new ItemCeramic(ceramic));
		Botany.proxy.registerBlock(stained, new ItemStainedGlass(stained));
		Botany.proxy.registerBlock(ceramicTile, new ItemDesign(ceramicTile));
		Botany.proxy.registerBlock(ceramicBrick, new ItemCeramicBrick(ceramicBrick));

		BinnieCore.getBinnieProxy().registerTileEntity(TileCeramic.class, "botany.tile.ceramic");
		//BinnieCore.proxy.registerCustomItemRenderer(Item.getItemFromBlock(ceramic), new ItemMetadataRenderer());
		//BinnieCore.proxy.registerCustomItemRenderer(Item.getItemFromBlock(stained), new ItemMetadataRenderer());
		BinnieCore.getBinnieProxy().registerTileEntity(TileCeramicBrick.class, "botany.tile.ceramicBrick");
		//BinnieCore.proxy.registerCustomItemRenderer(Item.getItemFromBlock(ceramicTile), new MultipassItemRenderer());
		//BinnieCore.proxy.registerCustomItemRenderer(Item.getItemFromBlock(ceramicBrick), new MultipassItemRenderer());

		OreDictionary.registerOre("pigment", pigment);
		OreDictionary.registerOre("toolTrowel", trowelWood);
		OreDictionary.registerOre("toolTrowel", trowelStone);
		OreDictionary.registerOre("toolTrowel", trowelIron);
		OreDictionary.registerOre("toolTrowel", trowelGold);
		OreDictionary.registerOre("toolTrowel", trowelDiamond);
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
					new CircuitGarden(moist, null, manual, fertilised, new ItemStack(insulatedTube, 1, 128 * insulate), icon);
					new CircuitGarden(moist, EnumAcidity.ACID, manual, fertilised, new ItemStack(insulatedTube, 1, 1 + 128 * insulate), icon);
					new CircuitGarden(moist, EnumAcidity.NEUTRAL, manual, fertilised, new ItemStack(insulatedTube, 1, 2 + 128 * insulate), icon);
					new CircuitGarden(moist, EnumAcidity.ALKALINE, manual, fertilised, new ItemStack(insulatedTube, 1, 3 + 128 * insulate), icon);
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
				ItemStack tubes = new ItemStack(insulatedTube, 2, mat + 128 * insulate);
				ItemStack insulateStack = ItemInsulatedTube.getInsulateStack(tubes);
				ItemStack forestryTube = new ItemStack(Mods.Forestry.item("thermionic_tubes"), 1, mat);
				GameRegistry.addShapelessRecipe(tubes, forestryTube, forestryTube, insulateStack);
			}
		}

		GameRegistry.addRecipe(new ShapedOreRecipe(
				trowelWood,
				"d  ", " x ", "  s",
				'd', Blocks.DIRT,
				's', "stickWood",
				'x', "plankWood")
		);

		GameRegistry.addRecipe(new ShapedOreRecipe(
				trowelStone,
				"d  ", " x ", "  s",
				'd', Blocks.DIRT,
				's', "stickWood",
				'x', "cobblestone"
		));

		GameRegistry.addRecipe(new ShapedOreRecipe(
				trowelIron,
				"d  ", " x ", "  s",
				'd', Blocks.DIRT,
				's', "stickWood",
				'x', "ingotIron"
		));

		GameRegistry.addRecipe(new ShapedOreRecipe(
				trowelGold,
				"d  ", " x ", "  s",
				'd', Blocks.DIRT,
				's', "stickWood",
				'x', "ingotGold"
		));

		GameRegistry.addRecipe(new ShapedOreRecipe(
				trowelDiamond,
				"d  ", " x ", "  s",
				'd', Blocks.DIRT,
				's', "stickWood",
				'x', "gemDiamond"
		));

		GameRegistry.addRecipe(new ShapedOreRecipe(
				soilMeter,
				" gg", " rg", "i  ",
				'g', "ingotGold",
				'r', "dustRedstone",
				'i', "ingotIron"
		));

		GameRegistry.addShapelessRecipe(
				BotanyItems.WEEDKILLER.get(4),
				new ItemStack(Items.SPIDER_EYE),
				new ItemStack(Items.WHEAT_SEEDS),
				new ItemStack(Items.WHEAT_SEEDS),
				new ItemStack(Items.WHEAT_SEEDS)
		);

		GameRegistry.addShapelessRecipe(
				BotanyItems.POWDER_ASH.get(4),
				Mods.Forestry.stack("ash")
		);

		GameRegistry.addShapelessRecipe(
				BotanyItems.POWDER_MULCH.get(4),
				Mods.Forestry.stack("mulch")
		);

		GameRegistry.addShapelessRecipe(
				BotanyItems.POWDER_COMPOST.get(4),
				Mods.Forestry.stack("fertilizer_bio")
		);

		GameRegistry.addShapelessRecipe(
				BotanyItems.POWDER_FERTILISER.get(4),
				Mods.Forestry.stack("fertilizer_compound")
		);

		GameRegistry.addShapelessRecipe(
				BotanyItems.POWDER_PULP.get(4),
				Mods.Forestry.stack("wood_pulp")
		);

		GameRegistry.addRecipe(new ShapelessOreRecipe(
				BotanyItems.POWDER_SULPHUR.get(4),
				"dustSulphur"
		));

		GameRegistry.addRecipe(new ShapelessOreRecipe(
				new ItemStack(pigment, 2, EnumFlowerColor.Black.ordinal()),
				"pigment", "pigment", "dyeBlack"
		));

		gardening.registerAcidFertiliser(BotanyItems.POWDER_SULPHUR.get(1), 1);
		gardening.registerAcidFertiliser(BotanyItems.POWDER_MULCH.get(1), 1);
		gardening.registerAcidFertiliser(Mods.Forestry.stack("mulch"), 2);
		for (ItemStack stack : OreDictionary.getOres("dustSulfur")) {
			gardening.registerAcidFertiliser(stack, 2);
		}

		gardening.registerAlkalineFertiliser(BotanyItems.POWDER_ASH.get(1), 1);
		gardening.registerAlkalineFertiliser(BotanyItems.POWDER_PULP.get(1), 1);
		gardening.registerAlkalineFertiliser(Mods.Forestry.stack("ash"), 2);
		gardening.registerAlkalineFertiliser(Mods.Forestry.stack("wood_pulp"), 2);
		gardening.registerNutrientFertiliser(BotanyItems.POWDER_COMPOST.get(1), 1);
		gardening.registerNutrientFertiliser(BotanyItems.POWDER_FERTILISER.get(1), 1);
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

		GameRegistry.addRecipe(BotanyItems.MORTAR.get(6), " c ", "cgc", " c ", 'c', Items.CLAY_BALL, 'g', Blocks.GRAVEL);
		for (EnumFlowerColor c : EnumFlowerColor.values()) {
			ItemStack clay = new ItemStack(this.clay, 1, c.ordinal());
			ItemStack pigment = new ItemStack(this.pigment, 1, c.ordinal());
			GameRegistry.addShapelessRecipe(clay, Items.CLAY_BALL, Items.CLAY_BALL, Items.CLAY_BALL, pigment);
			GameRegistry.addSmelting(clay, TileEntityMetadata.getItemStack(ceramic, c.ordinal()), 0.0f);
			ItemStack glass = TileEntityMetadata.getItemStack(stained, c.ordinal());
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
