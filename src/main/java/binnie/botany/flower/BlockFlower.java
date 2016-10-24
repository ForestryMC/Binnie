package binnie.botany.flower;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.mojang.authlib.GameProfile;

import binnie.botany.Botany;
import binnie.botany.api.IFlower;
import binnie.botany.core.BotanyCore;
import binnie.botany.gardening.Gardening;
import binnie.core.BinnieCore;
import forestry.core.blocks.IColoredBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockFlower extends BlockContainer implements /*IItemModelRegister, */IColoredBlock {
	
	public static final AxisAlignedBB FLOWER_BLOCK_AABB = new AxisAlignedBB(0.3D, 0.0D, 0.3D, 0.7D, 0.6D, 0.7D);

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
    
    
    /*@Override
    @SideOnly(Side.CLIENT)
    public void registerModel(Item item, IModelManager manager) {
		for (final EnumFlowerType type : EnumFlowerType.values()) {
			type.registerIcons(par1IconRegister);
		}
    }*/
	
	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, World worldIn, BlockPos pos) {
		//flowers have no collision
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

	/*@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(final IBlockAccess world, final int x, final int y, final int z, final int side) {
		final TileEntity tile = world.getTileEntity(x, y, z);
		if (tile instanceof TileEntityFlower) {
			final TileEntityFlower f = (TileEntityFlower) tile;
			final EnumFlowerStage stage = (f.getAge() == 0) ? EnumFlowerStage.SEED : EnumFlowerStage.FLOWER;
			final IFlowerType flower = f.getType();
			final int section = f.getRenderSection();
			final boolean flowered = f.isFlowered();
			return (RendererBotany.pass == 0) ? flower.getStem(stage, flowered, section) : ((RendererBotany.pass == 1) ? flower.getPetalIcon(stage, flowered, section) : flower.getVariantIcon(stage, flowered, section));
		}
		return super.getIcon(world, x, y, z, side);
	}*/
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
		return this.canPlaceBlockOn(blockState.getBlock());
	}
	
	protected boolean canPlaceBlockOn(final Block block) {
		return block == Blocks.GRASS || block == Blocks.DIRT || block == Blocks.FARMLAND || Gardening.isSoil(block);
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
