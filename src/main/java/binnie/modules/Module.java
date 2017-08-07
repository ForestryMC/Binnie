package binnie.modules;

import java.util.Collections;
import java.util.Set;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import binnie.core.IInitializable;

public abstract class Module implements IInitializable{

	public void registerItemsAndBlocks(){
	}

	public String getFailMessage(){
		return "";
	}

	public Set<String> getDependencyUids(){
		return Collections.emptySet();
	}

	public boolean canBeDisabled(){
		return true;
	}

	public boolean isAvailable(){
		return true;
	}

	public void setupAPI(){
	}

	public void disabledSetupAPI(){
	}

	public void preInit(FMLPreInitializationEvent event){
		preInit();
	}

	public void init(FMLInitializationEvent event){
		init();
	}

	public void postInit(FMLPostInitializationEvent event){
		postInit();
	}

	@Override
	public void preInit() {
	}

	@Override
	public void init() {
	}

	@Override
	public void postInit() {
	}
}
