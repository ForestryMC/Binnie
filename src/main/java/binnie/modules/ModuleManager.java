package binnie.modules;

import com.google.common.collect.ImmutableList;

import java.io.File;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import net.minecraft.util.text.translation.I18n;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

import net.minecraftforge.fml.common.discovery.ASMDataTable;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ModuleManager {
	private static Map<String, IModuleContainer> containers = new LinkedHashMap<>();
	private static Map<String, Configuration> configs = new LinkedHashMap<>();
	private static final String CONFIG_FILE_NAME = "modules.cfg";
	private static final String CONFIG_CATEGORY = "modules";
	private static Stage stage = Stage.SETUP;

	public enum Stage {
		SETUP, // setup API to make it functional. GameMode Configs are not yet accessible
		SETUP_DISABLED, // setup fallback API to avoid crashes
		REGISTER, // register basic blocks and items
		PRE_INIT, // register handlers, triggers, definitions, backpacks, crates, and anything that depends on basic items
		INIT, // anything that depends on PreInit stages, recipe registration
		POST_INIT, // stubborn mod integration, dungeon loot, and finalization of things that take input from mods
		FINISHED
	}

	public static boolean isEnabled(String containerID, String moduleID){
		IModuleContainer container = containers.get(containerID);
		if(container == null){
			return false;
		}
		return container.getEnabledModules().contains(moduleID);
	}

	public static void register(IModuleContainer container){
		containers.put(container.getID(), container);
	}

	private static void configureModules(Map<String, List<Module>> modules) {
		Locale locale = Locale.getDefault();
		Locale.setDefault(Locale.ENGLISH);

		Set<String> toLoad = new HashSet<>();

		for(IModuleContainer container : containers.values()) {
			String containerID = container.getID();
			List<Module> containerModules = modules.get(containerID);
			Configuration config = new Configuration(new File(container.getConfigFolder(), CONFIG_FILE_NAME));
			configs.put(container.getID(), config);

			config.load();
			config.addCustomCategoryComment(CONFIG_CATEGORY, "Disabling these modules can greatly change how the mod functions.\n"
				+ "Your mileage may vary, please report any issues.");

			Module coreModule = getPluginCore(containerModules, containerID);
			containerModules.remove(coreModule);
			containerModules.add(0, coreModule);


			Iterator<Module> iterator = containerModules.iterator();
			while (iterator.hasNext()) {
				Module module = iterator.next();
				if (module.canBeDisabled()) {
					if (!isEnabled(config, module)) {
						iterator.remove();
						Log.info("Module disabled: {}", module);
						continue;
					}
					if (!module.isAvailable()) {
						iterator.remove();
						Log.info("Module {} failed to load: {}", module, module.getFailMessage());
						continue;
					}
				}
				BinnieModule info = module.getClass().getAnnotation(BinnieModule.class);
				toLoad.add(info.moduleID());
			}
		}

		for(IModuleContainer container : containers.values()) {
			String containerID = container.getID();
			List<Module> containerModules = modules.get(containerID);
			ImmutableList<Module> allModules = ImmutableList.copyOf(containerModules);

			Iterator<Module> iterator;

			boolean changed;
			do {
				changed = false;
				iterator = containerModules.iterator();
				while (iterator.hasNext()) {
					Module module = iterator.next();
					Set<String> dependencies = module.getDependencyUids();
					if (!toLoad.containsAll(dependencies)) {
						iterator.remove();
						changed = true;
						BinnieModule info = module.getClass().getAnnotation(BinnieModule.class);
						String id = info.moduleID();
						toLoad.remove(id);
						Log.warning("Module {} is missing dependencies: {}", id, dependencies);
					}
				}
			} while (changed);

			Set<Module> loadedModules = container.getLoadedModules();
			Set<Module> unloadedModules = container.getUnloadedModules();

			loadedModules.addAll(containerModules);
			unloadedModules.addAll(allModules);
			unloadedModules.removeAll(loadedModules);
			for(Module module : loadedModules){
				BinnieModule info = module.getClass().getAnnotation(BinnieModule.class);
				container.enableModule(info.moduleID());
			}
			Configuration config = configs.get(container.getID());

			if (config.hasChanged()) {
				config.save();
			}
		}

		Locale.setDefault(locale);
	}

	private static Module getPluginCore(List<Module> modules, String containerID) {
		for (Module module : modules) {
			BinnieModule info = module.getClass().getAnnotation(BinnieModule.class);
			if (info.coreModule()) {
				return module;
			}
		}
		throw new IllegalStateException("Could not find core module for the container " + containerID);
	}

	public static void runPreInit(FMLPreInitializationEvent event, IModuleContainer moduleContainer) {
		stage = Stage.PRE_INIT;
		for (Module module : moduleContainer.getLoadedModules()) {
			Log.debug("Pre-Init Start: {}", module);
			module.preInit(event);
			Log.debug("Pre-Init Complete: {}", module);
		}
	}

	public static void runInit(FMLInitializationEvent event, IModuleContainer moduleContainer) {
		stage = Stage.INIT;
		for (Module module : moduleContainer.getLoadedModules()) {
			Log.debug("Init Start: {}", module);
			module.init(event);
			Log.debug("Init Complete: {}", module);
		}
	}

	public static void runPostInit(FMLPostInitializationEvent event, IModuleContainer moduleContainer) {
		stage = Stage.POST_INIT;
		for (Module module : moduleContainer.getLoadedModules()) {
			Log.debug("Post-Init Start: {}", module);
			module.postInit(event);
			Log.debug("Post-Init Complete: {}", module);
		}
	}

	public static void runRegisterItemsAndBlocks(IModuleContainer moduleContainer) {
		stage = Stage.REGISTER;
		for (Module module : moduleContainer.getLoadedModules()) {
			Log.debug("Register Items and Blocks Start: {}", module);
			module.registerItemsAndBlocks();
			Log.debug("Register Items and Blocks Complete: {}", module);
		}
	}

	public static void finish(){
		stage = Stage.FINISHED;
	}

	public static void loadModules(FMLPreInitializationEvent event){
		ASMDataTable asmDataTable = event.getAsmData();
		Map<String, List<Module>> modules = ModuleHelper.getModules(asmDataTable);

		stage = Stage.SETUP;
		configureModules(modules);

		for(IModuleContainer container : containers.values()) {
			for (Module module : container.getLoadedModules()) {
				Log.debug("Setup API Start: {}", module);
				module.setupAPI();
				Log.debug("Setup API Complete: {}", module);
			}
		}

		stage = Stage.SETUP_DISABLED;
		for(IModuleContainer container : containers.values()) {
			for (Module module : container.getUnloadedModules()) {
				Log.debug("Disabled-Setup Start: {}", module);
				module.disabledSetupAPI();
				Log.debug("Disabled-Setup Complete: {}", module);
			}
		}
	}

	private static boolean isEnabled(Configuration config, Module module) {
		BinnieModule info = module.getClass().getAnnotation(BinnieModule.class);

		String comment = I18n.translateToLocal(info.unlocalizedDescription());
		Set<String> dependencies = module.getDependencyUids();
		if(!dependencies.isEmpty()){
			Iterator<String> iDependencies = dependencies.iterator();

			StringBuilder builder = new StringBuilder(comment);
			builder.append("\n");
			builder.append("Dependencies: [ ");
			builder.append(iDependencies.next());
			while(iDependencies.hasNext()){
				String uid = iDependencies.next();
				builder.append(", ").append(uid);
			}
			builder.append(" ]");
			comment = builder.toString();
		}
		Property prop = config.get(CONFIG_CATEGORY, info.moduleID(), true, comment);
		boolean enabled = prop.getBoolean();

		if (!enabled) {
			IModuleContainer container = containers.get(info.moduleContainerID());
			if(container != null){
				container.disableModule(info.moduleID());
			}
		}

		return enabled;
	}
}
