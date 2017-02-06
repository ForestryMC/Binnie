package binnie.extratrees.carpentry;

import binnie.core.block.BlockMetadata;
import binnie.core.block.IMultipassBlock;
import binnie.core.block.TileEntityMetadata;
import binnie.core.models.DefaultStateMapper;
import binnie.core.models.ModelManager;
import binnie.core.models.ModelMutlipass;
import binnie.core.util.TileUtil;
import binnie.extratrees.api.CarpentryManager;
import binnie.extratrees.api.IDesign;
import binnie.extratrees.api.IDesignSystem;
import binnie.extratrees.api.IToolHammer;
import forestry.api.core.IItemModelRegister;
import forestry.api.core.IModelManager;
import forestry.api.core.ISpriteRegister;
import forestry.api.core.IStateMapperRegister;
import forestry.api.core.ITextureManager;
import forestry.core.blocks.IColoredBlock;
import forestry.core.blocks.propertys.UnlistedBlockAccess;
import forestry.core.blocks.propertys.UnlistedBlockPos;
import forestry.core.models.BlockModelEntry;
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
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
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

import javax.annotation.Nullable;
import java.util.List;

public abstract class BlockDesign extends BlockMetadata implements IMultipassBlock<Integer>, IColoredBlock, ISpriteRegister, IItemModelRegister, IStateMapperRegister {
	IDesignSystem designSystem;
	public static final EnumFacing[] RENDER_DIRECTIONS = new EnumFacing[]{EnumFacing.DOWN, EnumFacing.UP, EnumFacing.EAST, EnumFacing.WEST, EnumFacing.NORTH, EnumFacing.SOUTH};

