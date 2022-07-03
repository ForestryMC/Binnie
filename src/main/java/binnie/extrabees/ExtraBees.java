package binnie.extrabees;

import binnie.core.AbstractMod;
import binnie.core.BinnieCore;
import binnie.core.gui.IBinnieGUID;
import binnie.core.network.BinniePacketHandler;
import binnie.core.proxy.IProxyCore;
import binnie.extrabees.apiary.ModuleApiary;
import binnie.extrabees.config.ConfigurationMachines;
import binnie.extrabees.config.ConfigurationMain;
import binnie.extrabees.core.ExtraBeeGUID;
import binnie.extrabees.core.ModuleCore;
import binnie.extrabees.genetics.ModuleGenetics;
import binnie.extrabees.liquids.ModuleLiquids;
import binnie.extrabees.products.ModuleProducts;
import binnie.extrabees.proxy.ExtraBeesProxy;
import binnie.extrabees.worldgen.ModuleGeneration;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;

@Mod(
        modid = "ExtraBees",
        name = "Extra Bees",
        version = BinnieCore.VERSION,
        useMetadata = true,
        dependencies = "after:BinnieCore")
public class ExtraBees extends AbstractMod {
    @Mod.Instance("ExtraBees")
    public static ExtraBees instance;

    @SidedProxy(
            clientSide = "binnie.extrabees.proxy.ExtraBeesProxyClient",
            serverSide = "binnie.extrabees.proxy.ExtraBeesProxyServer")
    public static ExtraBeesProxy proxy;

    public static Block hive;
    public static Material materialBeehive;
    public static Block ectoplasm;
    public static Item comb;
    public static Item propolis;
    public static Item honeyDrop;
    public static Item dictionary;
    public static Item itemMisc;

    public ExtraBees() {
        ExtraBees.instance = this;
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        addModule(new ModuleCore());
        addModule(new ModuleProducts());
        addModule(new ModuleGenetics());
        addModule(new ModuleGeneration());
        addModule(new ModuleLiquids());
        addModule(new ModuleApiary());
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
        return ExtraBeeGUID.values();
    }

    @Override
    public Class<?>[] getConfigs() {
        return new Class[] {ConfigurationMain.class, ConfigurationMachines.class};
    }

    @Override
    public IProxyCore getProxy() {
        return ExtraBees.proxy;
    }

    @Override
    public String getChannel() {
        return "EB";
    }

    @Override
    public String getModID() {
        return "extrabees";
    }

    @Override
    protected Class<? extends BinniePacketHandler> getPacketHandler() {
        return PacketHandler.class;
    }

    @Override
    public boolean isActive() {
        return BinnieCore.isExtraBeesActive();
    }

    public static class PacketHandler extends BinniePacketHandler {
        public PacketHandler() {
            super(ExtraBees.instance);
        }
    }
}
