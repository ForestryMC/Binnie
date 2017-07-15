package binnie.botany;

import binnie.Constants;
import binnie.botany.api.EnumAcidity;
import binnie.botany.api.EnumSoilType;
import binnie.botany.api.IBlockSoil;
import binnie.botany.api.IFlower;
import binnie.botany.api.IFlowerRoot;
import binnie.botany.api.IGardeningManager;
import binnie.botany.ceramic.BlockCeramic;
import binnie.botany.ceramic.BlockCeramicPatterned;
import binnie.botany.ceramic.BlockStainedGlass;
import binnie.botany.ceramic.brick.BlockCeramicBrick;
import binnie.botany.core.BotanyCore;
import binnie.botany.core.BotanyGUI;
import binnie.botany.core.ModuleCore;
import binnie.botany.flower.BlockFlower;
import binnie.botany.flower.ItemBotany;
import binnie.botany.flower.ItemInsulatedTube;
import binnie.botany.flower.TileEntityFlower;
import binnie.botany.gardening.BlockPlant;
import binnie.botany.gardening.BlockSoil;
import binnie.botany.gardening.ItemSoilMeter;
import binnie.botany.gardening.ItemTrowel;
import binnie.botany.gardening.ModuleGardening;
import binnie.botany.genetics.ItemDictionary;
import binnie.botany.genetics.ModuleGenetics;
import binnie.botany.items.ItemClay;
import binnie.botany.items.ItemPigment;
import binnie.botany.network.PacketID;
import binnie.botany.proxy.Proxy;
import binnie.core.AbstractMod;
import binnie.core.BinnieCore;
import binnie.core.gui.IBinnieGUID;
import binnie.core.item.ItemMisc;
import binnie.core.network.BinniePacketHandler;
import binnie.core.network.IPacketID;
import binnie.core.proxy.IProxyCore;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nullable;

@Mod(modid = Constants.BOTANY_MOD_ID, name = "Binnie's Botany", useMetadata = true, dependencies = "required-after:" + Constants.CORE_MOD_ID)
public class Botany extends AbstractMod {
	public static final float AGE_CHANCE = 0.2f;

	@SuppressWarnings("NullableProblems")
	@Mod.Instance(Constants.BOTANY_MOD_ID)
	public static Botany instance;
	@SuppressWarnings("NullableProblems")
	@SidedProxy(clientSide = "binnie.botany.proxy.ProxyClient", serverSide = "binnie.botany.proxy.ProxyServer")
	public static Proxy proxy;
	/* MODULE GENETIC */
	public static BlockFlower flower;
	public static ItemBotany flowerItem;
	public static ItemBotany seed;
	public static ItemBotany pollen;
	public static ItemDictionary database;
	/* MODULE GARDENING */
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
	public static ItemInsulatedTube insulatedTube;
	public static ItemSoilMeter soilMeter;
	public static ItemMisc misc;
	public static ItemPigment pigment;
	public static ItemClay clay;
	public static BlockCeramic ceramic;
	public static BlockCeramicPatterned ceramicTile;
	public static BlockStainedGlass stained;
	public static BlockCeramicBrick ceramicBrick;
	@Nullable
	public static Item botanistBackpack;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent evt) {
		preInit();
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent evt) {
		init();
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent evt) {
		postInit();
	}

	@Override
	protected void registerModules() {
		addModule(new ModuleCore());
		addModule(new ModuleGenetics());
		addModule(new ModuleGardening());
	}

	@Override
	public IBinnieGUID[] getGUIDs() {
		return BotanyGUI.values();
	}

	@Override
	public Class<?>[] getConfigs() {
		return new Class[0];
	}

	@Override
	public IPacketID[] getPacketIDs() {
		return PacketID.values();
	}

	@Override
	public IProxyCore getProxy() {
		return Botany.proxy;
	}

	@Override
	public String getChannel() {
		return "BOT";
	}

	@Override
	public String getModID() {
		return Constants.BOTANY_MOD_ID;
	}

	@Override
	protected Class<? extends BinniePacketHandler> getPacketHandler() {
		return PacketHandler.class;
	}

	@Override
	public boolean isActive() {
		return BinnieCore.isBotanyActive();
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
		} else if (heldItem.getItem() == Botany.pollen) {
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
		int fertiliserStrength = gardening.getFertiliserStrength(heldItem);
		if (gardening.isNutrientFertiliser(heldItem) && soil.getType(world, pos) != EnumSoilType.FLOWERBED) {
			EnumSoilType type = soil.getType(world, pos);
			int next = Math.min(type.ordinal() + fertiliserStrength, 2);
			if (soil.fertilise(world, pos, EnumSoilType.values()[next]) && !player.capabilities.isCreativeMode) {
				heldItem.shrink(1);
				return;
			}
		}

		if (gardening.isAcidFertiliser(heldItem) && soil.getPH(world, pos) != EnumAcidity.ACID) {
			EnumAcidity pH = soil.getPH(world, pos);
			int next = Math.max(pH.ordinal() - fertiliserStrength, 0);
			if (soil.setPH(world, pos, EnumAcidity.values()[next]) && !player.capabilities.isCreativeMode) {
				heldItem.shrink(1);
				return;
			}
		}

		if (gardening.isAlkalineFertiliser(heldItem) && soil.getPH(world, pos) != EnumAcidity.ALKALINE) {
			EnumAcidity pH = soil.getPH(world, pos);
			int next = Math.min(pH.ordinal() + fertiliserStrength, 2);
			if (soil.setPH(world, pos, EnumAcidity.values()[next]) && !player.capabilities.isCreativeMode) {
				heldItem.shrink(1);
				return;
			}
		}

		if (gardening.isWeedkiller(heldItem) && gardening.addWeedKiller(world, pos) && !player.capabilities.isCreativeMode) {
			heldItem.shrink(1);
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
		if (!BinnieCore.getBinnieProxy().isSimulating(event.getWorld())) {
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
		Block block = event.getBlock().getBlock();
		if (BotanyCore.getGardening().isSoil(block)) {
			IBlockSoil soil = (IBlockSoil) block;
			if (soil.fertilise(event.getWorld(), pos, EnumSoilType.LOAM)) {
				event.setResult(Event.Result.ALLOW);
			}
		}

		TileEntity tile = event.getWorld().getTileEntity(pos);
		if (tile instanceof TileEntityFlower && ((TileEntityFlower) tile).onBonemeal()) {
			event.setResult(Event.Result.ALLOW);
		}
	}

	public static class PacketHandler extends BinniePacketHandler {
		public PacketHandler() {
			super(Botany.instance);
		}
	}
}
