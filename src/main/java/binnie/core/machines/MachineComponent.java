package binnie.core.machines;

import binnie.Binnie;
import binnie.core.network.packet.MachinePayload;
import forestry.api.core.INBTTagable;
import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.NBTTagCompound;

public class MachineComponent implements INBTTagable {
    private IMachine machine;

    public MachineComponent(IMachine machine) {
        setMachine(machine);
        machine.addComponent(this);
    }

    public void setMachine(IMachine machine) {
        this.machine = machine;
    }

    public IMachine getMachine() {
        return machine;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbttagcompound) {
        // ignored
    }

    @Override
    public void writeToNBT(NBTTagCompound nbttagcompound) {
        // ignored
    }

    public void onUpdate() {
        // ignored
    }

    public Class[] getComponentInterfaces() {
        return Binnie.Machine.getComponentInterfaces(getClass());
    }

    public void onInventoryUpdate() {
        // ignored
    }

    public MachinePayload getPayload() {
        return new MachinePayload(Binnie.Machine.getNetworkID(getClass()));
    }

    public void recieveData(MachinePayload payload) {}

    public MachineUtil getUtil() {
        return getMachine().getMachineUtil();
    }

    public void onDestruction() {
        // desctruction
    }

    public IInventory getInventory() {
        return getMachine().getInterface(IInventory.class);
    }
}
