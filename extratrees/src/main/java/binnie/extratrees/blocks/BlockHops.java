package binnie.extratrees.blocks;

import java.util.Locale;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import net.minecraftforge.common.EnumPlantType;

import binnie.extratrees.modules.ModuleCore;

public class BlockHops extends BlockCrops{
	public static final PropertyEnum<HopsHalf> HALF = PropertyEnum.create("half", HopsHalf.class);
	
	public BlockHops() {
		this.setDefaultState(this.blockState.getBaseState().withProperty(getAgeProperty(), 0).withProperty(HALF, HopsHalf.DOWN));
		setUnlocalizedName("hops");
		setRegistryName("hops");
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		state = getActualState(state, world, pos);
		int age = getAge(state);
		if(age == getMaxAge()) {
			if (!world.isRemote) {
				NonNullList<ItemStack> items = NonNullList.create();
				getDrops(items, world, pos, state, 0);
				
				for (ItemStack item : items) {
					spawnAsEntity(world, pos, item);
				}
				BlockPos cropPos = pos;
				if(state.getValue(HALF) == HopsHalf.UP){
					cropPos = cropPos.down();
				}
				placeAt(world, cropPos, 2);
			}
			return true;
		}
		return false;
	}
	
	@Override
	protected int getBonemealAgeIncrease(World worldIn) {
		return super.getBonemealAgeIncrease(worldIn) / 2;
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return FULL_BLOCK_AABB;
	}
	
	@Override
	public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos) {
		return EnumPlantType.Crop;
	}
	
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		return super.canPlaceBlockAt(worldIn, pos) && worldIn.isAirBlock(pos.up());
	}
	
	@Override
	protected void checkAndDropBlock(World worldIn, BlockPos pos, IBlockState state) {
		if (!this.canBlockStay(worldIn, pos, state)) {
			boolean flag = state.getValue(HALF) == HopsHalf.UP;
			BlockPos blockpos = flag ? pos : pos.up();
			BlockPos blockpos1 = flag ? pos.down() : pos;
			Block block = flag ? this : worldIn.getBlockState(blockpos).getBlock();
			Block block1 = flag ? worldIn.getBlockState(blockpos1).getBlock() : this;
			
			if (!flag){
				this.dropBlockAsItem(worldIn, pos, state, 0);
			}
			
			if (block == this) {
				worldIn.setBlockState(blockpos, Blocks.AIR.getDefaultState(), 2);
			}
			
			if (block1 == this) {
				worldIn.setBlockState(blockpos1, Blocks.AIR.getDefaultState(), 3);
			}
		}
	}
	
	@Override
	public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state) {
		if (state.getValue(HALF) == HopsHalf.UP) {
			return worldIn.getBlockState(pos.down()).getBlock() == this;
		} else {
			IBlockState iblockstate = worldIn.getBlockState(pos.up());
			return iblockstate.getBlock() == this && super.canBlockStay(worldIn, pos, iblockstate);
		}
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		if (state.getValue(HALF) == HopsHalf.UP) {
			return Items.AIR;
		} else {
			return super.getItemDropped(state, rand, fortune);
		}
	}
	
	@Override
	protected Item getCrop() {
		return ModuleCore.itemHops;
	}
	
	@Override
	protected Item getSeed() {
		return ModuleCore.itemHops;
	}
	
	public void placeAt(World worldIn, BlockPos lowerPos, int flags) {
		worldIn.setBlockState(lowerPos, this.getDefaultState().withProperty(HALF, HopsHalf.DOWN), flags);
		worldIn.setBlockState(lowerPos.up(), this.getDefaultState().withProperty(HALF, HopsHalf.UP), flags);
	}
	
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		worldIn.setBlockState(pos.up(), this.getDefaultState().withProperty(HALF, HopsHalf.UP), 2);
	}
	
	public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
		if (state.getValue(HALF) == HopsHalf.UP) {
			if (worldIn.getBlockState(pos.down()).getBlock() == this) {
				if (player.capabilities.isCreativeMode) {
					worldIn.setBlockToAir(pos.down());
				} else {
					worldIn.destroyBlock(pos.down(), true);
				}
			}
		} else if (worldIn.getBlockState(pos.up()).getBlock() == this) {
			worldIn.setBlockState(pos.up(), Blocks.AIR.getDefaultState(), 2);
		}
		
		super.onBlockHarvested(worldIn, pos, state, player);
	}
	
	@Override
	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
		return new ItemStack(getCrop());
	}
	
	@Override
	public boolean canGrow(World world, BlockPos pos, IBlockState state, boolean isClient) {
		if(state.getValue(HALF) == HopsHalf.UP){
			IBlockState stateDrown = world.getBlockState(pos.down());
			Block block = stateDrown.getBlock();
			if(block instanceof BlockHops) {
				return ((BlockHops) block).canGrow(world, pos.down(), stateDrown, isClient);
			}
			return false;
		}
		return super.canGrow(world, pos, state, isClient);
	}
	
	@Override
	public void grow(World world, BlockPos pos, IBlockState state) {
		if(state.getValue(HALF) == HopsHalf.UP){
			IBlockState stateDrown = world.getBlockState(pos.down());
			Block block = stateDrown.getBlock();
			if(block instanceof BlockHops) {
				((BlockHops) block).grow(world, pos.down(), stateDrown);
			}
			return;
		}
		super.grow(world, pos, state);
	}
	
	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		if(state.getValue(HALF) == HopsHalf.DOWN){
			super.updateTick(worldIn, pos, state, rand);
		}else {
			checkAndDropBlock(worldIn, pos, state);
		}
	}
	
	@Override
	public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
		if (state.getBlock() ==  this && state.getValue(HALF) == HopsHalf.DOWN && world.getBlockState(pos.up()).getBlock() == this) {
			world.setBlockToAir(pos.up());
		}
		return world.setBlockToAir(pos);
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, AGE, HALF);
	}
	
	public IBlockState getStateFromMeta(int meta) {
		return (meta & 8) > 0 ? this.getDefaultState().withProperty(HALF, HopsHalf.UP) : this.getDefaultState().withProperty(HALF, HopsHalf.DOWN).withProperty(getAgeProperty(), meta & 7);
	}
	
	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		if (state.getValue(HALF) == HopsHalf.UP) {
			IBlockState iblockstate = worldIn.getBlockState(pos.down());
			
			if (iblockstate.getBlock() == this) {
				state = state.withProperty(getAgeProperty(), iblockstate.getValue(getAgeProperty()));
			}
		}
		
		return state;
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(HALF) == HopsHalf.UP ? 8 : getAge(state);
	}
	
	public enum HopsHalf implements IStringSerializable {
		UP,
		DOWN;
		
		@Override
		public String toString()
		{
			return this.getName();
		}
		
		@Override
		public String getName()
		{
			return name().toLowerCase(Locale.ENGLISH);
		}
	}
}
