package binnie.core;

public abstract class ManagerBase implements IInitializable {
	public ManagerBase() {
		Binnie.MANAGERS.add(this);
	}
}
