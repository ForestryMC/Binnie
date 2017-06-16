package binnie.core.machines;

import javax.annotation.Nullable;

import binnie.core.network.IOrdinaled;

public interface IMachineType extends IOrdinaled {
	@Nullable
	Class<? extends MachinePackage> getPackageClass();

	boolean isActive();
}
