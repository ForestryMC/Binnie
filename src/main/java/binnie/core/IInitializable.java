package binnie.core;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public interface IInitializable {
	void preInit();

	void init();

	void postInit();

	default void preInit(FMLPreInitializationEvent event){
		preInit();
	}

	default void init(FMLInitializationEvent event){
		init();
	}

	default void postInit(FMLPostInitializationEvent event){
		postInit();
	}

	/**
	 * For compat modules if the mod is not loaded the module will not be registered.
	 */
	default boolean isAvailable(){
		return true;
	}

	default void setupAPI(){

	}

	default void disabledSetupAPI(){

	}
}
