package binnie.botany;

import binnie.Constants;
import binnie.botany.api.EnumAcidity;
import binnie.botany.api.EnumSoilType;
import binnie.botany.api.IFlower;
import binnie.botany.api.gardening.IBlockSoil;
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
import binnie.botany.gardening.Gardening;
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
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = Constants.BOTANY_MOD_ID, name = "Binnie's Botany", useMetadata = true, dependencies = "required-after:" + Constants.CORE_MOD_ID)
public class Botany extends AbstractMod {

	@Mod.Instance(Constants.BOTANY_MOD_ID)
	public static Botany instance;

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

	public static final float AGE_CHANCE = 0.2f;

	@Mod.EventHandler
	public void preInit(final FMLPreInitializationEvent evt) {
		this.preInit();
	}

	@Mod.EventHandler
	public void init(final FMLInitializationEvent evt) {
		this.init();
	}

	@Mod.EventHandler
	public void postInit(final FMLPostInitializationEvent evt) {
		this.postInit();
	}

	@Override
	protected void registerModules() {
		this.addModule(new ModuleCore());
		this.addModule(new ModuleGenetics());
		this.addModule(new ModuleGardening());
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
	public void onShearFlower(final PlayerInteractEvent.RightClickBlock event) {
		EntityPlayer player = event.getEntityPlayer();
		if (player != null) {
			ItemStack heldItem = player.getHeldItemMainhand();
			if (heldItem != null) {
				TileEntity tile = event.getWorld().getTileEntity(event.getPos());
				if (tile instanceof TileEntityFlower) {
					TileEntityFlower flower = (TileEntityFlower) tile;
					if (heldItem.getItem() == Items.SHEARS) {
						flower.onShear();
						heldItem.damageItem(1, player);
					} else if (heldItem.getItem() == Botany.pollen) {
						IFlower pollen = BotanyCore.getFlowerRoot().getMember(heldItem);
						if (pollen != null && flower.canMateWith(pollen)) {
							flower.mateWith(pollen);
							if (!player.capabilities.isCreativeMode) {
								--heldItem.stackSize;
							}
						}
					}
				}
			}
		}
	}

	@SubscribeEvent
	public void onFertiliseSoil(final PlayerInteractEvent.RightClickBlock event) {
		World world = event.getWorld();
		if (world == null) {
			return;
		}
		BlockPos pos = event.getPos();
		EntityPlayer player = event.getEntityPlayer();
		if (player != null) {
			ItemStack heldItem = player.getHeldItemMainhand();
			if (heldItem != null) {
				Block block = world.getBlockState(event.getPos()).getBlock();
				if (!Gardening.isSoil(block)) {
					pos = pos.down();
					block = world.getBlockState(pos).getBlock();
				}
				if (Gardening.isSoil(block)) {
					IBlockSoil soil = (IBlockSoil) block;
					int fertiliserStrength = Gardening.getFertiliserStrength(heldItem);
					if (Gardening.isNutrientFertiliser(heldItem) && soil.getType(world, pos) != EnumSoilType.FLOWERBED) {
						EnumSoilType type = soil.getType(world, pos);
						int next = Math.min(type.ordinal() + fertiliserStrength, 2);
						if (soil.fertilise(world, pos, EnumSoilType.values()[next]) && !player.capabilities.isCreativeMode) {
							--heldItem.stackSize;
							return;
						}
					}
					if (Gardening.isAcidFertiliser(heldItem) && soil.getPH(world, pos) != EnumAcidity.Acid) {
						EnumAcidity pH = soil.getPH(world, pos);
						int next = Math.max(pH.ordinal() - fertiliserStrength, 0);
						if (soil.setPH(world, pos, EnumAcidity.values()[next]) && !player.capabilities.isCreativeMode) {
							--heldItem.stackSize;
							return;
						}
					}
					if (Gardening.isAlkalineFertiliser(heldItem) && soil.getPH(world, pos) != EnumAcidity.Alkaline) {
						EnumAcidity pH = soil.getPH(world, pos);
						int next = Math.min(pH.ordinal() + fertiliserStrength, 2);
						if (soil.setPH(world, pos, EnumAcidity.values()[next]) && !player.capabilities.isCreativeMode) {
							--heldItem.stackSize;
							return;
						}
					}
					if (Gardening.isWeedkiller(heldItem) && Gardening.addWeedKiller(world, pos) && !player.capabilities.isCreativeMode) {
						--heldItem.stackSize;
					}
				}
			}
		}
	}

	@SubscribeEvent
	public void plantVanilla(final BlockEvent.PlaceEvent event) {
		World world = event.getWorld();
		BlockPos pos = event.getPos();
		Block block = world.getBlockState(pos.down()).getBlock();

		if (!Gardening.isSoil(block)) {
			return;
		}
		IFlower flower = BotanyCore.getFlowerRoot().getConversion(event.getItemInHand());
		if (flower != null) {
			Gardening.plant(world, pos, flower, event.getPlayer().getGameProfile());
		}
	}

	@Deprecated
	public void onPlantVanilla(final PlayerInteractEvent.RightClickBlock event) {
		BlockPos pos = event.getPos();
		World world = event.getWorld();
		EntityPlayer player = event.getEntityPlayer();
		ItemStack heldItem = player.getHeldItemMainhand();
		if (!BinnieCore.proxy.isSimulating(event.getWorld())) {
			return;
		}

		if (player != null && heldItem != null) {
			Block block = world.getBlockState(pos).getBlock();
			int py = -1;
			if (block instanceof IBlockSoil && (world.isAirBlock(pos.up()) || block.isReplaceable(world, pos))) {
				py = 1;
			}
			if (py >= 0) {
				IFlower flower = BotanyCore.getFlowerRoot().getConversion(event.getEntityPlayer().getHeldItemMainhand());
				if (flower != null && Gardening.plant(world, pos.add(0, py, 0), flower, player.getGameProfile()) && !player.capabilities.isCreativeMode) {
					--heldItem.stackSize;
				}
			}
		}
	}

	@SubscribeEvent
	public void onBonemeal(final BonemealEvent event) {
		BlockPos pos = event.getPos();
		Block block = event.getBlock().getBlock();
		if (Gardening.isSoil(block)) {
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

	@Mod.EventHandler
	public void onIMC(final FMLInterModComms.IMCEvent event) {
		for (FMLInterModComms.IMCMessage message : event.getMessages()) {
			if (message.key.equals("add-nutrient-fertiliser-1")) {
				ModuleGardening.queuedNutrientFertilisers.put(message.getItemStackValue(), 1);
			}
			if (message.key.equals("add-nutrient-fertiliser-2")) {
				ModuleGardening.queuedNutrientFertilisers.put(message.getItemStackValue(), 2);
			}
			if (message.key.equals("add-acid-fertiliser-1")) {
				ModuleGardening.queuedAcidFertilisers.put(message.getItemStackValue(), 1);
			}
			if (message.key.equals("add-acid-fertiliser-2")) {
				ModuleGardening.queuedAcidFertilisers.put(message.getItemStackValue(), 2);
			}
			if (message.key.equals("add-alkaline-fertiliser-1")) {
				ModuleGardening.queuedAlkalineFertilisers.put(message.getItemStackValue(), 1);
			}
			if (message.key.equals("add-alkaline-fertiliser-2")) {
				ModuleGardening.queuedAlkalineFertilisers.put(message.getItemStackValue(), 1);
			}
		}
	}

	public static class PacketHandler extends BinniePacketHandler {
		public PacketHandler() {
			super(Botany.instance);
		}
	}

}
