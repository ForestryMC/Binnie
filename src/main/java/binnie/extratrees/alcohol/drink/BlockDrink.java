package binnie.extratrees.alcohol.drink;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockDrink extends BlockContainer {
    public BlockDrink() {
        super(Material.GLASS);
        this.setRegistryName("drink");
        this.setCreativeTab(CreativeTabs.TOOLS);
    }

//	@Override
//	@SideOnly(Side.CLIENT)
//	public int getRenderBlockPass() {
//		return 1;
//	}

    @Override
    public TileEntity createNewTileEntity(final World p_149915_1_, final int p_149915_2_) {
        return new TileEntityDrink();
    }

//	@Override
//	public int getRenderType() {
//		return ModuleAlcohol.drinkRendererID;
//	}
//
//	@Override
//	public boolean isNormalCube() {
//		return false;
//	}
//
//	@Override
//	public boolean isOpaqueCube() {
//		return true;
//	}
}
