package binnie.core.machines;

import binnie.core.BinnieCore;
import binnie.core.machines.component.IRender;
import binnie.core.util.TileUtil;
import com.google.common.base.Preconditions;
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
import net.minecraft.util.NonNullList;
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
	public void getSubBlocks(Item itemIn, CreativeTabs tab, NonNullList<ItemStack> itemList) {
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
	public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer) {
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
	@Nullable
	public MachinePackage getPackage(final int meta) {
		return this.group.getPackage(meta);
	}

	@Override
	public String getMachineName(final int meta) {
		MachinePackage machinePackage = this.getPackage(meta);
		return (machinePackage == null) ? "Unnamed Machine" : machinePackage.getDisplayName();
	}

	@Override
	public int damageDropped(IBlockState state) {
		return state.getValue(MACHINE_TYPE);
	}

	@Override
	public TileEntity createNewTileEntity(final World world, final int meta) {
		MachinePackage machinePackage = this.group.getPackage(meta);
		if (machinePackage == null) {
			machinePackage = this.group.getPackage(0);
			Preconditions.checkNotNull(machinePackage, "Machine has no packages %s", this);
		}
		return machinePackage.createTileEntity();
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (!BinnieCore.getBinnieProxy().isSimulating(worldIn)) {
			return true;
		}
		if (playerIn.isSneaking()) {
			return true;
		}
		TileEntity tileEntity = worldIn.getTileEntity(pos);
		if (tileEntity != null) {
			if (tileEntity.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, facing)) {
				IFluidHandler tileFluidHandler = tileEntity.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, facing);
				ItemStack heldItem = playerIn.getHeldItem(hand);
				if (FluidUtil.interactWithFluidHandler(heldItem, tileFluidHandler, playerIn).isSuccess()) {
					return true;
				}
			}
			if (tileEntity instanceof TileEntityMachine) {
				((TileEntityMachine) tileEntity).getMachine().onRightClick(worldIn, playerIn, pos);
			}
		}
		return true;
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase entityliving, ItemStack stack) {
		super.onBlockPlacedBy(world, pos, state, entityliving, stack);
		if (!BinnieCore.getBinnieProxy().isSimulating(world)) {
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
		if (world.isBlockLoaded(pos)) {
			IMachine machine = Machine.getMachine(world.getTileEntity(pos));
			if (machine != null) {
				for (IRender.RandomDisplayTick renders : machine.getInterfaces(IRender.RandomDisplayTick.class)) {
					renders.onRandomDisplayTick(world, pos, rand);
				}
			}
		}
	}

	@Override
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		return new ArrayList<>();
	}

	@Override
	public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
		if (BinnieCore.getBinnieProxy().isSimulating(world) && this.canHarvestBlock(world, pos, player) && !player.capabilities.isCreativeMode) {
			//final int metadata = this.getMetaFromState(state);
			//final ItemStack stack = new ItemStack(Item.getItemFromBlock(this), 1, this.damageDropped(state));
			this.dropBlockAsItem(world, pos, state, 0);
		}
		return world.setBlockToAir(pos);
	}
}
