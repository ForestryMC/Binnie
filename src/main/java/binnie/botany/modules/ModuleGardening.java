package binnie.botany.modules;

import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.RecipeSorter;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import binnie.Constants;
import binnie.botany.Botany;
import binnie.botany.CreativeTabBotany;
import binnie.botany.api.gardening.EnumAcidity;
import binnie.botany.api.gardening.EnumFertiliserType;
import binnie.botany.api.gardening.EnumMoisture;
import binnie.botany.api.gardening.EnumSoilType;
import binnie.botany.api.gardening.IBlockSoil;
import binnie.botany.api.gardening.IGardeningManager;
import binnie.botany.blocks.BlockPlant;
import binnie.botany.blocks.BlockSoil;
import binnie.botany.core.BotanyCore;
import binnie.botany.items.BotanyItems;
import binnie.botany.items.ItemSoil;
import binnie.botany.items.ItemSoilMeter;
import binnie.botany.items.ItemTrowel;
import binnie.botany.items.ItemWeed;
import binnie.botany.recipes.CeramicTileRecipe;
import binnie.botany.recipes.PigmentRecipe;
import binnie.botany.tile.TileCeramic;
import binnie.botany.tile.TileCeramicBrick;
import binnie.core.BinnieCore;
import binnie.core.Mods;
import binnie.core.item.ItemMisc;
import binnie.core.util.OreDictionaryUtil;
import binnie.modules.BinnieModule;
import binnie.modules.Module;

@BinnieModule(moduleID = BotanyModuleUIDs.GARDENING, moduleContainerID = Constants.BOTANY_MOD_ID, name = "Gardening", unlocalizedDescription = "botany.module.gardening")
public class ModuleGardening extends Module {
	public static BlockPlant plant;
	public static ItemTrowel trowelWood;
	public static ItemTrowel trowelStone;
	public static ItemTrowel trowelIron;
	public static ItemTrowel trowelDiamond;
	public static ItemTrowel trowelGold;
	public static BlockSoil soil;
	public static BlockSoil loam;
	public static BlockSoil flowerbed;
	public static BlockSoil soilNoWeed;
	public static BlockSoil loamNoWeed;
	public static BlockSoil flowerbedNoWeed;
	public static ItemSoilMeter soilMeter;
	public static ItemMisc misc;

	@Override
	public void preInit() {
		MinecraftForge.EVENT_BUS.register(this);
		plant = new BlockPlant();
		soil = new BlockSoil(EnumSoilType.SOIL, "soil", false);
		loam = new BlockSoil(EnumSoilType.LOAM, "loam", false);
		flowerbed = new BlockSoil(EnumSoilType.FLOWERBED, "flowerbed", false);
		soilNoWeed = new BlockSoil(EnumSoilType.SOIL, "soil_no_weed", true);
		loamNoWeed = new BlockSoil(EnumSoilType.LOAM, "loam_no_weed", true);
		flowerbedNoWeed = new BlockSoil(EnumSoilType.FLOWERBED, "flowerbed_no_weed", true);
		soilMeter = new ItemSoilMeter();
		trowelWood = new ItemTrowel(Item.ToolMaterial.WOOD, "wood");
		trowelStone = new ItemTrowel(Item.ToolMaterial.STONE, "stone");
		trowelIron = new ItemTrowel(Item.ToolMaterial.IRON, "iron");
		trowelDiamond = new ItemTrowel(Item.ToolMaterial.DIAMOND, "diamond");
		trowelGold = new ItemTrowel(Item.ToolMaterial.GOLD, "gold");
		misc = new ItemMisc(CreativeTabBotany.instance, BotanyItems.values());

		Botany.proxy.registerBlock(plant, new ItemWeed(plant));
		Botany.proxy.registerBlock(soil, new ItemSoil(soil));
		Botany.proxy.registerBlock(loam, new ItemSoil(loam));
		Botany.proxy.registerBlock(flowerbed, new ItemSoil(flowerbed));
		Botany.proxy.registerBlock(soilNoWeed, new ItemSoil(soilNoWeed));
		Botany.proxy.registerBlock(loamNoWeed, new ItemSoil(loamNoWeed));
		Botany.proxy.registerBlock(flowerbedNoWeed, new ItemSoil(flowerbedNoWeed));
		Botany.proxy.registerItem(soilMeter);
		Botany.proxy.registerItem(trowelWood);
		Botany.proxy.registerItem(trowelStone);
		Botany.proxy.registerItem(trowelIron);
		Botany.proxy.registerItem(trowelDiamond);
		Botany.proxy.registerItem(trowelGold);
		Botany.proxy.registerItem(misc);

		BinnieCore.getBinnieProxy().registerTileEntity(TileCeramic.class, "botany.tile.ceramic");
		//BinnieCore.proxy.registerCustomItemRenderer(Item.getItemFromBlock(ceramic), new ItemMetadataRenderer());
		//BinnieCore.proxy.registerCustomItemRenderer(Item.getItemFromBlock(stained), new ItemMetadataRenderer());
		BinnieCore.getBinnieProxy().registerTileEntity(TileCeramicBrick.class, "botany.tile.ceramicBrick");
		//BinnieCore.proxy.registerCustomItemRenderer(Item.getItemFromBlock(ceramicTile), new MultipassItemRenderer());
		//BinnieCore.proxy.registerCustomItemRenderer(Item.getItemFromBlock(ceramicBrick), new MultipassItemRenderer());

		OreDictionary.registerOre("weedkiller", BotanyItems.WEEDKILLER.get(1));
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
	}

