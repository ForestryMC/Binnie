package binnie.core;

import binnie.Binnie;

public abstract class ManagerBase implements IInitializable {
    public ManagerBase() {
        Binnie.Managers.add(this);
    }

    @Override
    public void preInit() {
        // ignored
    }

    @Override
    public void init() {
        // ignored
    }

    @Override
    public void postInit() {
        // ignored
    }
}
