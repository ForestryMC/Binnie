package binnie.botany;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import binnie.botany.gui.BotanyGUI;
import binnie.botany.network.PacketID;
import binnie.botany.proxy.Proxy;
import binnie.core.BinnieCore;
import binnie.core.Constants;
import binnie.core.gui.IBinnieGUID;
import binnie.core.modules.BlankModuleContainer;
import binnie.core.network.BinniePacketHandler;
import binnie.core.network.IPacketID;
import binnie.core.proxy.IProxyCore;

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

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent evt) {
		super.preInit(evt);
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

	public static class PacketHandler extends BinniePacketHandler {
		public PacketHandler() {
			super(Botany.instance);
		}
	}
}
