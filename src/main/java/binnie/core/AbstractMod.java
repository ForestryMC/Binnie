package binnie.core;

import com.google.common.base.Preconditions;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.common.MinecraftForge;

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

import binnie.Binnie;
import binnie.core.gui.IBinnieGUID;
import binnie.core.network.BinniePacketHandler;
import binnie.core.network.IPacketID;
import binnie.core.network.IPacketProvider;
import binnie.core.network.packet.MessageBinnie;
import binnie.core.proxy.IProxyCore;

public abstract class AbstractMod implements IPacketProvider, IInitializable {
	protected List<IInitializable> modules;

	@Nullable
	private SimpleNetworkWrapper wrapper;

	public AbstractMod() {
		this.modules = new ArrayList<>();
		BinnieCore.registerMod(this);
		MinecraftForge.EVENT_BUS.register(this);
	}

	protected abstract void registerModules();

	public abstract boolean isAvailable();

	public abstract String getChannel();

	@Override
	public IPacketID[] getPacketIDs() {
		return new IPacketID[0];
	}

	public IBinnieGUID[] getGUIDs() {
		return new IBinnieGUID[0];
	}

	public Class<?>[] getConfigs() {
		return new Class[0];
	}

	public abstract IProxyCore getProxy();

	public abstract String getModID();

	public SimpleNetworkWrapper getNetworkWrapper() {
		Preconditions.checkState(wrapper != null, "Tried to get network wrapper before it has been init.");
		return this.wrapper;
	}

	protected abstract Class<? extends BinniePacketHandler> getPacketHandler();

	public Object getMod() {
		return this;
	}

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		getProxy().setMod(this);
		registerModules();
		for (final Class<?> cls : this.getConfigs()) {
			Binnie.CONFIGURATION.registerConfiguration(cls, this);
		}
		this.getProxy().preInit();
		for (final IInitializable module : this.modules) {
			module.preInit();
		}
		preInitModules(event);
		getProxy().registerModels();
	}

	@Override
	public void preInit() {

	}

	protected void preInitModules(FMLPreInitializationEvent event){

	}

	@Override
	public void init() {
		this.getProxy().init();
		this.wrapper = NetworkRegistry.INSTANCE.newSimpleChannel(this.getChannel());
		this.wrapper.registerMessage(this.getPacketHandler(), MessageBinnie.class, 1, Side.CLIENT);
		this.wrapper.registerMessage(this.getPacketHandler(), MessageBinnie.class, 1, Side.SERVER);
		for (final IInitializable module : this.modules) {
			module.init();
		}
		getProxy().registerItemAndBlockColors();
	}

	@Override
	public void postInit() {
		this.getProxy().postInit();
		for (final IInitializable module : this.modules) {
			module.postInit();
		}
	}

	protected final void addModule(IInitializable init) {
		this.modules.add(init);
		MinecraftForge.EVENT_BUS.register(init);
	}
}
