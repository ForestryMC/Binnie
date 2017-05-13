package binnie.extratrees.block.wood;

import binnie.Constants;
import binnie.extratrees.block.EnumShrubLog;
import forestry.api.arboriculture.IWoodType;
import forestry.api.arboriculture.WoodBlockKind;
import forestry.api.core.IItemModelRegister;
import forestry.api.core.IModelManager;
import forestry.api.core.IStateMapperRegister;
import forestry.arboriculture.IWoodTyped;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.entity.Entity;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class BlockShrubLog extends Block implements IWoodTyped, IStateMapperRegister, IItemModelRegister {

	public static final PropertyBool FIREPROOF = PropertyBool.create("fireproof");
	public static final PropertyBool NORTH = PropertyBool.create("north");
	public static final PropertyBool EAST = PropertyBool.create("east");
	public static final PropertyBool SOUTH = PropertyBool.create("south");
	public static final PropertyBool WEST = PropertyBool.create("west");
	protected static final AxisAlignedBB[] BOUNDING_BOXES = new AxisAlignedBB[] {
			new AxisAlignedBB(0.375D, 0.0D, 0.375D, 0.625D, 1.0D, 0.625D),
			new AxisAlignedBB(0.375D, 0.0D, 0.375D, 0.625D, 1.0D, 1.0D),
			new AxisAlignedBB(0.0D, 0.0D, 0.375D, 0.625D, 1.0D, 0.625D),
			new AxisAlignedBB(0.0D, 0.0D, 0.375D, 0.625D, 1.0D, 1.0D),
			new AxisAlignedBB(0.375D, 0.0D, 0.0D, 0.625D, 1.0D, 0.625D),
			new AxisAlignedBB(0.375D, 0.0D, 0.0D, 0.625D, 1.0D, 1.0D),
			new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.625D, 1.0D, 0.625D),
			new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.625D, 1.0D, 1.0D),
			new AxisAlignedBB(0.375D, 0.0D, 0.375D, 1.0D, 1.0D, 0.625D),
			new AxisAlignedBB(0.375D, 0.0D, 0.375D, 1.0D, 1.0D, 1.0D),
			new AxisAlignedBB(0.0D, 0.0D, 0.375D, 1.0D, 1.0D, 0.625D),
			new AxisAlignedBB(0.0D, 0.0D, 0.375D, 1.0D, 1.0D, 1.0D),
			new AxisAlignedBB(0.375D, 0.0D, 0.0D, 1.0D, 1.0D, 0.625D),
			new AxisAlignedBB(0.375D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D),
			new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.625D),
			new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D) };
	public static final AxisAlignedBB PILLAR_AABB = new AxisAlignedBB(0.375D, 0.0D, 0.375D, 0.625D, 1D, 0.625D);
	public static final AxisAlignedBB SOUTH_AABB = new AxisAlignedBB(0.375D, 0.0D, 0.625D, 0.625D, 1D, 1.0D);
	public static final AxisAlignedBB WEST_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.375D, 0.375D, 1D, 0.625D);
	public static final AxisAlignedBB NORTH_AABB = new AxisAlignedBB(0.375D, 0.0D, 0.0D, 0.625D, 1D, 0.375D);
	public static final AxisAlignedBB EAST_AABB = new AxisAlignedBB(0.625D, 0.0D, 0.375D, 1.0D, 1D, 0.625D);

	public BlockShrubLog() {
		super(Material.WOOD);
		this.setDefaultState(this.blockState.getBaseState()
				.withProperty(NORTH, false)
				.withProperty(EAST, false)
				.withProperty(SOUTH, false)
				.withProperty(WEST, false)
				.withProperty(FIREPROOF, false));
		setRegistryName(new ResourceLocation(Constants.EXTRA_TREES_MOD_ID, "shrub_log"));
		setUnlocalizedName("shrub_log");
		setSoundType(SoundType.WOOD);
		setHardness(EnumShrubLog.INSTANCE.getHardness());
	}

	@Override
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox,
			List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean p_185477_7_) {
		if (!p_185477_7_) {
			state = state.getActualState(worldIn, pos);
		}

		addCollisionBoxToList(pos, entityBox, collidingBoxes, PILLAR_AABB);

		if (state.getValue(NORTH)) {
			addCollisionBoxToList(pos, entityBox, collidingBoxes, NORTH_AABB);
		}

		if (state.getValue(EAST)) {
			addCollisionBoxToList(pos, entityBox, collidingBoxes, EAST_AABB);
		}

		if (state.getValue(SOUTH)) {
			addCollisionBoxToList(pos, entityBox, collidingBoxes, SOUTH_AABB);
		}

		if (state.getValue(WEST)) {
			addCollisionBoxToList(pos, entityBox, collidingBoxes, WEST_AABB);
		}
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		state = this.getActualState(state, source, pos);
		return BOUNDING_BOXES[getBoundingBoxIdx(state)];
	}

	private static int getBoundingBoxIdx(IBlockState state) {
		int i = 0;

		if (state.getValue(NORTH)) {
			i |= 1 << EnumFacing.NORTH.getHorizontalIndex();
		}

		if (state.getValue(EAST)) {
			i |= 1 << EnumFacing.EAST.getHorizontalIndex();
		}

		if (state.getValue(SOUTH)) {
			i |= 1 << EnumFacing.SOUTH.getHorizontalIndex();
		}

		if (state.getValue(WEST)) {
			i |= 1 << EnumFacing.WEST.getHorizontalIndex();
		}

		return i;
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
	public final int getFireSpreadSpeed(IBlockAccess world, BlockPos pos, EnumFacing face) {
		IBlockState state = world.getBlockState(pos);
		if (state.getValue(FIREPROOF)) {
			return 0;
		} else if (face == EnumFacing.DOWN) {
			return 20;
		} else if (face != EnumFacing.UP) {
			return 10;
		} else {
			return 5;
		}
	}

	@Override
	public final int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face) {
		IBlockState state = world.getBlockState(pos);
		if (state.getValue(FIREPROOF)) {
			return 0;
		}
		return 5;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos,
			EnumFacing side) {
		return true;
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(FIREPROOF) ? 1 : 0;
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(FIREPROOF, meta == 1);
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		return state.withProperty(NORTH, canFenceConnectTo(worldIn, pos, EnumFacing.NORTH))
				.withProperty(EAST, canFenceConnectTo(worldIn, pos, EnumFacing.EAST))
				.withProperty(SOUTH, canFenceConnectTo(worldIn, pos, EnumFacing.SOUTH))
				.withProperty(WEST, canFenceConnectTo(worldIn, pos, EnumFacing.WEST));
	}

	private boolean canFenceConnectTo(IBlockAccess world, BlockPos pos, EnumFacing facing) {
		IBlockState blockState = world.getBlockState(pos.offset(facing));
		Block block = blockState.getBlock();
		return block.isLeaves(blockState, world, pos) || block == this;
	}

	@Override
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		Random rand = world instanceof World ? ((World)world).rand : RANDOM;
		List<ItemStack> ret = new java.util.ArrayList<>();
		float chance = 0.75F;
		int amount = 2;
		while(chance >= rand.nextFloat()){
			chance-=0.25;
			amount++;
		}
		ret.add(new ItemStack(Items.STICK, amount));
		return ret;
	}
	
	@Override
	public boolean isWood(IBlockAccess world, BlockPos pos) {
		return true;
	}
	
    @Override 
    public boolean canSustainLeaves(IBlockState state, IBlockAccess world, BlockPos pos){
    	return true;
    }
    
    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state){
        int i = 4;
        int j = 5;

        if (worldIn.isAreaLoaded(pos.add(-5, -5, -5), pos.add(5, 5, 5))) {
            for (BlockPos blockpos : BlockPos.getAllInBox(pos.add(-4, -4, -4), pos.add(4, 4, 4))) {
                IBlockState iblockstate = worldIn.getBlockState(blockpos);

                if (iblockstate.getBlock().isLeaves(iblockstate, worldIn, blockpos)) {
                    iblockstate.getBlock().beginLeavesDecay(iblockstate, worldIn, blockpos);
                }
            }
        }
    }
	
	@Override
	public IBlockState withRotation(IBlockState state, Rotation rot) {
		switch (rot) {
		case CLOCKWISE_180:
			return state.withProperty(NORTH, state.getValue(SOUTH)).withProperty(EAST, state.getValue(WEST))
					.withProperty(SOUTH, state.getValue(NORTH)).withProperty(WEST, state.getValue(EAST));
		case COUNTERCLOCKWISE_90:
			return state.withProperty(NORTH, state.getValue(EAST)).withProperty(EAST, state.getValue(SOUTH))
					.withProperty(SOUTH, state.getValue(WEST)).withProperty(WEST, state.getValue(NORTH));
		case CLOCKWISE_90:
			return state.withProperty(NORTH, state.getValue(WEST)).withProperty(EAST, state.getValue(NORTH))
					.withProperty(SOUTH, state.getValue(EAST)).withProperty(WEST, state.getValue(SOUTH));
		default:
			return state;
		}
	}

	@Override
	public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
		switch (mirrorIn) {
		case LEFT_RIGHT:
			return state.withProperty(NORTH, state.getValue(SOUTH)).withProperty(SOUTH, state.getValue(NORTH));
		case FRONT_BACK:
			return state.withProperty(EAST, state.getValue(WEST)).withProperty(WEST, state.getValue(EAST));
		default:
			return super.withMirror(state, mirrorIn);
		}
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, NORTH, EAST, WEST, SOUTH, FIREPROOF);
	}

	@Override
	public WoodBlockKind getBlockKind() {
		return WoodBlockKind.LOG;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerStateMapper() {
		ModelLoader.setCustomStateMapper(this, new StateMap.Builder().ignore(FIREPROOF).build());
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerModel(Item item, IModelManager manager) {
		manager.registerItemModel(item, 0, "shrub_trunk");
		manager.registerItemModel(item, 1, "shrub_trunk");
	}

	//TODO: Add meta to forestry api
	@Override
	public boolean isFireproof() {
		return false;
	}

	@Override
	public IWoodType getWoodType(int meta) {
		return EnumShrubLog.INSTANCE;
	}

	@Override
	public Collection<? extends IWoodType> getWoodTypes() {
		return Collections.singletonList(EnumShrubLog.INSTANCE);
	}

}
