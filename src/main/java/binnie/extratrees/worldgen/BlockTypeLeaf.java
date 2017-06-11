package binnie.extratrees.worldgen;

import binnie.core.Mods;
import com.mojang.authlib.GameProfile;
import forestry.api.world.ITreeGenData;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockTypeLeaf extends BlockType {
	GameProfile owner;

	public BlockTypeLeaf(@Nullable  GameProfile owner) {
		super(Mods.Forestry.block("leaves"), 0);
		this.owner = owner;
	}

	@Override
	public void setBlock(final World world, final ITreeGenData tree, final BlockPos pos) {
		tree.setLeaves(world, owner, pos);
	}
}
