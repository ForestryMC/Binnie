package binnie.botany.blocks;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
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
import forestry.api.core.ISpriteRegister;
import forestry.api.core.IStateMapperRegister;
import forestry.api.core.ITextureManager;
import forestry.core.blocks.IColoredBlock;
import forestry.core.blocks.properties.UnlistedBlockAccess;
import forestry.core.blocks.properties.UnlistedBlockPos;
import forestry.core.models.BlockModelEntry;

import binnie.core.Constants;
import binnie.botany.CreativeTabBotany;
import binnie.botany.ceramic.brick.CeramicBrickPair;
import binnie.botany.ceramic.brick.CeramicBrickType;
import binnie.botany.api.genetics.EnumFlowerColor;
import binnie.botany.tile.TileCeramic;
import binnie.botany.tile.TileCeramicBrick;
import binnie.core.block.IMultipassBlock;
import binnie.core.models.DefaultStateMapper;
import binnie.core.models.ModelManager;
import binnie.core.models.ModelMutlipass;
import binnie.core.util.TileUtil;

public class BlockCeramicBrick extends Block implements IMultipassBlock<CeramicBrickPair>, IColoredBlock, ISpriteRegister, IStateMapperRegister, IItemModelRegister {
	private static final PropertyEnum<CeramicBrickType> TYPE = PropertyEnum.create("type", CeramicBrickType.class);

	public BlockCeramicBrick() {
		super(Material.ROCK);
		setHardness(1.0f);
		setResistance(5.0f);
		setRegistryName("ceramicBrick");
		setCreativeTab(CreativeTabBotany.INSTANCE);
	}

	@Override
	public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {

	}

