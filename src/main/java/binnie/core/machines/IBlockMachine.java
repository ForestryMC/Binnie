package binnie.core.machines;

interface IBlockMachine {
    MachinePackage getPackage(int meta);

    String getMachineName(int meta);
}
