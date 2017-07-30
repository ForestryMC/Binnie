package binnie.core.integration.extrabees;

import com.google.common.base.Preconditions;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import forestry.api.apiculture.IAlleleBeeSpecies;
import forestry.api.genetics.AlleleManager;

import binnie.core.IInitializable;

public class ExtraBeesIntegration implements IInitializable {

	private static final boolean loaded;
	public static IAlleleBeeSpecies water, rock, basalt, marble;
	public static Block hive;
	public static Item dictionary;

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
		dictionary = Preconditions.checkNotNull(ForgeRegistries.ITEMS.getValue(new ResourceLocation("genetics", "dictionary")));
	}

	@Override
	public void postInit() {
		water = getExtraBeesSpecies("water");
		rock = getExtraBeesSpecies("rock");
		basalt = getExtraBeesSpecies("basalt");
		marble = getExtraBeesSpecies("marble");
	}

	private IAlleleBeeSpecies getExtraBeesSpecies(String species) {
		return Preconditions.checkNotNull((IAlleleBeeSpecies) AlleleManager.alleleRegistry.getAllele("extrabees.species." + species));
	}
}
