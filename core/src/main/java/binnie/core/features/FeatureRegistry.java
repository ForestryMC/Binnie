package binnie.core.features;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistryEntry;

import forestry.api.core.ForestryAPI;

import binnie.core.AbstractMod;
import binnie.core.features.integration.ModItem;
import binnie.core.liquid.FluidType;
import binnie.core.machines.IMachineType;
import binnie.core.machines.MachineGroup;

public class FeatureRegistry implements IFeatureRegistry {
	private final AbstractMod mod;
	private final String moduleID;
	private final Map<String, Feature> masterFeatures = new LinkedHashMap<>();

	public FeatureRegistry(AbstractMod mod, String moduleID) {
		this.mod = mod;
		this.moduleID = moduleID;
	}

	public <T extends IForgeRegistryEntry<T>> void onRegister(RegistryEvent.Register<T> event) {
		masterFeatures.values().forEach(feature -> feature.onRegister(event));
	}

	public <T extends IForgeRegistryEntry<T>> void afterRegistration(RegistryEvent.Register<T> event) {
		masterFeatures.values().forEach(feature -> feature.afterRegistration(event));
	}

	@Override
	@Nullable
	public <F extends Feature> F getFeature(String identifier, Class<? extends F> featureClass) {
		Optional<Feature> optionalFeature = getFeature(identifier);
		if (optionalFeature.isPresent()) {
			Feature feature = optionalFeature.get();
			if (featureClass.isInstance(feature)) {
				return featureClass.cast(feature);
			}
		}
		return null;
	}

	@Override
	public Optional<Feature> getFeature(String identifier) {
		return Optional.ofNullable(masterFeatures.get(identifier));
	}

	@Override
	public <F extends Feature> F addFeature(F feature) {
		masterFeatures.put(feature.identifier, feature);
		return feature;
	}

	@Override
	public Collection<Feature> getFeatures() {
		return masterFeatures.values();
	}

	@Override
	public <B extends Block, I extends ItemBlock> FeatureBlock<B, I> createBlock(String identifier, Supplier<B> blockSupplier) {
		return addFeature(new FeatureBlock<>(this, identifier, blockSupplier, null));
	}

	@Override
	public <B extends Block, I extends ItemBlock> FeatureBlock<B, I> createBlock(String identifier, Supplier<B> blockSupplier, Function<B, I> itemSupplier) {
		return addFeature(new FeatureBlock<>(this, identifier, blockSupplier, itemSupplier));
	}

	@Override
	public <I extends Item> FeatureItem<I> createItem(String identifier, Supplier<I> itemSupplier) {
		return addFeature(new FeatureItem<>(this, identifier, itemSupplier));
	}

	@Override
	public <I extends Item> ModItem<I> modItem(String modId, String identifier) {
		return addFeature(new ModItem<>(this, modId, identifier));
	}

	@Override
	public MachineGroup createMachine(String groupUID, String identifier, IMachineType[] types) {
		return addFeature(new MachineGroup(this, groupUID, identifier, types));
	}

	@Override
	public FluidType createFluid(String identifier, String unlocalizedName, int color) {
		return addFeature(new FluidType(this, identifier, unlocalizedName, color));
	}

	@Override
	public AbstractMod getMod() {
		return mod;
	}

	@Override
	public String getModId() {
		return mod.getModId();
	}

	@Override
	public String getModuleId() {
		return moduleID;
	}

	@Override
	public boolean isEnabled() {
		return ForestryAPI.moduleManager.isModuleEnabled(getModId(), getModuleId());
	}
}
