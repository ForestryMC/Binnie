package binnie.design.blocks;

import javax.annotation.Nullable;
import java.util.List;

import binnie.design.Design;
import binnie.design.DesignHelper;
import binnie.design.api.IDesign;
import binnie.design.api.IDesignSystem;
import binnie.design.api.IToolHammer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.core.IItemModelRegister;
import forestry.api.core.IModelManager;
import forestry.api.core.ISpriteRegister;
import forestry.api.core.IStateMapperRegister;
import forestry.api.core.ITextureManager;
import forestry.core.blocks.IColoredBlock;
import forestry.core.blocks.properties.UnlistedBlockAccess;
import forestry.core.blocks.properties.UnlistedBlockPos;
import forestry.core.models.BlockModelEntry;

import binnie.core.block.BlockMetadata;
import binnie.core.block.TileEntityMetadata;
import binnie.core.block.IMultipassBlock;
import binnie.core.models.DefaultStateMapper;
import binnie.core.models.ModelManager;
import binnie.core.models.ModelMutlipass;
import binnie.core.util.TileUtil;

public abstract class BlockDesign extends BlockMetadata implements IMultipassBlock<BlockDesign.Key>, IColoredBlock, ISpriteRegister, IItemModelRegister, IStateMapperRegister {
	public static final EnumFacing[] RENDER_DIRECTIONS = new EnumFacing[]{EnumFacing.DOWN, EnumFacing.UP, EnumFacing.EAST, EnumFacing.WEST, EnumFacing.NORTH, EnumFacing.SOUTH};
	public static final EnumFacing[] RENDER_DIRECTIONS_ITEM = new EnumFacing[]{EnumFacing.DOWN, EnumFacing.UP, EnumFacing.WEST, EnumFacing.EAST, EnumFacing.SOUTH, EnumFacing.NORTH};
	IDesignSystem designSystem;

	public BlockDesign(final IDesignSystem system, final Material material) {
		super(material);
		this.designSystem = system;
	}

	public static int getMetadata(final int plank1, final int plank2, final int design) {
		return plank1 + (plank2 << 9) + (design << 18);
	}

	@SubscribeEvent
	public void onClick(final PlayerInteractEvent.RightClickBlock event) {
		if(event.getHand() != EnumHand.MAIN_HAND) return;
		final World world = event.getWorld();
		final EntityPlayer player = event.getEntityPlayer();
		final BlockPos pos = event.getPos();
		if (!(world.getBlockState(pos).getBlock() instanceof BlockDesign)) {
			return;
		}
		final BlockDesign blockC = (BlockDesign) world.getBlockState(pos).getBlock();
		final ItemStack item = player.getHeldItemMainhand();
		if (item.isEmpty()) {
			return;
		}
		if (!(item.getItem() instanceof IToolHammer)) {
			return;
		}
		if (!((IToolHammer) item.getItem()).isActive(item)) {
			return;
		}
		final DesignBlock block = blockC.getCarpentryBlock(world, pos);
		final TileEntityMetadata tile = TileUtil.getTile(world, pos, TileEntityMetadata.class);
		if (tile != null && event.getFace() != null) {
			block.rotate(event.getFace(), item, player, world, pos);
			final int meta = block.getBlockMetadata(blockC.getDesignSystem());
			tile.setTileMetadata(meta, true);
		}
	}

	public abstract ItemStack getCreativeStack(final IDesign p0);

