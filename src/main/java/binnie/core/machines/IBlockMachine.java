package binnie.core.machines;

import javax.annotation.Nullable;

interface IBlockMachine {
	@Nullable
	MachinePackage getPackage(int meta);

	String getMachineName(int meta);
}
