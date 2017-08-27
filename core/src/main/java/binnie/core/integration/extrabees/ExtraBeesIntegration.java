package binnie.core.integration.extrabees;

import com.google.common.base.Preconditions;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import forestry.api.apiculture.IAlleleBeeSpecies;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAlleleSpecies;

import binnie.core.IInitializable;

public class ExtraBeesIntegration implements IInitializable {

	private static final boolean loaded;
	private static IAlleleBeeSpecies water;
	private static IAlleleBeeSpecies rock;
	private static IAlleleBeeSpecies basalt;
	private static IAlleleBeeSpecies marble;
	private static Block hive;

	static {
		loaded = Loader.isModLoaded("extrabees");
	}

	public static boolean isLoaded() {
		return loaded;
	}

	@Override
	public void preInit() {

	}

	@Override
	public void init() {
		hive = Preconditions.checkNotNull(ForgeRegistries.BLOCKS.getValue(new ResourceLocation("extrabees", "hive")));
	}

	@Override
	public void postInit() {
		water = getExtraBeesSpecies("water");
		rock = getExtraBeesSpecies("rock");
		basalt = getExtraBeesSpecies("basalt");
		marble = getExtraBeesSpecies("marble");
	}

	public static ItemStack getHive(IAlleleSpecies speciesCurrent){
		if (speciesCurrent == ExtraBeesIntegration.water) {
			return new ItemStack(ExtraBeesIntegration.hive, 1, 0);
		}
		if (speciesCurrent == ExtraBeesIntegration.rock) {
			return new ItemStack(ExtraBeesIntegration.hive, 1, 1);
		}
		if (speciesCurrent == ExtraBeesIntegration.basalt) {
			return new ItemStack(ExtraBeesIntegration.hive, 1, 2);
		}
		if (speciesCurrent == ExtraBeesIntegration.marble) {
			return new ItemStack(ExtraBeesIntegration.hive, 1, 3);
		}
		return ItemStack.EMPTY;
	}

	private IAlleleBeeSpecies getExtraBeesSpecies(String species) {
		return Preconditions.checkNotNull((IAlleleBeeSpecies) AlleleManager.alleleRegistry.getAllele("extrabees.species." + species));
	}
}
