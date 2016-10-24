package binnie.botany.gardening;

import binnie.botany.Botany;
import binnie.botany.CreativeTabBotany;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BlockPlant extends BlockBush {
    public static final PropertyEnum<Type> PLANT_TYPE = PropertyEnum.create("plant_type", Type.class);

//	@Override
//	@SideOnly(Side.CLIENT)
//	public void registerBlockIcons(final IIconRegister p_149651_1_) {
//		for (final Type t : Type.values()) {
//			t.icon = Botany.proxy.getIcon(p_149651_1_, t.name().toLowerCase());
//		}
//	}

    public BlockPlant() {
        this.setRegistryName("plant");
        this.setCreativeTab(CreativeTabBotany.instance);
        this.setTickRandomly(true);
    }
    
    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        return new ArrayList<>();
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        return super.canPlaceBlockAt(worldIn, pos) || Gardening.isSoil(worldIn.getBlockState(pos).getBlock());
    }

//	@Override
//	@SideOnly(Side.CLIENT)
//	public IIcon getIcon(final int side, final int meta) {
//		return Type.values()[meta % Type.values().length].icon;
//	}

//	@Override
//	public int damageDropped(final int p_149692_1_) {
//		return p_149692_1_;
//	}

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(final Item p_149666_1_, final CreativeTabs p_149666_2_, final List p_149666_3_) {
        for (final Type type : Type.values()) {
            p_149666_3_.add(type.get());
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
//			final IBlockSoil soil = (IBlockSoil) below;
//			if (world.rand.nextInt(3) == 0) {
//				if (type == Type.Weeds || type == Type.WeedsLong || type == Type.WeedsVeryLong) {
//					if (!soil.degrade(world, pos.down(), EnumSoilType.LOAM)) {
//						soil.degrade(world, pos.down(), EnumSoilType.SOIL);
//					}
//				} else if (type == Type.DecayingFlower && !soil.fertilise(world, pos.down(), EnumSoilType.LOAM)) {
//					soil.fertilise(world, pos.down(), EnumSoilType.FLOWERBED);
//				}
//			}
        }
    }

    @Override
    public boolean isReplaceable(IBlockAccess worldIn, BlockPos pos) {
        return true;
    }

    public static boolean isWeed(final IBlockAccess world, final BlockPos pos) {
        if (!(world.getBlockState(pos).getBlock() instanceof BlockPlant)) {
            return false;
        }
        final Type type = world.getBlockState(pos).getValue(PLANT_TYPE);
        return type == Type.Weeds || type == Type.WeedsLong || type == Type.WeedsVeryLong;
    }

    public enum Type implements IStringSerializable {
        Weeds("Weeds"),
        WeedsLong("Long Weeds"),
        WeedsVeryLong("Very Long Weeds"),
        DeadFlower("Dead Flower"),
        DecayingFlower("Decaying Flower");

        //		public IIcon icon;
        String name;

        Type(final String name) {
            this.name = name;
        }

        public ItemStack get() {
            return new ItemStack(Botany.plant, 1, this.ordinal());
        }

        public static Type get(final int id) {
            return values()[id % values().length];
        }

        @Override
		public String getName() {
            return this.name;
        }
    }
}
