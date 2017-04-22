package binnie.core.machines;

import binnie.core.network.*;

public interface IMachineType extends IOrdinaled {
	Class<? extends MachinePackage> getPackageClass();

	boolean isActive();
}
