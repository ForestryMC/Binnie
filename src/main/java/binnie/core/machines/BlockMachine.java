package binnie.core.machines;

import binnie.core.BinnieCore;
import binnie.core.machines.component.IRender;
import binnie.core.util.TileUtil;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class BlockMachine extends BlockContainer implements IBlockMachine {
	private MachineGroup group;
	public static final PropertyInteger MACHINE_TYPE = PropertyInteger.create("machine_type", 0, 15);

	public BlockMachine(MachineGroup group, String blockName) {
		super(Material.IRON);
		this.group = group;
		this.setHardness(1.5f);
		this.setRegistryName(blockName);
	}

	@Override
	public void getSubBlocks(final Item itemIn, final CreativeTabs par2CreativeTabs, final List<ItemStack> itemList) {
		for (final MachinePackage pack : this.group.getPackages()) {
			if (pack.isActive()) {
				itemList.add(new ItemStack(this, 1, pack.getMetadata()));
			}
		}
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
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}
	
	@Override
	public boolean canRenderInLayer(BlockRenderLayer layer) {
		return layer == BlockRenderLayer.CUTOUT;
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(MACHINE_TYPE);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(MACHINE_TYPE, meta);
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, MACHINE_TYPE);
	}

	@Override
	public MachinePackage getPackage(final int meta) {
		return this.group.getPackage(meta);
	}

	@Override
	public String getMachineName(final int meta) {
		return (this.getPackage(meta) == null) ? "Unnamed Machine" : this.getPackage(meta).getDisplayName();
	}

	@Override
	public int damageDropped(IBlockState state) {
		return state.getValue(MACHINE_TYPE);
	}

	@Override
	public TileEntity createNewTileEntity(final World var1, final int meta) {
		if (this.group.getPackage(meta) == null) {
			return null;
		}
		return this.group.getPackage(meta).createTileEntity();
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (!BinnieCore.proxy.isSimulating(world)) {
			return true;
		}
		if (player.isSneaking()) {
			return true;
		}
		TileEntity tileEntity = world.getTileEntity(pos);
		if (tileEntity.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, side)) {
			IFluidHandler tileFluidHandler = tileEntity.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, side);
			if (FluidUtil.interactWithFluidHandler(heldItem, tileFluidHandler, player)) {
				return true;
			}
		}
		if (tileEntity instanceof TileEntityMachine) {
			((TileEntityMachine) tileEntity).getMachine().onRightClick(world, player, pos);
		}
		return true;
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase entityliving, ItemStack stack) {
		super.onBlockPlacedBy(world, pos, state, entityliving, stack);
		if (!BinnieCore.proxy.isSimulating(world)) {
			return;
		}
		final IMachine machine = Machine.getMachine(world.getTileEntity(pos));
		if (machine == null) {
			return;
		}
		if (entityliving instanceof EntityPlayer) {
			machine.setOwner(((EntityPlayer) entityliving).getGameProfile());
		}
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		final TileEntityMachine entity = TileUtil.getTile(world, pos, TileEntityMachine.class);
		if (entity != null) {
			entity.onBlockDestroy();
		}
		super.breakBlock(world, pos, state);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {
		IMachine machine = Machine.getMachine(world.getTileEntity(pos));
		if (machine != null) {
			for (IRender.RandomDisplayTick renders : machine.getInterfaces(IRender.RandomDisplayTick.class)) {
				renders.onRandomDisplayTick(world, pos, rand);
			}
		}
	}

	@Override
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		return new ArrayList<>();
	}

	@Override
	public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
		if (BinnieCore.proxy.isSimulating(world) && this.canHarvestBlock(world, pos, player) && !player.capabilities.isCreativeMode) {
			//final int metadata = this.getMetaFromState(state);
			//final ItemStack stack = new ItemStack(Item.getItemFromBlock(this), 1, this.damageDropped(state));
			this.dropBlockAsItem(world, pos, state, 0);
		}
		return world.setBlockToAir(pos);
	}
}
