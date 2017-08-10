package binnie.core;

public abstract class ManagerBase implements IInitializable {
	public ManagerBase() {
		Binnie.MANAGERS.add(this);
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
