package binnie.core.modules;

import java.io.File;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import net.minecraftforge.common.config.Configuration;

import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import binnie.core.Constants;

public class ModuleContainer implements IModuleContainer {

	protected final Set<Module> loadedModules;
	protected final Set<Module> unloadedModules;
	protected final Set<String> enabledModules;
	protected final File configFolder;
	protected final Configuration configModules;
	protected final String modId;
	protected final ContainerState state;
	protected final Set<IConfigHandler> configHandlers;

	public ModuleContainer(String modId, ContainerState state) {
		this.modId = modId;
		this.state = state;
		loadedModules = new LinkedHashSet<>();
		unloadedModules = new LinkedHashSet<>();
		enabledModules = new LinkedHashSet<>();
		configFolder = new File(Loader.instance().getConfigDir(), Constants.FORESTRY_CONFIG_FOLDER + modId);
		configModules = new Configuration(new File(configFolder, "modules.cfg"));
		configHandlers = new HashSet<>();
	}

	protected void runPreInit(FMLPreInitializationEvent event) {
		for (Module module : loadedModules) {
			module.registerItemsAndBlocks();
		}
		for (Module module : loadedModules) {
			module.preInit();
		}
	}

	public void runInit(FMLInitializationEvent event) {
		for (Module module : loadedModules) {
			module.init();
		}
		for(IConfigHandler handler : configHandlers){
			handler.loadConfig();
		}
	}

	public void runPostInit(FMLPostInitializationEvent event) {
		for (Module module : loadedModules) {
			module.postInit();
		}
	}

	@Override
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
		for(IConfigHandler handler : configHandlers){
			handler.loadConfig();
		}
	}

	@Override
	public final boolean isModuleEnabled(String moduleUID){
		return enabledModules.contains(moduleUID);
	}

	@Override
	public String getID() {
		return modId;
	}

	@Override
	public File getConfigFolder() {
		return configFolder;
	}

	@Override
	public Configuration getModulesConfig() {
		return configModules;
	}

	@Override
	public void enableModule(String uid) {
		enabledModules.add(uid);
	}

	@Override
	public Set<Module> getLoadedModules() {
		return loadedModules;
	}

	@Override
	public Set<Module> getUnloadedModules() {
		return unloadedModules;
	}

	@Override
	public void registerConfigHandler(IConfigHandler handler) {
		configHandlers.add(handler);
	}

	@Override
	public boolean isAvailable() {
		return state.isAvailable();
	}

	public interface ContainerState{
		boolean isAvailable();
	}
}
