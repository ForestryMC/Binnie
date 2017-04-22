package binnie.core.multiblock;

import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.tileentity.*;
import net.minecraft.world.*;

// TODO unused class?
public class BlockMultiblockMachine extends BlockContainer {
	public BlockMultiblockMachine(String blockName) {
		super(Material.iron);
		this.setHardness(1.5f);
		setBlockName(blockName);
	}

	@Override
	public TileEntity createTileEntity(World world, int metadata) {
		return new TileEntityMultiblockMachine();
	}

	@Override
	public TileEntity createNewTileEntity(World world, int metadata) {
		return new TileEntityMultiblockMachine();
	}
}
