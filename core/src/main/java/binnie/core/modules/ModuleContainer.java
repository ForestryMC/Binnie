package binnie.core.modules;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistryEntry;

import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import forestry.api.modules.ForestryModule;
import forestry.api.modules.IForestryModule;
import forestry.modules.ForestryPluginUtil;

import binnie.core.Constants;
import binnie.core.features.Feature;
import binnie.core.features.FeatureProvider;
import binnie.core.features.FeatureRegistry;
import binnie.core.features.IFeatureRegistry;
import binnie.core.util.IConfigHandler;

import mezz.jei.util.Log;

public class ModuleContainer implements forestry.api.modules.IModuleContainer {

	private static final String CONFIG_CATEGORY = "modules";

	private final ModuleProvider mod;
	protected final Set<IForestryModule> loadedModules;
	protected final Set<IForestryModule> unloadedModules;
	protected final File configFolder;
	protected final Configuration configModules;
	protected final String containerID;
	protected final Set<IConfigHandler> configHandlers;
	protected final Map<String, FeatureRegistry> registries = new HashMap<>();

	public ModuleContainer(ModuleProvider mod) {
		this.mod = mod;
		containerID = mod.getModId();
		loadedModules = new LinkedHashSet<>();
		unloadedModules = new LinkedHashSet<>();
		configFolder = new File(Loader.instance().getConfigDir(), Constants.FORESTRY_CONFIG_FOLDER + containerID);
		configModules = new Configuration(new File(configFolder, "modules.cfg"));
		configHandlers = new HashSet<>();
	}

	public IFeatureRegistry getRegistry(String moduleId) {
		return registries.computeIfAbsent(moduleId, key -> new FeatureRegistry(mod, moduleId));
	}

	public void loadFeatures(ASMDataTable asmDataTable) {
		String annotationClassName = FeatureProvider.class.getCanonicalName();
		Set<ASMDataTable.ASMData> asmDataSet = asmDataTable.getAll(annotationClassName);
		for (ASMDataTable.ASMData asmData : asmDataSet) {
			try {
				Class.forName(asmData.getClassName());
			} catch (ClassNotFoundException | LinkageError e) {
				Log.get().error("Failed to load: {}", asmData.getClassName(), e);
			}
		}
	}

	public void setupAPI() {
		for (IForestryModule module : loadedModules) {
			module.setupAPI();
		}

		for (IForestryModule module : unloadedModules) {
			module.disabledSetupAPI();
		}
	}

	void runPreInit(FMLPreInitializationEvent event) {
		for (IForestryModule module : loadedModules) {
			ForestryModule info = module.getClass().getAnnotation(ForestryModule.class);
			FeatureRegistry registry = registries.get(info.moduleID());
			if (registry != null) {
				for (Feature feature : registry.getFeatures()) {
					feature.createContent();
				}
			}
		}
		for (IForestryModule module : loadedModules) {
			if (module instanceof BinnieModule) {
				((BinnieModule) module).onFeatureCreation();
			}
		}
		for (IForestryModule module : loadedModules) {
			module.registerItemsAndBlocks();
		}
		for (IForestryModule module : loadedModules) {
			module.preInit();
		}
	}

	public <T extends IForgeRegistryEntry<T>> void onObjectRegistration(RegistryEvent.Register<T> event) {
		registries.values().forEach(registry -> registry.onRegister(event));
	}

	public <T extends IForgeRegistryEntry<T>> void afterObjectRegistration(RegistryEvent.Register<T> event) {
		registries.values().forEach(registry -> registry.afterRegistration(event));
	}

	void runInit(FMLInitializationEvent event) {
		for (IForestryModule module : loadedModules) {
			module.doInit();
			module.registerRecipes();
		}
		for (IConfigHandler handler : configHandlers) {
			handler.loadConfig();
		}
	}

	void runPostInit(FMLPostInitializationEvent event) {
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
		return true;
	}
}
