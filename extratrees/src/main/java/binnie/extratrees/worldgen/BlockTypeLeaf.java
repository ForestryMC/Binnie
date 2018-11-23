package binnie.extratrees.worldgen;

import javax.annotation.Nullable;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.mojang.authlib.GameProfile;

import forestry.api.world.ITreeGenData;

public class BlockTypeLeaf implements WorldGenBlockType {
	@Nullable
	private final GameProfile owner;

	public BlockTypeLeaf(@Nullable GameProfile owner) {
		this.owner = owner;
	}

	@Override
	public void setBlock(World world, ITreeGenData tree, BlockPos pos) {
		tree.setLeaves(world, owner, pos);
	}
}
