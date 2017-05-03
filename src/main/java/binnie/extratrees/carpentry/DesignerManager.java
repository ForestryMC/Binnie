package binnie.extratrees.carpentry;

import binnie.extratrees.api.IDesignSystem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DesignerManager {
	public static DesignerManager instance;

	static {
		DesignerManager.instance = new DesignerManager();
	}

	List<IDesignSystem> systems;

	public DesignerManager() {
		systems = new ArrayList<>();
	}

	public void registerDesignSystem(IDesignSystem system) {
		systems.add(system);
	}

	public Collection<IDesignSystem> getDesignSystems() {
		return systems;
	}

	public void addDesignSystem(DesignSystem system) {
		systems.add(system);
	}
}
