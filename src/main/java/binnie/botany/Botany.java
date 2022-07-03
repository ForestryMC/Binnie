package binnie.botany;

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
import binnie.botany.flower.ItemFlower;
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
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;

@Mod(
        modid = "Botany",
        name = "Botany",
        version = BinnieCore.VERSION,
        useMetadata = true,
        dependencies = "after:BinnieCore")
public class Botany extends AbstractMod {
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
    public static ItemFlower flowerItem;
    public static ItemPigment pigment;
    public static ItemClay clay;
    public static BlockCeramic ceramic;
    public static BlockCeramicPatterned ceramicTile;
    public static BlockStained stained;
    public static BlockCeramicBrick ceramicBrick;

    public Botany() {
        Botany.instance = this;
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        addModule(new ModuleCore());
        addModule(new ModuleGenetics());
        addModule(new ModuleGardening());
        preInit();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        init();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        postInit();
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
    public void onShearFlower(PlayerInteractEvent event) {
        if (event.action != PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        if (event.entityPlayer != null
                && event.entityPlayer.getHeldItem() != null
                && event.entityPlayer.getHeldItem().getItem() == Items.shears) {
            TileEntity tile = event.world.getTileEntity(event.x, event.y, event.z);
            if (tile instanceof TileEntityFlower) {
                TileEntityFlower flower = (TileEntityFlower) tile;
                flower.onShear();
                event.entityPlayer.getHeldItem().damageItem(1, event.entityPlayer);
            }
        }

        if (event.entityPlayer != null
                && event.entityPlayer.getHeldItem() != null
                && event.entityPlayer.getHeldItem().getItem() == Botany.pollen) {
            TileEntity tile = event.world.getTileEntity(event.x, event.y, event.z);
            if (tile instanceof TileEntityFlower) {
                TileEntityFlower flower = (TileEntityFlower) tile;
                IFlower pollen = BotanyCore.getFlowerRoot().getMember(event.entityPlayer.getHeldItem());
                if (pollen != null && flower.canMateWith(pollen)) {
                    flower.mateWith(pollen);
                    if (!event.entityPlayer.capabilities.isCreativeMode) {
                        ItemStack heldItem = event.entityPlayer.getHeldItem();
                        heldItem.stackSize--;
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onFertiliseSoil(PlayerInteractEvent event) {
        if (event.action != PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK || event.world == null) {
            return;
        }

        if (event.entityPlayer == null || event.entityPlayer.getHeldItem() == null) {
            return;
        }

        int y = event.y;
        Block block = event.world.getBlock(event.x, y, event.z);
        if (block == null || !Gardening.isSoil(block)) {
            block = event.world.getBlock(event.x, --y, event.z);
        }
        if (block == null || !Gardening.isSoil(block)) {
            return;
        }

        IBlockSoil soil = (IBlockSoil) block;
        if (Gardening.isNutrientFertiliser(event.entityPlayer.getHeldItem())
                && soil.getType(event.world, event.x, y, event.z) != EnumSoilType.FLOWERBED) {
            EnumSoilType type = soil.getType(event.world, event.x, y, event.z);
            int next = Math.min(type.ordinal() + Gardening.getFertiliserStrength(event.entityPlayer.getHeldItem()), 2);
            if (soil.fertilise(event.world, event.x, y, event.z, EnumSoilType.values()[next])
                    && !event.entityPlayer.capabilities.isCreativeMode) {
                ItemStack heldItem = event.entityPlayer.getHeldItem();
                heldItem.stackSize--;
                return;
            }
        }

        if (Gardening.isAcidFertiliser(event.entityPlayer.getHeldItem())
                && soil.getPH(event.world, event.x, y, event.z) != EnumAcidity.ACID) {
            EnumAcidity pH = soil.getPH(event.world, event.x, y, event.z);
            int next = Math.max(pH.ordinal() - Gardening.getFertiliserStrength(event.entityPlayer.getHeldItem()), 0);
            if (soil.setPH(event.world, event.x, y, event.z, EnumAcidity.values()[next])
                    && !event.entityPlayer.capabilities.isCreativeMode) {
                ItemStack heldItem2 = event.entityPlayer.getHeldItem();
                heldItem2.stackSize--;
                return;
            }
        }

        if (Gardening.isAlkalineFertiliser(event.entityPlayer.getHeldItem())
                && soil.getPH(event.world, event.x, y, event.z) != EnumAcidity.ALKALINE) {
            EnumAcidity pH = soil.getPH(event.world, event.x, y, event.z);
            int next = Math.min(pH.ordinal() + Gardening.getFertiliserStrength(event.entityPlayer.getHeldItem()), 2);
            if (soil.setPH(event.world, event.x, y, event.z, EnumAcidity.values()[next])
                    && !event.entityPlayer.capabilities.isCreativeMode) {
                ItemStack heldItem3 = event.entityPlayer.getHeldItem();
                heldItem3.stackSize--;
                return;
            }
        }

        if (Gardening.isWeedkiller(event.entityPlayer.getHeldItem())
                && Gardening.addWeedKiller(event.world, event.x, y, event.z)
                && !event.entityPlayer.capabilities.isCreativeMode) {
            ItemStack heldItem4 = event.entityPlayer.getHeldItem();
            heldItem4.stackSize--;
        }
    }

    @SubscribeEvent
    public void plantVanilla(BlockEvent.PlaceEvent event) {
        World world = event.world;
        int x = event.x;
        int y = event.y;
        int z = event.z;
        Block block = world.getBlock(x, y - 1, z);
        if (!(block instanceof IBlockSoil)) {
            return;
        }

        IFlower flower = BotanyCore.getFlowerRoot().getConversion(event.itemInHand);
        if (flower != null) {
            Gardening.plant(world, x, y, z, flower, event.player.getGameProfile());
        }
    }

    @Deprecated
    public void onPlantVanilla(PlayerInteractEvent event) {
        if (!BinnieCore.proxy.isSimulating(event.world)
                || event.action != PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
            return;
        }
        if (event.entityPlayer == null || event.entityPlayer.getHeldItem() == null) {
            return;
        }

        World world = event.world;
        int x = event.x;
        int y = event.y;
        int z = event.z;
        Block block = world.getBlock(x, y, z);
        int py = -1;
        if (block instanceof IBlockSoil
                && (world.isAirBlock(x, y + 1, z) || world.getBlock(x, y, z).isReplaceable(world, x, y, z))) {
            py = 1;
        }

        if (py < 0) {
            return;
        }

        IFlower flower = BotanyCore.getFlowerRoot().getConversion(event.entityPlayer.getHeldItem());
        if (flower != null
                && Gardening.plant(world, x, y + py, z, flower, event.entityPlayer.getGameProfile())
                && !event.entityPlayer.capabilities.isCreativeMode) {
            ItemStack heldItem = event.entityPlayer.getHeldItem();
            heldItem.stackSize--;
        }
    }

    @SubscribeEvent
    public void onBonemeal(BonemealEvent event) {
        Block block = event.world.getBlock(event.x, event.y, event.z);
        if (Gardening.isSoil(block)) {
            IBlockSoil soil = (IBlockSoil) block;
            if (soil.fertilise(event.world, event.x, event.y, event.z, EnumSoilType.LOAM)) {
                event.setResult(Event.Result.ALLOW);
            }
        }

        TileEntity tile = event.world.getTileEntity(event.x, event.y, event.z);
        if (tile instanceof TileEntityFlower && ((TileEntityFlower) tile).onBonemeal()) {
            event.setResult(Event.Result.ALLOW);
        }
    }

    @Override
    public boolean isActive() {
        return BinnieCore.isBotanyActive();
    }

    @Mod.EventHandler
    public void onIMC(FMLInterModComms.IMCEvent event) {
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
