package binnie.botany.gardening;

import binnie.Binnie;
import binnie.botany.Botany;
import binnie.botany.CreativeTabBotany;
import binnie.botany.api.EnumAcidity;
import binnie.botany.api.EnumMoisture;
import binnie.botany.api.EnumSoilType;
import binnie.botany.api.IBlockSoil;
import forestry.api.core.IItemModelRegister;
import forestry.api.core.IModelManager;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class BlockSoil extends Block implements IBlockSoil, IItemModelRegister {

	private final boolean weedKilled;
	private final EnumSoilType type;

	public static final PropertyEnum<EnumMoisture> MOISTURE = PropertyEnum.create("moisture", EnumMoisture.class);
	public static final PropertyEnum<EnumAcidity> ACIDITY = PropertyEnum.create("acidity", EnumAcidity.class);
	public static final AxisAlignedBB SOIL_BLOCK_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.9375D, 1.0D);

	public BlockSoil(EnumSoilType type, String blockName, boolean weedKilled) {
		super(Material.GROUND);
		this.weedKilled = weedKilled;
		setUnlocalizedName("botany.soil." + type.getName());
		this.setCreativeTab(CreativeTabBotany.instance);
		this.setRegistryName(blockName);
		this.setTickRandomly(true);
		this.setLightOpacity(255);
		this.setHardness(0.5f);
		this.setSoundType(SoundType.GROUND);
		this.type = type;
    }

    @Override
	@SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
        switch (side){
            case UP:
                return true;
            case NORTH:
            case SOUTH:
            case WEST:
            case EAST:
                IBlockState iblockstate = blockAccess.getBlockState(pos.offset(side));
                Block block = iblockstate.getBlock();
                return !iblockstate.isOpaqueCube() && block != Blocks.FARMLAND && block != Blocks.GRASS_PATH && block != this;
            default:
                return super.shouldSideBeRendered(blockState, blockAccess, pos, side);
        }
    }

    /**
     * Used to determine ambient occlusion and culling when rebuilding chunks for render
     */
    @Override
	public boolean isOpaqueCube(IBlockState state){
        return false;
    }

    @Override
	public boolean isFullCube(IBlockState state){
        return false;
    }

	@Override
	public String getUnlocalizedName() {
		return super.getUnlocalizedName().replaceFirst("tile.", "");
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return SOIL_BLOCK_AABB;
	}

	@Nullable
	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
		return SOIL_BLOCK_AABB;
	}

	@Override
	public AxisAlignedBB getSelectedBoundingBox(IBlockState state, World worldIn, BlockPos pos) {
		return SOIL_BLOCK_AABB;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(final Item par1, final CreativeTabs par2CreativeTabs, final NonNullList<ItemStack> par3List) {
		for (int i = 0; i < 9; ++i) {
			par3List.add(new ItemStack(this, 1, i));
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerModel(Item item, IModelManager manager) {
		for (EnumAcidity acidity : EnumAcidity.values()) {
			for (EnumMoisture moisture : EnumMoisture.values()) {
				String modelName = "";
				if (acidity != EnumAcidity.Neutral) {
					modelName += acidity.getName();
				}
				if (moisture != EnumMoisture.Normal) {
					if (!modelName.isEmpty()) {
						modelName += "_";
					}
					modelName += moisture.getName();
				}
				if (modelName.isEmpty()) {
					modelName = "normal";
				}
				manager.registerItemModel(item, moisture.ordinal() + acidity.ordinal() * 3, type.getName() + (weedKilled ? "NoWeed" : "") + "/" + modelName);
			}
		}

	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, MOISTURE, ACIDITY);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return getMeta(state.getValue(ACIDITY), state.getValue(MOISTURE));
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		final EnumMoisture moisture = EnumMoisture.values()[meta % 3];
		final EnumAcidity acidity = EnumAcidity.values()[meta / 3];
		return getDefaultState().withProperty(MOISTURE, moisture).withProperty(ACIDITY, acidity);
	}

	public EnumSoilType getType() {
		return this.type;
	}

	@Override
	public int damageDropped(IBlockState state) {
		return getMetaFromState(state);
	}

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		int meta = getMetaFromState(state);
		EnumMoisture moisture = EnumMoisture.values()[meta % 3];
		EnumAcidity acidity = EnumAcidity.values()[meta / 3];
		EnumMoisture desiredMoisture = Gardening.getNaturalMoisture(world, pos);
		if (desiredMoisture.ordinal() > moisture.ordinal()) {
			moisture = ((moisture == EnumMoisture.Dry) ? EnumMoisture.Normal : EnumMoisture.Damp);
		}
		if (desiredMoisture.ordinal() < moisture.ordinal()) {
			moisture = ((moisture == EnumMoisture.Damp) ? EnumMoisture.Normal : EnumMoisture.Dry);
		}
		int meta2 = getMeta(acidity, moisture);
		if (meta != meta2) {
			world.setBlockState(pos, getStateFromMeta(meta2), 2);
		}
		if (!this.weedKilled && rand.nextInt(5 - getType(world, pos).ordinal()) == 0 && world.getBlockState(pos.up()).getBlock() == Blocks.AIR) {
			world.setBlockState(pos.up(), Botany.plant.getStateFromMeta(BlockPlant.Type.Weeds.ordinal()), 2);
		}
	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		super.neighborChanged(state, worldIn, pos, blockIn, fromPos);

		if (worldIn.getBlockState(pos.up()).getMaterial().isSolid()) {
			worldIn.setBlockState(pos, Blocks.DIRT.getDefaultState());
		}
	}

	@Override
	public EnumAcidity getPH(final World world, final BlockPos pos) {
		return world.getBlockState(pos).getValue(ACIDITY);
	}

	@Override
	public EnumMoisture getMoisture(final World world, final BlockPos pos) {
		return world.getBlockState(pos).getValue(MOISTURE);
	}

	@Override
	public EnumSoilType getType(final World world, final BlockPos pos) {
		return this.type;
	}

	@Override
	public boolean fertilise(final World world, final BlockPos pos, final EnumSoilType maxLevel) {
		final EnumSoilType type = this.getType(world, pos);
		if (type.ordinal() >= maxLevel.ordinal()) {
			return false;
		}
		final IBlockState old = world.getBlockState(pos);
		return world.setBlockState(pos, Gardening.getSoilBlock(maxLevel, this.weedKilled).getDefaultState().withProperty(ACIDITY, old.getValue(ACIDITY)).withProperty(MOISTURE, old.getValue(MOISTURE)), 2);
	}

	@Override
	public boolean degrade(final World world, final BlockPos pos, final EnumSoilType minLevel) {
		final EnumSoilType type = this.getType(world, pos);
		if (type.ordinal() <= minLevel.ordinal()) {
			return false;
		}
		final IBlockState old = world.getBlockState(pos);
		return world.setBlockState(pos, Gardening.getSoilBlock(minLevel, this.weedKilled).getDefaultState().withProperty(ACIDITY, old.getValue(ACIDITY)).withProperty(MOISTURE, old.getValue(MOISTURE)), 2);
	}

	@Override
	public boolean setPH(final World world, final BlockPos pos, final EnumAcidity pH) {
		final int meta = getMeta(pH, this.getMoisture(world, pos));
		return world.setBlockState(pos, world.getBlockState(pos).withProperty(ACIDITY, pH));
	}

	@Override
	public boolean setMoisture(final World world, final BlockPos pos, final EnumMoisture moisture) {
		final int meta = getMeta(this.getPH(world, pos), moisture);
		return world.setBlockState(pos, world.getBlockState(pos).withProperty(MOISTURE, moisture));
	}

	public static int getMeta(final EnumAcidity acid, final EnumMoisture moisture) {
		return acid.ordinal() * 3 + moisture.ordinal();
	}

	@Override
	public void onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos neighbor) {
		super.onNeighborChange(world, pos, neighbor);
		if (world.isSideSolid(pos.up(), EnumFacing.DOWN, false)) {
			((World) world).setBlockState(pos, Blocks.DIRT.getDefaultState(), 2);
		}
	}

	public static String getPH(ItemStack stack, boolean withColor, boolean byNeutralNone) {
		EnumAcidity ph = EnumAcidity.values()[stack.getItemDamage() / 3];
		if (byNeutralNone) {
			if (ph == EnumAcidity.Neutral) {
				return "";
			}
		}
		return TextFormatting.GRAY + Binnie.LANGUAGE.localise("botany.ph") + ": " + ph.getTranslated(withColor);
	}

	public static String getMoisture(ItemStack stack, boolean withColor, boolean byNormalNone) {
		EnumMoisture moisure = EnumMoisture.values()[stack.getItemDamage() % 3];
		if (byNormalNone) {
			if (moisure == EnumMoisture.Normal) {
				return "";
			}
		}
		return TextFormatting.GRAY + Binnie.LANGUAGE.localise("botany.moisture") + ": " + moisure.getTranslated(withColor);
	}

	@Override
	public boolean canSustainPlant(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing direction, IPlantable plantable) {
		IBlockState plant = plantable.getPlant(world, pos.up());
		if (plant.getBlock() == Botany.flower) {
			return true;
		}
		if (plant.getBlock() == Botany.plant) {
			return !this.weedKilled || !BlockPlant.isWeed(world, pos);
		}
		return world instanceof World && Blocks.DIRT.canSustainPlant(state, world, pos, direction, plantable);
	}

	@Override
	public boolean resistsWeeds(final World world, final BlockPos pos) {
		return this.weedKilled;
	}

	public boolean isWeedKilled() {
		return weedKilled;
	}
}
