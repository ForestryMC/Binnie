package binnie.core.modules;

import net.minecraftforge.event.RegistryEvent;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import forestry.api.core.ForestryAPI;

import binnie.core.AbstractMod;
import binnie.core.features.IFeatureRegistry;

public abstract class ModuleProvider extends AbstractMod {
	protected final ModuleContainer container;

	public ModuleProvider() {
		super();
		this.container = new ModuleContainer(this);
		ForestryAPI.moduleManager.registerContainers(container);
	}

	public ModuleContainer getContainer() {
		return container;
	}

	@Override
	protected void preInitModules(FMLPreInitializationEvent event) {
		container.runPreInit(event);
	}

	@Override
	public void init(FMLInitializationEvent event) {
		super.init(event);
		container.runInit(event);
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
		super.postInit(event);
		container.runPostInit(event);
	}

	public IFeatureRegistry registry(String moduleID) {
		return container.getRegistry(moduleID);
	}

	@SubscribeEvent
	@SuppressWarnings("unchecked")
	public void onRegister(RegistryEvent.Register event) {
		container.onObjectRegistration(event);
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	@SuppressWarnings("unchecked")
	public void afterRegistration(RegistryEvent.Register event) {
		container.afterObjectRegistration(event);
	}
}