	@SubscribeEvent
	public void onClick(final PlayerInteractEvent.RightClickBlock event) {
		final World world = event.getWorld();
		final EntityPlayer player = event.getEntityPlayer();
		final BlockPos pos = event.getPos();
		if (!(world.getBlockState(pos).getBlock() instanceof BlockDesign)) {
			return;
		}
		final BlockDesign blockC = (BlockDesign) world.getBlockState(pos).getBlock();
		final ItemStack item = player.getHeldItemMainhand();
		if (item == null) {
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
		if (tile != null) {
			block.rotate(event.getFace(), item, player, world, pos);
			final int meta = block.getBlockMetadata(blockC.getDesignSystem());
			tile.setTileMetadata(meta, true);
		}
	}

	public BlockDesign(final IDesignSystem system, final Material material) {
		super(material);
		this.designSystem = system;
	}

	public abstract ItemStack getCreativeStack(final IDesign p0);

	@Override
	public void getSubBlocks(final Item itemIn, final CreativeTabs par2CreativeTabs, final List<ItemStack> itemList) {
		for (final IDesign design : CarpentryManager.carpentryInterface.getSortedDesigns()) {
			itemList.add(this.getCreativeStack(design));
		}
	}

	public IDesignSystem getDesignSystem() {
		return this.designSystem;
	}

	@Override
	public String getDisplayName(final ItemStack stack) {
		final DesignBlock block = ModuleCarpentry.getDesignBlock(this.getDesignSystem(), TileEntityMetadata.getItemDamage(stack));
		return this.getBlockName(block);
	}

	public abstract String getBlockName(final DesignBlock p0);

	public DesignBlock getCarpentryBlock(final IBlockAccess world, final BlockPos pos) {
		return ModuleCarpentry.getDesignBlock(this.getDesignSystem(), TileEntityMetadata.getTileMetadata(world, pos));
	}

	@Override
	public boolean canRenderInLayer(BlockRenderLayer layer) {
		return layer == BlockRenderLayer.CUTOUT_MIPPED;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int colorMultiplier(IBlockState state, IBlockAccess world, BlockPos pos, int tintIndex) {
		if(world == null || pos == null){
			return 0;
		}
		DesignBlock block = this.getCarpentryBlock(world, pos);
		if(tintIndex > 0){
			return block.getSecondaryColour();
		}
		return block.getPrimaryColour();
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced) {
		DesignBlock block = ModuleCarpentry.getDesignBlock(this.getDesignSystem(), TileEntityMetadata.getItemDamage(stack));
		if (block.getPrimaryMaterial() != block.getSecondaryMaterial()) {
			tooltip.add(block.getPrimaryMaterial().getName() + " and " + block.getSecondaryMaterial().getName());
		} else {
			tooltip.add(block.getPrimaryMaterial().getName());
		}
	}

	public int primaryColor(final int damage) {
		final DesignBlock block = ModuleCarpentry.getDesignBlock(this.getDesignSystem(), damage);
		return block.getPrimaryColour();
	}

	public int secondaryColor(final int damage) {
		final DesignBlock block = ModuleCarpentry.getDesignBlock(this.getDesignSystem(), damage);
		return block.getSecondaryColour();
	}

	public ItemStack getItemStack(final int plank1, final int plank2, final int design) {
		return TileEntityMetadata.getItemStack(this, getMetadata(plank1, plank2, design));
	}

	public static int getMetadata(final int plank1, final int plank2, final int design) {
		return plank1 + (plank2 << 9) + (design << 18);
	}

	@Override
	public int getDroppedMeta(IBlockState state, int tileMetadata) {
		final DesignBlock block = ModuleCarpentry.getDesignBlock(this.getDesignSystem(), tileMetadata);
		return block.getItemMetadata(this.getDesignSystem());
	}

	@Override
	@SideOnly(Side.CLIENT)
    public EnumBlockRenderType getRenderType(IBlockState state){
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
				new ModelMutlipass<>(BlockDesign.class), this));
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerModel(Item item, IModelManager manager) {
		manager.registerItemModel(item, new DesignMeshDefinition());
	}

	private class DesignMeshDefinition implements ItemMeshDefinition{

		@Override
		public ModelResourceLocation getModelLocation(ItemStack stack) {
			return new ModelResourceLocation(getRegistryName(), "inventory");
		}

	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new ExtendedBlockState(this, new IProperty[0], new IUnlistedProperty[]{UnlistedBlockPos.POS, UnlistedBlockAccess.BLOCKACCESS});
	}

	@Override
	public IBlockState getExtendedState(IBlockState state, IBlockAccess world, BlockPos pos) {
		return ((IExtendedBlockState)state).withProperty(UnlistedBlockPos.POS, pos).withProperty(UnlistedBlockAccess.BLOCKACCESS, world);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int getRenderPasses() {
		return 2;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public Integer getInventoryKey(ItemStack stack) {
		return TileEntityMetadata.getItemDamage(stack);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public Integer getWorldKey(IBlockState state) {
		IExtendedBlockState extendedState = (IExtendedBlockState) state;
		IBlockAccess world = extendedState.getValue(UnlistedBlockAccess.BLOCKACCESS);
		BlockPos pos = extendedState.getValue(UnlistedBlockPos.POS);
		return TileEntityMetadata.getTileMetadata(world, pos);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public TextureAtlasSprite getSprite(Integer key, @Nullable EnumFacing facing, int pass) {
		DesignBlock block = ModuleCarpentry.getDesignBlock(this.getDesignSystem(), key);

		if(facing == null){
			return block.getPrimarySprite(getDesignSystem(), BlockDesign.RENDER_DIRECTIONS[0]);
		}

		if(pass > 0){
			return block.getSecondarySprite(getDesignSystem(), BlockDesign.RENDER_DIRECTIONS[facing.ordinal()]);
		}
		return block.getPrimarySprite(getDesignSystem(), BlockDesign.RENDER_DIRECTIONS[facing.ordinal()]);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerSprites(ITextureManager manager) {
		for (IDesignSystem system : DesignerManager.instance.getDesignSystems()) {
			system.registerSprites();
		}
	}

}
