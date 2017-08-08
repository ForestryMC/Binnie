package binnie.botany.blocks;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

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
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.mojang.authlib.GameProfile;

import net.minecraftforge.client.model.ModelLoader;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.core.IStateMapperRegister;
import forestry.core.blocks.IColoredBlock;

import binnie.botany.Botany;
import binnie.botany.api.genetics.IFlower;
import binnie.botany.api.genetics.IFlowerGenome;
import binnie.botany.api.genetics.IFlowerRoot;
import binnie.botany.api.genetics.IFlowerType;
import binnie.botany.blocks.properties.PropertyFlower;
import binnie.botany.core.BotanyCore;
import binnie.botany.genetics.EnumFlowerType;
import binnie.botany.genetics.FlowerDefinition;
import binnie.botany.models.StateMapperFlower;
import binnie.botany.modules.ModuleFlowers;
import binnie.botany.network.PacketID;
import binnie.botany.tile.TileEntityFlower;
import binnie.core.BinnieCore;
import binnie.core.network.packet.MessageNBT;
import binnie.core.util.TileUtil;

public class BlockFlower extends BlockContainer implements IColoredBlock, IStateMapperRegister {
	public static final AxisAlignedBB FLOWER_BLOCK_AABB = new AxisAlignedBB(0.3D, 0.0D, 0.3D, 0.7D, 0.6D, 0.7D);
	/* PROPERTIES */
	public static final PropertyFlower FLOWER = new PropertyFlower("flower", IFlowerType.class);
	public static final PropertyInteger SECTION = PropertyInteger.create("section", 0, EnumFlowerType.highestSection - 1);
	public static final PropertyBool FLOWERED = PropertyBool.create("flowered");
	public static final PropertyBool SEED = PropertyBool.create("seed");

