package binnie.genetics;

import binnie.Constants;
import binnie.core.AbstractMod;
import binnie.core.BinnieCore;
import binnie.core.gui.IBinnieGUID;
import binnie.core.machines.MachineGroup;
import binnie.core.network.BinniePacketHandler;
import binnie.core.network.IPacketID;
import binnie.core.proxy.IProxyCore;
import binnie.genetics.core.GeneticsGUI;
import binnie.genetics.core.GeneticsPacket;
import binnie.genetics.item.ItemAnalyst;
import binnie.genetics.item.ItemDatabase;
import binnie.genetics.item.ItemSerumArray;
import binnie.genetics.item.ModuleItem;
import binnie.genetics.machine.ModuleMachine;
import binnie.genetics.proxy.Proxy;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Constants.GENETICS_MOD_ID, name = "Binnie's Genetics", useMetadata = true, dependencies = "required-after:" + Constants.CORE_MOD_ID)
public class Genetics extends AbstractMod {
    public static ItemSerumArray itemSerumArray;
    @Mod.Instance(Constants.GENETICS_MOD_ID)
    public static Genetics instance;
    @SidedProxy(clientSide = "binnie.genetics.proxy.ProxyClient", serverSide = "binnie.genetics.proxy.ProxyServer")
    public static Proxy proxy;
    public static String channel;
    public static Item itemGenetics;
    public static Item itemSerum;
    public static Item itemSequencer;
    public static MachineGroup packageGenetic;
    public static MachineGroup packageAdvGenetic;
    public static MachineGroup packageLabMachine;
    public static ItemDatabase database;
    public static ItemAnalyst analyst;
    public static Item registry;
    public static Item masterRegistry;

    @Mod.EventHandler
    public void preInit(final FMLPreInitializationEvent evt) {
        this.addModule(new ModuleItem());
        this.addModule(new ModuleMachine());
        this.preInit();
        proxy.registerModels();
    }

    @Mod.EventHandler
    public void init(final FMLInitializationEvent evt) {
        this.init();
        proxy.registerItemAndBlockColors();
    }

    @Mod.EventHandler
    public void postInit(final FMLPostInitializationEvent evt) {
        this.postInit();
    }

    public Genetics() {
        Genetics.instance = this;
    }

    @Override
    public IBinnieGUID[] getGUIDs() {
        return GeneticsGUI.values();
    }

    @Override
    public IPacketID[] getPacketIDs() {
        return GeneticsPacket.values();
    }

    @Override
    public String getChannel() {
        return "GEN";
    }

    @Override
    public IProxyCore getProxy() {
        return Genetics.proxy;
    }

    @Override
    public String getModID() {
        return "genetics";
    }

    @Override
    protected Class<? extends BinniePacketHandler> getPacketHandler() {
        return PacketHandler.class;
    }

    @Override
    public boolean isActive() {
        return BinnieCore.isGeneticsActive();
    }

    static {
        Genetics.itemSerumArray = null;
        Genetics.channel = "GEN";
    }

    public static class PacketHandler extends BinniePacketHandler {
        public PacketHandler() {
            super(Genetics.instance);
        }
    }
}
