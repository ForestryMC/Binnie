package binnie.extratrees.blocks;

import javax.annotation.Nullable;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import net.minecraftforge.event.ForgeEventFactory;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.block.TileEntityMetadata;
import binnie.core.util.I18N;
import binnie.design.DesignHelper;
import binnie.design.api.IDesign;
import binnie.design.blocks.BlockDesign;
import binnie.design.blocks.DesignBlock;
import binnie.extratrees.carpentry.DesignSystem;
import binnie.extratrees.carpentry.GlassType;

public class BlockStainedDesign extends BlockDesign {
	public BlockStainedDesign() {
		super(DesignSystem.Glass, Material.GLASS);
		this.setSoundType(SoundType.GLASS);
		this.setRegistryName("stainedGlass");
		this.setHardness(0.3F);
	}

	@Override
	public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack) {
		player.addStat(StatList.getBlockStats(this));
		player.addExhaustion(0.005F);

		if (EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, stack) > 0 && te instanceof TileEntityMetadata) {
			java.util.List<ItemStack> items = new java.util.ArrayList<>();
			TileEntityMetadata tile = (TileEntityMetadata) te;
			int damage = getDroppedMeta(state, tile.getTileMetadata());
			ItemStack itemstack = TileEntityMetadata.getItemStack(this, damage);

			if (!itemstack.isEmpty()) {
				items.add(itemstack);
			}

			ForgeEventFactory.fireBlockHarvesting(items, worldIn, pos, state, 0, 1.0f, true, player);
			for (ItemStack item : items) {
				spawnAsEntity(worldIn, pos, item);
			}
		}
	}

	@Override
	public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {

	}

	@Override
	public int quantityDropped(Random random) {
		return 0;
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
	public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer) {
		return layer == BlockRenderLayer.TRANSLUCENT;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int getRenderPasses() {
		return 2;
	}

	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings("deprecation")
	public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		IBlockState iblockstate = blockAccess.getBlockState(pos.offset(side));
		Block block = iblockstate.getBlock();
		return block != this;
	}

	@Override
	public ItemStack getCreativeStack(final IDesign design) {
		return DesignHelper.getItemStack(this, GlassType.get(0), GlassType.get(1), design);
	}

	@Override
	public String getBlockName(final DesignBlock design) {
		return I18N.localise("extratrees.block.stainedglass.name", design.getDesign().getName());
	}
}
