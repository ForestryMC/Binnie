package binnie.core.machines;

import binnie.core.network.IOrdinaled;

import java.util.function.Supplier;

public interface IMachineType extends IOrdinaled {
	Supplier<MachinePackage> getSupplier();
}
