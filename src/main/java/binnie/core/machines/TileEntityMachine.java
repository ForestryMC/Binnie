// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.machines;

import binnie.core.machines.component.IInteraction;
import net.minecraft.network.Packet;
import binnie.core.network.packet.PacketPayload;
import binnie.Binnie;
import net.minecraft.nbt.NBTTagCompound;
import binnie.core.network.INetworkedEntity;
import binnie.core.machines.base.TileEntityMachineBase;

public class TileEntityMachine extends TileEntityMachineBase implements INetworkedEntity
{
	private Machine machine;

	@Override
	public void updateEntity() {
		super.updateEntity();
		if (this.machine != null) {
			this.machine.onUpdate();
		}
	}

	@Override
	public boolean canUpdate() {
		return super.canUpdate();
	}

	public TileEntityMachine(final MachinePackage pack) {
		this.setMachine(pack);
	}

	public TileEntityMachine() {
	}

	public void setMachine(final MachinePackage pack) {
		if (pack != null) {
			this.machine = new Machine(pack, this);
		}
	}

	@Override
	public void readFromNBT(final NBTTagCompound nbtTagCompound) {
		super.readFromNBT(nbtTagCompound);
		final String name = nbtTagCompound.getString("name");
		final String group = nbtTagCompound.getString("group");
		final MachinePackage pack = Binnie.Machine.getPackage(group, name);
		if (pack == null) {
			this.invalidate();
			return;
		}
		this.setMachine(pack);
		this.getMachine().readFromNBT(nbtTagCompound);
	}

	@Override
	public void writeToNBT(final NBTTagCompound nbtTagCompound) {
		super.writeToNBT(nbtTagCompound);
		final String name = this.machine.getPackage().getUID();
		final String group = this.machine.getPackage().getGroup().getUID();
		nbtTagCompound.setString("group", group);
		nbtTagCompound.setString("name", name);
		this.getMachine().writeToNBT(nbtTagCompound);
	}

	@Override
	public void writeToPacket(final PacketPayload payload) {
		this.machine.writeToPacket(payload);
	}

	@Override
	public void readFromPacket(final PacketPayload payload) {
		this.machine.readFromPacket(payload);
	}

	public Machine getMachine() {
		return this.machine;
	}

	public void onBlockDestroy() {
		if (this.getMachine() != null) {
			this.getMachine().onBlockDestroy();
		}
	}

	@Override
	public final Packet getDescriptionPacket() {
		return (this.getMachine() != null) ? this.getMachine().getDescriptionPacket() : null;
	}

	@Override
	public void invalidate() {
		super.invalidate();
		for (final IInteraction.Invalidation c : this.getMachine().getInterfaces(IInteraction.Invalidation.class)) {
			c.onInvalidation();
		}
	}

	@Override
	public void onChunkUnload() {
		super.onChunkUnload();
		for (final IInteraction.ChunkUnload c : this.getMachine().getInterfaces(IInteraction.ChunkUnload.class)) {
			c.onChunkUnload();
		}
	}
}
