package binnie.core.modules;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;

import java.io.File;
import java.util.Set;

public interface IModuleContainer {

	/**
	 * @return Unique identifier for the module container, no spaces!
	 */
	String getID();

	/**
	 * @return The config folder of this container.
	 */
	File getConfigFolder();

	void enableModule(String uid);

	boolean isModuleEnabled(String moduleID);

	Set<Module> getLoadedModules();

	Set<Module> getUnloadedModules();

	boolean isAvailable();

	Configuration getModulesConfig();

	void registerConfigHandler(IConfigHandler handler);

	void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event);
}
