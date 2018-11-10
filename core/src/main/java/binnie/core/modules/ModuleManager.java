package binnie.core.modules;

import java.util.LinkedHashMap;
import java.util.Map;

import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import forestry.api.core.ForestryAPI;

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
			container.onConfigChanged(event);
			break;
		}
	}
}
