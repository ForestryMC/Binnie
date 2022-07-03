package binnie.core.machines;

import binnie.Binnie;
import binnie.core.BinnieCore;
import binnie.core.machines.component.IRender;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

class BlockMachine extends BlockContainer implements IBlockMachine {
    private MachineGroup group;

    public BlockMachine(MachineGroup group, String blockName) {
        super(Material.iron);
        this.group = group;
        setHardness(1.5f);
        setBlockName(blockName);
    }

    @Override
    public void getSubBlocks(Item item, CreativeTabs tab, List itemList) {
        for (MachinePackage pack : group.getPackages()) {
            if (pack.isActive()) {
                itemList.add(new ItemStack(this, 1, pack.getMetadata()));
            }
        }
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return !group.customRenderer;
    }

    @Override
    public int getRenderType() {
        return Binnie.Machine.getMachineRenderID();
    }

    @Override
    public TileEntity createTileEntity(World world, int meta) {
        MachinePackage pkg = group.getPackage(meta);
        if (pkg == null) {
            return null;
        }
        return pkg.createTileEntity();
    }

    @Override
    public MachinePackage getPackage(int meta) {
        return group.getPackage(meta);
    }

    @Override
    public String getMachineName(int meta) {
        MachinePackage pkg = getPackage(meta);
        return (pkg == null) ? "Unnamed Machine" : pkg.getDisplayName();
    }

    @Override
    public int damageDropped(int meta) {
        return meta;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return createTileEntity(world, meta);
    }

    @Override
    public boolean onBlockActivated(
            World world,
            int x,
            int y,
            int z,
            EntityPlayer player,
            int side,
            float xOffset,
            float yOffset,
            float zOffset) {
        if (!BinnieCore.proxy.isSimulating(world) || player.isSneaking()) {
            return true;
        }

        TileEntity entity = world.getTileEntity(x, y, z);
        if (entity instanceof TileEntityMachine) {
            ((TileEntityMachine) entity).getMachine().onRightClick(world, player, x, y, z);
        }
        return true;
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
        super.onBlockPlacedBy(world, x, y, z, entity, stack);
        if (!BinnieCore.proxy.isSimulating(world)) {
            return;
        }

        IMachine machine = Machine.getMachine(world.getTileEntity(x, y, z));
        if (machine == null) {
            return;
        }

        if (entity instanceof EntityPlayer) {
            machine.setOwner(((EntityPlayer) entity).getGameProfile());
        }
    }

    @Override
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
        TileEntity entity = world.getTileEntity(x, y, z);
        if (entity instanceof TileEntityMachine
                && ((TileEntityMachine) entity).getMachine().hasInterface(IMachineTexturedFaces.class)) {
            return ((TileEntityMachine) entity)
                    .getMachine()
                    .getInterface(IMachineTexturedFaces.class)
                    .getIcon(side);
        }
        return Blocks.dirt.getIcon(0, 0);
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
        TileEntity tileentity = world.getTileEntity(x, y, z);
        if (!(tileentity instanceof TileEntityMachine)) {
            return;
        }

        TileEntityMachine entity = (TileEntityMachine) tileentity;
        entity.onBlockDestroy();
        super.breakBlock(world, x, y, z, block, meta);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register) {
        // ignored
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
        IMachine machine = Machine.getMachine(world.getTileEntity(x, y, z));
        if (machine != null) {
            for (IRender.RandomDisplayTick renders : machine.getInterfaces(IRender.RandomDisplayTick.class)) {
                renders.onRandomDisplayTick(world, x, y, z, rand);
            }
        }
    }

    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int meta, int fortune) {
        return new ArrayList<>();
    }

    @Override
    public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z, boolean willHarvest) {
        if (BinnieCore.proxy.isSimulating(world)
                && canHarvestBlock(player, world.getBlockMetadata(x, y, z))
                && !player.capabilities.isCreativeMode) {
            int metadata = world.getBlockMetadata(x, y, z);
            ItemStack stack = new ItemStack(Item.getItemFromBlock(this), 1, damageDropped(metadata));
            dropBlockAsItem(world, x, y, z, stack);
        }
        return world.setBlockToAir(x, y, z);
    }

    public interface IMachineTexturedFaces {
        IIcon getIcon(int p0);
    }
}
