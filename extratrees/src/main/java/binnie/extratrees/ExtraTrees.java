package binnie.extratrees;

import binnie.core.BinnieCore;
import binnie.core.Constants;
import binnie.core.gui.IBinnieGUID;
import binnie.core.machines.errors.ErrorStateRegistry;
import binnie.core.modules.BlankModuleContainer;
import binnie.core.network.BinniePacketHandler;
import binnie.core.proxy.IProxyCore;
import binnie.extratrees.config.ConfigurationMain;
import binnie.extratrees.genetics.gui.analyst.ButterflyAnalystPagePlugin;
import binnie.extratrees.genetics.gui.analyst.TreeAnalystPagePlugin;
import binnie.extratrees.genetics.gui.analyst.TreeProducePlugin;
import binnie.extratrees.gui.ExtraTreesGUID;
import binnie.extratrees.liquid.Juice;
import binnie.extratrees.machines.ExtraTreesErrorCode;
import binnie.extratrees.modules.ModuleWood;
import binnie.extratrees.proxy.Proxy;
import binnie.genetics.api.GeneticsApi;
import binnie.genetics.api.analyst.IAnalystManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.oredict.OreDictionary;

@Mod(
	modid = Constants.EXTRA_TREES_MOD_ID,
	name = "Binnie's Extra Trees",
	version = "@VERSION@",
	acceptedMinecraftVersions = Constants.ACCEPTED_MINECRAFT_VERSIONS,
	dependencies = "required-after:" + Constants.CORE_MOD_ID + ';' + "required-after:" + Constants.GENETICS_MOD_ID + ';'
		+ "after:" + Constants.DESIGN_MOD_ID + ';'
)
public class ExtraTrees extends BlankModuleContainer {

	@SuppressWarnings("NullableProblems")
	@Mod.Instance(Constants.EXTRA_TREES_MOD_ID)
	public static ExtraTrees instance;

	@SuppressWarnings("NullableProblems")
	@SidedProxy(clientSide = "binnie.extratrees.proxy.ProxyClient", serverSide = "binnie.extratrees.proxy.ProxyServer")
	public static Proxy proxy;

	public ExtraTrees() {
		super();
		MinecraftForge.EVENT_BUS.register(ModuleWood.class);
	}

	@Mod.EventHandler
	public void preInit(final FMLPreInitializationEvent evt) {
		container.registerConfigHandler(new ConfigurationMain(container));
		super.preInit(evt);

		IAnalystManager analystManager = GeneticsApi.analystManager;
		if (analystManager != null) {
			analystManager.registerAnalystPagePlugin(new TreeAnalystPagePlugin());
			analystManager.registerAnalystPagePlugin(new ButterflyAnalystPagePlugin());
			analystManager.registerProducePlugin(new TreeProducePlugin());
		}

		for (ExtraTreesErrorCode errorCode : ExtraTreesErrorCode.values()) {
			ErrorStateRegistry.registerErrorState(errorCode);
		}

		for (Juice juice : Juice.values()) {
			OreDictionary.registerOre(juice.getOreDict(), BinnieCore.getGlassBottle().getContainer(juice.getType()));
		}
	}

	@Mod.EventHandler
	public void init(final FMLInitializationEvent evt) {
		super.init(evt);
	}

	@Mod.EventHandler
	public void postInit(final FMLPostInitializationEvent evt) {
		super.postInit(evt);
	}

	@Override
	protected void registerModules() {
	}

	@Override
	public IBinnieGUID[] getGUIDs() {
		return ExtraTreesGUID.values();
	}

	@Override
	public Class<?>[] getConfigs() {
		return new Class[]{ConfigurationMain.class};
	}

	@Override
	public String getChannel() {
		return "ET";
	}

	@Override
	public IProxyCore getProxy() {
		return ExtraTrees.proxy;
	}

	@Override
	public String getModId() {
		return Constants.EXTRA_TREES_MOD_ID;
	}

	@Override
	protected Class<? extends BinniePacketHandler> getPacketHandler() {
		return PacketHandler.class;
	}

	@Override
	public boolean isAvailable() {
		return BinnieCore.isExtraTreesActive();
	}

	public static class PacketHandler extends BinniePacketHandler {
		public PacketHandler() {
			super(ExtraTrees.instance);
		}
	}
}
