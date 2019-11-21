package binnie.extrabees;

import binnie.core.Binnie;
import binnie.core.Constants;
import binnie.core.api.genetics.IBreedingSystem;
import binnie.core.gui.BinnieGUIHandler;
import binnie.core.gui.IBinnieGUID;
import binnie.core.modules.BlankModuleContainer;
import binnie.core.network.BinniePacketHandler;
import binnie.core.proxy.IProxyCore;
import binnie.extrabees.genetics.BeeBreedingSystem;
import binnie.extrabees.genetics.gui.analyst.AnalystPagePlugin;
import binnie.extrabees.gui.ExtraBeesGUID;
import binnie.extrabees.modules.ModuleCore;
import binnie.extrabees.proxy.ExtraBeesCommonProxy;
import binnie.extrabees.utils.config.ConfigHandler;
import binnie.extrabees.utils.config.ConfigurationMain;
import binnie.genetics.api.GeneticsApi;
import binnie.genetics.api.analyst.IAnalystManager;
import forestry.api.apiculture.BeeManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import java.io.File;

@Mod(
	modid = ExtraBees.MODID,
	name = "Binnie's Extra Bees",
	version = "@VERSION@",
	acceptedMinecraftVersions = Constants.ACCEPTED_MINECRAFT_VERSIONS,
	dependencies = "required-after:" + Constants.CORE_MOD_ID
)
public class ExtraBees extends BlankModuleContainer {

	public static final String MODID = "extrabees";

	@Mod.Instance(MODID)
	public static ExtraBees instance;

	@SidedProxy(clientSide = "binnie.extrabees.proxy.ExtraBeesClientProxy", serverSide = "binnie.extrabees.proxy.ExtraBeesCommonProxy")
	public static ExtraBeesCommonProxy proxy;

	public static IBreedingSystem beeBreedingSystem;

	public ExtraBees() {
		super();
		MinecraftForge.EVENT_BUS.register(ModuleCore.class);
	}

	public static ConfigHandler configHandler;

	@Mod.EventHandler
	public void preInit(final FMLPreInitializationEvent event) {
		super.preInit(event);
		File configFile = new File(event.getModConfigurationDirectory(), "forestry/extrabees/main.conf");
		configHandler = new ConfigHandler(configFile);
		configHandler.addConfigurable(new ConfigurationMain());

		NetworkRegistry.INSTANCE.registerGuiHandler(this, new BinnieGUIHandler(ExtraBeesGUID.values()));

		if (BeeManager.beeRoot != null) {
			beeBreedingSystem = new BeeBreedingSystem();
			Binnie.GENETICS.registerBreedingSystem(beeBreedingSystem);
		}

		IAnalystManager analystManager = GeneticsApi.analystManager;
		if (analystManager != null) {
			analystManager.registerAnalystPagePlugin(new AnalystPagePlugin());
		}
	}

	@Mod.EventHandler
	public void init(final FMLInitializationEvent evt) {
		super.init(evt);
		configHandler.reload(true);
	}

	@Override
	protected void registerModules() {

	}

	@Override
	public boolean isAvailable() {
		return true;
	}

	@Override
	public String getChannel() {
		return "EB";
	}

	@Override
	public IProxyCore getProxy() {
		return proxy;
	}

	@Override
	public String getModId() {
		return Constants.EXTRA_BEES_MOD_ID;
	}

	@Override
	public IBinnieGUID[] getGUIDs() {
		return ExtraBeesGUID.values();
	}

	@Override
	protected Class<? extends BinniePacketHandler> getPacketHandler() {
		return PacketHandler.class;
	}

	public static class PacketHandler extends BinniePacketHandler {
		public PacketHandler() {
			super(ExtraBees.instance);
		}
	}
}
