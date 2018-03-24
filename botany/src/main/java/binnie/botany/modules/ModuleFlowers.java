package binnie.botany.modules;

import javax.annotation.Nullable;
import java.awt.Color;
import java.util.function.Predicate;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;

import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import forestry.api.apiculture.FlowerManager;
import forestry.api.genetics.AlleleManager;
import forestry.api.modules.ForestryModule;
import forestry.api.storage.BackpackManager;
import forestry.api.storage.IBackpackInterface;
import forestry.storage.BackpackDefinition;

import binnie.botany.Botany;
import binnie.botany.CreativeTabBotany;
import binnie.botany.api.BotanyAPI;
import binnie.botany.api.gardening.IBlockSoil;
import binnie.botany.api.genetics.EnumFlowerStage;
import binnie.botany.api.genetics.IFlower;
import binnie.botany.api.genetics.IFlowerRoot;
import binnie.botany.blocks.BlockFlower;
import binnie.botany.core.BotanyCore;
import binnie.botany.genetics.FlowerBreedingSystem;
import binnie.botany.genetics.FlowerColorMutations;
import binnie.botany.genetics.FlowerDefinition;
import binnie.botany.genetics.FlowerFactory;
import binnie.botany.genetics.allele.AlleleEffectNone;
import binnie.botany.items.ItemFlowerGE;
import binnie.botany.tile.TileEntityFlower;
import binnie.core.Binnie;
import binnie.core.BinnieCore;
import binnie.core.Constants;
import binnie.core.api.genetics.IBreedingSystem;
import binnie.core.modules.BlankModule;
import binnie.core.modules.BotanyModuleUIDs;
import binnie.core.modules.ModuleManager;
import binnie.core.util.RecipeUtil;
import binnie.core.util.TileUtil;

@ForestryModule(moduleID = BotanyModuleUIDs.FLOWERS, containerID = Constants.BOTANY_MOD_ID, name = "Flowers", unlocalizedDescription = "botany.module.flowers")
public class ModuleFlowers extends BlankModule {
	public static final AlleleEffectNone alleleEffectNone = new AlleleEffectNone();
	@Nullable
	public static Item botanistBackpack;
	/* MODULE GENETIC */
	public static BlockFlower flower;
	public static ItemFlowerGE flowerItem;
	public static ItemFlowerGE seed;
	public static ItemFlowerGE pollen;
	public static IBreedingSystem flowerBreedingSystem;

	public ModuleFlowers() {
		super(Constants.BOTANY_MOD_ID, BotanyModuleUIDs.GARDENING);
	}

	@Override
	public void setupAPI() {
		BotanyAPI.flowerFactory = new FlowerFactory();
		AlleleManager.alleleRegistry.registerSpeciesRoot(BotanyCore.getFlowerRoot());
	}

	@Override
	public void registerItemsAndBlocks() {
		AlleleManager.alleleRegistry.registerAllele(ModuleFlowers.alleleEffectNone);
		FlowerColorMutations.registerFlowerColorMutations();
		FlowerDefinition.preInitFlowers();

		IBackpackInterface backpackInterface = BackpackManager.backpackInterface;
		if (ModuleManager.isModuleEnabled("forestry", "backpacks")) {
			Predicate<ItemStack> filter = BackpackManager.backpackInterface.createNaturalistBackpackFilter("rootFlowers");
			BackpackDefinition definition = new BackpackDefinition(new Color(0xf6e83e), Color.WHITE, filter);
			BackpackManager.backpackInterface.registerBackpackDefinition("botanist", definition);
			botanistBackpack = backpackInterface.createNaturalistBackpack("botanist", BotanyAPI.flowerRoot).setRegistryName("botanist_bag").setUnlocalizedName("botany.botanist_bag");
			Botany.proxy.registerItem(botanistBackpack);
			botanistBackpack.setCreativeTab(CreativeTabBotany.INSTANCE);
		} else {
			botanistBackpack = null;
		}

		/* ITEMS */
		flower = new BlockFlower();
		flowerItem = new ItemFlowerGE("itemFlower", EnumFlowerStage.FLOWER, "");
		pollen = new ItemFlowerGE("pollen", EnumFlowerStage.POLLEN, "pollen");
		seed = new ItemFlowerGE("seed", EnumFlowerStage.SEED, "germling");

		Botany.proxy.registerBlock(flower);
		Botany.proxy.registerItem(flowerItem);
		Botany.proxy.registerItem(pollen);
		Botany.proxy.registerItem(seed);

		BinnieCore.getBinnieProxy().registerTileEntity(TileEntityFlower.class, "botany.tile.flower");
	}

