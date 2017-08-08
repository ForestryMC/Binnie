package binnie.modules;

import javax.annotation.Nullable;
import java.io.File;
import java.util.LinkedHashSet;
import java.util.Set;

import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

import binnie.core.AbstractMod;

public abstract class BlankModuleContainer extends AbstractMod implements IModuleContainer {
	protected Set<Module> loadedModules;
	protected Set<Module> unloadedModules;
	protected Set<String> enabledModules;
	protected Set<String> disabledModules;
	protected File configFolder;

	@Nullable
	private SimpleNetworkWrapper wrapper;

	public BlankModuleContainer() {
		super();
		this.loadedModules = new LinkedHashSet<>();
		this.unloadedModules = new LinkedHashSet<>();
		this.enabledModules = new LinkedHashSet<>();
		this.disabledModules = new LinkedHashSet<>();
		ModuleManager.register(this);
		configFolder = new File(Loader.instance().getConfigDir(), "forestry/" + getModID());
	}

	@Override
	protected void preInitModules(FMLPreInitializationEvent event) {
		ModuleManager.runRegisterItemsAndBlocks(this);
		ModuleManager.runPreInit(event, this);
	}

	@Override
	public void init(FMLInitializationEvent event) {
		super.init(event);
		ModuleManager.runInit(event, this);
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
		super.postInit(event);
		ModuleManager.runPostInit(event, this);
	}

	public final boolean isModuleEnabled(String moduleUID){
		return enabledModules.contains(moduleUID);
	}

	@Override
	public String getID() {
		return getModID();
	}

	@Override
	public File getConfigFolder() {
		return configFolder;
	}

	@Override
	public Set<String> getEnabledModules() {
		return enabledModules;
	}

	@Override
	public void enableModule(String uid) {
		enabledModules.add(uid);
	}

	@Override
	public void disableModule(String uid) {
		disabledModules.add(uid);
	}

	@Override
	public Set<Module> getLoadedModules() {
		return loadedModules;
	}

	@Override
	public Set<Module> getUnloadedModules() {
		return unloadedModules;
	}
}
