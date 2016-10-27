package binnie.core;

import binnie.Binnie;
import binnie.core.gui.IBinnieGUID;
import binnie.core.mod.parser.FieldParser;
import binnie.core.network.BinniePacketHandler;
import binnie.core.network.IPacketID;
import binnie.core.network.IPacketProvider;
import binnie.core.network.packet.MessageBinnie;
import binnie.core.proxy.IProxyCore;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

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

	protected abstract void registerModules();

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
		return this.wrapper;
	}

	protected abstract Class<? extends BinniePacketHandler> getPacketHandler();

	@Override
	public void preInit() {
		getProxy().setMod(this);
		if (!this.isActive()) {
			return;
		}
		registerModules();
		if (this.getConfigs() != null) {
			for (final Class cls : this.getConfigs()) {
				Binnie.Configuration.registerConfiguration(cls, this);
			}
		}
		this.getProxy().preInit();
		for (final IInitializable module : this.modules) {
			module.preInit();
		}
		Collections.addAll(this.fields, this.getClass().getFields());
		for (final Class cls : this.getClass().getClasses()) {
			Collections.addAll(this.fields, this.getClass().getFields());
		}
		for (final IInitializable module : this.modules) {
			Collections.addAll(this.fields, module.getClass().getFields());
		}
		for (final Field field4 : this.fields) {
			try {
				FieldParser.preInitParse(field4, this);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		getProxy().registerModels();
	}

	@Override
	public void init() {
		if (!this.isActive()) {
			return;
		}
		this.getProxy().init();
		(this.wrapper = NetworkRegistry.INSTANCE.newSimpleChannel(this.getChannel())).registerMessage(this.getPacketHandler(), MessageBinnie.class, 1, Side.CLIENT);
		this.wrapper.registerMessage(this.getPacketHandler(), MessageBinnie.class, 1, Side.SERVER);
		for (final IInitializable module : this.modules) {
			module.init();
		}
		for (final Field field : this.fields) {
			try {
				FieldParser.initParse(field, this);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		getProxy().registerItemAndBlockColors();
	}

	@Override
	public void postInit() {
		if (!this.isActive()) {
			return;
		}
		this.getProxy().postInit();
		for (final IInitializable module : this.modules) {
			module.postInit();
		}
		for (final Field field : this.fields) {
			try {
				FieldParser.postInitParse(field, this);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	protected final void addModule(final IInitializable init) {
		this.modules.add(init);
		MinecraftForge.EVENT_BUS.register(init);
	}
}
