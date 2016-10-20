// 
// Decompiled by Procyon v0.5.30
// 

package binnie.extratrees.alcohol.drink;

import binnie.extratrees.alcohol.ModuleAlcohol;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.block.material.Material;
import net.minecraft.block.BlockContainer;

public class BlockDrink extends BlockContainer
{
	public BlockDrink() {
		super(Material.glass);
		this.setBlockName("drink");
		this.setCreativeTab(CreativeTabs.tabTools);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderBlockPass() {
		return 1;
	}

	@Override
	public TileEntity createNewTileEntity(final World p_149915_1_, final int p_149915_2_) {
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