	@Override
	public void postInit() {
		IGardeningManager gardening = BotanyCore.getGardening();

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

		gardening.registerFertiliser(EnumFertiliserType.ACID, BotanyItems.POWDER_SULPHUR.get(1), 1);
		gardening.registerFertiliser(EnumFertiliserType.ACID, BotanyItems.POWDER_MULCH.get(1), 1);
		gardening.registerFertiliser(EnumFertiliserType.ACID, Mods.Forestry.stack("mulch"), 2);
		for (ItemStack stack : OreDictionary.getOres("dustSulfur")) {
			gardening.registerFertiliser(EnumFertiliserType.ACID, stack, 2);
		}

		gardening.registerFertiliser(EnumFertiliserType.ALKALINE, BotanyItems.POWDER_ASH.get(1), 1);
		gardening.registerFertiliser(EnumFertiliserType.ALKALINE, BotanyItems.POWDER_PULP.get(1), 1);
		gardening.registerFertiliser(EnumFertiliserType.ALKALINE, Mods.Forestry.stack("ash"), 2);
		gardening.registerFertiliser(EnumFertiliserType.ALKALINE, Mods.Forestry.stack("wood_pulp"), 2);

		gardening.registerFertiliser(EnumFertiliserType.NUTRIENT, BotanyItems.POWDER_COMPOST.get(1), 1);
		gardening.registerFertiliser(EnumFertiliserType.NUTRIENT, BotanyItems.POWDER_FERTILISER.get(1), 1);
		gardening.registerFertiliser(EnumFertiliserType.NUTRIENT, Mods.Forestry.stack("fertilizer_bio"), 2);
		gardening.registerFertiliser(EnumFertiliserType.NUTRIENT, Mods.Forestry.stack("fertilizer_compound"), 2);

		addFertiliserRecipes();
	}

	private ItemStack getStack(EnumSoilType type, EnumAcidity pH, EnumMoisture moisture, boolean weedkiller) {
		IGardeningManager gardeningManager = BotanyCore.getGardening();
		return new ItemStack(gardeningManager.getSoilBlock(type, weedkiller), 1, BlockSoil.getMeta(pH, moisture));
	}

	private void addFertiliserRecipes() {
		IGardeningManager gardening = BotanyCore.getGardening();
		for (EnumMoisture moisture : EnumMoisture.values()) {
			for (EnumAcidity acidity : EnumAcidity.values()) {
				int pH = acidity.ordinal();
				for (EnumSoilType type : EnumSoilType.values()) {
					Map<EnumFertiliserType, Map<ItemStack, Integer>> fertilisers = gardening.getFertilisers();
					for(EnumFertiliserType fertiliserType : EnumFertiliserType.values()) {
						for (Map.Entry<ItemStack, Integer> entry : fertilisers.get(fertiliserType).entrySet()) {
							ItemStack stack = entry.getKey();
							int strengthMax = entry.getValue();
							for (boolean weedkiller : new boolean[]{false, true}) {
								int numOfBlocks = strengthMax * strengthMax;
								for (int strength = 1; strength < strengthMax; ++strength) {
									int endPh;
									if (fertiliserType == EnumFertiliserType.ACID) {
										endPh = pH - strength;
									} else if (fertiliserType == EnumFertiliserType.ALKALINE) {
										endPh = pH + strength;
									} else {
										endPh = type.ordinal() + strength;
									}
									if(endPh < 0 || endPh > 2 || pH == endPh){
										continue;
									}
									ItemStack start = getStack(type, acidity, moisture, weedkiller);
									ItemStack end = getStack(type, EnumAcidity.values()[endPh], moisture, weedkiller);
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
					ItemStack start = getStack(type, acidity, moisture, false);
					ItemStack end = getStack(type, acidity, moisture, true);
					GameRegistry.addRecipe(new ShapelessOreRecipe(end, start, start, start, start, "weedkiller"));
				}
			}
		}
	}

	@SubscribeEvent
	public void onFertiliseSoil(PlayerInteractEvent.RightClickBlock event) {
		World world = event.getWorld();
		if (world == null) {
			return;
		}

		BlockPos pos = event.getPos();
		EntityPlayer player = event.getEntityPlayer();
		if (player == null) {
			return;
		}

		ItemStack heldItem = player.getHeldItemMainhand();
		if (heldItem.isEmpty()) {
			return;
		}

		IGardeningManager gardening = BotanyCore.getGardening();
		Block block = world.getBlockState(event.getPos()).getBlock();
		if (!gardening.isSoil(block)) {
			pos = pos.down();
			block = world.getBlockState(pos).getBlock();
		}

		if (!gardening.isSoil(block)) {
			return;
		}

		IBlockSoil soil = (IBlockSoil) block;
		if(gardening.onFertiliseSoil(heldItem, soil, world, pos, player)){
			return;
		}

		if (OreDictionaryUtil.hasOreName(heldItem, "weedkiller") && gardening.addWeedKiller(world, pos)) {
			if(!player.capabilities.isCreativeMode) {
				heldItem.shrink(1);
			}
		}
	}

	@SubscribeEvent
	public void onBonemeal(BonemealEvent event) {
		IGardeningManager gardening = BotanyCore.getGardening();
		BlockPos pos = event.getPos();
		Block block = event.getBlock().getBlock();
		if (gardening.isSoil(block)) {
			IBlockSoil soil = (IBlockSoil) block;
			if (soil.fertilise(event.getWorld(), pos, EnumSoilType.LOAM)) {
				event.setResult(Event.Result.ALLOW);
			}
		}
	}
}
