package binnie.extratrees.block.plank;

import binnie.Constants;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.block.EnumExtraTreeLog;
import binnie.extratrees.block.IWoodKind;
import binnie.extratrees.block.PropertyEnumWoodType;
import binnie.extratrees.block.log.BlockETLog;
import binnie.extratrees.block.log.ItemETLog;
import binnie.extratrees.genetics.WoodAccess;
import forestry.api.arboriculture.WoodBlockKind;
import forestry.api.core.IItemModelRegister;
import forestry.api.core.IModelManager;
import forestry.api.core.IStateMapperRegister;
import forestry.api.core.Tabs;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public abstract class BlockETPlank extends Block implements IWoodKind, IItemModelRegister, IStateMapperRegister {
	protected static final int VARIANTS_PER_BLOCK = 16;
	protected static final int VARIANTS_META_MASK = VARIANTS_PER_BLOCK - 1;
	public static final String BLOCK_NAME = "plank";
	private final boolean fireproof;
	protected int offset = -1;
	protected static PropertyEnumWoodType[] types = PropertyEnumWoodType.create(VARIANTS_PER_BLOCK);

	public BlockETPlank(int offset, boolean fireproof) {
		super(Material.WOOD);
		this.fireproof = fireproof;
		this.offset = offset;
		this.setRegistryName(BLOCK_NAME + "." + (fireproof ? "fireproof." : "") + offset);
		this.setCreativeTab(Tabs.tabArboriculture);
		this.setResistance(5.0f);
		this.setHardness(2.0f);
		this.setHarvestLevel("axe", 0);
		this.setSoundType(SoundType.WOOD);
	}

	@Override
	public float getBlockHardness(IBlockState blockState, World worldIn, BlockPos pos) {
		int meta = getMetaFromState(blockState);
		EnumExtraTreeLog woodType = getWoodType(meta);
		return woodType.getHardness();
	}

	@Override
	public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face) {
		if (fireproof) {
			return 0;
		}
		return 20;
	}

	@Override
	public int getFireSpreadSpeed(IBlockAccess world, BlockPos pos, EnumFacing face) {
		if (fireproof) {
			return 0;
		}
		return 5;
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return damageDropped(state);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		EnumExtraTreeLog woodType = getWoodType(meta);
		return getDefaultState().withProperty(getVariant(), woodType);
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, getVariant());
	}

	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List<ItemStack> list) {
		list.addAll(getVariant().getAllowedValues().stream().map(woodType -> new ItemStack(this, 1, woodType.getMetadata() % VARIANTS_PER_BLOCK)).collect(Collectors.toList()));
	}

	@Override
	public final int damageDropped(IBlockState state) {
		return state.getValue(getVariant()).getMetadata() % VARIANTS_PER_BLOCK;
	}

	@Override
	public final IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		EnumExtraTreeLog woodType = getWoodType(meta);
		return getDefaultState().withProperty(getVariant(), woodType);
	}

	public EnumExtraTreeLog getWoodType(int meta) {
		return EnumExtraTreeLog.values()[offset * VARIANTS_PER_BLOCK + (meta & VARIANTS_META_MASK)];
	}

	@Override
	public WoodBlockKind getWoodKind() {
		return WoodBlockKind.PLANKS;
	}

	@Override
	public void registerStateMapper() {
		ExtraTrees.proxy.setCustomStateMapper(BLOCK_NAME, this);
	}

	@Override
	public void registerModel(Item item, IModelManager manager) {
		manager.registerItemModel(item, stack -> new ModelResourceLocation(Constants.EXTRA_TREES_MOD_ID + ":" + BLOCK_NAME, "wood_type=" + getWoodType(stack.getMetadata()).getName()));
	}

	public static void init() {
		Arrays.stream(types).forEach(type -> {
			BlockETPlank plank = new BlockETPlank(type.getGroup(), false) {
				@Override
				public PropertyEnum<EnumExtraTreeLog> getVariant() {
					return type;
				}
			};

			BlockETPlank plank_fireproof = new BlockETPlank(type.getGroup(), true) {
				@Override
				public PropertyEnum<EnumExtraTreeLog> getVariant() {
					return type;
				}
			};

			ExtraTrees.proxy.registerItem(new ItemETPlank<>(plank));
			ExtraTrees.proxy.registerItem(new ItemETPlank<>(plank_fireproof));
			ExtraTrees.proxy.registerBlock(plank);
			ExtraTrees.proxy.registerBlock(plank_fireproof);

			WoodAccess.getInstance().registerWithVariants(plank, plank.getWoodKind(), type, true);
			WoodAccess.getInstance().registerWithVariants(plank_fireproof, plank.getWoodKind(), type, false);
		});

	}

}
