package binnie.extratrees.worldgen;

import com.mojang.authlib.GameProfile;
import forestry.api.world.ITreeGenData;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Random;

public class BlockTypeLeaf implements WorldGenBlockType {
	@Nullable
	private final GameProfile owner;

	public BlockTypeLeaf(@Nullable GameProfile owner) {
		this.owner = owner;
	}

	@Override
	public void setBlock(final World world, final ITreeGenData tree, final BlockPos pos, final Random random) {
		tree.setLeaves(world, owner, pos, random);
	}
}
