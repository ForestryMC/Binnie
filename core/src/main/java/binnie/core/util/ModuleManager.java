package binnie.core.util;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import forestry.api.core.ForestryAPI;
import forestry.api.modules.IModuleContainer;

import binnie.core.features.FeatureProvider;

import mezz.jei.util.Log;

public class ModuleManager {
	private static final Map<String, IModuleContainer> containers = new LinkedHashMap<>();

	public static boolean isModuleEnabled(String containerID, String moduleID) {
		return ForestryAPI.moduleManager.isModuleEnabled(containerID, moduleID);
	}


	@SubscribeEvent
	public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
		for (IModuleContainer container : containers.values()) {
			if (!event.getModID().equals(container.getID())) {
				continue;
			}
			//container.onConfigChanged(event);
			break;
		}
	}

	public static void loadFeatures(ASMDataTable asmDataTable) {
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
}
