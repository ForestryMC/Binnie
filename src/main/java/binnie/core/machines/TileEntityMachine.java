package binnie.core.machines;

import binnie.Binnie;
import binnie.core.machines.base.TileEntityMachineBase;
import binnie.core.machines.component.IInteraction;
import binnie.core.network.INetworkedEntity;
import binnie.core.network.packet.PacketPayload;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;

public class TileEntityMachine extends TileEntityMachineBase implements INetworkedEntity {
    private Machine machine;

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (machine != null) {
            machine.onUpdate();
        }
    }

    public TileEntityMachine(MachinePackage pack) {
        setMachine(pack);
    }

    public TileEntityMachine() {
        // ignored
    }

    public void setMachine(MachinePackage pack) {
        if (pack != null) {
            machine = new Machine(pack, this);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound) {
        super.readFromNBT(nbtTagCompound);
        String name = nbtTagCompound.getString("name");
        String group = nbtTagCompound.getString("group");
        MachinePackage pack = Binnie.Machine.getPackage(group, name);
        if (pack == null) {
            invalidate();
            return;
        }

        setMachine(pack);
        getMachine().readFromNBT(nbtTagCompound);
    }

    @Override
    public void writeToNBT(NBTTagCompound nbtTagCompound) {
        super.writeToNBT(nbtTagCompound);
        String name = machine.getPackage().getUID();
        String group = machine.getPackage().getGroup().getUID();
        nbtTagCompound.setString("group", group);
        nbtTagCompound.setString("name", name);
        getMachine().writeToNBT(nbtTagCompound);
    }

    @Override
    public void writeToPacket(PacketPayload payload) {
        machine.writeToPacket(payload);
    }

    @Override
    public void readFromPacket(PacketPayload payload) {
        machine.readFromPacket(payload);
    }

    public Machine getMachine() {
        return machine;
    }

    public void onBlockDestroy() {
        if (getMachine() != null) {
            getMachine().onBlockDestroy();
        }
    }

    @Override
    public Packet getDescriptionPacket() {
        return (getMachine() != null) ? getMachine().getDescriptionPacket() : null;
    }

    @Override
    public void invalidate() {
        super.invalidate();
        for (IInteraction.Invalidation c : getMachine().getInterfaces(IInteraction.Invalidation.class)) {
            c.onInvalidation();
        }
    }

    @Override
    public void onChunkUnload() {
        super.onChunkUnload();
        for (IInteraction.ChunkUnload c : getMachine().getInterfaces(IInteraction.ChunkUnload.class)) {
            c.onChunkUnload();
        }
    }
}
