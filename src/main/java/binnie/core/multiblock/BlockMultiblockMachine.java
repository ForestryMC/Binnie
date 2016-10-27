package binnie.core.multiblock;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockMultiblockMachine extends BlockContainer {
	public BlockMultiblockMachine(final String blockName) {
		super(Material.IRON);
		this.setHardness(1.5f);
		this.setRegistryName(blockName);
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityMultiblockMachine();
	}

	@Override
	public TileEntity createNewTileEntity(final World var1, final int i) {
		return new TileEntityMultiblockMachine();
	}
}
