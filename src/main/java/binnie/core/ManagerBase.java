package binnie.core;

import binnie.Binnie;

public abstract class ManagerBase implements IInitializable {
    public ManagerBase() {
        Binnie.Managers.add(this);
    }

    @Override
    public void preInit() {
    }

    @Override
    public void init() {
    }

    @Override
    public void postInit() {
    }
}
