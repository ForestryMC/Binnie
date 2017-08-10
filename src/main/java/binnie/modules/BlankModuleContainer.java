package binnie.modules;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import binnie.core.AbstractMod;

public abstract class BlankModuleContainer extends AbstractMod {
	protected final ModuleContainer container;

	public BlankModuleContainer() {
		super();
		this.container = new ModuleContainer(getModID(), this::isAvailable);
		ModuleManager.register(container);
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
}
