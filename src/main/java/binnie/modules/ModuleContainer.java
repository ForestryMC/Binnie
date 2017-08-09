package binnie.modules;

import java.io.File;
import java.util.LinkedHashSet;
import java.util.Set;

import net.minecraftforge.common.config.Configuration;

import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import binnie.Constants;

public class ModuleContainer implements IModuleContainer {

	protected final Set<Module> loadedModules;
	protected final Set<Module> unloadedModules;
	protected final Set<String> enabledModules;
	protected final File configFolder;
	protected final Configuration configModules;
	protected final Configuration configMain;
	protected final String modId;
	protected final ContainerState state;

	public ModuleContainer(String modId, ContainerState state) {
		this.modId = modId;
		this.state = state;
		loadedModules = new LinkedHashSet<>();
		unloadedModules = new LinkedHashSet<>();
		enabledModules = new LinkedHashSet<>();
		configFolder = new File(Loader.instance().getConfigDir(), Constants.FORESTRY_CONFIG_FOLDER + modId);
		configModules = new Configuration(configFolder, "modules.cfg");
		configMain = new Configuration(configFolder, "main.cfg");
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
	}

	public void runPostInit(FMLPostInitializationEvent event) {
		for (Module module : loadedModules) {
			module.postInit();
		}
	}

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
	public Configuration getMainConfig() {
		return configMain;
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
	public Set<Module> getLoadedModules() {
		return loadedModules;
	}

	@Override
	public Set<Module> getUnloadedModules() {
		return unloadedModules;
	}

	@Override
	public boolean isAvailable() {
		return state.isAvailable();
	}

	public static interface ContainerState{
		boolean isAvailable();
	}
}
