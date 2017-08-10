package binnie.extratrees.integration.crafttweaker;

import com.google.common.collect.ImmutableSet;

import java.util.Set;

import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Optional;

import binnie.core.Constants;
import binnie.extratrees.integration.crafttweaker.handlers.BreweryRecipeHandler;
import binnie.extratrees.integration.crafttweaker.handlers.DistilleryRecipeHandler;
import binnie.extratrees.integration.crafttweaker.handlers.FruitPressRecipeHandler;
import binnie.extratrees.integration.crafttweaker.handlers.LumbermillRecipeHandler;
import binnie.extratrees.modules.ExtraTreesModuleUIDs;
import binnie.core.modules.BinnieModule;
import binnie.core.modules.Module;
import crafttweaker.CraftTweakerAPI;

@BinnieModule(moduleID = ExtraTreesModuleUIDs.CRAFT_TWEAKER, moduleContainerID = Constants.EXTRA_TREES_MOD_ID, name = "Craft Tweaker", unlocalizedDescription = "extratrees.module.crafttweaker")
public class CraftTweakerModule extends Module {
	public static final String MOD_ID = "crafttweaker";

	@Override
	public void init() {
		initCT();
	}

	@Optional.Method(modid = "crafttweaker")
	private void initCT(){
		CraftTweakerAPI.registerClass(BreweryRecipeHandler.class);
		CraftTweakerAPI.registerClass(DistilleryRecipeHandler.class);
		CraftTweakerAPI.registerClass(FruitPressRecipeHandler.class);
		CraftTweakerAPI.registerClass(LumbermillRecipeHandler.class);
	}

	@Override
	public boolean isAvailable() {
		return Loader.isModLoaded(MOD_ID);
	}

	@Override
	public Set<String> getDependencyUids() {
		return ImmutableSet.of(ExtraTreesModuleUIDs.MACHINES);
	}
}