	@Override
	public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> itemList) {
		for (final IDesign design : Design.getDesignManager().getSortedDesigns()) {
			itemList.add(this.getCreativeStack(design));
		}
	}

	public IDesignSystem getDesignSystem() {
		return this.designSystem;
	}

	@Override
	public String getDisplayName(final ItemStack itemStack) {
		final DesignBlock block = DesignHelper.getDesignBlock(this.getDesignSystem(), TileEntityMetadata.getItemDamage(itemStack));
		return this.getBlockName(block);
	}

	public abstract String getBlockName(final DesignBlock p0);

	public DesignBlock getCarpentryBlock(final IBlockAccess world, final BlockPos pos) {
		return DesignHelper.getDesignBlock(this.getDesignSystem(), TileEntityMetadata.getTileMetadata(world, pos));
	}

	@Override
	public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer) {
		return layer == BlockRenderLayer.CUTOUT_MIPPED;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int colorMultiplier(IBlockState state, @Nullable IBlockAccess world, @Nullable BlockPos pos, int tintIndex) {
		if (world == null || pos == null) {
			return 0;
		}
		DesignBlock block = this.getCarpentryBlock(world, pos);
		if (tintIndex > 0) {
			return block.getSecondaryColour();
		}
		return block.getPrimaryColour();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, ITooltipFlag advanced) {
		DesignBlock block = DesignHelper.getDesignBlock(this.getDesignSystem(), TileEntityMetadata.getItemDamage(stack));
		if (block.getPrimaryMaterial() != block.getSecondaryMaterial()) {
			tooltip.add(block.getPrimaryMaterial().getDesignMaterialName() + " and " + block.getSecondaryMaterial().getDesignMaterialName());
		} else {
			tooltip.add(block.getPrimaryMaterial().getDesignMaterialName());
		}
	}

	public int primaryColor(final int damage) {
		final DesignBlock block = DesignHelper.getDesignBlock(this.getDesignSystem(), damage);
		return block.getPrimaryColour();
	}

	public int secondaryColor(final int damage) {
		final DesignBlock block = DesignHelper.getDesignBlock(this.getDesignSystem(), damage);
		return block.getSecondaryColour();
	}

	public ItemStack getItemStack(final int plank1, final int plank2, final int design) {
		return TileEntityMetadata.getItemStack(this, getMetadata(plank1, plank2, design));
	}

	@Override
	public int getDroppedMeta(IBlockState state, int tileMetadata) {
		final DesignBlock block = DesignHelper.getDesignBlock(this.getDesignSystem(), tileMetadata);
		return block.getItemMetadata(this.getDesignSystem());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerStateMapper() {
		ResourceLocation registryName = getRegistryName();
		ModelLoader.setCustomStateMapper(this, new DefaultStateMapper(registryName));
		ModelManager.registerCustomBlockModel(new BlockModelEntry(
			new ModelResourceLocation(registryName, "normal"),
			new ModelResourceLocation(registryName, "inventory"),
			new ModelMutlipass<>(BlockDesign.class), this
		));
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerModel(Item item, IModelManager manager) {
		manager.registerItemModel(item, new DesignMeshDefinition());
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new ExtendedBlockState(this, new IProperty[0], new IUnlistedProperty[]{UnlistedBlockPos.POS, UnlistedBlockAccess.BLOCKACCESS});
	}

	@Override
	public IBlockState getExtendedState(IBlockState state, IBlockAccess world, BlockPos pos) {
		return ((IExtendedBlockState) state).withProperty(UnlistedBlockPos.POS, pos).withProperty(UnlistedBlockAccess.BLOCKACCESS, world);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int getRenderPasses() {
		return 2;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public Key getInventoryKey(ItemStack stack) {
		return new Key(TileEntityMetadata.getItemDamage(stack), true);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public Key getWorldKey(IBlockState state) {
		IExtendedBlockState extendedState = (IExtendedBlockState) state;
		IBlockAccess world = extendedState.getValue(UnlistedBlockAccess.BLOCKACCESS);
		BlockPos pos = extendedState.getValue(UnlistedBlockPos.POS);
		return new Key(TileEntityMetadata.getTileMetadata(world, pos), false);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public TextureAtlasSprite getSprite(Key key, @Nullable EnumFacing facing, int pass) {
		EnumFacing[] renderDirections = RENDER_DIRECTIONS;
		if(key.item){
			renderDirections = RENDER_DIRECTIONS_ITEM;
		}
		DesignBlock block = DesignHelper.getDesignBlock(this.getDesignSystem(), key.metadata);

		if (facing == null) {
			return block.getPrimarySprite(getDesignSystem(), renderDirections[0]);
		}

		if (pass > 0) {
			return block.getSecondarySprite(getDesignSystem(), renderDirections[facing.ordinal()]);
		}
		return block.getPrimarySprite(getDesignSystem(), renderDirections[facing.ordinal()]);
	}

	public static class Key {
		int metadata;
		boolean item;

		public Key(int metadata, boolean item) {
			this.metadata = metadata;
			this.item = item;
		}

		@Override
		public boolean equals(Object obj) {
			if(!(obj instanceof Key)){
				return false;
			}
			Key k = (Key)obj;
			return k.item == item && k.metadata == metadata;
		}

		@Override
		public int hashCode() {
			return 1 + metadata * 31 + Boolean.hashCode(item) * 31;
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerSprites(ITextureManager manager) {
		for (IDesignSystem system : Design.getDesignManager().getDesignSystems()) {
			system.registerSprites();
		}
	}

	private class DesignMeshDefinition implements ItemMeshDefinition {

		@Override
		public ModelResourceLocation getModelLocation(ItemStack stack) {
			return new ModelResourceLocation(getRegistryName(), "inventory");
		}
	}
}
