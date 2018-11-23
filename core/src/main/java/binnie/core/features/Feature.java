package binnie.core.features;

import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistryEntry;

import binnie.core.AbstractMod;

/**
 * A feature can be used to provide an game object like a block, an item or an fluid. There are different implementations
 * of this class for every game objects.
 * <p>
 * Features are automatically loaded by the modules if you annotate the class that contains the public static final fields.
 * Events like {@link #register(RegistryEvent.Register)} and {@link #create()} are automatically fired by modules.
 *
 * @see FeatureBlock
 * @see FeatureItem
 * @see binnie.core.liquid.FluidType
 * @see binnie.core.machines.MachineGroup
 */
public abstract class Feature {

	protected final IFeatureRegistry registry;
	protected final String identifier;
	protected final List<Feature> children = new ArrayList<>();
	private boolean created = false;

	public Feature(IFeatureRegistry registry, String identifier) {
		this.registry = registry;
		this.identifier = identifier;
	}

	protected void addChild(Feature child) {
		this.children.add(child);
	}

	/**
	 * Receives an {@link RegistryEvent.Register} event every time a event of this type gets fired.
	 *
	 * @param event The registry event.
	 * @param <T> The type of the registry entry.
	 */
	public final <T extends IForgeRegistryEntry<T>> void onRegister(RegistryEvent.Register<T> event) {
		register(event);
		for (Feature child : children) {
			child.onRegister(event);
		}
	}

	public <T extends IForgeRegistryEntry<T>> void afterRegistration(RegistryEvent.Register<T> event) {
	}

	protected <T extends IForgeRegistryEntry<T>> void register(RegistryEvent.Register<T> event) {
		//Not used by the fluids
	}

	public String getIdentifier() {
		return identifier;
	}

	/**
	 * Called at the pre init phase of the mod to create the content that this feature provides like the block or item.
	 */
	public final void createContent() {
		Preconditions.checkArgument(!created, "The feature %s can not be created twice.", identifier);
		create();
		created = true;
		for (Feature child : children) {
			child.createContent();
		}
	}

	public boolean isEnabled() {
		return registry.isEnabled();
	}

	protected void create() {

	}

	public AbstractMod getMod() {
		return registry.getMod();
	}
}
