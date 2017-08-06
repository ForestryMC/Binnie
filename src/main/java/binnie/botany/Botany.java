package binnie.botany;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import binnie.Constants;
import binnie.botany.gui.BotanyGUI;
import binnie.botany.network.PacketID;
import binnie.botany.proxy.Proxy;
import binnie.core.BinnieCore;
import binnie.core.gui.IBinnieGUID;
import binnie.core.network.BinniePacketHandler;
import binnie.core.network.IPacketID;
import binnie.core.proxy.IProxyCore;
import binnie.modules.BlankModuleContainer;
import binnie.modules.ModuleManager;

@Mod(
	modid = Constants.BOTANY_MOD_ID,
	name = "Binnie's Botany",
	dependencies = "required-after:" + Constants.CORE_MOD_ID
)
public class Botany extends BlankModuleContainer {
	public static final float AGE_CHANCE = 0.2f;

	@SuppressWarnings("NullableProblems")
	@Mod.Instance(Constants.BOTANY_MOD_ID)
	public static Botany instance;
	@SuppressWarnings("NullableProblems")
	@SidedProxy(clientSide = "binnie.botany.proxy.ProxyClient", serverSide = "binnie.botany.proxy.ProxyServer")
	public static Proxy proxy;

	public Botany() {
		ModuleManager.register(this);
	}

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent evt) {
		preInit();
		ModuleManager.register(evt, this);
		ModuleManager.runPreInit(evt, this);
		getProxy().registerModels();
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent evt) {
		init();
		ModuleManager.runInit(evt, this);
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent evt) {
		postInit();
		ModuleManager.runPostInit(evt, this);
	}

	@Override
	protected void registerModules() {
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
		return Constants.BOTANY_MOD_ID;
	}

	@Override
	protected Class<? extends BinniePacketHandler> getPacketHandler() {
		return PacketHandler.class;
	}

	@Override
	public boolean isAvailable() {
		return BinnieCore.isBotanyActive();
	}

	public static boolean isModuleActive(String moduleUID){
		return instance.isModuleEnabled(moduleUID);
	}

	public static class PacketHandler extends BinniePacketHandler {
		public PacketHandler() {
			super(Botany.instance);
		}
	}
}
