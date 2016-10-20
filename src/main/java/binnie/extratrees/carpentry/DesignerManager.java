package binnie.extratrees.carpentry;

import binnie.extratrees.api.IDesignSystem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DesignerManager {
    public static DesignerManager instance;
    List<IDesignSystem> systems;

    public DesignerManager() {
        this.systems = new ArrayList<IDesignSystem>();
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

    static {
        DesignerManager.instance = new DesignerManager();
    }
}
