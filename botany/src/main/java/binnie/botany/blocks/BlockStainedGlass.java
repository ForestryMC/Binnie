package binnie.botany.blocks;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import net.minecraftforge.event.ForgeEventFactory;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.core.IItemModelRegister;
import forestry.api.core.IModelManager;
import forestry.core.blocks.IColoredBlock;

import binnie.botany.CreativeTabBotany;
import binnie.botany.api.genetics.EnumFlowerColor;
import binnie.core.block.BlockMetadata;
import binnie.core.block.IBlockMetadata;
import binnie.core.block.TileEntityMetadata;
import binnie.core.util.I18N;

public class BlockStainedGlass extends Block implements IBlockMetadata, IColoredBlock, IItemModelRegister {
	public BlockStainedGlass() {
		super(Material.GLASS);
		setCreativeTab(CreativeTabBotany.INSTANCE);
		setRegistryName("stained");
		setSoundType(SoundType.GLASS);
		setHardness(0.3F);
	}

	@Override
	public int quantityDropped(Random rand) {
		return 0;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.TRANSLUCENT;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerModel(Item item, IModelManager manager) {
		for (EnumFlowerColor color : EnumFlowerColor.values()) {
			manager.registerItemModel(item, color.getFlowerColorAllele().getID());
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		IBlockState iblockstate = blockAccess.getBlockState(pos.offset(side));
		Block block = iblockstate.getBlock();
		return block != this;
	}

	@Override
	public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack) {
		player.addStat(StatList.getBlockStats(this));
		player.addExhaustion(0.005F);

		if (EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, stack) > 0 && te instanceof TileEntityMetadata) {
			List<ItemStack> items = new ArrayList<>();
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
	public TileEntity createNewTileEntity(World var1, int i) {
		return new TileEntityMetadata();
	}

	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}

	@Override
	public boolean eventReceived(IBlockState state, World worldIn, BlockPos pos, int id, int param) {
		TileEntity tileentity = worldIn.getTileEntity(pos);
		return tileentity != null && tileentity.receiveClientEvent(id, param);
	}

	@Override
	public int getPlacedMeta(ItemStack stack, World world, BlockPos pos, EnumFacing clickedBlock) {
		return TileEntityMetadata.getItemDamage(stack);
	}

	@Override
	public int getDroppedMeta(IBlockState state, int tileMetadata) {
		return tileMetadata;
	}

	@Override
	public String getDisplayName(ItemStack itemStack) {
		EnumFlowerColor color = EnumFlowerColor.get(TileEntityMetadata.getItemDamage(itemStack));
		return I18N.localise("botany.pigmented.glass.name", color.getDisplayName());
	}

	@Override
	public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> itemList) {
		for (EnumFlowerColor color : EnumFlowerColor.values()) {
			itemList.add(TileEntityMetadata.getItemStack(this, color.ordinal()));
		}
	}

	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		return BlockMetadata.getPickBlock(world, pos);
	}

	@Override
	public int colorMultiplier(IBlockState state, @Nullable IBlockAccess worldIn, @Nullable BlockPos pos, int tintIndex) {
		if (worldIn == null || pos == null) {
			return 0;
		}
		EnumFlowerColor color = EnumFlowerColor.get(TileEntityMetadata.getTileMetadata(worldIn, pos));
		return color.getFlowerColorAllele().getColor(false);
	}
}
