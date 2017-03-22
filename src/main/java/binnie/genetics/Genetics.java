package binnie.genetics;

import binnie.Constants;
import binnie.core.AbstractMod;
import binnie.core.BinnieCore;
import binnie.core.gui.IBinnieGUID;
import binnie.core.network.BinniePacketHandler;
import binnie.core.network.IPacketID;
import binnie.core.proxy.IProxyCore;
import binnie.genetics.core.GeneticsGUI;
import binnie.genetics.core.GeneticsPacket;
import binnie.genetics.item.ModuleItems;
import binnie.genetics.machine.ModuleMachine;
import binnie.genetics.proxy.Proxy;
import com.google.common.base.Preconditions;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import javax.annotation.Nullable;

@Mod(modid = Constants.GENETICS_MOD_ID, name = "Binnie's Genetics", useMetadata = true, dependencies = "required-after:" + Constants.CORE_MOD_ID)
public class Genetics extends AbstractMod {

	@Mod.Instance(Constants.GENETICS_MOD_ID)
	public static Genetics instance;

	@SidedProxy(clientSide = "binnie.genetics.proxy.ProxyClient", serverSide = "binnie.genetics.proxy.ProxyServer")
	public static Proxy proxy;

	public static final String CHANNEL = "GEN";
	@Nullable
	private static ModuleItems items;
	@Nullable
	private static ModuleMachine machine;

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

	public static ModuleItems items() {
		Preconditions.checkState(items != null);
		return items;
	}
	
	public static ModuleMachine machine() {
		Preconditions.checkState(machine != null);
		return machine;
	}

	@Override
	protected void registerModules() {
		this.addModule(items = new ModuleItems());
		this.addModule(machine = new ModuleMachine());
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
		return CHANNEL;
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
