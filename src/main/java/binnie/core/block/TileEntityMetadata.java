package binnie.core.block;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class TileEntityMetadata extends TileEntity {
    private int meta;
    private boolean droppedBlock;

    public TileEntityMetadata() {
        this.droppedBlock = false;
    }

    @Override
    public boolean receiveClientEvent(final int par1, final int par2) {
        if (par1 == 42) {
            this.meta = par2;
            //TODO UPDATE
            //this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
        }
        return true;
    }

    @Override
    public void readFromNBT(final NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        this.meta = nbt.getInteger("meta");
    }

    @Override
    public NBTTagCompound writeToNBT(final NBTTagCompound nbt) {
        NBTTagCompound nbt2 = super.writeToNBT(nbt);
        nbt2.setInteger("meta", this.meta);
        return nbt2;
    }

//	@Override
//	public boolean canUpdate() {
//		return false;
//	}

    public int getTileMetadata() {
        return this.meta;
    }

    public void setTileMetadata(final int meta, final boolean notify) {
        if (this.meta != meta) {
            this.meta = meta;
            if (notify) {
                //TODO UPDATE
                //this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
            }
        }
    }

    //TODO update packet
//	@Override
//	public Packet getDescriptionPacket() {
//		return BinnieCore.instance.getNetworkWrapper().getPacketFrom(new MessageMetadata(this.xCoord, this.yCoord, this.zCoord, this.meta).GetMessage());
//	}

    public static TileEntityMetadata getTile(final IBlockAccess world, final BlockPos pos) {
        final TileEntity tile = world.getTileEntity(pos);
        if (!(tile instanceof TileEntityMetadata)) {
            return null;
        }
        return (TileEntityMetadata) tile;
    }

    public static ItemStack getItemStack(final Block block, final int damage) {
        final ItemStack item = new ItemStack(block, 1, 0);
        setItemDamage(item, damage);
        return item;
    }

    public static void setItemDamage(final ItemStack item, final int i) {
        item.setItemDamage((i < 16387) ? i : 16387);
        final NBTTagCompound tag = new NBTTagCompound();
        tag.setInteger("meta", i);
        item.setTagCompound(tag);
    }

    public static int getItemDamage(final ItemStack item) {
        if (item.hasTagCompound() && item.getTagCompound().hasKey("meta")) {
            return item.getTagCompound().getInteger("meta");
        }
        return item.getItemDamage();
    }

    public static int getTileMetadata(final IBlockAccess world, final BlockPos pos) {
        final TileEntityMetadata tile = getTile(world, pos);
        return (tile == null) ? 0 : tile.getTileMetadata();
    }

    public boolean hasDroppedBlock() {
        return this.droppedBlock;
    }

    public void dropBlock() {
        this.droppedBlock = true;
    }
}
