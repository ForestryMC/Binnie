package binnie.core.features;

import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistryEntry;

import binnie.core.AbstractMod;

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
