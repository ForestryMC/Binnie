// 
// Decompiled by Procyon v0.5.30
// 

package binnie.extratrees.worldgen;

import forestry.api.world.ITreeGenData;

import net.minecraft.world.World;

public class BlockTypeVoid extends BlockType
{
	public BlockTypeVoid() {
		super(null, 0);
	}

	@Override
	public void setBlock(final World world, final ITreeGenData tree, final int x, final int y, final int z) {
		world.setBlockToAir(x, y, z);
		if (world.getTileEntity(x, y, z) != null) {
			world.removeTileEntity(x, y, z);
		}
	}
}
