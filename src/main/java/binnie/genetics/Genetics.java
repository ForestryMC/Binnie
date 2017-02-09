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
import binnie.genetics.item.ItemSequence;
import binnie.genetics.item.ItemSerum;
import binnie.genetics.item.ItemSerumArray;
import binnie.genetics.item.ModuleItem;
import binnie.genetics.machine.ModuleMachine;
import binnie.genetics.proxy.Proxy;
import com.google.common.base.Preconditions;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Mod(modid = Constants.GENETICS_MOD_ID, name = "Binnie's Genetics", useMetadata = true, dependencies = "required-after:" + Constants.CORE_MOD_ID)
public class Genetics extends AbstractMod {

	@Mod.Instance(Constants.GENETICS_MOD_ID)
	public static Genetics instance;

	@SidedProxy(clientSide = "binnie.genetics.proxy.ProxyClient", serverSide = "binnie.genetics.proxy.ProxyServer")
	public static Proxy proxy;

	public static String channel = "GEN";
	@Nullable
	private static Item itemGenetics;
	public static ItemSerum itemSerum;
	public static ItemSequence itemSequencer;
	public static MachineGroup packageGenetic;
	public static MachineGroup packageAdvGenetic;
	public static MachineGroup packageLabMachine;
	public static ItemDatabase database;
	public static ItemAnalyst analyst;
	public static Item registry;
	public static Item masterRegistry;
	public static ItemSerumArray itemSerumArray = null;

	public static Item getItemGenetics() {
		Preconditions.checkState(itemGenetics != null);
		return itemGenetics;
	}

	public static void setItemGenetics(Item itemGenetics) {
		Genetics.itemGenetics = itemGenetics;
	}

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
		this.addModule(new ModuleItem());
		this.addModule(new ModuleMachine());
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
		return Constants.GENETICS_MOD_ID;
	}

	@Override
	protected Class<? extends BinniePacketHandler> getPacketHandler() {
		return PacketHandler.class;
	}

	@Override
	public boolean isActive() {
		return BinnieCore.isGeneticsActive();
	}

	public static class PacketHandler extends BinniePacketHandler {
		public PacketHandler() {
			super(Genetics.instance);
		}
	}

}
