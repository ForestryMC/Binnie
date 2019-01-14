package binnie.core.modules;

import java.io.File;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import forestry.api.modules.ForestryModule;
import forestry.api.modules.IForestryModule;
import forestry.modules.ForestryPluginUtil;

import binnie.core.Constants;

public class ModuleContainer implements forestry.api.modules.IModuleContainer {

	private static final String CONFIG_CATEGORY = "modules";

	protected final Set<IForestryModule> loadedModules;
	protected final Set<IForestryModule> unloadedModules;
	protected final File configFolder;
	protected final Configuration configModules;
	protected final String containerID;
	protected final ContainerState state;
	protected final Set<IConfigHandler> configHandlers;

	public ModuleContainer(String containerID, ContainerState state) {
		this.containerID = containerID;
		this.state = state;
		loadedModules = new LinkedHashSet<>();
		unloadedModules = new LinkedHashSet<>();
		configFolder = new File(Loader.instance().getConfigDir(), Constants.FORESTRY_CONFIG_FOLDER + containerID);
		configModules = new Configuration(new File(configFolder, "modules.cfg"));
		configHandlers = new HashSet<>();
	}

	public void setupAPI() {
		for (IForestryModule module : loadedModules) {
			module.setupAPI();
		}

		for (IForestryModule module : unloadedModules) {
			module.disabledSetupAPI();
		}
	}

	protected void runPreInit(FMLPreInitializationEvent event) {
		for (IConfigHandler handler : configHandlers) {
			handler.loadConfig();
		}
		for (IForestryModule module : loadedModules) {
			module.registerItemsAndBlocks();
		}
		for (IForestryModule module : loadedModules) {
			module.preInit();
		}
	}

	public void runInit(FMLInitializationEvent event) {
		for (IForestryModule module : loadedModules) {
			module.doInit();
			module.registerRecipes();
		}
	}

	public void runPostInit(FMLPostInitializationEvent event) {
		for (IForestryModule module : loadedModules) {
			module.postInit();
		}
	}

	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
		for (IConfigHandler handler : configHandlers) {
			handler.loadConfig();
		}
	}

	@Override
	public boolean isModuleEnabled(IForestryModule module) {
		ForestryModule info = module.getClass().getAnnotation(ForestryModule.class);

		String comment = ForestryPluginUtil.getComment(module);
		Property prop = getModulesConfig().get(CONFIG_CATEGORY, info.moduleID(), true, comment);
		return prop.getBoolean();
	}

	@Override
	public void onConfiguredModules(Collection<IForestryModule> activeModules, Collection<IForestryModule> unloadedModules) {
		this.loadedModules.addAll(activeModules);
		this.unloadedModules.addAll(unloadedModules);
	}

	@Override
	public String getID() {
		return containerID;
	}

	public File getConfigFolder() {
		return configFolder;
	}

	@Override
	public Configuration getModulesConfig() {
		return configModules;
	}

	public void registerConfigHandler(IConfigHandler handler) {
		configHandlers.add(handler);
	}

	@Override
	public boolean isAvailable() {
		return state.isAvailable();
	}

	public interface ContainerState {
		boolean isAvailable();
	}
}
