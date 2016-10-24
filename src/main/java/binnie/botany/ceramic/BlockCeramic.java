package binnie.botany.ceramic;

import binnie.botany.CreativeTabBotany;
import binnie.botany.genetics.EnumFlowerColor;
import binnie.core.block.BlockMetadata;
import binnie.core.block.IBlockMetadata;
import binnie.core.block.TileEntityMetadata;
import forestry.core.blocks.IColoredBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;

public class BlockCeramic extends Block implements IBlockMetadata, IColoredBlock {
    public BlockCeramic() {
        super(Material.ROCK);
        this.setHardness(1.0f);
        this.setResistance(5.0f);
        this.setRegistryName("ceramic");
        this.setCreativeTab(CreativeTabBotany.instance);
    }

    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
    	return BlockMetadata.getBlockDropped(this, world, pos);
    }
    
    @Override
    public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
    	return BlockMetadata.breakBlock(this, player, world, pos);
    }

    @Override
    public TileEntity createNewTileEntity(final World var1, final int i) {
        return new TileEntityMetadata();
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
    	return true;
    }
    
   @Override
   public boolean eventReceived(IBlockState state, World worldIn, BlockPos pos, int id, int param) {
       super.eventReceived(state, worldIn, pos, id, param);
       TileEntity tileentity = worldIn.getTileEntity(pos);
       return tileentity == null ? false : tileentity.receiveClientEvent(id, param);
   }

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
    public void dropAsStack(final World world, final BlockPos pos, final ItemStack itemStack) {
    	spawnAsEntity(world, pos, itemStack);
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
    
    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
    	return BlockMetadata.getPickBlock(world, pos);
    }
    
    @Override
    public int colorMultiplier(IBlockState state, IBlockAccess worldIn, BlockPos pos, int tintIndex) {
    	return EnumFlowerColor.get(getMetaFromState(state)).getColor(false);
    }
}
