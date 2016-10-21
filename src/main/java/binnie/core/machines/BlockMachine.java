package binnie.core.machines;

import binnie.core.BinnieCore;
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
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

class BlockMachine extends BlockContainer implements IBlockMachine {
    private MachineGroup group;
    public static final PropertyInteger MACHINE_TYPE = PropertyInteger.create("machine_type", 0, 15);

    public BlockMachine(final MachineGroup group, final String blockName) {
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

    //	@Override
//	public boolean isOpaqueCube() {
//		return false;
//	}
//
//	@Override
//	public boolean renderAsNormalBlock() {
//		return !this.group.customRenderer;
//	}
//
//	@Override
//	public int getRenderType() {
//		return Binnie.Machine.getMachineRenderID();
//	}
//
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
        final TileEntity entity = world.getTileEntity(pos);
        if (entity instanceof TileEntityMachine) {
            ((TileEntityMachine) entity).getMachine().onRightClick(world, player, pos);
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

//	@Override
//	public IIcon getIcon(final IBlockAccess world, final int x, final int y, final int z, final int side) {
//		final TileEntity entity = world.getTileEntity(x, y, z);
//		if (entity instanceof TileEntityMachine && ((TileEntityMachine) entity).getMachine().hasInterface(IMachineTexturedFaces.class)) {
//			return ((TileEntityMachine) entity).getMachine().getInterface(IMachineTexturedFaces.class).getIcon(side);
//		}
//		return Blocks.dirt.getIcon(0, 0);
//	}

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        final TileEntity tileentity = world.getTileEntity(pos);
        if (!(tileentity instanceof TileEntityMachine)) {
            return;
        }
        final TileEntityMachine entity = (TileEntityMachine) tileentity;
        if (entity != null) {
            entity.onBlockDestroy();
        }
        super.breakBlock(world, pos, state);
    }

    //	@Override
//	@SideOnly(Side.CLIENT)
//	public void registerBlockIcons(final IIconRegister register) {
//	}
//
//	@Override
//	@SideOnly(Side.CLIENT)
//	public void randomDisplayTick(final World world, final int x, final int y, final int z, final Random rand) {
//		final IMachine machine = Machine.getMachine(world.getTileEntity(x, y, z));
//		if (machine != null) {
//			for (final IRender.RandomDisplayTick renders : machine.getInterfaces(IRender.RandomDisplayTick.class)) {
//				renders.onRandomDisplayTick(world, x, y, z, rand);
//			}
//		}
//	}


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


    public interface IMachineTexturedFaces {
        //IIcon getIcon(final int p0);
    }
}
