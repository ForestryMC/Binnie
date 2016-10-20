// 
// Decompiled by Procyon v0.5.30
// 

package binnie.botany;

import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.eventhandler.Event;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraft.world.IBlockAccess;
import binnie.core.BinnieCore;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraft.block.Block;
import binnie.botany.api.EnumAcidity;
import binnie.botany.api.EnumSoilType;
import binnie.botany.api.gardening.IBlockSoil;
import binnie.botany.gardening.Gardening;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.item.ItemStack;
import binnie.botany.api.IFlower;
import net.minecraft.tileentity.TileEntity;
import forestry.api.genetics.IIndividual;
import binnie.botany.core.BotanyCore;
import net.minecraft.entity.EntityLivingBase;
import binnie.botany.flower.TileEntityFlower;
import net.minecraft.init.Items;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import binnie.core.network.BinniePacketHandler;
import binnie.core.proxy.IProxyCore;
import binnie.botany.network.PacketID;
import binnie.core.network.IPacketID;
import binnie.botany.core.BotanyGUI;
import binnie.core.gui.IBinnieGUID;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import binnie.botany.gardening.ModuleGardening;
import binnie.botany.genetics.ModuleGenetics;
import binnie.botany.core.ModuleCore;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import binnie.botany.ceramic.BlockCeramicBrick;
import binnie.botany.ceramic.BlockStained;
import binnie.botany.ceramic.BlockCeramicPatterned;
import binnie.botany.ceramic.BlockCeramic;
import binnie.botany.items.ItemClay;
import binnie.botany.items.ItemPigment;
import binnie.botany.flower.ItemFlower;
import binnie.botany.genetics.ItemEncyclopedia;
import binnie.core.item.ItemMisc;
import binnie.botany.gardening.ItemSoilMeter;
import binnie.botany.flower.ItemInsulatedTube;
import binnie.botany.gardening.BlockSoil;
import binnie.botany.gardening.ItemTrowel;
import binnie.botany.gardening.BlockPlant;
import binnie.botany.genetics.ItemDictionary;
import net.minecraft.item.Item;
import binnie.botany.flower.BlockFlower;
import cpw.mods.fml.common.SidedProxy;
import binnie.botany.proxy.Proxy;
import cpw.mods.fml.common.Mod;
import binnie.core.AbstractMod;

@Mod(modid = "Botany", name = "Botany", useMetadata = true, dependencies = "after:BinnieCore")
public class Botany extends AbstractMod
{
	public static final float AGE_CHANCE = 0.2f;
	@Mod.Instance("Botany")
	public static Botany instance;
	@SidedProxy(clientSide = "binnie.botany.proxy.ProxyClient", serverSide = "binnie.botany.proxy.ProxyServer")
	public static Proxy proxy;
	public static BlockFlower flower;
	public static Item seed;
	public static Item pollen;
	public static ItemDictionary database;
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
	public static ItemEncyclopedia encyclopedia;
	public static ItemEncyclopedia encyclopediaIron;
	public static ItemFlower flowerItem;
	public static ItemPigment pigment;
	public static ItemClay clay;
	public static BlockCeramic ceramic;
	public static BlockCeramicPatterned ceramicTile;
	public static BlockStained stained;
	public static BlockCeramicBrick ceramicBrick;

