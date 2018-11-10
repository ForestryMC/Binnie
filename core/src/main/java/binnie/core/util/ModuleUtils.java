package binnie.core.util;

import forestry.api.core.ForestryAPI;

public class ModuleUtils {
	public static boolean isModuleActive(String... moduleIDs) {
		for (String moduleID : moduleIDs) {
			if (!ForestryAPI.moduleManager.isModuleEnabled("forestry", moduleID)) {
				return false;
			}
		}
		return true;
	}
}
