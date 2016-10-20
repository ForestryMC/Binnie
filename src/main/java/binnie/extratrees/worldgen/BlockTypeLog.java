// 
// Decompiled by Procyon v0.5.30
// 

package binnie.extratrees.worldgen;

import forestry.api.world.ITreeGenData;

import net.minecraft.world.World;
import binnie.extratrees.block.ILogType;

public class BlockTypeLog extends BlockType
{
	ILogType log;

	public BlockTypeLog(final ILogType log) {
		super(null, 0);
		this.log = log;
	}

	@Override
	public void setBlock(final World world, final ITreeGenData tree, final int x, final int y, final int z) {
		this.log.placeBlock(world, x, y, z);
	}
}
