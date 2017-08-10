package binnie.core.modules;

import com.google.common.collect.ImmutableList;

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
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ModuleManager {
	private static Map<String, IModuleContainer> containers = new LinkedHashMap<>();
	private static final String CONFIG_CATEGORY = "modules";
	private static boolean initialized = false;

	public static boolean isModuleEnabled(String containerID, String moduleID){
		IModuleContainer container = containers.get(containerID);
		if(container == null){
			return false;
		}
		return container.isModuleEnabled(moduleID);
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
			Configuration config = container.getModulesConfig();

			config.load();
			config.addCustomCategoryComment(CONFIG_CATEGORY, "Disabling these modules can greatly change how the mod functions.\n"
				+ "Your mileage may vary, please report any issues.");

			Module coreModule = getCoreModule(containerModules, containerID);
			containerModules.remove(coreModule);
			containerModules.add(0, coreModule);


			Iterator<Module> iterator = containerModules.iterator();
			while (iterator.hasNext()) {
				Module module = iterator.next();
				if(!container.isAvailable()){
					iterator.remove();
					Log.info("Module disabled: {}", module);
					continue;
				}
				if (module.canBeDisabled()) {
					if (!isModuleEnabled(config, module)) {
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
			Configuration config = container.getModulesConfig();

			if (config.hasChanged()) {
				config.save();
			}
		}

		Locale.setDefault(locale);
	}

	private static Module getCoreModule(List<Module> modules, String containerID) {
		for (Module module : modules) {
			BinnieModule info = module.getClass().getAnnotation(BinnieModule.class);
			if (info.coreModule()) {
				return module;
			}
		}
		throw new IllegalStateException("Could not find core module for the container " + containerID);
	}

	public static void loadModules(FMLPreInitializationEvent event){
		if(initialized){
			return;
		}
		initialized = true;
		ASMDataTable asmDataTable = event.getAsmData();
		Map<String, List<Module>> modules = ModuleHelper.getModules(asmDataTable);

		configureModules(modules);

		for(IModuleContainer container : containers.values()) {
			for (Module module : container.getLoadedModules()) {
				Log.debug("Setup API Start: {}", module);
				module.setupAPI();
				Log.debug("Setup API Complete: {}", module);
			}
		}

		for(IModuleContainer container : containers.values()) {
			for (Module module : container.getUnloadedModules()) {
				Log.debug("Disabled-Setup Start: {}", module);
				module.disabledSetupAPI();
				Log.debug("Disabled-Setup Complete: {}", module);
			}
		}
	}

	private static boolean isModuleEnabled(Configuration config, Module module) {
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
		return prop.getBoolean();
	}
}
