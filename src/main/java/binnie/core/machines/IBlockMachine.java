package binnie.core.machines;

import javax.annotation.Nullable;

interface IBlockMachine {
	@Nullable
	MachinePackage getPackage(final int meta);

	String getMachineName(final int meta);
}
