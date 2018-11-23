package binnie.design;

import javax.annotation.Nullable;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import binnie.core.block.TileEntityMetadata;
import binnie.design.api.IDesign;
import binnie.design.api.IDesignMaterial;
import binnie.design.api.IDesignSystem;
import binnie.design.blocks.BlockDesign;
import binnie.design.blocks.DesignBlock;

public final class DesignHelper {
	private DesignHelper() {
	}

	public static DesignBlock getDesignBlock(IDesignSystem system, int meta) {
		int plankID1 = meta & 0xFF;
		int plankID2 = meta >> 8 & 0xFF;
		int tile = meta >> 16 & 0x3FF;
		int rotation = meta >> 26 & 0x3;
		int axis = meta >> 28 & 0x7;
		IDesignMaterial type1 = system.getMaterial(plankID1);
		IDesignMaterial type2 = system.getMaterial(plankID2);
		IDesign type3 = Design.getDesignManager().getDesign(tile);
		return new DesignBlock(system, type1, type2, type3, rotation, EnumFacing.VALUES[axis]);
	}

	public static int getBlockMetadata(IDesignSystem system, DesignBlock block) {
		int plank1 = system.getMaterialIndex(block.getPrimaryMaterial());
		int plank2 = system.getMaterialIndex(block.getSecondaryMaterial());
		int design = Design.getDesignManager().getDesignIndex(block.getDesign());
		int rotation = block.getRotation();
		int facing = block.getFacing().ordinal();
		return getMetadata(plank1, plank2, design, rotation, facing);
	}

	public static int getItemMetadata(IDesignSystem system, DesignBlock block) {
		int plank1 = system.getMaterialIndex(block.getPrimaryMaterial());
		int plank2 = system.getMaterialIndex(block.getSecondaryMaterial());
		int design = Design.getDesignManager().getDesignIndex(block.getDesign());
		return getMetadata(plank1, plank2, design, 0, EnumFacing.UP.ordinal());
	}

	public static ItemStack getItemStack(BlockDesign block, IDesignMaterial type1, IDesignMaterial type2, IDesign design) {
		int designIndex = Design.getDesignManager().getDesignIndex(design);
		int materialIndex1 = block.getDesignSystem().getMaterialIndex(type1);
		int materialIndex2 = block.getDesignSystem().getMaterialIndex(type2);
		return getItemStack(block, materialIndex1, materialIndex2, designIndex);
	}

	public static ItemStack getItemStack(BlockDesign block, int type1, int type2, int design) {
		return TileEntityMetadata.getItemStack(block, getMetadata(type1, type2, design, 0, EnumFacing.UP.ordinal()));
	}

	public static ItemStack getItemStack(BlockDesign blockC, DesignBlock block) {
		return getItemStack(blockC, block.getPrimaryMaterial(), block.getSecondaryMaterial(), block.getDesign());
	}

	public static int getMetadata(int plank1, int plank2, int design, int rotation, int facing) {
		return plank1 + (plank2 << 8) + (design << 16) + (rotation << 26) + (facing << 28);
	}

	public static boolean isValidPanelPlacement(IBlockAccess world, BlockPos pos, @Nullable EnumFacing facing) {
		if (facing == null) {
			return false;
		}
		pos = pos.offset(facing);
		IBlockState state = world.getBlockState(pos);
		return state.isSideSolid(world, pos, facing.getOpposite());
	}
}
