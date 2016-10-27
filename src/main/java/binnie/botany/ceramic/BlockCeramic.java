package binnie.botany.ceramic;

import binnie.botany.CreativeTabBotany;
import binnie.botany.genetics.EnumFlowerColor;
import binnie.core.block.BlockMetadata;
import binnie.core.block.IBlockMetadata;
import binnie.core.block.TileEntityMetadata;
import forestry.api.core.IItemModelRegister;
import forestry.api.core.IModelManager;
import forestry.core.blocks.IColoredBlock;
import forestry.core.items.IColoredItem;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class BlockCeramic extends Block implements IBlockMetadata, IColoredBlock, IColoredItem, IItemModelRegister {
	public static final PropertyEnum<EnumFlowerColor> COLOUR = PropertyEnum.create("color", EnumFlowerColor.class);

	public BlockCeramic() {
		super(Material.ROCK);
		this.setHardness(1.0f);
		this.setResistance(5.0f);
		this.setRegistryName("ceramic");
		this.setCreativeTab(CreativeTabBotany.instance);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerModel(Item item, IModelManager manager) {
		for (final EnumFlowerColor c : EnumFlowerColor.values()) {
			manager.registerItemModel(item, c.ordinal());
		}
	}

	//TODO: Adding Meta
	/*@Override
    protected BlockStateContainer createBlockState() {
    	return new BlockStateContainer(this, COLOUR);
    }
    
    @Override
    public int getMetaFromState(IBlockState state) {
    	return state.getValue(COLOUR).ordinal();
    }
    
    @Override
    public IBlockState getStateFromMeta(int meta) {
    	return getDefaultState().withProperty(COLOUR, EnumFlowerColor.values()[meta]);
    }*/

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
		return EnumFlowerColor.get(meta).getColourName() + " Ceramic Block";
	}

	@Override
	public void getBlockTooltip(final ItemStack par1ItemStack, final List par3List) {
	}

	@Override
	public void dropAsStack(final World world, final BlockPos pos, final ItemStack itemStack) {
		spawnAsEntity(world, pos, itemStack);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(final Item itemIn, final CreativeTabs par2CreativeTabs, final List<ItemStack> itemList) {
		for (final EnumFlowerColor c : EnumFlowerColor.values()) {
			itemList.add(TileEntityMetadata.getItemStack(this, c.ordinal()));
		}
	}

	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		return BlockMetadata.getPickBlock(world, pos);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int getColorFromItemstack(ItemStack stack, int tintIndex) {
		return EnumFlowerColor.get(stack.getMetadata()).getColor(false);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int colorMultiplier(IBlockState state, IBlockAccess worldIn, BlockPos pos, int tintIndex) {
		return EnumFlowerColor.get(getMetaFromState(state)).getColor(false);
	}
}
