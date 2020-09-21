package binnie.genetics;

import binnie.core.Binnie;
import binnie.core.BinnieCore;
import binnie.core.Constants;
import binnie.core.gui.IBinnieGUID;
import binnie.core.machines.errors.ErrorStateRegistry;
import binnie.core.modules.BlankModuleContainer;
import binnie.core.network.BinniePacketHandler;
import binnie.core.network.IPacketID;
import binnie.core.proxy.IProxyCore;
import binnie.genetics.api.GeneticsApi;
import binnie.genetics.api.acclimatiser.IAcclimatiserManager;
import binnie.genetics.api.analyst.IAnalystManager;
import binnie.genetics.config.ConfigHandler;
import binnie.genetics.config.ConfigurationMain;
import binnie.genetics.core.GeneticsGUI;
import binnie.genetics.core.GeneticsPacket;
import binnie.genetics.core.GeneticsTexture;
import binnie.genetics.genetics.BeeBreedingSystem;
import binnie.genetics.genetics.MothBreedingSystem;
import binnie.genetics.genetics.TreeBreedingSystem;
import binnie.genetics.gui.Icons;
import binnie.genetics.gui.analyst.AnalystManager;
import binnie.genetics.gui.analyst.GeneticsProducePlugin;
import binnie.genetics.item.ItemPunnettSquare;
import binnie.genetics.machine.GeneticsErrorCode;
import binnie.genetics.machine.acclimatiser.AcclimatiserManager;
import binnie.genetics.machine.sequencer.Sequencer;
import binnie.genetics.proxy.Proxy;
import com.google.common.base.Preconditions;
import forestry.api.apiculture.BeeManager;
import forestry.api.arboriculture.TreeManager;
import forestry.api.lepidopterology.ButterflyManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import javax.annotation.Nullable;
import java.io.File;

@Mod(
	modid = Constants.GENETICS_MOD_ID,
	name = "Binnie's Genetics",
	version = "@VERSION@",
	acceptedMinecraftVersions = Constants.ACCEPTED_MINECRAFT_VERSIONS,
	dependencies = "required-after:" + Constants.CORE_MOD_ID
)
public class Genetics extends BlankModuleContainer {
	@Mod.Instance(Constants.GENETICS_MOD_ID)
	public static Genetics instance;
	@SidedProxy(clientSide = "binnie.genetics.proxy.ProxyClient", serverSide = "binnie.genetics.proxy.ProxyServer")
	public static Proxy proxy;
	@Nullable
	private static IAnalystManager analystManager;
	@Nullable
	private static IAcclimatiserManager acclimatiserManager;
	@Nullable
	private static Icons icons;

	public static ConfigHandler configHandler;

	public static IAnalystManager getAnalystManager() {
		Preconditions.checkNotNull(analystManager);
		return analystManager;
	}

	public static IAcclimatiserManager getAcclimatiserManager() {
		Preconditions.checkNotNull(acclimatiserManager);
		return acclimatiserManager;
	}

	public static Icons getIcons() {
		Preconditions.checkNotNull(icons);
		return icons;
	}

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent evt) {
		File configFile = new File(evt.getModConfigurationDirectory(), "forestry/genetics/main.conf");
		configHandler = new ConfigHandler(configFile);
		configHandler.addConfigurable(new ConfigurationMain());


		if (BeeManager.beeRoot != null) {
			GeneticsApi.beeBreedingSystem = new BeeBreedingSystem();
			Binnie.GENETICS.registerBreedingSystem(GeneticsApi.beeBreedingSystem);
		}

		if (ButterflyManager.butterflyRoot != null) {
			GeneticsApi.mothBreedingSystem = new MothBreedingSystem();
			Binnie.GENETICS.registerBreedingSystem(GeneticsApi.mothBreedingSystem);
		}

		if (TreeManager.treeRoot != null) {
			TreeBreedingSystem treeBreedingSystem = new TreeBreedingSystem();
			Binnie.GENETICS.registerBreedingSystem(treeBreedingSystem);
		}

		proxy.registerItem(new ItemPunnettSquare());
		super.preInit(evt);

		GeneticsTexture.dnaIcon = Binnie.RESOURCE.getItemSprite(Genetics.instance, "dna");
		Sequencer.fxSeqA = Binnie.RESOURCE.getBlockSprite(Genetics.instance, "fx/sequencer.a");
		Sequencer.fxSeqG = Binnie.RESOURCE.getBlockSprite(Genetics.instance, "fx/sequencer.g");
		Sequencer.fxSeqT = Binnie.RESOURCE.getBlockSprite(Genetics.instance, "fx/sequencer.t");
		Sequencer.fxSeqC = Binnie.RESOURCE.getBlockSprite(Genetics.instance, "fx/sequencer.c");

		AnalystManager analystManager = new AnalystManager();
		Genetics.analystManager = GeneticsApi.analystManager = analystManager;
		AcclimatiserManager acclimatiserManager = new AcclimatiserManager();
		Genetics.acclimatiserManager = GeneticsApi.acclimatiserManager = acclimatiserManager;

		analystManager.registerProducePlugin(new GeneticsProducePlugin());

		Genetics.icons = new Icons();

		for (GeneticsErrorCode errorCode : GeneticsErrorCode.values()) {
			ErrorStateRegistry.registerErrorState(errorCode);
		}
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent evt) {
		super.init(evt);
		configHandler.reload(true);
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent evt) {
		super.postInit(evt);
	}

	@Override
	protected void registerModules() {
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
	public String getModId() {
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
