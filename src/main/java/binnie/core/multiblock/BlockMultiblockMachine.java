package binnie.core.multiblock;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

// TODO unused class?
public class BlockMultiblockMachine extends BlockContainer {
	public BlockMultiblockMachine(String blockName) {
		super(Material.iron);
		setHardness(1.5f);
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
