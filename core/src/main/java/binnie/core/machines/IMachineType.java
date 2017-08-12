package binnie.core.machines;

import java.util.function.Supplier;

import binnie.core.network.IOrdinaled;

public interface IMachineType extends IOrdinaled {
	Supplier<MachinePackage> getSupplier();
}
