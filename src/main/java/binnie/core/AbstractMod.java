package binnie.core;

import binnie.Binnie;
import binnie.core.gui.IBinnieGUID;
import binnie.core.mod.parser.FieldParser;
import binnie.core.network.BinniePacketHandler;
import binnie.core.network.IPacketID;
import binnie.core.network.IPacketProvider;
import binnie.core.network.packet.MessageBinnie;
import binnie.core.proxy.IProxyCore;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import net.minecraftforge.common.MinecraftForge;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;

public abstract class AbstractMod implements IPacketProvider, IInitializable {
	private SimpleNetworkWrapper wrapper;
	private LinkedHashSet<Field> fields;
	protected List<IInitializable> modules;

	public AbstractMod() {
		this.fields = new LinkedHashSet<>();
		this.modules = new ArrayList<>();
		BinnieCore.registerMod(this);
		MinecraftForge.EVENT_BUS.register(this);
	}

	public abstract boolean isActive();

	@Override
	public abstract String getChannel();

	@Override
	public IPacketID[] getPacketIDs() {
		return new IPacketID[0];
	}

	public IBinnieGUID[] getGUIDs() {
		return new IBinnieGUID[0];
	}

	public Class[] getConfigs() {
		return new Class[0];
	}

	public abstract IProxyCore getProxy();

	public abstract String getModID();

	public SimpleNetworkWrapper getNetworkWrapper() {
		return wrapper;
	}

	protected abstract Class<? extends BinniePacketHandler> getPacketHandler();

	@Override
	public void preInit() {
		if (!isActive()) {
			return;
		}

		if (getConfigs() != null) {
			for (Class cls : getConfigs()) {
				Binnie.Configuration.registerConfiguration(cls, this);
			}
		}

		getProxy().preInit();
		for (IInitializable module : modules) {
			module.preInit();
		}
		
		// TODO what does it mean?
		Collections.addAll(fields, getClass().getFields());
		for (Class cls : getClass().getClasses()) {
			Collections.addAll(fields, getClass().getFields());
		}
		for (IInitializable module : modules) {
			Collections.addAll(fields, module.getClass().getFields());
		}
		for (Field field4 : this.fields) {
			try {
				FieldParser.preInitParse(field4, this);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	@Override
	public void init() {
		if (!isActive()) {
			return;
		}
		getProxy().init();

		wrapper = NetworkRegistry.INSTANCE.newSimpleChannel(this.getChannel());
		wrapper.registerMessage(getPacketHandler(), MessageBinnie.class, 1, Side.CLIENT);
		wrapper.registerMessage(getPacketHandler(), MessageBinnie.class, 1, Side.SERVER);
		for (IInitializable module : this.modules) {
			module.init();
		}

		for (Field field : this.fields) {
			try {
				FieldParser.initParse(field, this);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	@Override
	public void postInit() {
		if (!isActive()) {
			return;
		}

		getProxy().postInit();
		for (IInitializable module : this.modules) {
			module.postInit();
		}

		for (Field field : this.fields) {
			try {
				FieldParser.postInitParse(field, this);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	protected void addModule(IInitializable init) {
		modules.add(init);
		MinecraftForge.EVENT_BUS.register(init);
	}
}
