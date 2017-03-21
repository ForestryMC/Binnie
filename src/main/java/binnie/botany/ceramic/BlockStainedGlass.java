package binnie.botany.ceramic;

import binnie.botany.CreativeTabBotany;
import binnie.botany.genetics.EnumFlowerColor;
import binnie.core.BinnieCore;
import binnie.core.block.BlockMetadata;
import binnie.core.block.IBlockMetadata;
import binnie.core.block.TileEntityMetadata;
import binnie.core.util.TileUtil;
import forestry.api.core.IItemModelRegister;
import forestry.api.core.IModelManager;
import forestry.core.blocks.IColoredBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class BlockStainedGlass extends Block implements IBlockMetadata, IColoredBlock, IItemModelRegister {
	public BlockStainedGlass() {
		super(Material.GLASS);
		this.setCreativeTab(CreativeTabBotany.instance);
		this.setRegistryName("stained");
		setSoundType(SoundType.GLASS);
	}

	@Override
	public int quantityDropped(final Random p_149745_1_) {
		return 0;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.TRANSLUCENT;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerModel(Item item, IModelManager manager) {
		for (EnumFlowerColor color : EnumFlowerColor.values()) {
			manager.registerItemModel(item, color.getID());
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		IBlockState iblockstate = blockAccess.getBlockState(pos.offset(side));
		Block block = iblockstate.getBlock();
		if (blockState != iblockstate) {
			return true;
		}

		if (block == this) {
			return false;
		}

		return super.shouldSideBeRendered(blockState, blockAccess, pos, side);
	}

	@Override
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		List<ItemStack> list = new ArrayList<>();
		TileEntityMetadata tile = TileEntityMetadata.getTile(world, pos);
		if (tile != null && !tile.hasDroppedBlock()) {
			int meta = getDroppedMeta(state, tile.getTileMetadata());
			list.add(TileEntityMetadata.getItemStack(this, meta));
		}
		return list;
	}

	@Override
	public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, @Nullable EntityPlayer player, boolean willHarvest) {
		List<ItemStack> drops = new ArrayList<>();
		TileCeramic ceramic = TileUtil.getTile(world, pos, TileCeramic.class);
		if (ceramic != null) {
			drops = Collections.singletonList(ceramic.getStack());
		}
		boolean hasBeenBroken = world.setBlockToAir(pos);
		if (hasBeenBroken && BinnieCore.getBinnieProxy().isSimulating(world) && drops.size() > 0 && (player == null || !player.capabilities.isCreativeMode)) {
			for (ItemStack drop : drops) {
				spawnAsEntity(world, pos, drop);
			}
		}
		return hasBeenBroken;
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
		TileEntity tileentity = worldIn.getTileEntity(pos);
		return tileentity == null ? false : tileentity.receiveClientEvent(id, param);
	}

	@Override
	public int getPlacedMeta(final ItemStack stack, final World world, final BlockPos pos, final EnumFacing clickedBlock) {
		return TileEntityMetadata.getItemDamage(stack);
	}

	@Override
	public int getDroppedMeta(IBlockState state, int tileMetadata) {
		return tileMetadata;
	}

	@Override
	public String getDisplayName(final ItemStack par1ItemStack) {
		final int meta = TileEntityMetadata.getItemDamage(par1ItemStack);
		//TODO:localise
		return EnumFlowerColor.get(meta).getColourName() + " Pigmented Glass";
	}

	@Override
	public void getSubBlocks(final Item itemIn, final CreativeTabs par2CreativeTabs, final NonNullList<ItemStack> itemList) {
		for (final EnumFlowerColor c : EnumFlowerColor.values()) {
			itemList.add(TileEntityMetadata.getItemStack(this, c.ordinal()));
		}
	}

	@Override
	public boolean isWood(IBlockAccess world, BlockPos pos) {
		return true;
	}

	@Override
	public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face) {
		return 20;
	}

	@Override
	public boolean isFlammable(IBlockAccess world, BlockPos pos, EnumFacing face) {
		return true;
	}

	@Override
	public int getFireSpreadSpeed(IBlockAccess world, BlockPos pos, EnumFacing face) {
		return 5;
	}

	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		return BlockMetadata.getPickBlock(world, pos);
	}

	@Override
	public int colorMultiplier(IBlockState state, IBlockAccess worldIn, BlockPos pos, int tintIndex) {
		if (worldIn == null || pos == null) {
			return 0;
		}
		return EnumFlowerColor.get(TileEntityMetadata.getTileMetadata(worldIn, pos)).getColor(false);
	}
}
