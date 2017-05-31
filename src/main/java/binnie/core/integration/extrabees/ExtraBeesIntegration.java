package binnie.core.integration.extrabees;

import binnie.core.IInitializable;
import com.google.common.base.Preconditions;
import forestry.api.apiculture.IAlleleBeeSpecies;
import forestry.api.genetics.AlleleManager;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class ExtraBeesIntegration implements IInitializable {

	private static final boolean loaded;
	public static IAlleleBeeSpecies water, rock, basalt;
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
		water = getExtraBeesSpecies("water");
		rock = getExtraBeesSpecies("rock");
		basalt = getExtraBeesSpecies("basalt");
		hive = Preconditions.checkNotNull(ForgeRegistries.BLOCKS.getValue(new ResourceLocation("extrabees", "hive")));
		dictionary = Preconditions.checkNotNull(ForgeRegistries.ITEMS.getValue(new ResourceLocation("genetics", "dictionary")));
	}

	@Override
	public void postInit() {

	}

	private IAlleleBeeSpecies getExtraBeesSpecies(String species) {
		return Preconditions.checkNotNull((IAlleleBeeSpecies) AlleleManager.alleleRegistry.getAllele("extrabees.species." + species));
	}
}