	@Override
	public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, @Nullable EntityPlayer player, boolean willHarvest) {
		List<ItemStack> drops = new ArrayList<>();
		TileCeramic ceramic = TileUtil.getTile(world, pos, TileCeramic.class);
		if (ceramic != null) {
			drops = Collections.singletonList(ceramic.getStack());
		}
		boolean hasBeenBroken = world.setBlockToAir(pos);
		if (hasBeenBroken && !world.isRemote && drops.size() > 0 && (player == null || !player.capabilities.isCreativeMode)) {
			for (ItemStack drop : drops) {
				spawnAsEntity(world, pos, drop);
			}
		}
		return hasBeenBroken;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileCeramicBrick();
	}

	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}

	@Override
	public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> itemList) {
		for (EnumFlowerColor color : EnumFlowerColor.values()) {
			itemList.add(new CeramicBrickPair(color, color, CeramicBrickType.TILE).getStack(1));
		}
		for (CeramicBrickType type : CeramicBrickType.VALUES) {
			if (type.canDouble()) {
				itemList.add(new CeramicBrickPair(EnumFlowerColor.Brown, EnumFlowerColor.Gold, type).getStack(1));
			}
		}
		itemList.add(new CeramicBrickPair(EnumFlowerColor.Gold, EnumFlowerColor.Gold, CeramicBrickType.SPLIT).getStack(1));
		itemList.add(new CeramicBrickPair(EnumFlowerColor.Brown, EnumFlowerColor.Brown, CeramicBrickType.CHEQUERED).getStack(1));
		itemList.add(new CeramicBrickPair(EnumFlowerColor.Gold, EnumFlowerColor.Brown, CeramicBrickType.LARGE_BRICK).getStack(1));
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerStateMapper() {
		ResourceLocation resourceLocation = new ResourceLocation(Constants.BOTANY_MOD_ID, "ceramicBrick");
		ModelLoader.setCustomStateMapper(this, new DefaultStateMapper(resourceLocation));
		ModelManager.registerCustomBlockModel(new BlockModelEntry(
				new ModelResourceLocation(resourceLocation, "normal"),
				new ModelResourceLocation(resourceLocation, "inventory"),
				new ModelMutlipass<>(BlockCeramicBrick.class),
				this
		));
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerModel(Item item, IModelManager manager) {
		manager.registerItemModel(item, new CeramicBrickMeshDefinition());
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new ExtendedBlockState(
				this,
				new IProperty[]{TYPE},
				new IUnlistedProperty[]{
						UnlistedBlockPos.POS,
						UnlistedBlockAccess.BLOCKACCESS
				}
		);
	}

	@Override
	@SuppressWarnings("deprecation")
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(TYPE, CeramicBrickType.VALUES[meta]);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(TYPE).ordinal();
	}

	@Override
	public IBlockState getExtendedState(IBlockState state, IBlockAccess world, BlockPos pos) {
		return ((IExtendedBlockState) super.getExtendedState(state, world, pos)).withProperty(UnlistedBlockPos.POS, pos).withProperty(UnlistedBlockAccess.BLOCKACCESS, world);
	}

	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
		ItemStack stack = placer.getHeldItem(hand);
		CeramicBrickPair pair = new CeramicBrickPair(stack);
		return getDefaultState().withProperty(TYPE, pair.getType());
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		TileCeramicBrick ceramic = TileUtil.getTile(worldIn, pos, TileCeramicBrick.class);
		if (ceramic != null) {
			CeramicBrickPair pair = new CeramicBrickPair(stack);
			ceramic.setColors(pair.getColorFirst(), pair.getColorSecond());
		}
	}

	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		TileCeramicBrick ceramic = TileUtil.getTile(world, pos, TileCeramicBrick.class);
		if (ceramic != null) {
			return ceramic.pair().getStack(1);
		}
		return super.getPickBlock(state, target, world, pos, player);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int colorMultiplier(IBlockState state, @Nullable IBlockAccess world, @Nullable BlockPos pos, int tintIndex) {
		if (world != null && pos != null) {
			TileCeramicBrick ceramic = TileUtil.getTile(world, pos, TileCeramicBrick.class);
			if (ceramic != null) {
				if (tintIndex == 1) {
					return ceramic.getColorFirst().getFlowerColorAllele().getColor(false);
				} else if (tintIndex == 2) {
					return ceramic.getColorSecond().getFlowerColorAllele().getColor(false);
				}
			}
		}
		return 0xffffff;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderPasses() {
		return 3;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public CeramicBrickPair getInventoryKey(ItemStack stack) {
		return new CeramicBrickPair(stack);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public CeramicBrickPair getWorldKey(IBlockState state) {
		IExtendedBlockState extendedState = (IExtendedBlockState) state;
		IBlockAccess world = extendedState.getValue(UnlistedBlockAccess.BLOCKACCESS);
		BlockPos pos = extendedState.getValue(UnlistedBlockPos.POS);
		TileCeramicBrick ceramic = TileUtil.getTile(world, pos, TileCeramicBrick.class);
		if (ceramic != null) {
			return ceramic.pair();
		}
		return CeramicBrickPair.EMPTY;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public TextureAtlasSprite getSprite(CeramicBrickPair type, @Nullable EnumFacing facing, int pass) {
		return type.getSprite(pass);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerSprites(ITextureManager manager) {
		TextureMap textureMap = Minecraft.getMinecraft().getTextureMapBlocks();
		for (CeramicBrickType type : CeramicBrickType.VALUES) {
			TextureAtlasSprite[] sprites = new TextureAtlasSprite[3];
			for (int i = 0; i < 3; ++i) {
				ResourceLocation location = new ResourceLocation(Constants.BOTANY_MOD_ID + ":blocks/ceramic." + type.getId() + '.' + i);
				sprites[i] = textureMap.registerSprite(location);
			}
			type.setSprites(sprites);
		}
	}

	private static class CeramicBrickMeshDefinition implements ItemMeshDefinition {
		@Override
		public ModelResourceLocation getModelLocation(ItemStack stack) {
			return new ModelResourceLocation(Constants.BOTANY_MOD_ID + ":ceramicBrick", "inventory");
		}
	}
}
