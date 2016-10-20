// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.multiblock;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.block.material.Material;
import net.minecraft.block.BlockContainer;

public class BlockMultiblockMachine extends BlockContainer
{
	public BlockMultiblockMachine(final String blockName) {
		super(Material.iron);
		this.setHardness(1.5f);
		this.setBlockName(blockName);
	}

	@Override
	public TileEntity createTileEntity(final World world, final int metadata) {
		return new TileEntityMultiblockMachine();
	}

	@Override
	public TileEntity createNewTileEntity(final World var1, final int i) {
		return new TileEntityMultiblockMachine();
	}
}
