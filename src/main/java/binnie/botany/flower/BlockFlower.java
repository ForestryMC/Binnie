package binnie.botany.flower;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.mojang.authlib.GameProfile;

import binnie.botany.Botany;
import binnie.botany.api.EnumFlowerStage;
import binnie.botany.api.IFlower;
import binnie.botany.api.IFlowerType;
import binnie.botany.core.BotanyCore;
import binnie.botany.gardening.Gardening;
import binnie.botany.genetics.FlowerDefinition;
import binnie.core.BinnieCore;
import binnie.core.util.TileUtil;
import forestry.api.core.IItemModelRegister;
import forestry.api.core.IModelManager;
import forestry.core.blocks.IColoredBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockFlower extends BlockContainer implements IColoredBlock, IItemModelRegister {
	
	public static final AxisAlignedBB FLOWER_BLOCK_AABB = new AxisAlignedBB(0.3D, 0.0D, 0.3D, 0.7D, 0.6D, 0.7D);
	/* PROPERTYS */
	public static final PropertyFlower FLOWER = new PropertyFlower("flower", IFlowerType.class);
	
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
    public void registerModel(Item item, IModelManager manager) {
    	for(EnumFlowerStage type : EnumFlowerStage.VALUES){
			for (IFlowerType flowerType : (List<IFlowerType>)FLOWER.getAllowedValues()) {
				flowerType.registerModels(item, manager, type);
			}
    	}
    }
	
	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, World worldIn, BlockPos pos) {
		return null;
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return FLOWER_BLOCK_AABB;
	}
	
	@Override
	public AxisAlignedBB getSelectedBoundingBox(IBlockState state, World worldIn, BlockPos pos) {
		return FLOWER_BLOCK_AABB;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		super.onBlockPlacedBy(world, pos, state, placer, stack);
		final TileEntity flower = world.getTileEntity(pos);
		if (!BinnieCore.proxy.isSimulating(world)) {
			if (flower != null && flower instanceof TileEntityFlower) {
				final IFlower f = BotanyCore.getFlowerRoot().getMember(stack);
				((TileEntityFlower) flower).setRender(new TileEntityFlower.RenderInfo(f, (TileEntityFlower) flower));
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
		final TileEntity tile = world.getTileEntity(pos);
		if (tile instanceof TileEntityFlower) {
			final TileEntityFlower f = (TileEntityFlower) tile;
			if(tintIndex == 0){
				return f.getStemColour();
			}else if(tintIndex == 1){
				return f.getPrimaryColour();
			}else{
				return f.getSecondaryColour();
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
	
	@SideOnly(Side.CLIENT)
	@Override
	public EnumOffsetType getOffsetType() {
		return EnumOffsetType.XZ;
	}
	
	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
		TileEntityFlower flower = TileUtil.getTile(world, pos, TileEntityFlower.class);
		if(flower != null && flower.getFlower() != null){
			state = state.withProperty(FLOWER, flower.getFlower().getGenome().getPrimary().getType());
		}else{
			state = state.withProperty(FLOWER, FlowerDefinition.Dandelion.getSpecies().getType());
		}
		return state;
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, FLOWER);
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
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn) {
		this.checkAndDropBlock(world, pos);
		final TileEntity tile = world.getTileEntity(pos);
		if (tile instanceof TileEntityFlower) {
			final TileEntityFlower flower = (TileEntityFlower) tile;
			if (flower.getSection() == 0 && flower.getFlower() != null && flower.getFlower().getAge() > 0 && flower.getFlower().getGenome().getPrimary().getType().getSections() > 1 && world.getBlockState(pos.up()).getBlock() != Botany.flower) {
				this.dropBlockAsItem(world, pos, world.getBlockState(pos), 0);
				world.setBlockToAir(pos);
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
		ArrayList<ItemStack> drops = new ArrayList<ItemStack>();
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
		if (hasBeenBroken && BinnieCore.proxy.isSimulating(world) && drops.size() > 0 && (player == null || !player.capabilities.isCreativeMode)) {
			for (final ItemStack drop : drops) {
				spawnAsEntity(world, pos, drop);
			}
		}
		return hasBeenBroken;
	}
	
}
