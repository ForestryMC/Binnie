package binnie.core.block;

import binnie.core.BinnieCore;
import binnie.core.network.packet.MessageMetadata;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;

public class TileEntityMetadata extends TileEntity {
	private int meta;
	private boolean droppedBlock;

	public TileEntityMetadata() {
		this.droppedBlock = false;
	}

	@Override
	public boolean receiveClientEvent(final int par1, final int meta) {
		// TODO fix magic const
		if (par1 == 42) {
			this.meta = meta;
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		}
		return true;
	}

	@Override
	public void readFromNBT(final NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		meta = nbt.getInteger("meta");
	}

	@Override
	public void writeToNBT(final NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("meta", this.meta);
	}

	@Override
	public boolean canUpdate() {
		return false;
	}

	public int getTileMetadata() {
		return meta;
	}

	public void setTileMetadata(final int meta, final boolean notify) {
		if (this.meta == meta) {
			return;
		}

		this.meta = meta;
		if (notify) {
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		}
	}

	@Override
	public Packet getDescriptionPacket() {
		MessageMetadata message = new MessageMetadata(xCoord, yCoord, zCoord, meta);
		return BinnieCore.instance.getNetworkWrapper().getPacketFrom(message.getMessage());
	}

	public static TileEntityMetadata getTile(final IBlockAccess world, final int x, final int y, final int z) {
		final TileEntity tile = world.getTileEntity(x, y, z);
		if (tile instanceof TileEntityMetadata) {
			return (TileEntityMetadata) tile;
		}
		return null;
	}

	public static ItemStack getItemStack(final Block block, final int damage) {
		final NBTTagCompound tag = new NBTTagCompound();
		tag.setInteger("meta", damage);

		final ItemStack item = new ItemStack(block, 1, 0);
		item.setItemDamage((damage < 16387) ? damage : 16387);
		item.setTagCompound(tag);
		return item;
	}

	public static int getItemDamage(final ItemStack item) {
		if (item.hasTagCompound() && item.getTagCompound().hasKey("meta")) {
			return item.getTagCompound().getInteger("meta");
		}
		return item.getItemDamage();
	}

	public static int getTileMetadata(final IBlockAccess world, final int x, final int y, final int z) {
		final TileEntityMetadata tile = getTile(world, x, y, z);
		return (tile == null) ? 0 : tile.getTileMetadata();
	}

	public boolean hasDroppedBlock() {
		return droppedBlock;
	}

	public void dropBlock() {
		droppedBlock = true;
	}
}
