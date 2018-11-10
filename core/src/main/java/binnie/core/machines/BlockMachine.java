package binnie.core.machines;

import com.google.common.base.Preconditions;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import net.minecraftforge.fluids.FluidUtil;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.machines.component.IRender;
import binnie.core.util.I18N;
import binnie.core.util.TileUtil;

class BlockMachine extends Block implements IBlockMachine, ITileEntityProvider {
	public static final PropertyInteger MACHINE_TYPE = PropertyInteger.create("machine_type", 0, 15);
	private final MachineGroup group;

	public BlockMachine(MachineGroup group, String blockName) {
		super(Material.IRON);
		this.group = group;
		this.setHardness(1.5f);
		this.setRegistryName(blockName);
	}

	@Override
	public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> itemList) {
		for (final MachinePackage pack : this.group.getPackages()) {
			if (pack.isActive()) {
				itemList.add(new ItemStack(this, 1, pack.getMetadata()));
			}
		}
	}

	@Override
	@SuppressWarnings("deprecation")
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	@SuppressWarnings("deprecation")
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	@SuppressWarnings("deprecation")
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
	@SuppressWarnings("deprecation")
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(MACHINE_TYPE, meta);
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, MACHINE_TYPE);
	}

	@Nullable
	private MachinePackage getPackage(final int meta) {
		return this.group.getPackage(meta);
	}

	@Override
	public String getMachineName(final int meta) {
		MachinePackage machinePackage = this.getPackage(meta);
		return (machinePackage == null) ? "Unnamed Machine" : machinePackage.getDisplayName();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, ITooltipFlag advanced) {
		super.addInformation(stack, player, tooltip, advanced);
		if (GuiScreen.isShiftKeyDown()) {
			MachinePackage machinePackage = this.getPackage(stack.getMetadata());
			if (machinePackage != null) {
				tooltip.add(machinePackage.getInformation());
			}
		} else {
			tooltip.add(TextFormatting.ITALIC + "<" + I18N.localise("for.gui.tooltip.tmi") + '>');
		}
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
		if (worldIn.isRemote) {
			return true;
		}
		TileEntity tileEntity = worldIn.getTileEntity(pos);
		if (tileEntity != null) {
			if (!playerIn.isSneaking()) {
				if (FluidUtil.interactWithFluidHandler(playerIn, hand, worldIn, pos, facing)) {
					return true;
				}
			}
			if (tileEntity instanceof TileEntityMachine) {
				TileEntityMachine tileEntityMachine = (TileEntityMachine) tileEntity;
				Machine machine = tileEntityMachine.getMachine();
				machine.onRightClick(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
			}
		}
		return true;
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase entityliving, ItemStack stack) {
		super.onBlockPlacedBy(world, pos, state, entityliving, stack);
		if (world.isRemote) {
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
			IMachine machine = Machine.getMachine(TileUtil.getTile(world, pos, TileEntity.class));
			if (machine != null) {
				for (IRender.RandomDisplayTick renders : machine.getInterfaces(IRender.RandomDisplayTick.class)) {
					renders.onRandomDisplayTick(world, pos, rand);
				}
			}
		}
	}

	@Override
	public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
		if (!player.capabilities.isCreativeMode) {
			return super.removedByPlayer(state, world, pos, player, willHarvest);
		}
		return world.setBlockToAir(pos);
	}
}
