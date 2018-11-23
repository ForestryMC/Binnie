package binnie.core.machines;

import javax.annotation.Nullable;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.Binnie;
import binnie.core.machines.base.TileEntityMachineBase;
import binnie.core.machines.component.IInteraction;
import binnie.core.machines.inventory.ComponentInventorySlots;
import binnie.core.machines.inventory.IInventorySlots;
import binnie.core.machines.inventory.InventorySlot;
import binnie.core.network.INetworkedEntity;
import binnie.core.network.packet.PacketPayload;

public class TileEntityMachine extends TileEntityMachineBase implements INetworkedEntity, IInventorySlots {
	@Nullable
	private Machine machine;

	public TileEntityMachine(MachinePackage pack) {
		this.setMachine(pack);
	}

	public TileEntityMachine() {
	}

	@Override
	public void update() {
		super.update();
		if (this.machine != null) {
			this.machine.onUpdate();
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbtTagCompound) {
		super.readFromNBT(nbtTagCompound);
		if (!nbtTagCompound.hasKey("sync")) {
			String name = nbtTagCompound.getString("name");
			String group = nbtTagCompound.getString("group");
			MachinePackage pack = Binnie.MACHINE.getPackage(group, name);
			if (pack == null) {
				this.invalidate();
				return;
			}
			this.setMachine(pack);
			if (machine != null) {
				machine.readFromNBT(nbtTagCompound);
			}
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbtTagCompound2) {
		NBTTagCompound nbtTagCompound = super.writeToNBT(nbtTagCompound2);
		if (machine != null) {
			String name = this.machine.getPackage().getUID();
			String group = this.machine.getPackage().getGroup().getUID();
			nbtTagCompound.setString("group", group);
			nbtTagCompound.setString("name", name);
			machine.writeToNBT(nbtTagCompound);
		}
		return nbtTagCompound;
	}

	@Override
	public void writeToPacket(PacketPayload payload) {
		if (machine == null) {
			return;
		}
		this.machine.writeToPacket(payload);
	}

	@Override
	public void readFromPacket(PacketPayload payload) {
		if (machine == null) {
			return;
		}
		this.machine.readFromPacket(payload);
	}

	@Nullable
	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		return new SPacketUpdateTileEntity(this.getPos(), 0, getUpdateTag());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		super.onDataPacket(net, pkt);
		NBTTagCompound nbt = pkt.getNbtCompound();
		handleUpdateTag(nbt);
	}

	@Override
	public NBTTagCompound getUpdateTag() {
		NBTTagCompound compound = super.getUpdateTag();
		NBTTagCompound syncCompound = new NBTTagCompound();
		if (machine != null) {
			machine.syncToNBT(syncCompound);
		}
		compound.setTag("sync", syncCompound);
		return compound;
	}

	@Override
	public void handleUpdateTag(NBTTagCompound tag) {
		super.handleUpdateTag(tag);
		NBTTagCompound syncCompound = tag.getCompoundTag("sync");
		if (machine == null) {
			return;
		}
		machine.syncFromNBT(syncCompound);
	}

	@Nullable
	public Machine getMachine() {
		return this.machine;
	}

	public void setMachine(MachinePackage pack) {
		if (pack != null) {
			this.machine = new Machine(pack, this);
		}
	}

	public void onBlockDestroy() {
		if (this.getMachine() != null) {
			this.getMachine().onBlockDestroy();
		}
	}

	@Override
	public void invalidate() {
		super.invalidate();
		if (machine == null) {
			return;
		}
		for (IInteraction.Invalidation component : machine.getInterfaces(IInteraction.Invalidation.class)) {
			component.onInvalidation();
		}
	}

	@Override
	public void onChunkUnload() {
		super.onChunkUnload();
		if (machine == null) {
			return;
		}
		for (IInteraction.ChunkUnload component : machine.getInterfaces(IInteraction.ChunkUnload.class)) {
			component.onChunkUnload();
		}
	}

	@Nullable
	@Override
	public InventorySlot getSlot(int index) {
		Machine machine = getMachine();
		if (machine == null) {
			return null;
		}
		if (!machine.hasComponent(ComponentInventorySlots.class)) {
			return null;
		}
		ComponentInventorySlots inventorySlots = machine.getComponent(ComponentInventorySlots.class);
		return inventorySlots.getSlot(index);
	}
}
