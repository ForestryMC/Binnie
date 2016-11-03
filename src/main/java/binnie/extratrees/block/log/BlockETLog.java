package binnie.extratrees.block.log;

import binnie.Constants;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.block.EnumExtraTreeLog;
import binnie.extratrees.block.IWoodKind;
import binnie.extratrees.block.PropertyEnumWoodType;
import binnie.extratrees.genetics.WoodAccess;
import forestry.api.arboriculture.WoodBlockKind;
import forestry.api.core.IItemModelRegister;
import forestry.api.core.IModelManager;
import forestry.api.core.IStateMapperRegister;
import forestry.api.core.Tabs;
import net.minecraft.block.BlockLog;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public abstract class BlockETLog extends BlockLog implements IWoodKind, IStateMapperRegister, IItemModelRegister {
	protected static final int VARIANTS_PER_BLOCK = 4;
	protected static final int VARIANTS_META_MASK = VARIANTS_PER_BLOCK - 1;
	public static final String BLOCK_NAME = "log";
	protected int offset = -1;
	protected boolean fireproof;
	protected static PropertyEnumWoodType[] types = PropertyEnumWoodType.create(VARIANTS_PER_BLOCK);

	public BlockETLog(int offset, boolean fireproof) {
		this.setCreativeTab(Tabs.tabArboriculture);
		this.setRegistryName(BLOCK_NAME + "." + (fireproof ? "fireproof." : "") + offset);
		this.setResistance(5.0f);
		this.setHardness(2.0f);
		this.setSoundType(SoundType.WOOD);
		this.setUnlocalizedName(BLOCK_NAME + "." + (fireproof ? "fireproof." : "") + offset);
		this.offset = offset;
		this.fireproof = fireproof;
		setDefaultState(this.blockState.getBaseState().withProperty(types[offset], types[offset].getFirstValue()).withProperty(LOG_AXIS, EnumAxis.Y));
	}


	public abstract PropertyEnum<EnumExtraTreeLog> getVariant();

	public EnumExtraTreeLog getWoodType(int meta) {
		return EnumExtraTreeLog.values()[offset * VARIANTS_PER_BLOCK + (meta & VARIANTS_META_MASK)];
	}

	private static EnumAxis getAxis(int meta) {
		switch (meta & 12) {
			case 0:
				return EnumAxis.Y;
			case 4:
				return EnumAxis.X;
			case 8:
				return EnumAxis.Z;
			default:
				return EnumAxis.NONE;
		}
	}


	@Override
	public final IBlockState getStateFromMeta(int meta) {
		EnumExtraTreeLog woodType = getWoodType(meta);
		EnumAxis axis = getAxis(meta);
		return getDefaultState().withProperty(getVariant(), woodType).withProperty(LOG_AXIS, axis);
	}

	@SuppressWarnings("incomplete-switch")
	@Override
	public final int getMetaFromState(IBlockState state) {
		int i = 0;
		try {
			i = damageDropped(state);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		switch (state.getValue(LOG_AXIS)) {
			case X:
				i |= 4;
				break;
			case Z:
				i |= 8;
				break;
			case NONE:
				i |= 12;
		}

		return i;
	}

	@Override
	protected final BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, getVariant(), LOG_AXIS);
	}


	@Override
	public final int damageDropped(IBlockState state) {
		return state.getValue(getVariant()).getMetadata() % VARIANTS_PER_BLOCK;
	}

	@Override
	protected final ItemStack createStackedBlock(IBlockState state) {
		int meta = damageDropped(state);
		Item item = Item.getItemFromBlock(this);
		return new ItemStack(item, 1, meta);
	}

	@Override
	public final IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		EnumAxis axis = EnumAxis.fromFacingAxis(facing.getAxis());
		EnumExtraTreeLog woodType = getWoodType(meta);
		return getDefaultState().withProperty(getVariant(), woodType).withProperty(LOG_AXIS, axis);
	}

	@Override
	public final void getSubBlocks(Item item, CreativeTabs tab, List<ItemStack> list) {
		list.addAll(getVariant().getAllowedValues().stream().map(woodType -> new ItemStack(this, 1, woodType.getMetadata() % VARIANTS_PER_BLOCK)).collect(Collectors.toList()));
	}

	@Override
	public final float getBlockHardness(IBlockState blockState, World worldIn, BlockPos pos) {
		int meta = getMetaFromState(blockState);
		EnumExtraTreeLog woodType = getWoodType(meta);
		return woodType.getHardness();
	}

	@Override
	public final int getFireSpreadSpeed(IBlockAccess world, BlockPos pos, EnumFacing face) {
		if (fireproof) {
			return 0;
		} else if (face == EnumFacing.DOWN) {
			return 20;
		} else if (face != EnumFacing.UP) {
			return 10;
		} else {
			return 5;
		}
	}

	@Override
	public final int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face) {
		if (fireproof) {
			return 0;
		}
		return 5;
	}

	@Override
	public WoodBlockKind getWoodKind() {
		return WoodBlockKind.LOG;
	}

	@Override
	public void registerModel(Item item, IModelManager manager) {
		manager.registerItemModel(item, stack -> new ModelResourceLocation(Constants.EXTRA_TREES_MOD_ID + ":" + BLOCK_NAME, "axis=y,wood_type=" + getWoodType(stack.getMetadata()).getName()));
	}

	@Override
	public void registerStateMapper() {
		ExtraTrees.proxy.setCustomStateMapper(BLOCK_NAME, this);
	}

	public static void init() {
		Arrays.stream(types).forEach(type -> {
			BlockETLog log = new BlockETLog(type.getGroup(), false) {
				@Override
				public PropertyEnum<EnumExtraTreeLog> getVariant() {
					return type;
				}
			};

			BlockETLog log_fireproof = new BlockETLog(type.getGroup(), true) {
				@Override
				public PropertyEnum<EnumExtraTreeLog> getVariant() {
					return type;
				}
			};

			ExtraTrees.proxy.registerItem(new ItemETLog<>(log));
			ExtraTrees.proxy.registerItem(new ItemETLog<>(log_fireproof));
			ExtraTrees.proxy.registerBlock(log);
			ExtraTrees.proxy.registerBlock(log_fireproof);

			WoodAccess.getInstance().registerWithVariants(log, log.getWoodKind(), type, true);
			WoodAccess.getInstance().registerWithVariants(log_fireproof, log.getWoodKind(), type, false);
		});

	}
}
