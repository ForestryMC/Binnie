package binnie.extratrees.api.carpentry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import binnie.extratrees.api.IDesignSystem;

public final class DesignerManager {
	public static DesignerManager instance = new DesignerManager();

	private final List<IDesignSystem> systems;

	private DesignerManager() {
		this.systems = new ArrayList<>();
	}

	public void registerDesignSystem(final IDesignSystem system) {
		this.systems.add(system);
	}

	public Collection<IDesignSystem> getDesignSystems() {
		return this.systems;
	}
}
