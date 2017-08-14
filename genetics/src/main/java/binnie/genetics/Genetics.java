package binnie.genetics;

import binnie.genetics.api.GeneticsApi;
import binnie.genetics.core.GeneticsTexture;
import binnie.genetics.gui.analyst.GeneticsProducePlugin;
import binnie.genetics.machine.acclimatiser.AcclimatiserManager;
import binnie.genetics.machine.sequencer.Sequencer;
import com.google.common.base.Preconditions;

import javax.annotation.Nullable;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import binnie.core.Binnie;
import binnie.core.Constants;
import binnie.core.AbstractMod;
import binnie.core.BinnieCore;
import binnie.core.gui.IBinnieGUID;
import binnie.core.network.BinniePacketHandler;
import binnie.core.network.IPacketID;
import binnie.core.proxy.IProxyCore;
import binnie.genetics.core.GeneticsGUI;
import binnie.genetics.core.GeneticsPacket;
import binnie.genetics.item.ItemPunnettSquare;
import binnie.genetics.item.ModuleItems;
import binnie.genetics.machine.ModuleMachine;
import binnie.genetics.proxy.Proxy;

@Mod(
	modid = Constants.GENETICS_MOD_ID,
	name = "Binnie's Genetics",
	dependencies = "required-after:" + Constants.CORE_MOD_ID
)
public class Genetics extends AbstractMod {
	public static final String CHANNEL = "GEN";
	@Mod.Instance(Constants.GENETICS_MOD_ID)
	public static Genetics instance;
	@SidedProxy(clientSide = "binnie.genetics.proxy.ProxyClient", serverSide = "binnie.genetics.proxy.ProxyServer")
	public static Proxy proxy;
	@Nullable
	private static ModuleItems items;
	@Nullable
	private static ModuleMachine machine;

	public static ModuleItems items() {
		Preconditions.checkState(items != null);
		return items;
	}

	public static ModuleMachine machine() {
		Preconditions.checkState(machine != null);
		return machine;
	}

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent evt) {
		proxy.registerItem(new ItemPunnettSquare());
		super.preInit(evt);

		GeneticsTexture.dnaIcon = Binnie.RESOURCE.getItemSprite(Genetics.instance, "dna");
		Sequencer.fxSeqA = Binnie.RESOURCE.getBlockSprite(Genetics.instance, "fx/sequencer.a");
		Sequencer.fxSeqG = Binnie.RESOURCE.getBlockSprite(Genetics.instance, "fx/sequencer.g");
		Sequencer.fxSeqT = Binnie.RESOURCE.getBlockSprite(Genetics.instance, "fx/sequencer.t");
		Sequencer.fxSeqC = Binnie.RESOURCE.getBlockSprite(Genetics.instance, "fx/sequencer.c");

		GeneticsApi.acclimatiserManager = new AcclimatiserManager();
		GeneticsApi.registerProducePlugin(new GeneticsProducePlugin());
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent evt) {
		super.init(evt);
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent evt) {
		super.postInit(evt);
	}

	@Override
	protected void registerModules() {
		addModule(items = new ModuleItems());
		addModule(machine = new ModuleMachine());
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
	public boolean isAvailable() {
		return BinnieCore.isGeneticsActive();
	}

	public static class PacketHandler extends BinniePacketHandler {
		public PacketHandler() {
			super(Genetics.instance);
		}
	}
}
