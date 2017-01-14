package binnie.extratrees.block.stairs;

import binnie.Constants;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.block.*;
import binnie.extratrees.genetics.WoodAccess;
import forestry.api.arboriculture.WoodBlockKind;
import forestry.api.core.IItemModelRegister;
import forestry.api.core.IModelManager;
import forestry.api.core.IStateMapperRegister;
import forestry.api.core.Tabs;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import java.util.Arrays;

public abstract class BlockETStairs extends BlockStairs implements IWoodKind, IItemModelRegister, IStateMapperRegister {
	private final boolean fireproof;
	protected static final int VARIANTS_PER_BLOCK = 1;
	protected static final PropertyEnumWoodType[] types = PropertyEnumWoodType.create(VARIANTS_PER_BLOCK);
	public static final String BLOCK_NAME = "stairs";
	private final int offset;

	public BlockETStairs(int offset, boolean fireproof) {
		super(WoodAccess.getInstance().getBlock(types[offset].getFirstValue(), WoodBlockKind.PLANKS, fireproof));
		this.setCreativeTab(Tabs.tabArboriculture);
		this.setRegistryName(BLOCK_NAME + "." + (fireproof ? "fireproof." : "") + offset);
		this.offset = offset;
		this.fireproof = fireproof;
		this.setUnlocalizedName(types[offset].getFirstValue().getName());
	}

	@Override
	public EnumExtraTreeLog getWoodType(int meta) {
		return types[offset].getFirstValue();
	}

	@Override
	public WoodBlockKind getWoodKind() {
		return WoodBlockKind.STAIRS;
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
	public void registerModel(Item item, IModelManager manager) {
		manager.registerItemModel(item, stack -> new ModelResourceLocation(Constants.EXTRA_TREES_MOD_ID + ":" + BLOCK_NAME + "." + getWoodType(0).getMetadata(), "facing=east,half=bottom,shape=straight"));
	}

	@Override
	public void registerStateMapper() {
		ExtraTrees.proxy.setCustomStateMapper(BLOCK_NAME + "." + getWoodType(0).getMetadata(), this);
	}

	public static void init() {
		Arrays.stream(types).forEach(type -> {
			BlockETStairs stairs = new BlockETStairs(type.getGroup(), false) {
				@Override
				public PropertyEnum<EnumExtraTreeLog> getVariant() {
					return type;
				}
			};

			BlockETStairs stairs_fireproof = new BlockETStairs(type.getGroup(), true) {
				@Override
				public PropertyEnum<EnumExtraTreeLog> getVariant() {
					return type;
				}
			};

			ExtraTrees.proxy.registerItem(new ItemETStairs<>(stairs));
			ExtraTrees.proxy.registerItem(new ItemETStairs<>(stairs_fireproof));
			ExtraTrees.proxy.registerBlock(stairs);
			ExtraTrees.proxy.registerBlock(stairs_fireproof);

			WoodAccess.getInstance().registerWithBlockState(stairs.getDefaultState(), stairs.getWoodKind(), type.getFirstValue(), true);
			WoodAccess.getInstance().registerWithBlockState(stairs_fireproof.getDefaultState(), stairs.getWoodKind(), type.getFirstValue(), false);
		});

	}
}
