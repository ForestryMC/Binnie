package binnie.core.machines;

import javax.annotation.Nullable;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.Binnie;
import binnie.core.machines.base.TileEntityMachineBase;
import binnie.core.machines.component.IInteraction;
import binnie.core.network.INetworkedEntity;
import binnie.core.network.packet.PacketPayload;

public class TileEntityMachine extends TileEntityMachineBase implements INetworkedEntity {
	private Machine machine;

	public TileEntityMachine(final MachinePackage pack) {
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
	public void readFromNBT(final NBTTagCompound nbtTagCompound) {
		super.readFromNBT(nbtTagCompound);
		if(!nbtTagCompound.hasKey("sync")) {
			final String name = nbtTagCompound.getString("name");
			final String group = nbtTagCompound.getString("group");
			final MachinePackage pack = Binnie.MACHINE.getPackage(group, name);
			if (pack == null) {
				this.invalidate();
				return;
			}
			this.setMachine(pack);
			this.getMachine().readFromNBT(nbtTagCompound);
		}
	}

	@Override
	public NBTTagCompound writeToNBT(final NBTTagCompound nbtTagCompound2) {
		NBTTagCompound nbtTagCompound = super.writeToNBT(nbtTagCompound2);
		final String name = this.machine.getPackage().getUID();
		final String group = this.machine.getPackage().getGroup().getUID();
		nbtTagCompound.setString("group", group);
		nbtTagCompound.setString("name", name);
		this.getMachine().writeToNBT(nbtTagCompound);
		return nbtTagCompound;
	}

	@Override
	public void writeToPacket(final PacketPayload payload) {
		this.machine.writeToPacket(payload);
	}

	@Override
	public void readFromPacket(final PacketPayload payload) {
		this.machine.readFromPacket(payload);
	}

	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) {
		return super.shouldRefresh(world, pos, oldState, newSate);
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
		machine.syncToNBT(syncCompound);
		compound.setTag("sync", syncCompound);
		return compound;
	}

	@Override
	public void handleUpdateTag(NBTTagCompound tag) {
		super.handleUpdateTag(tag);
		NBTTagCompound syncCompound = tag.getCompoundTag("sync");
		machine.syncFromNBT(syncCompound);
	}

	public Machine getMachine() {
		return this.machine;
	}

	public void setMachine(final MachinePackage pack) {
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
