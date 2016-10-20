// 
// Decompiled by Procyon v0.5.30
// 

package binnie.extratrees.worldgen;

import forestry.api.world.ITreeGenData;
import com.mojang.authlib.GameProfile;
import net.minecraft.world.World;
import binnie.core.Mods;

public class BlockTypeLeaf extends BlockType
{
	public BlockTypeLeaf() {
		super(Mods.Forestry.block("leaves"), 0);
	}

	@Override
	public void setBlock(final World world, final ITreeGenData tree, final int x, final int y, final int z) {
		tree.setLeaves(world, (GameProfile) null, x, y, z);
	}
}
