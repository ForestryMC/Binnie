package binnie.extratrees.alcohol.drink;

import binnie.extratrees.alcohol.ModuleAlcohol;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

// TODO unused class?
public class BlockDrink extends BlockContainer {
	public BlockDrink() {
		super(Material.glass);
		setBlockName("drink");
		setCreativeTab(CreativeTabs.tabTools);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderBlockPass() {
		return 1;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityDrink();
	}

	@Override
	public int getRenderType() {
		return ModuleAlcohol.drinkRendererID;
	}

	@Override
	public boolean isNormalCube() {
		return false;
	}

	@Override
	public boolean isOpaqueCube() {
		return true;
	}
}
