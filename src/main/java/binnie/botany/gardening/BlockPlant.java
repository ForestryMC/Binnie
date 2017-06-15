package binnie.botany.gardening;

import java.util.ArrayList;
import java.util.List;
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
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.core.IItemModelRegister;
import forestry.api.core.IModelManager;

import binnie.botany.Botany;
import binnie.botany.CreativeTabBotany;
import binnie.botany.api.EnumSoilType;
import binnie.botany.api.IBlockSoil;

public class BlockPlant extends BlockBush implements IItemModelRegister {
	public static final PropertyEnum<Type> PLANT_TYPE = PropertyEnum.create("plant_type", Type.class);

	public BlockPlant() {
		this.setRegistryName("plant");
		this.setCreativeTab(CreativeTabBotany.instance);
		this.setTickRandomly(true);
		this.setSoundType(SoundType.PLANT);
	}

	public static boolean isWeed(final IBlockAccess world, final BlockPos pos) {
		if (!(world.getBlockState(pos).getBlock() instanceof BlockPlant)) {
			return false;
		}
		final Type type = world.getBlockState(pos).getValue(PLANT_TYPE);
		return type == Type.Weeds || type == Type.WeedsLong || type == Type.WeedsVeryLong;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerModel(Item item, IModelManager manager) {
		int index = 0;
		for (Type type : Type.values()) {
			manager.registerItemModel(item, index, type.getName());
			index++;
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
		return getDefaultState().withProperty(PLANT_TYPE, Type.values()[meta]);
	}

	@Override
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		return new ArrayList<>();
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		return super.canPlaceBlockAt(worldIn, pos) || Gardening.isSoil(worldIn.getBlockState(pos).getBlock());
	}

	@Override
	public int damageDropped(IBlockState state) {
		return getMetaFromState(state);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(final Item p_149666_1_, final CreativeTabs p_149666_2_, final NonNullList<ItemStack> subBlocks) {
		for (final Type type : Type.values()) {
			subBlocks.add(type.get());
		}
	}

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		final Type type = state.getValue(PLANT_TYPE);
		if (world.rand.nextInt(4) == 0) {
			if (type == Type.Weeds) {
				world.setBlockState(pos, state.withProperty(PLANT_TYPE, Type.WeedsLong), 2);
			} else if (type == Type.WeedsLong) {
				world.setBlockState(pos, state.withProperty(PLANT_TYPE, Type.WeedsVeryLong), 2);
			} else if (type == Type.DeadFlower) {
				world.setBlockState(pos, state.withProperty(PLANT_TYPE, Type.DecayingFlower), 2);
			} else if (type == Type.DecayingFlower) {
				world.setBlockToAir(pos);
				return;
			}
		}
		if (world.rand.nextInt(6) == 0) {
			if (type == Type.Weeds) {
				world.setBlockToAir(pos);
			} else if (type == Type.WeedsLong) {
				world.setBlockState(pos, state.withProperty(PLANT_TYPE, Type.Weeds), 2);
			} else if (type == Type.WeedsVeryLong) {
				world.setBlockState(pos, state.withProperty(PLANT_TYPE, Type.WeedsLong), 2);
			}
		}
		final Block below = world.getBlockState(pos.down()).getBlock();
		if (Gardening.isSoil(below)) {
			final IBlockSoil soil = (IBlockSoil) below;
			if (world.rand.nextInt(3) == 0) {
				if (type == Type.Weeds || type == Type.WeedsLong || type == Type.WeedsVeryLong) {
					if (!soil.degrade(world, pos.down(), EnumSoilType.LOAM)) {
						soil.degrade(world, pos.down(), EnumSoilType.SOIL);
					}
				} else if (type == Type.DecayingFlower && !soil.fertilise(world, pos.down(), EnumSoilType.LOAM)) {
					soil.fertilise(world, pos.down(), EnumSoilType.FLOWERBED);
				}
			}
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

	public enum Type implements IStringSerializable {
		Weeds("weeds"),
		WeedsLong("weeds_long"),
		WeedsVeryLong("weeds_very_long"),
		DeadFlower("dead_flower"),
		DecayingFlower("decaying_flower");

		String name;

		Type(final String name) {
			this.name = name;
		}

		public static Type get(final int id) {
			return values()[id % values().length];
		}

		public ItemStack get() {
			return new ItemStack(Botany.plant, 1, this.ordinal());
		}

		@Override
		public String getName() {
			return name;
		}
	}
}
