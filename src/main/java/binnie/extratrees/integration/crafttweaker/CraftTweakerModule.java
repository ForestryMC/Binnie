package binnie.extratrees.integration.crafttweaker;

import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Optional;

import binnie.core.IInitializable;
import binnie.extratrees.integration.crafttweaker.handlers.BreweryRecipeHandler;
import binnie.extratrees.integration.crafttweaker.handlers.DistilleryRecipeHandler;
import binnie.extratrees.integration.crafttweaker.handlers.FruitPressRecipeHandler;
import binnie.extratrees.integration.crafttweaker.handlers.LumbermillRecipeHandler;
import minetweaker.MineTweakerAPI;

public class CraftTweakerModule implements IInitializable {
	public static final String MOD_ID = "crafttweaker";

	public void init(){
		initCT();
	}

	@Override
	public void preInit() {
	}

	@Override
	public void postInit() {
	}

	@Optional.Method(modid = "crafttweaker")
	private void initCT(){
		MineTweakerAPI.registerClass(BreweryRecipeHandler.class);
		MineTweakerAPI.registerClass(DistilleryRecipeHandler.class);
		MineTweakerAPI.registerClass(FruitPressRecipeHandler.class);
		MineTweakerAPI.registerClass(LumbermillRecipeHandler.class);
	}

	@Override
	public boolean isAvailable() {
		return Loader.isModLoaded(MOD_ID);
	}
}
