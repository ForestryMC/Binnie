package binnie.core.modules;

import net.minecraftforge.event.RegistryEvent;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import forestry.api.core.ForestryAPI;

import binnie.core.AbstractMod;

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

	@SubscribeEvent
	@SuppressWarnings("unchecked")
	public void onRegister(RegistryEvent.Register event) {
		container.onObjectRegistration(event);
	}
}
