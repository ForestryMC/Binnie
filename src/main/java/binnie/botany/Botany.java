package binnie.botany;

import binnie.Constants;
import binnie.botany.api.EnumAcidity;
import binnie.botany.api.EnumSoilType;
import binnie.botany.api.IFlower;
import binnie.botany.api.gardening.IBlockSoil;
import binnie.botany.ceramic.BlockCeramic;
import binnie.botany.ceramic.BlockCeramicBrick;
import binnie.botany.ceramic.BlockCeramicPatterned;
import binnie.botany.ceramic.BlockStained;
import binnie.botany.core.BotanyCore;
import binnie.botany.core.BotanyGUI;
import binnie.botany.core.ModuleCore;
import binnie.botany.flower.BlockFlower;
import binnie.botany.flower.ItemBotany;
import binnie.botany.flower.ItemInsulatedTube;
import binnie.botany.flower.TileEntityFlower;
import binnie.botany.gardening.*;
import binnie.botany.genetics.ItemDictionary;
import binnie.botany.genetics.ItemEncyclopedia;
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
    public static ItemBotany flowerItem;
    public static ItemPigment pigment;
    public static ItemClay clay;
    public static BlockCeramic ceramic;
    public static BlockCeramicPatterned ceramicTile;
    public static BlockStained stained;
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
        return "botany";
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
        if (event.getEntityPlayer() != null && event.getEntityPlayer().getHeldItemMainhand() != null && event.getEntityPlayer().getHeldItemMainhand().getItem() == Items.SHEARS) {
            final TileEntity tile = event.getWorld().getTileEntity(event.getPos());
            if (tile instanceof TileEntityFlower) {
                final TileEntityFlower flower = (TileEntityFlower) tile;
                flower.onShear();
                event.getEntityPlayer().getHeldItemMainhand().damageItem(1, event.getEntityPlayer());
            }
        }
        if (event.getEntityPlayer() != null && event.getEntityPlayer().getHeldItemMainhand() != null && event.getEntityPlayer().getHeldItemMainhand().getItem() == Botany.pollen) {
            final TileEntity tile = event.getWorld().getTileEntity(event.getPos());
            if (tile instanceof TileEntityFlower) {
//				final TileEntityFlower flower = (TileEntityFlower) tile;
//				final IFlower pollen = BotanyCore.getFlowerRoot().getMember(event.getEntityPlayer().getHeldItemMainhand());
//				if (pollen != null && flower.canMateWith(pollen)) {
//					flower.mateWith(pollen);
//					if (!event.getEntityPlayer().capabilities.isCreativeMode) {
//						final ItemStack heldItem = event.getEntityPlayer().getHeldItemMainhand();
//						--heldItem.stackSize;
//					}
//				}
            }
        }
    }

    @SubscribeEvent
    public void onFertiliseSoil(final PlayerInteractEvent.RightClickBlock event) {
        if (event.getWorld() == null) {
            return;
        }
        if (event.getEntityPlayer() != null && event.getEntityPlayer().getHeldItemMainhand() != null) {
            BlockPos down = event.getPos();
            Block block = event.getWorld().getBlockState(event.getPos()).getBlock();
            if (!Gardening.isSoil(block)) {
                down = down.down();
                block = event.getWorld().getBlockState(down).getBlock();
            }
            if (Gardening.isSoil(block)) {
                final IBlockSoil soil = (IBlockSoil) block;
				if (Gardening.isNutrientFertiliser(event.getEntityPlayer().getHeldItemMainhand()) && soil.getType(event.getWorld(), down) != EnumSoilType.FLOWERBED) {
					final EnumSoilType type = soil.getType(event.getWorld(), down);
					final int next = Math.min(type.ordinal() + Gardening.getFertiliserStrength(event.getEntityPlayer().getHeldItemMainhand()), 2);
					if (soil.fertilise(event.getWorld(), down, EnumSoilType.values()[next]) && !event.getEntityPlayer().capabilities.isCreativeMode) {
						final ItemStack heldItem = event.getEntityPlayer().getHeldItemMainhand();
						--heldItem.stackSize;
						return;
					}
				}
				if (Gardening.isAcidFertiliser(event.getEntityPlayer().getHeldItemMainhand()) && soil.getPH(event.getWorld(), down) != EnumAcidity.Acid) {
					final EnumAcidity pH = soil.getPH(event.getWorld(), down);
					final int next = Math.max(pH.ordinal() - Gardening.getFertiliserStrength(event.getEntityPlayer().getHeldItemMainhand()), 0);
					if (soil.setPH(event.getWorld(), down, EnumAcidity.values()[next]) && !event.getEntityPlayer().capabilities.isCreativeMode) {
						final ItemStack heldItem2 = event.getEntityPlayer().getHeldItemMainhand();
						--heldItem2.stackSize;
						return;
					}
				}
				if (Gardening.isAlkalineFertiliser(event.getEntityPlayer().getHeldItemMainhand()) && soil.getPH(event.getWorld(), down) != EnumAcidity.Alkaline) {
					final EnumAcidity pH = soil.getPH(event.getWorld(), down);
					final int next = Math.min(pH.ordinal() + Gardening.getFertiliserStrength(event.getEntityPlayer().getHeldItemMainhand()), 2);
					if (soil.setPH(event.getWorld(), down, EnumAcidity.values()[next]) && !event.getEntityPlayer().capabilities.isCreativeMode) {
						final ItemStack heldItem3 = event.getEntityPlayer().getHeldItemMainhand();
						--heldItem3.stackSize;
						return;
					}
				}
				if (Gardening.isWeedkiller(event.getEntityPlayer().getHeldItemMainhand()) && Gardening.addWeedKiller(event.getWorld(), down) && !event.getEntityPlayer().capabilities.isCreativeMode) {
					final ItemStack heldItem4 = event.getEntityPlayer().getHeldItemMainhand();
					--heldItem4.stackSize;
				}
            }
        }
    }

    @SubscribeEvent
    public void plantVanilla(final BlockEvent.PlaceEvent event) {
        final World world = event.getWorld();
        final Block block = world.getBlockState(event.getPos().down()).getBlock();
        if (!(block instanceof IBlockSoil)) {
            return;
        }
        final IFlower flower = BotanyCore.getFlowerRoot().getConversion(event.getItemInHand());
        if (flower != null) {
            Gardening.plant(world, event.getPos(), flower, event.getPlayer().getGameProfile());
        }
    }

    @Deprecated
    public void onPlantVanilla(final PlayerInteractEvent.RightClickBlock event) {
        if (!BinnieCore.proxy.isSimulating(event.getWorld())) {
            return;
        }

        if (event.getEntityPlayer() != null && event.getEntityPlayer().getHeldItemMainhand() != null) {
            final World world = event.getWorld();
            final Block block = world.getBlockState(event.getPos()).getBlock();
            int py = -1;
            if (block instanceof IBlockSoil && (world.isAirBlock(event.getPos().up()) || block.isReplaceable(world, event.getPos()))) {
                py = 1;
            }
            if (py >= 0) {
                final IFlower flower = BotanyCore.getFlowerRoot().getConversion(event.getEntityPlayer().getHeldItemMainhand());
                if (flower != null && Gardening.plant(world, event.getPos().add(0, py, 0), flower, event.getEntityPlayer().getGameProfile()) && !event.getEntityPlayer().capabilities.isCreativeMode) {
                    final ItemStack heldItem = event.getEntityPlayer().getHeldItemMainhand();
                    --heldItem.stackSize;
                }
            }
        }
    }

    @SubscribeEvent
    public void onBonemeal(final BonemealEvent event) {
        final Block block = event.getBlock().getBlock();
        if (Gardening.isSoil(block)) {
            final IBlockSoil soil = (IBlockSoil) block;
			if (soil.fertilise(event.getWorld(), event.getPos(), EnumSoilType.LOAM)) {
				event.setResult(Event.Result.ALLOW);
			}
        }
        final TileEntity tile = event.getWorld().getTileEntity(event.getPos());
        if (tile instanceof TileEntityFlower && ((TileEntityFlower) tile).onBonemeal()) {
            event.setResult(Event.Result.ALLOW);
        }
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

    public static class PacketHandler extends BinniePacketHandler {
        public PacketHandler() {
            super(Botany.instance);
        }
    }

}