	@Mod.EventHandler
	public void preInit(final FMLPreInitializationEvent evt) {
		this.addModule(new ModuleCore());
		this.addModule(new ModuleGenetics());
		this.addModule(new ModuleGardening());
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

	public Botany() {
		Botany.instance = this;
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
		return "botany";
	}

	@Override
	protected Class<? extends BinniePacketHandler> getPacketHandler() {
		return PacketHandler.class;
	}

	@SubscribeEvent
	public void onShearFlower(final PlayerInteractEvent event) {
		if (event.action != PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
			return;
		}
		if (event.entityPlayer != null && event.entityPlayer.getHeldItem() != null && event.entityPlayer.getHeldItem().getItem() == Items.shears) {
			final TileEntity tile = event.world.getTileEntity(event.x, event.y, event.z);
			if (tile instanceof TileEntityFlower) {
				final TileEntityFlower flower = (TileEntityFlower) tile;
				flower.onShear();
				event.entityPlayer.getHeldItem().damageItem(1, event.entityPlayer);
			}
		}
		if (event.entityPlayer != null && event.entityPlayer.getHeldItem() != null && event.entityPlayer.getHeldItem().getItem() == Botany.pollen) {
			final TileEntity tile = event.world.getTileEntity(event.x, event.y, event.z);
			if (tile instanceof TileEntityFlower) {
				final TileEntityFlower flower = (TileEntityFlower) tile;
				final IFlower pollen = BotanyCore.getFlowerRoot().getMember(event.entityPlayer.getHeldItem());
				if (pollen != null && flower.canMateWith(pollen)) {
					flower.mateWith(pollen);
					if (!event.entityPlayer.capabilities.isCreativeMode) {
						final ItemStack heldItem = event.entityPlayer.getHeldItem();
						--heldItem.stackSize;
					}
				}
			}
		}
	}

	@SubscribeEvent
	public void onFertiliseSoil(final PlayerInteractEvent event) {
		if (event.action != PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
			return;
		}
		if (event.world == null) {
			return;
		}
		if (event.entityPlayer != null && event.entityPlayer.getHeldItem() != null) {
			int y = event.y;
			Block block = event.world.getBlock(event.x, y, event.z);
			if (block == null || !Gardening.isSoil(block)) {
				block = event.world.getBlock(event.x, --y, event.z);
			}
			if (block == null) {
				return;
			}
			if (Gardening.isSoil(block)) {
				final IBlockSoil soil = (IBlockSoil) block;
				if (Gardening.isNutrientFertiliser(event.entityPlayer.getHeldItem()) && soil.getType(event.world, event.x, y, event.z) != EnumSoilType.FLOWERBED) {
					final EnumSoilType type = soil.getType(event.world, event.x, y, event.z);
					final int next = Math.min(type.ordinal() + Gardening.getFertiliserStrength(event.entityPlayer.getHeldItem()), 2);
					if (soil.fertilise(event.world, event.x, y, event.z, EnumSoilType.values()[next]) && !event.entityPlayer.capabilities.isCreativeMode) {
						final ItemStack heldItem = event.entityPlayer.getHeldItem();
						--heldItem.stackSize;
						return;
					}
				}
				if (Gardening.isAcidFertiliser(event.entityPlayer.getHeldItem()) && soil.getPH(event.world, event.x, y, event.z) != EnumAcidity.Acid) {
					final EnumAcidity pH = soil.getPH(event.world, event.x, y, event.z);
					final int next = Math.max(pH.ordinal() - Gardening.getFertiliserStrength(event.entityPlayer.getHeldItem()), 0);
					if (soil.setPH(event.world, event.x, y, event.z, EnumAcidity.values()[next]) && !event.entityPlayer.capabilities.isCreativeMode) {
						final ItemStack heldItem2 = event.entityPlayer.getHeldItem();
						--heldItem2.stackSize;
						return;
					}
				}
				if (Gardening.isAlkalineFertiliser(event.entityPlayer.getHeldItem()) && soil.getPH(event.world, event.x, y, event.z) != EnumAcidity.Alkaline) {
					final EnumAcidity pH = soil.getPH(event.world, event.x, y, event.z);
					final int next = Math.min(pH.ordinal() + Gardening.getFertiliserStrength(event.entityPlayer.getHeldItem()), 2);
					if (soil.setPH(event.world, event.x, y, event.z, EnumAcidity.values()[next]) && !event.entityPlayer.capabilities.isCreativeMode) {
						final ItemStack heldItem3 = event.entityPlayer.getHeldItem();
						--heldItem3.stackSize;
						return;
					}
				}
				if (Gardening.isWeedkiller(event.entityPlayer.getHeldItem()) && Gardening.addWeedKiller(event.world, event.x, y, event.z) && !event.entityPlayer.capabilities.isCreativeMode) {
					final ItemStack heldItem4 = event.entityPlayer.getHeldItem();
					--heldItem4.stackSize;
				}
			}
		}
	}

	@SubscribeEvent
	public void plantVanilla(final BlockEvent.PlaceEvent event) {
		final World world = event.world;
		final int x = event.x;
		final int y = event.y;
		final int z = event.z;
		final Block block = world.getBlock(x, y - 1, z);
		if (!(block instanceof IBlockSoil)) {
			return;
		}
		final IFlower flower = BotanyCore.getFlowerRoot().getConversion(event.itemInHand);
		if (flower != null) {
			Gardening.plant(world, x, y, z, flower, event.player.getGameProfile());
		}
	}

	@Deprecated
	public void onPlantVanilla(final PlayerInteractEvent event) {
		if (!BinnieCore.proxy.isSimulating(event.world)) {
			return;
		}
		if (event.action != PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
			return;
		}
		if (event.entityPlayer != null && event.entityPlayer.getHeldItem() != null) {
			final World world = event.world;
			final int x = event.x;
			final int y = event.y;
			final int z = event.z;
			final Block block = world.getBlock(x, y, z);
			int py = -1;
			if (block instanceof IBlockSoil && (world.isAirBlock(x, y + 1, z) || world.getBlock(x, y, z).isReplaceable(world, x, y, z))) {
				py = 1;
			}
			if (py >= 0) {
				final IFlower flower = BotanyCore.getFlowerRoot().getConversion(event.entityPlayer.getHeldItem());
				if (flower != null && Gardening.plant(world, x, y + py, z, flower, event.entityPlayer.getGameProfile()) && !event.entityPlayer.capabilities.isCreativeMode) {
					final ItemStack heldItem = event.entityPlayer.getHeldItem();
					--heldItem.stackSize;
				}
			}
		}
	}

	@SubscribeEvent
	public void onBonemeal(final BonemealEvent event) {
		final Block block = event.world.getBlock(event.x, event.y, event.z);
		if (Gardening.isSoil(block)) {
			final IBlockSoil soil = (IBlockSoil) block;
			if (soil.fertilise(event.world, event.x, event.y, event.z, EnumSoilType.LOAM)) {
				event.setResult(Event.Result.ALLOW);
			}
		}
		final TileEntity tile = event.world.getTileEntity(event.x, event.y, event.z);
		if (tile instanceof TileEntityFlower && ((TileEntityFlower) tile).onBonemeal()) {
			event.setResult(Event.Result.ALLOW);
		}
	}

	@Override
	public boolean isActive() {
		return BinnieCore.isBotanyActive();
	}

	@Mod.EventHandler
	public void onIMC(final FMLInterModComms.IMCEvent event) {
		for (final FMLInterModComms.IMCMessage message : event.getMessages()) {
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

	public static class PacketHandler extends BinniePacketHandler
	{
		public PacketHandler() {
			super(Botany.instance);
		}
	}
}
