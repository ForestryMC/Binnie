package binnie.extratrees.block.slab;

import binnie.Constants;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.block.EnumExtraTreeLog;
import binnie.extratrees.block.IWoodKind;
import binnie.extratrees.block.PropertyEnumWoodType;
import binnie.extratrees.block.log.BlockETLog;
import binnie.extratrees.block.log.ItemETLog;
import binnie.extratrees.block.plank.BlockETPlank;
import binnie.extratrees.genetics.WoodAccess;
import forestry.api.arboriculture.WoodBlockKind;
import forestry.api.core.IItemModelRegister;
import forestry.api.core.IModelManager;
import forestry.api.core.IStateMapperRegister;
import forestry.api.core.Tabs;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public abstract class BlockETSlab extends BlockSlab implements IWoodKind, IItemModelRegister, IStateMapperRegister{
	protected static final int VARIANTS_PER_BLOCK = 8;
	protected static final int VARIANTS_META_MASK = VARIANTS_PER_BLOCK - 1;
	public static final String BLOCK_NAME = "slab";
	private final boolean fireproof;
	private final int offset;
	protected static final PropertyEnumWoodType[] types = PropertyEnumWoodType.create(VARIANTS_PER_BLOCK);

	public BlockETSlab(int offset, boolean fireproof) {
		super(Material.WOOD);
		this.fireproof = fireproof;
		this.offset = offset;
		setCreativeTab(Tabs.tabArboriculture);
		setHardness(2.0f);
		setResistance(5.0f);
		setSoundType(SoundType.WOOD);
		setLightOpacity(0);
		setRegistryName(BLOCK_NAME + "." + (fireproof ? "fireproof." : "") + (isDouble()?"double.":"") + offset);
	}

	public EnumExtraTreeLog getWoodType(int meta) {
		return EnumExtraTreeLog.values()[offset * VARIANTS_PER_BLOCK + (meta & VARIANTS_META_MASK)];
	}

	@Nonnull
	@Override
	protected BlockStateContainer createBlockState() {
		return this.isDouble() ? new BlockStateContainer(this, getVariant()) : new BlockStateContainer(this, HALF, getVariant());
	}

	@Nonnull
	@Override
	public IBlockState getStateFromMeta(int meta) {
		EnumExtraTreeLog woodType = getWoodType(meta);
		IBlockState iblockstate = this.getDefaultState().withProperty(getVariant(), woodType);

		if (!this.isDouble()) {
			iblockstate = iblockstate.withProperty(HALF, (meta & 8) == 0 ? BlockSlab.EnumBlockHalf.BOTTOM : BlockSlab.EnumBlockHalf.TOP);
		}

		return iblockstate;
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		int meta = damageDropped(state);

		if (!this.isDouble() && state.getValue(HALF) == BlockSlab.EnumBlockHalf.TOP) {
			meta |= 8;
		}

		return meta;
	}

	@Override
	public int damageDropped(IBlockState state) {
		EnumExtraTreeLog woodType = state.getValue(getVariant());
		return woodType.getMetadata() - offset * VARIANTS_PER_BLOCK;
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		EnumExtraTreeLog woodType = state.getValue(getVariant());
		ItemStack slab = WoodAccess.getInstance().getStack(woodType, WoodBlockKind.SLAB, fireproof);
		return slab.getItem();
	}

	@SuppressWarnings("deprecation") // this is the way the vanilla slabs work
	@Override
	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
		EnumExtraTreeLog woodType = state.getValue(getVariant());
		ItemStack slab = WoodAccess.getInstance().getStack(woodType, WoodBlockKind.SLAB, fireproof);
		return new ItemStack(slab.getItem(), 1, getMetaFromState(state));
	}

	@Override
	public void getSubBlocks(Item item, CreativeTabs par2CreativeTabs, List<ItemStack> list) {
		if (!isDouble()) {
			list.addAll(getVariant().getAllowedValues().stream().map(woodType -> new ItemStack(this, 1, woodType.getMetadata() % VARIANTS_PER_BLOCK)).collect(Collectors.toList()));
		}
	}

	@Override
	public float getBlockHardness(IBlockState blockState, World worldIn, BlockPos pos) {
		int meta = getMetaFromState(blockState);
		EnumExtraTreeLog woodType = getWoodType(meta);
		return woodType.getHardness();
	}

	@Override
	public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face) {
		return fireproof ? 0 : 20;
	}

	@Override
	public int getFireSpreadSpeed(IBlockAccess world, BlockPos pos, EnumFacing face) {
		return fireproof ? 0 : 5;
	}

	@Override
	public String getUnlocalizedName(int meta) {
		EnumExtraTreeLog woodType = getWoodType(meta);
		return woodType.name();
	}

	@Override
	public boolean isDouble() {
		return false;
	}

	@Override
	public IProperty<?> getVariantProperty() {
		return getVariant();
	}

	@Override
	public Comparable<?> getTypeForItem(ItemStack stack) {
		return getWoodType(stack.getMetadata());
	}

	@Override
	public WoodBlockKind getWoodKind() {
		return WoodBlockKind.SLAB;
	}

	@Override
	public void registerModel(Item item, IModelManager manager) {
		if(!isDouble())
			manager.registerItemModel(item, stack -> new ModelResourceLocation(Constants.EXTRA_TREES_MOD_ID + ":" + BLOCK_NAME, "half=bottom,wood_type=" + getWoodType(stack.getMetadata()).getName()));
	}

	@Override
	public void registerStateMapper() {
		if(isDouble())
			ExtraTrees.proxy.setCustomStateMapper(BlockETPlank.BLOCK_NAME, this);
		else
			ExtraTrees.proxy.setCustomStateMapper(BLOCK_NAME, this);
	}

	public static void init() {
		Arrays.stream(types).forEach(type -> {
			BlockETSlab slab = new BlockETSlab(type.getGroup(), false) {
				@Override
				public PropertyEnum<EnumExtraTreeLog> getVariant() {
					return type;
				}
			};

			BlockETSlab slab_double = new BlockETSlab(type.getGroup(), false) {
				@Override
				public PropertyEnum<EnumExtraTreeLog> getVariant() {
					return type;
				}

				@Override
				public boolean isDouble() {
					return true;
				}
			};

			BlockETSlab slab_fireproof = new BlockETSlab(type.getGroup(), true) {
				@Override
				public PropertyEnum<EnumExtraTreeLog> getVariant() {
					return type;
				}
			};

			BlockETSlab slab_double_fireproof = new BlockETSlab(type.getGroup(), true) {
				@Override
				public PropertyEnum<EnumExtraTreeLog> getVariant() {
					return type;
				}

				@Override
				public boolean isDouble() {
					return true;
				}
			};

			ExtraTrees.proxy.registerItem(new ItemETSlab<>(slab, slab_double));
			ExtraTrees.proxy.registerItem(new ItemETSlab<>(slab_fireproof, slab_double_fireproof));
			ExtraTrees.proxy.registerBlock(slab);
			ExtraTrees.proxy.registerBlock(slab_fireproof);
			ExtraTrees.proxy.registerBlock(slab_double);
			ExtraTrees.proxy.registerBlock(slab_double_fireproof);

			WoodAccess.getInstance().registerWithVariants(slab, slab.getWoodKind(), type, true);
			WoodAccess.getInstance().registerWithVariants(slab_fireproof, slab.getWoodKind(), type, false);
		});

	}
}
