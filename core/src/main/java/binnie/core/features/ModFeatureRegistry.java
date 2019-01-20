package binnie.core.features;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;

import java.util.HashMap;
import java.util.LinkedHashMap;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistryEntry;

import forestry.api.core.ForestryAPI;

import binnie.core.liquid.FluidType;
import binnie.core.liquid.IFluidDefinition;
import binnie.core.machines.MachineGroup;

public class ModFeatureRegistry {
	private static final HashMap<String, ModFeatureRegistry> registries = new LinkedHashMap<>();

	public static ModFeatureRegistry get(String modId) {
		return registries.computeIfAbsent(modId, ModFeatureRegistry::new);
	}

	private final HashMap<String, ModuleFeatures> modules = new LinkedHashMap<>();
	private final String modId;

	private ModFeatureRegistry(String modId) {
		this.modId = modId;
	}

	public static void fireEvent() {
		RegisterFeatureEvent event = new RegisterFeatureEvent();
		MinecraftForge.EVENT_BUS.post(event);
	}

	public void register(IModFeature feature) {
		modules.computeIfAbsent(feature.getModuleId(), key -> new ModuleFeatures()).register(feature);
	}

	public boolean isEnabled(IModFeature feature) {
		return ForestryAPI.moduleManager.isModuleEnabled(modId, feature.getModuleId());
	}

	public void createObjects() {
		for (FeatureType type : FeatureType.values()) {
			modules.values().forEach(features -> features.createObjects(type));
			MinecraftForge.EVENT_BUS.post(new FeatureCreationEvent(modId, type));
		}
	}

	public <T extends IForgeRegistryEntry<T>> void onRegister(RegistryEvent.Register<T> event) {
		for (ModuleFeatures features : modules.values()) {
			features.onRegister(event);
		}
	}

	private static class ModuleFeatures {
		private final HashMap<String, IModFeature> featureById = new LinkedHashMap<>();
		private final Multimap<FeatureType, IModFeature> featureByType = LinkedListMultimap.create();

		public void register(IModFeature feature) {
			featureById.put(feature.getIdentifier(), feature);
			featureByType.put(feature.getType(), feature);
		}

		public IModFeature getFeature(String identifier) {
			return featureById.get(identifier);
		}

		public void createObjects(FeatureType type) {
			for (IModFeature feature : featureByType.get(type)) {
				createObject(feature);
			}
		}

		@SuppressWarnings("unchecked")
		private void createObject(IModFeature feature) {
			if (!feature.isEnabled()) {
				return;
			}
			if (feature instanceof IBlockFeature) {
				IFeatureConstructor<Block> blockConstructor = feature.getConstructor();
				Block block = initObject(feature, ((IBlockFeature) feature).apply(blockConstructor.createObject()));
				((IBlockFeature) feature).setBlock(block);
				IFeatureConstructor<Item> constructor = ((IBlockFeature) feature).getItemConstructor();
				if (constructor != null) {
					Item item = initObject(feature, ((IBlockFeature) feature).apply(constructor.createObject()));
					if (item.getRegistryName() == null && block.getRegistryName() != null) {
						item.setRegistryName(block.getRegistryName());
					}
					((IBlockFeature) feature).setItem(item);
				}
			} else if (feature instanceof IItemFeature) {
				Item item = initObject(feature, ((IItemFeature) feature).apply((Item) feature.getConstructor().createObject()));
				((IItemFeature) feature).setItem(item);
			}
			if (feature instanceof IMachineFeature) {
				MachineGroup group = initObject(feature, ((IMachineFeature) feature).apply(((IMachineFeature) feature).getConstructor().createObject()));
				((IMachineFeature) feature).setGroup(group);
			}
			if (feature instanceof IFluidDefinition) {
				FluidType fluid = initObject(feature, ((IFluidDefinition) feature).apply(((IFluidDefinition) feature).getConstructor().createObject()));
				((IFluidDefinition) feature).setFluid(fluid);
			}

		}

		@SuppressWarnings("unchecked")
		private <O, F extends IModFeature<O>> O initObject(F feature, O object) {
			if (object instanceof IFeatureObject) {
				((IFeatureObject<F>) object).init(feature);
			}
			feature.init();
			if (object instanceof IChildFeature) {
				register((IModFeature) object);
			}
			return object;
		}

		public <T extends IForgeRegistryEntry<T>> void onRegister(RegistryEvent.Register<T> event) {
			for (FeatureType type : FeatureType.values()) {
				for (IModFeature feature : featureByType.get(type)) {
					feature.register(event);
				}
			}
		}

	}
}