	@Override
	public void preInit() {
		MinecraftForge.EVENT_BUS.register(this);

		flowerBreedingSystem = new FlowerBreedingSystem();
		Binnie.GENETICS.registerBreedingSystem(flowerBreedingSystem);
	}

	@Override
	public void doInit() {
		FlowerColorMutations.registerFlowerColorAlleles();
		FlowerDefinition.initFlowers();

		RecipeUtil recipeUtil = new RecipeUtil(Constants.BOTANY_MOD_ID);
		FlowerManager.flowerRegistry.registerAcceptableFlower(flower, "flowersVanilla");
		recipeUtil.addRecipe("botanist_backpack", botanistBackpack,
			"X#X",
			"VYZ",
			"X#X",
			'#', Blocks.WOOL,
			'X', Items.STRING,
			'V', ModuleGardening.soilMeter,
			'Z', "toolTrowel",
			'Y', "chestWood"
		);
	}

	@SubscribeEvent
	public void onShearFlower(PlayerInteractEvent.RightClickBlock event) {
		EntityPlayer player = event.getEntityPlayer();
		if (player == null) {
			return;
		}

		ItemStack heldItem = player.getHeldItemMainhand();
		if (heldItem.isEmpty()) {
			return;
		}

		TileEntity tile = event.getWorld().getTileEntity(event.getPos());
		if (!(tile instanceof TileEntityFlower)) {
			return;
		}

		TileEntityFlower flower = (TileEntityFlower) tile;
		if (heldItem.getItem() == Items.SHEARS) {
			flower.onShear();
			heldItem.damageItem(1, player);
		} else if (heldItem.getItem() == pollen) {
			IFlower pollen = BotanyCore.getFlowerRoot().getMember(heldItem);
			if (pollen != null && flower.canMateWith(pollen)) {
				flower.mateWith(pollen);
				if (!player.capabilities.isCreativeMode) {
					heldItem.shrink(1);
				}
			}
		}
	}

	@SubscribeEvent
	public void plantVanilla(BlockEvent.PlaceEvent event) {
		World world = event.getWorld();
		BlockPos pos = event.getPos();
		Block block = world.getBlockState(pos.down()).getBlock();
		if (!BotanyCore.getGardening().isSoil(block)) {
			return;
		}

		EntityPlayer player = event.getPlayer();
		ItemStack heldItem = player.getHeldItem(event.getHand());
		IFlowerRoot flowerRoot = BotanyCore.getFlowerRoot();
		IFlower flower = flowerRoot.getConversion(heldItem);
		if (flower != null) {
			flowerRoot.plant(world, pos, flower, player.getGameProfile());
		}
	}

	@Deprecated
	public void onPlantVanilla(PlayerInteractEvent.RightClickBlock event) {
		BlockPos pos = event.getPos();
		World world = event.getWorld();
		EntityPlayer player = event.getEntityPlayer();
		ItemStack heldItem = player.getHeldItemMainhand();
		if (event.getWorld().isRemote) {
			return;
		}

		if (heldItem.isEmpty()) {
			return;
		}

		Block block = world.getBlockState(pos).getBlock();
		int py = -1;
		if (block instanceof IBlockSoil && (world.isAirBlock(pos.up()) || block.isReplaceable(world, pos))) {
			py = 1;
		}
		if (py < 0) {
			return;
		}

		IFlowerRoot flowerRoot = BotanyCore.getFlowerRoot();
		IFlower flower = flowerRoot.getConversion(heldItem);
		if (flower != null && flowerRoot.plant(world, pos.add(0, py, 0), flower, player.getGameProfile()) && !player.capabilities.isCreativeMode) {
			heldItem.shrink(1);
		}
	}

	@SubscribeEvent
	public void onBonemeal(BonemealEvent event) {
		BlockPos pos = event.getPos();

		TileEntityFlower tile = TileUtil.getTile(event.getWorld(), pos, TileEntityFlower.class);
		if (tile != null && tile.onBonemeal()) {
			event.setResult(Event.Result.ALLOW);
		}
	}
}
