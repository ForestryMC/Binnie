package binnie.extratrees.blocks.decor;

import javax.annotation.Nullable;

import net.minecraft.block.BlockFence;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.core.IItemModelRegister;
import forestry.api.core.IModelManager;
import forestry.api.core.IStateMapperRegister;
import forestry.api.core.Tabs;
import forestry.core.blocks.properties.UnlistedBlockAccess;
import forestry.core.blocks.properties.UnlistedBlockPos;
import forestry.core.models.BlockModelEntry;

import binnie.core.block.BlockMetadata;
import binnie.core.block.IBlockMetadata;
import binnie.core.block.TileEntityMetadata;
import binnie.core.models.DefaultStateMapper;
import binnie.core.models.ModelManager;
import binnie.core.util.I18N;
import binnie.extratrees.models.ModelMultiFence;
import binnie.extratrees.wood.WoodManager;
import binnie.extratrees.wood.planks.IPlankType;
import binnie.extratrees.wood.planks.VanillaPlanks;

public class BlockMultiFence extends BlockFence implements IBlockMetadata, IStateMapperRegister, IItemModelRegister, IBlockFence {
	public BlockMultiFence() {
		super(Material.WOOD, MapColor.WOOD);
		this.setCreativeTab(Tabs.tabArboriculture);
		this.setRegistryName("multifence");
		this.setResistance(5.0f);
		this.setHardness(2.0f);
		this.setSoundType(SoundType.WOOD);
	}

	public FenceDescription getDescription(final int meta) {
		return WoodManager.getFenceDescription(meta);
	}

	@Override
	public boolean eventReceived(IBlockState state, World world, BlockPos pos, int id, int param) {
		TileEntity tileentity = world.getTileEntity(pos);
		return tileentity != null && tileentity.receiveClientEvent(id, param);
	}

	@Override
	public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		BlockMetadata.getDrops(drops, this, world, pos);
	}

	@Override
	public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
		return BlockMetadata.breakBlock(this, player, world, pos);
	}

	@Override
	public TileEntity createNewTileEntity(final World var1, final int i) {
		return new TileEntityMetadata();
	}

	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}

	@Override
	public int getPlacedMeta(final ItemStack stack, final World world, final BlockPos pos, final EnumFacing clickedBlock) {
		return TileEntityMetadata.getItemDamage(stack);
	}

	@Override
	public int getDroppedMeta(IBlockState state, final int tileMeta) {
		return tileMeta;
	}

	@Override
	public boolean canPlaceTorchOnTop(IBlockState state, IBlockAccess world, BlockPos pos) {
		return true;
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		super.breakBlock(world, pos, state);
		world.removeTileEntity(pos);
	}

	@Override
	public boolean isWood(final IBlockAccess world, final BlockPos pos) {
		return true;
	}

	@Override
	public int getFlammability(final IBlockAccess world, final BlockPos pos, final EnumFacing face) {
		return 20;
	}

	@Override
	public boolean isFlammable(final IBlockAccess world, final BlockPos pos, final EnumFacing face) {
		return true;
	}

	@Override
	public int getFireSpreadSpeed(final IBlockAccess world, final BlockPos pos, final EnumFacing face) {
		return 5;
	}

	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		return BlockMetadata.getPickBlock(world, pos);
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new ExtendedBlockState(this, new IProperty[]{NORTH, EAST, WEST, SOUTH}, new IUnlistedProperty[]{UnlistedBlockPos.POS, UnlistedBlockAccess.BLOCKACCESS});
	}

	@Override
	public IBlockState getExtendedState(IBlockState state, IBlockAccess world, BlockPos pos) {
		return ((IExtendedBlockState) state).withProperty(UnlistedBlockPos.POS, pos).withProperty(UnlistedBlockAccess.BLOCKACCESS, world);
	}

	@Override
	public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> itemList) {
		for (FenceType type : FenceType.getValues()) {
			itemList.add(WoodManager.getFence(VanillaPlanks.SPRUCE, VanillaPlanks.BIRCH, type, 1));
		}
	}

	@SideOnly(Side.CLIENT)
	@Nullable
	public TextureAtlasSprite getSprite(int meta, boolean secondary) {
		FenceDescription desc = getDescription(meta);
		if (desc != null) {
			IPlankType type = secondary ? desc.getSecondaryPlankType() : desc.getPlankType();
			return type.getSprite();
		}
		return null;
	}

	@Override
	public String getDisplayName(ItemStack itemStack) {
		int meta = TileEntityMetadata.getItemDamage(itemStack);
		IPlankType typeFirst = this.getDescription(meta).getPlankType();
		IPlankType typeSecond = this.getDescription(meta).getSecondaryPlankType();
		boolean twoTypes = typeFirst != typeSecond;
		FenceType fenceType = this.getDescription(meta).getFenceType();
		String woodGrammar = I18N.localise("extratrees.block.multifence" + (twoTypes ? "2" : "") + ".grammar");

		woodGrammar = woodGrammar.replaceAll("%PREFIX", fenceType.getPrefix());
		woodGrammar = woodGrammar.replaceAll("%TYPE", typeSecond.getDesignMaterialName());
		woodGrammar = woodGrammar.replaceAll("%WOOD", typeFirst.getDesignMaterialName());
		return woodGrammar;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerStateMapper() {
		ResourceLocation registryName = getRegistryName();
		ModelLoader.setCustomStateMapper(this, new DefaultStateMapper(registryName));
		ModelManager.registerCustomBlockModel(new BlockModelEntry(
			new ModelResourceLocation(registryName, "normal"),
			new ModelResourceLocation(registryName, "inventory"),
			new ModelMultiFence(), this
		));
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerModel(Item item, IModelManager manager) {
		manager.registerItemModel(item, new FenceMeshDefinition());
	}

	private class FenceMeshDefinition implements ItemMeshDefinition {

		@Override
		public ModelResourceLocation getModelLocation(ItemStack stack) {
			return new ModelResourceLocation(getRegistryName(), "inventory");
		}
	}
}
