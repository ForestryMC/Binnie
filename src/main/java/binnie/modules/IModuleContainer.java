package binnie.modules;

import java.io.File;
import java.util.Set;

import net.minecraftforge.common.config.Configuration;

public interface IModuleContainer {

	/**
	 * @return Unique identifier for the module container, no spaces!
	 */
	String getID();

	/**
	 * @return The config folder of this container.
	 */
	File getConfigFolder();

	Set<String> getEnabledModules();

	void enableModule(String uid);

	boolean isModuleEnabled(String moduleID);

	Set<Module> getLoadedModules();

	Set<Module> getUnloadedModules();

	boolean isAvailable();

	Configuration getModulesConfig();

	Configuration getMainConfig();
}
