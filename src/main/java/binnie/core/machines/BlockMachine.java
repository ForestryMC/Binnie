// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.machines;

import java.util.ArrayList;
import binnie.core.machines.component.IRender;
import java.util.Random;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.entity.EntityLivingBase;
import binnie.core.BinnieCore;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import binnie.Binnie;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import java.util.List;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.block.material.Material;
import net.minecraft.block.BlockContainer;

class BlockMachine extends BlockContainer implements IBlockMachine
{
	private MachineGroup group;

	public BlockMachine(final MachineGroup group, final String blockName) {
		super(Material.iron);
		this.group = group;
		this.setHardness(1.5f);
		this.setBlockName(blockName);
	}

	@Override
	public void getSubBlocks(final Item par1, final CreativeTabs par2CreativeTabs, final List itemList) {
		for (final MachinePackage pack : this.group.getPackages()) {
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
		return !this.group.customRenderer;
	}

	@Override
	public int getRenderType() {
		return Binnie.Machine.getMachineRenderID();
	}

	@Override
	public TileEntity createTileEntity(final World world, final int metadata) {
		if (this.group.getPackage(metadata) == null) {
			return null;
		}
		return this.group.getPackage(metadata).createTileEntity();
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
	public int damageDropped(final int par1) {
		return par1;
	}

	@Override
	public TileEntity createNewTileEntity(final World var1, final int meta) {
		return this.createTileEntity(var1, meta);
	}

	@Override
	public boolean onBlockActivated(final World world, final int x, final int y, final int z, final EntityPlayer player, final int par6, final float par7, final float par8, final float par9) {
		if (!BinnieCore.proxy.isSimulating(world)) {
			return true;
		}
		if (player.isSneaking()) {
			return true;
		}
		final TileEntity entity = world.getTileEntity(x, y, z);
		if (entity instanceof TileEntityMachine) {
			((TileEntityMachine) entity).getMachine().onRightClick(world, player, x, y, z);
		}
		return true;
	}

	@Override
	public void onBlockPlacedBy(final World world, final int i, final int j, final int k, final EntityLivingBase entityliving, final ItemStack stack) {
		super.onBlockPlacedBy(world, i, j, k, entityliving, stack);
		if (!BinnieCore.proxy.isSimulating(world)) {
			return;
		}
		final IMachine machine = Machine.getMachine(world.getTileEntity(i, j, k));
		if (machine == null) {
			return;
		}
		if (entityliving instanceof EntityPlayer) {
			machine.setOwner(((EntityPlayer) entityliving).getGameProfile());
		}
	}

	@Override
	public IIcon getIcon(final IBlockAccess world, final int x, final int y, final int z, final int side) {
		final TileEntity entity = world.getTileEntity(x, y, z);
		if (entity instanceof TileEntityMachine && ((TileEntityMachine) entity).getMachine().hasInterface(IMachineTexturedFaces.class)) {
			return ((TileEntityMachine) entity).getMachine().getInterface(IMachineTexturedFaces.class).getIcon(side);
		}
		return Blocks.dirt.getIcon(0, 0);
	}

	@Override
	public void breakBlock(final World world, final int x, final int y, final int z, final Block par5, final int par6) {
		final TileEntity tileentity = world.getTileEntity(x, y, z);
		if (!(tileentity instanceof TileEntityMachine)) {
			return;
		}
		final TileEntityMachine entity = (TileEntityMachine) tileentity;
		if (entity != null) {
			entity.onBlockDestroy();
		}
		super.breakBlock(world, x, y, z, par5, par6);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(final IIconRegister register) {
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(final World world, final int x, final int y, final int z, final Random rand) {
		final IMachine machine = Machine.getMachine(world.getTileEntity(x, y, z));
		if (machine != null) {
			for (final IRender.RandomDisplayTick renders : machine.getInterfaces(IRender.RandomDisplayTick.class)) {
				renders.onRandomDisplayTick(world, x, y, z, rand);
			}
		}
	}

	@Override
	public ArrayList<ItemStack> getDrops(final World world, final int x, final int y, final int z, final int metadata, final int fortune) {
		return new ArrayList<ItemStack>();
	}

	@Override
	public boolean removedByPlayer(final World world, final EntityPlayer player, final int x, final int y, final int z, final boolean willHarvest) {
		if (BinnieCore.proxy.isSimulating(world) && this.canHarvestBlock(player, world.getBlockMetadata(x, y, z)) && !player.capabilities.isCreativeMode) {
			final int metadata = world.getBlockMetadata(x, y, z);
			final ItemStack stack = new ItemStack(Item.getItemFromBlock(this), 1, this.damageDropped(metadata));
			this.dropBlockAsItem(world, x, y, z, stack);
		}
		return world.setBlockToAir(x, y, z);
	}

	public interface IMachineTexturedFaces
	{
		IIcon getIcon(final int p0);
	}
}
