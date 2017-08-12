package binnie.core;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public interface IInitializable {
	default void preInit() {

	}

	default void init() {

	}

	default void postInit() {

	}

	default void preInit(FMLPreInitializationEvent event){
		preInit();
	}

	default void init(FMLInitializationEvent event){
		init();
	}

	default void postInit(FMLPostInitializationEvent event){
		postInit();
	}
}
