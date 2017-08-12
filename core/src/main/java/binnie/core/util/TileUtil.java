package binnie.core.util;

import javax.annotation.Nullable;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import net.minecraftforge.common.capabilities.Capability;

public class TileUtil {
	@Nullable
	public static <T> T getTile(IBlockAccess world, BlockPos pos, Class<T> tileClass) {
		TileEntity tileEntity = world.getTileEntity(pos);
		if (tileClass.isInstance(tileEntity)) {
			return tileClass.cast(tileEntity);
		} else {
			return null;
		}
	}

	public interface ITileAction<T>  {
		void actOnTile(T tile);
	}

	/**
	 * Performs an {@link ITileAction} on a tile if the tile exists.
	 */
	public static <T> void actOnTile(IBlockAccess world, BlockPos pos, Class<T> tileClass, ITileAction<T> tileAction) {
		T tile = getTile(world, pos, tileClass);
		if (tile != null) {
			tileAction.actOnTile(tile);
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
