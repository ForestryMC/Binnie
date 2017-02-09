package binnie.core.machines;

import binnie.core.network.IOrdinaled;

import javax.annotation.Nullable;

public interface IMachineType extends IOrdinaled {
	@Nullable
	Class<? extends MachinePackage> getPackageClass();

	boolean isActive();
}
