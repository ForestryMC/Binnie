package binnie.core.features;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

import binnie.core.AbstractMod;
import binnie.core.features.integration.ModItem;
import binnie.core.liquid.FluidType;
import binnie.core.machines.IMachineType;
import binnie.core.machines.MachineGroup;

public interface IFeatureRegistry {

	<F extends Feature> F addFeature(F feature);

	@Nullable
	<F extends Feature> F getFeature(String identifier, Class<? extends F> featureClass);

	Optional<Feature> getFeature(String identifier);

	Collection<Feature> getFeatures();

	<B extends Block, I extends ItemBlock> FeatureBlock<B, I> createBlock(String identifier, Supplier<B> blockSupplier);

	<B extends Block, I extends ItemBlock> FeatureBlock<B, I> createBlock(String identifier, Supplier<B> blockSupplier, Function<B, I> itemSupplier);

	<I extends Item> FeatureItem<I> createItem(String identifier, Supplier<I> itemSupplier);

	<I extends Item> ModItem<I> modItem(String modId, String identifier);

	MachineGroup createMachine(String groupUID, String identifier, IMachineType[] types);

	FluidType createFluid(String identifier, String unlocalizedName, int color);

	AbstractMod getMod();

	String getModId();

	String getModuleId();

	boolean isEnabled();
}