	public BlockFlower() {
		super(Material.PLANTS);
		setTickRandomly(true);
		setRegistryName("flower");
		setSoundType(SoundType.PLANT);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityFlower();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerStateMapper() {
		ModelLoader.setCustomStateMapper(this, new StateMapperFlower());
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		ItemStack heldItem = player.getHeldItemMainhand();
		if (heldItem.isEmpty() || heldItem.getItem() != BinnieCore.getFieldKit() || !player.isSneaking()) {
			return false;
		}
		if (world.isRemote) {
			return true;
		}

		TileEntity tile = world.getTileEntity(pos);
		if (!(tile instanceof TileEntityFlower)) {
			return true;
		}

		TileEntityFlower tileFlower = (TileEntityFlower) tile;
		IFlower flower = tileFlower.getFlower();
		if (flower == null) {
			return true;
		}

		IFlowerGenome flowerGenome = flower.getGenome();
		NBTTagCompound info = new NBTTagCompound();
		info.setString("Species", flowerGenome.getPrimary().getUID());
		info.setString("Species2", flowerGenome.getSecondary().getUID());
		info.setFloat("Age", flower.getAge() / flowerGenome.getLifespan());
		info.setShort("Colour", (short) flowerGenome.getPrimaryColor().getID());
		info.setShort("Colour2", (short) flowerGenome.getSecondaryColor().getID());
		info.setBoolean("Wilting", flower.isWilted());
		info.setBoolean("Flowered", flower.hasFlowered());
		Botany.proxy.sendToPlayer(new MessageNBT(PacketID.FIELDKIT.ordinal(), info), player);
		heldItem.damageItem(1, player);
		return true;
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
		return FLOWER_BLOCK_AABB.move(state.getOffset(source, pos));
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
		IFlowerRoot flowerRoot = BotanyCore.getFlowerRoot();
		TileEntity flower = world.getTileEntity(pos);
		if (!BinnieCore.getBinnieProxy().isSimulating(world)) {
			if (flower != null && flower instanceof TileEntityFlower) {
				IFlower f = flowerRoot.getMember(stack);
				if (f != null) {
					((TileEntityFlower) flower).setRender(new TileEntityFlower.RenderInfo(f, (TileEntityFlower) flower));
				}
			}
			return;
		}

		TileEntity below = world.getTileEntity(pos.down());
		if (flower != null && flower instanceof TileEntityFlower) {
			if (below instanceof TileEntityFlower) {
				((TileEntityFlower) flower).setSection(((TileEntityFlower) below).getSection());
			} else {
				GameProfile owner = (placer instanceof EntityPlayer) ? ((EntityPlayer) placer).getGameProfile() : null;
				((TileEntityFlower) flower).create(stack, owner);
			}
		}
		flowerRoot.tryGrowSection(world, pos);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int colorMultiplier(IBlockState state, @Nullable IBlockAccess world, @Nullable BlockPos pos, int tintIndex) {
		if (world != null && pos != null) {
			TileEntity tile = world.getTileEntity(pos);
			if (tile instanceof TileEntityFlower) {
				TileEntityFlower flower = (TileEntityFlower) tile;
				if (tintIndex == 0) {
					return flower.getStemColour();
				} else if (tintIndex == 1) {
					return flower.getPrimaryColour();
				}
				return flower.getSecondaryColour();
			}
		}
		return 0xffffff;
	}

	@Override
	public boolean canPlaceBlockAt(World world, BlockPos pos) {
		return super.canPlaceBlockAt(world, pos) && canBlockStay(world, pos);
	}

	public boolean canBlockStay(World world, BlockPos pos) {
		TileEntity tile = world.getTileEntity(pos);
		IBlockState downState = world.getBlockState(pos.down());
		if (tile instanceof TileEntityFlower && ((TileEntityFlower) tile).getSection() > 0) {
			return downState.getBlock() == ModuleFlowers.flower;
		}
		return canPlaceBlockOn(downState.getBlock());
	}

	protected boolean canPlaceBlockOn(Block block) {
		return block == Blocks.GRASS
				|| block == Blocks.DIRT
				|| block == Blocks.FARMLAND
				|| BotanyCore.getGardening().isSoil(block);
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
		checkAndDropBlock(worldIn, pos);
		TileEntity tile = worldIn.getTileEntity(pos);
		if (tile instanceof TileEntityFlower) {
			TileEntityFlower flower = (TileEntityFlower) tile;
			if (flower.getSection() == 0 && flower.getFlower() != null && flower.getFlower().getAge() > 0 && flower.getFlower().getGenome().getPrimary().getType().getSections() > 1 && worldIn.getBlockState(pos.up()).getBlock() != ModuleFlowers.flower) {
				dropBlockAsItem(worldIn, pos, worldIn.getBlockState(pos), 0);
				worldIn.setBlockToAir(pos);
			}
		}
	}

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		TileEntity tile = world.getTileEntity(pos);
		if (tile instanceof TileEntityFlower) {
			((TileEntityFlower) tile).randomUpdate(rand);
			checkAndDropBlock(world, pos);
			return;
		}
		world.setBlockToAir(pos);
	}

	protected void checkAndDropBlock(World world, BlockPos pos) {
		if (!canBlockStay(world, pos)) {
			dropBlockAsItem(world, pos, world.getBlockState(pos), 0);
			world.setBlockToAir(pos);
		}
	}

	@Override
	public NonNullList<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		NonNullList<ItemStack> drops = NonNullList.create();
		TileEntity tile = world.getTileEntity(pos);
		if (tile instanceof TileEntityFlower && ((TileEntityFlower) tile).getSection() == 0) {
			ItemStack flower = ((TileEntityFlower) tile).getItemStack();
			if (!flower.isEmpty()) {
				drops.add(flower);
			}
		}
		return drops;
	}

	@Override
	public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
		List<ItemStack> drops = getDrops(world, pos, world.getBlockState(pos), 0);
		boolean hasBeenBroken = world.setBlockToAir(pos);
		if (hasBeenBroken && BinnieCore.getBinnieProxy().isSimulating(world) && drops.size() > 0 && (player == null || !player.capabilities.isCreativeMode)) {
			for (ItemStack drop : drops) {
				spawnAsEntity(world, pos, drop);
			}
		}
		return hasBeenBroken;
	}
}
