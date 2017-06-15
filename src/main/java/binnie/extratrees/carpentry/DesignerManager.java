package binnie.extratrees.carpentry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import binnie.extratrees.api.IDesignSystem;

public class DesignerManager {
	public static DesignerManager instance = new DesignerManager();

	List<IDesignSystem> systems;

	public DesignerManager() {
		this.systems = new ArrayList<>();
	}

	public void registerDesignSystem(final IDesignSystem system) {
		this.systems.add(system);
	}

	public Collection<IDesignSystem> getDesignSystems() {
		return this.systems;
	}

	public void addDesignSystem(final DesignSystem system) {
		this.systems.add(system);
	}
}
