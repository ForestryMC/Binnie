package binnie.botany.ceramic;

import binnie.botany.CreativeTabBotany;
import binnie.botany.genetics.EnumFlowerColor;
import binnie.core.block.IBlockMetadata;
import binnie.core.block.TileEntityMetadata;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class BlockCeramic extends Block implements IBlockMetadata {
    public BlockCeramic() {
        super(Material.ROCK);
        this.setHardness(1.0f);
        this.setResistance(5.0f);
        this.setRegistryName("ceramic");
        this.setCreativeTab(CreativeTabBotany.instance);
    }

//	@Override
//	public ArrayList<ItemStack> getDrops(final World world, final BlockPos pos, final int blockMeta, final int fortune) {
//		return BlockMetadata.getBlockDropped(this, world, pos, blockMeta);
//	}
//
//	@Override
//	public boolean removedByPlayer(final World world, final EntityPlayer player, final int x, final int y, final int z) {
//		return BlockMetadata.breakBlock(this, player, world, x, y, z);
//	}

    @Override
    public TileEntity createNewTileEntity(final World var1, final int i) {
        return new TileEntityMetadata();
    }

//	@Override
//	public boolean hasTileEntity(final int meta) {
//		return true;
//	}
//
//	@Override
//	public boolean onBlockEventReceived(final World par1World, final int par2, final int par3, final int par4, final int par5, final int par6) {
//		super.onBlockEventReceived(par1World, par2, par3, par4, par5, par6);
//		final TileEntity tileentity = par1World.getTileEntity(par2, par3, par4);
//		return tileentity != null && tileentity.receiveClientEvent(par5, par6);
//	}

    @Override
    public int getPlacedMeta(final ItemStack stack, final World world, BlockPos pos, final EnumFacing clickedBlock) {
        return TileEntityMetadata.getItemDamage(stack);
    }

    @Override
    public int getDroppedMeta(final int blockMeta, final int tileMeta) {
        return tileMeta;
    }

    @Override
    public String getBlockName(final ItemStack par1ItemStack) {
        final int meta = TileEntityMetadata.getItemDamage(par1ItemStack);
        return EnumFlowerColor.get(meta).getName() + " Ceramic Block";
    }

    @Override
    public void getBlockTooltip(final ItemStack par1ItemStack, final List par3List) {
    }

    @Override
    public void dropAsStack(final World world, final BlockPos pos, final ItemStack drop) {
        //TODO IMPLEMENT
        // this.dropBlockAsItem(world, pos, drop);
    }

    @Override
    public void getSubBlocks(final Item itemIn, final CreativeTabs par2CreativeTabs, final List<ItemStack> itemList) {
        for (final EnumFlowerColor c : EnumFlowerColor.values()) {
            itemList.add(TileEntityMetadata.getItemStack(this, c.ordinal()));
        }
    }

//	@Override
//	@SideOnly(Side.CLIENT)
//	public IIcon getIcon(final IBlockAccess world, final int x, final int y, final int z, final int side) {
//		final TileEntityMetadata tile = TileEntityMetadata.getTile(world, x, y, z);
//		if (tile != null) {
//			return this.getIcon(side, tile.getTileMetadata());
//		}
//		return super.getIcon(world, x, y, z, side);
//	}
//
//	@Override
//	public IIcon getIcon(final int side, final int meta) {
//		return this.blockIcon;
//	}
//
//	@Override
//	@SideOnly(Side.CLIENT)
//	public void registerBlockIcons(final IIconRegister register) {
//		this.blockIcon = Botany.proxy.getIcon(register, "ceramic");
//	}
//
//	@Override
//	@SideOnly(Side.CLIENT)
//	public int colorMultiplier(final IBlockAccess world, final int x, final int y, final int z) {
//		final TileEntityMetadata tile = TileEntityMetadata.getTile(world, x, y, z);
//		if (tile != null) {
//			return this.getRenderColor(tile.getTileMetadata());
//		}
//		return 16777215;
//	}
//
//	@Override
//	public void breakBlock(final World par1World, final int par2, final int par3, final int par4, final Block par5, final int par6) {
//		super.breakBlock(par1World, par2, par3, par4, par5, par6);
//		par1World.removeTileEntity(par2, par3, par4);
//	}
//
//	@Override
//	public ItemStack getPickBlock(final MovingObjectPosition target, final World world, final int x, final int y, final int z) {
//		return BlockMetadata.getPickBlock(world, x, y, z);
//	}
//
//	@Override
//	@SideOnly(Side.CLIENT)
//	public int getRenderColor(final int meta) {
//		return EnumFlowerColor.get(meta).getColor(false);
//	}
}
