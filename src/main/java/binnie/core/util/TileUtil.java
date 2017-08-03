package binnie.core.util;

import javax.annotation.Nullable;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

import net.minecraftforge.common.capabilities.Capability;

public class TileUtil {
	@Nullable
	public static <T extends TileEntity> T getTile(IBlockAccess world, BlockPos pos, Class<T> tileClass) {
		TileEntity tileEntity;
		if (world instanceof ChunkCache) {
			ChunkCache chunkCache = (ChunkCache) world;
			tileEntity = chunkCache.getTileEntity(pos, Chunk.EnumCreateEntityType.CHECK);
		} else {
			tileEntity = world.getTileEntity(pos);
		}
		if (tileClass.isInstance(tileEntity)) {
			return tileClass.cast(tileEntity);
		} else {
			return null;
		}
	}

	@Nullable
	public static <T> T getCapability(World world, BlockPos pos, Capability<T> capability, EnumFacing facing) {
		TileEntity tileEntity = world.getTileEntity(pos);
		if (tileEntity != null && tileEntity.hasCapability(capability, facing)) {
			return tileEntity.getCapability(capability, facing);
		}
		return null;
	}
}
