package binnie.botany.flower;

import binnie.botany.Botany;
import binnie.botany.api.IFlower;
import binnie.botany.api.IFlowerType;
import binnie.botany.core.BotanyCore;
import binnie.botany.gardening.Gardening;
import binnie.botany.genetics.EnumFlowerType;
import binnie.botany.genetics.FlowerDefinition;
import binnie.core.BinnieCore;
import binnie.core.util.TileUtil;
import com.mojang.authlib.GameProfile;
import forestry.api.core.IStateMapperRegister;
import forestry.core.blocks.IColoredBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BlockFlower extends BlockContainer implements IColoredBlock, IStateMapperRegister {

	public static final AxisAlignedBB FLOWER_BLOCK_AABB = new AxisAlignedBB(0.3D, 0.0D, 0.3D, 0.7D, 0.6D, 0.7D);
	/* PROPERTYS */
	public static final PropertyFlower FLOWER = new PropertyFlower("flower", IFlowerType.class);
	public static final PropertyInteger SECTION = PropertyInteger.create("section", 0, EnumFlowerType.highestSection - 1);
	public static final PropertyBool FLOWERED = PropertyBool.create("flowered");
	public static final PropertyBool SEED = PropertyBool.create("seed");

	public BlockFlower() {
		super(Material.PLANTS);
		final float f = 0.2f;
		this.setTickRandomly(true);
		this.setRegistryName("flower");
		this.setSoundType(SoundType.PLANT);
	}

	@Override
	public TileEntity createNewTileEntity(final World var1, final int i) {
		return new TileEntityFlower();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerStateMapper() {
		ModelLoader.setCustomStateMapper(this, new StateMapperFlower());
	}

	@Override
	@Nullable
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
		return null;
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		TileEntity tile = source.getTileEntity(pos);
		if (tile instanceof TileEntityFlower) {
			TileEntityFlower flower = (TileEntityFlower) tile;
			if (flower.getType().getSections() > 1) {
				return FULL_BLOCK_AABB;
			}
		}
		return FLOWER_BLOCK_AABB;
	}

	@Override
	public AxisAlignedBB getSelectedBoundingBox(IBlockState state, World world, BlockPos pos) {
		TileEntity tile = world.getTileEntity(pos);
		if (tile instanceof TileEntityFlower) {
			TileEntityFlower flower = (TileEntityFlower) tile;
			if (flower.getType().getSections() > 1) {
				return FULL_BLOCK_AABB.offset(pos);
			}
		}
		return FLOWER_BLOCK_AABB.offset(pos);
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		super.onBlockPlacedBy(world, pos, state, placer, stack);
		final TileEntity flower = world.getTileEntity(pos);
		if (!BinnieCore.getBinnieProxy().isSimulating(world)) {
			if (flower != null && flower instanceof TileEntityFlower) {
				final IFlower f = BotanyCore.getFlowerRoot().getMember(stack);
				if (f != null) {
					((TileEntityFlower) flower).setRender(new TileEntityFlower.RenderInfo(f, (TileEntityFlower) flower));
				}
			}
			return;
		}
		final TileEntity below = world.getTileEntity(pos.down());
		if (flower != null && flower instanceof TileEntityFlower) {
			if (below instanceof TileEntityFlower) {
				((TileEntityFlower) flower).setSection(((TileEntityFlower) below).getSection());
			} else {
				final GameProfile owner = (placer instanceof EntityPlayer) ? ((EntityPlayer) placer).getGameProfile() : null;
				((TileEntityFlower) flower).create(stack, owner);
			}
		}
		Gardening.tryGrowSection(world, pos);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int colorMultiplier(IBlockState state, IBlockAccess world, BlockPos pos, int tintIndex) {
		if (pos != null) {
			TileEntity tile = world.getTileEntity(pos);
			if (tile instanceof TileEntityFlower) {
				TileEntityFlower flower = (TileEntityFlower) tile;
				if (tintIndex == 0) {
					return flower.getStemColour();
				} else if (tintIndex == 1) {
					return flower.getPrimaryColour();
				} else {
					return flower.getSecondaryColour();
				}
			}
		}
		return 16777215;
	}

	@Override
	public boolean canPlaceBlockAt(World world, BlockPos pos) {
		return super.canPlaceBlockAt(world, pos) && canBlockStay(world, pos);
	}

	public boolean canBlockStay(World world, BlockPos pos) {
		TileEntity tile = world.getTileEntity(pos);
		IBlockState downState = world.getBlockState(pos.down());
		if (tile instanceof TileEntityFlower && ((TileEntityFlower) tile).getSection() > 0) {
			return downState.getBlock() == Botany.flower;
		}
		return this.canPlaceBlockOn(downState.getBlock());
	}

	protected boolean canPlaceBlockOn(final Block block) {
		return block == Blocks.GRASS || block == Blocks.DIRT || block == Blocks.FARMLAND || Gardening.isSoil(block);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumOffsetType getOffsetType() {
		return EnumOffsetType.XZ;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
		TileEntityFlower flower = TileUtil.getTile(world, pos, TileEntityFlower.class);
		if (flower != null && flower.getType() != null) {
			state = state.withProperty(FLOWER, flower.getType());
			state = state.withProperty(FLOWERED, flower.isFlowered());
			state = state.withProperty(SECTION, flower.getRenderSection());
			state = state.withProperty(SEED, flower.getAge() == 0);
		} else {
			state = state.withProperty(FLOWER, FlowerDefinition.Dandelion.getSpecies().getType());
			state = state.withProperty(FLOWERED, false);
			state = state.withProperty(SEED, false);
		}
		return state;
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, FLOWER, FLOWERED, SECTION, SEED);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return 0;
	}

	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		this.checkAndDropBlock(worldIn, pos);
		final TileEntity tile = worldIn.getTileEntity(pos);
		if (tile instanceof TileEntityFlower) {
			final TileEntityFlower flower = (TileEntityFlower) tile;
			if (flower.getSection() == 0 && flower.getFlower() != null && flower.getFlower().getAge() > 0 && flower.getFlower().getGenome().getPrimary().getType().getSections() > 1 && worldIn.getBlockState(pos.up()).getBlock() != Botany.flower) {
				this.dropBlockAsItem(worldIn, pos, worldIn.getBlockState(pos), 0);
				worldIn.setBlockToAir(pos);
			}
		}
	}

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		final TileEntity tile = world.getTileEntity(pos);
		if (tile instanceof TileEntityFlower) {
			((TileEntityFlower) tile).randomUpdate(rand);
			this.checkAndDropBlock(world, pos);
			return;
		}
		world.setBlockToAir(pos);
	}

	protected void checkAndDropBlock(final World world, final BlockPos pos) {
		if (!this.canBlockStay(world, pos)) {
			this.dropBlockAsItem(world, pos, world.getBlockState(pos), 0);
			world.setBlockToAir(pos);
		}
	}

	@Override
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		ArrayList<ItemStack> drops = new ArrayList<>();
		TileEntity tile = world.getTileEntity(pos);
		if (tile instanceof TileEntityFlower && ((TileEntityFlower) tile).getSection() == 0) {
			ItemStack flower = ((TileEntityFlower) tile).getItemStack();
			if (flower != null) {
				drops.add(flower);
			}
		}
		return drops;
	}

	@Override
	public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
		final List<ItemStack> drops = this.getDrops(world, pos, world.getBlockState(pos), 0);
		final boolean hasBeenBroken = world.setBlockToAir(pos);
		if (hasBeenBroken && BinnieCore.getBinnieProxy().isSimulating(world) && drops.size() > 0 && (player == null || !player.capabilities.isCreativeMode)) {
			for (final ItemStack drop : drops) {
				spawnAsEntity(world, pos, drop);
			}
		}
		return hasBeenBroken;
	}

}
