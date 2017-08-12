package binnie.botany.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.core.IItemModelRegister;
import forestry.api.core.IModelManager;

import binnie.botany.CreativeTabBotany;
import binnie.botany.api.gardening.EnumSoilType;
import binnie.botany.api.gardening.IBlockSoil;
import binnie.botany.core.BotanyCore;

public class BlockPlant extends BlockBush implements IItemModelRegister {
	public static final PropertyEnum<PlantType> PLANT_TYPE = PropertyEnum.create("plant_type", PlantType.class);

	public BlockPlant() {
		setRegistryName("plant");
		setCreativeTab(CreativeTabBotany.instance);
		setTickRandomly(true);
		setSoundType(SoundType.PLANT);
	}

	public static boolean isWeed(IBlockAccess world, BlockPos pos) {
		IBlockState blockState = world.getBlockState(pos);
		if (!(blockState.getBlock() instanceof BlockPlant)) {
			return false;
		}

		PlantType type = blockState.getValue(PLANT_TYPE);
		return type.isWeed();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerModel(Item item, IModelManager manager) {
		for (int i = 0;i < PlantType.values().length;i++) {
			PlantType type = PlantType.getType(i);
			manager.registerItemModel(item, i, type.getName());
		}
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, PLANT_TYPE);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(PLANT_TYPE).ordinal();
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(PLANT_TYPE, PlantType.getType(meta));
	}

	@Override
	public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {

	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		return super.canPlaceBlockAt(worldIn, pos) || BotanyCore.getGardening().isSoil(worldIn.getBlockState(pos).getBlock());
	}

	@Override
	public int damageDropped(IBlockState state) {
		return getMetaFromState(state);
	}

	@Override
	public void getSubBlocks(CreativeTabs p_149666_2_, NonNullList<ItemStack> subBlocks) {
		for (PlantType type : PlantType.values()) {
			subBlocks.add(type.get());
		}
	}

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		PlantType type = state.getValue(PLANT_TYPE);
		if (rand.nextInt(4) == 0) {
			if (type == PlantType.WEEDS) {
				world.setBlockState(pos, state.withProperty(PLANT_TYPE, PlantType.WEEDS_LONG), 2);
			} else if (type == PlantType.WEEDS_LONG) {
				world.setBlockState(pos, state.withProperty(PLANT_TYPE, PlantType.WEEDS_VERY_LONG), 2);
			} else if (type == PlantType.DEAD_FLOWER) {
				world.setBlockState(pos, state.withProperty(PLANT_TYPE, PlantType.DECAYING_FLOWER), 2);
			} else if (type == PlantType.DECAYING_FLOWER) {
				world.setBlockToAir(pos);
				return;
			}
		}

		if (rand.nextInt(6) == 0) {
			if (type == PlantType.WEEDS) {
				world.setBlockToAir(pos);
			} else if (type == PlantType.WEEDS_LONG) {
				world.setBlockState(pos, state.withProperty(PLANT_TYPE, PlantType.WEEDS), 2);
			} else if (type == PlantType.WEEDS_VERY_LONG) {
				world.setBlockState(pos, state.withProperty(PLANT_TYPE, PlantType.WEEDS_LONG), 2);
			}
		}

		Block below = world.getBlockState(pos.down()).getBlock();
		if (!BotanyCore.getGardening().isSoil(below)) {
			return;
		}

		IBlockSoil soil = (IBlockSoil) below;
		if (rand.nextInt(3) != 0) {
			return;
		}

		if (type.isWeed()) {
			if (!soil.degrade(world, pos.down(), EnumSoilType.LOAM)) {
				soil.degrade(world, pos.down(), EnumSoilType.SOIL);
			}
		} else if (type == PlantType.DECAYING_FLOWER && !soil.fertilise(world, pos.down(), EnumSoilType.LOAM)) {
			soil.fertilise(world, pos.down(), EnumSoilType.FLOWERBED);
		}
	}

	@Override
	public boolean isReplaceable(IBlockAccess worldIn, BlockPos pos) {
		return true;
	}

	@Override
	public EnumOffsetType getOffsetType() {
		return EnumOffsetType.XZ;
	}
}
