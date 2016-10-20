package binnie.core.machines;

import binnie.core.network.IOrdinaled;

public interface IMachineType extends IOrdinaled {
    Class<? extends MachinePackage> getPackageClass();

    boolean isActive();
}
