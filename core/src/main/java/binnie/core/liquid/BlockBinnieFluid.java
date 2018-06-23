package binnie.core.liquid;

import forestry.api.core.IItemModelRegister;
import forestry.api.core.IModelManager;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.Random;

public class BlockBinnieFluid extends BlockFluidClassic implements IItemModelRegister {

    private final boolean flammable;
    private final int flammability;
    private final Color color;

    public BlockBinnieFluid(FluidType binnieFluid) {
        this(binnieFluid, 0, false);
    }

    public BlockBinnieFluid(FluidType binnieFluid, int flammability, boolean flammable) {
        this(binnieFluid.getFluid(), flammability, flammable, binnieFluid.getParticleColor());
    }

    private BlockBinnieFluid(Fluid fluid, int flammability, boolean flammable, Color color) {
        super(fluid, Material.WATER);

        setDensity(fluid.getDensity());

        this.flammability = flammability;
        this.flammable = flammable;

        this.color = color;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerModel(Item item, IModelManager manager) {

    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        double d0 = (double) pos.getX();
        double d1 = (double) pos.getY();
        double d2 = (double) pos.getZ();

        if (this.blockMaterial == Material.WATER) {
            int i = stateIn.getValue(LEVEL);

            if (i > 0 && i < 8) {
                if (rand.nextInt(64) == 0) {
                    worldIn.playSound(d0 + 0.5D, d1 + 0.5D, d2 + 0.5D, SoundEvents.BLOCK_WATER_AMBIENT, SoundCategory.BLOCKS, rand.nextFloat() * 0.25F + 0.75F, rand.nextFloat() + 0.5F, false);
                }
            } else if (rand.nextInt(10) == 0) {
                worldIn.spawnParticle(EnumParticleTypes.SUSPENDED, d0 + (double) rand.nextFloat(), d1 + (double) rand.nextFloat(), d2 + (double) rand.nextFloat(), 0.0D, 0.0D, 0.0D);
            }
        }

        if (this.blockMaterial == Material.LAVA && worldIn.getBlockState(pos.up()).getMaterial() == Material.AIR && !worldIn.getBlockState(pos.up()).isOpaqueCube()) {
            if (rand.nextInt(100) == 0) {
                double d8 = d0 + (double) rand.nextFloat();
                double d4 = d1 + stateIn.getBoundingBox(worldIn, pos).maxY;
                double d6 = d2 + (double) rand.nextFloat();
                worldIn.spawnParticle(EnumParticleTypes.LAVA, d8, d4, d6, 0.0D, 0.0D, 0.0D);
                worldIn.playSound(d8, d4, d6, SoundEvents.BLOCK_LAVA_POP, SoundCategory.BLOCKS, 0.2F + rand.nextFloat() * 0.2F, 0.9F + rand.nextFloat() * 0.15F, false);
            }

            if (rand.nextInt(200) == 0) {
                worldIn.playSound(d0, d1, d2, SoundEvents.BLOCK_LAVA_AMBIENT, SoundCategory.BLOCKS, 0.2F + rand.nextFloat() * 0.2F, 0.9F + rand.nextFloat() * 0.15F, false);
            }
        }

        if (rand.nextInt(10) == 0 && worldIn.getBlockState(pos.down()).isTopSolid()) {
            Material material = worldIn.getBlockState(pos.down(2)).getMaterial();

            if (!material.blocksMovement() && !material.isLiquid()) {
                double d3 = d0 + (double) rand.nextFloat();
                double d5 = d1 - 1.05D;
                double d7 = d2 + (double) rand.nextFloat();

                if (this.blockMaterial == Material.WATER) {
                    worldIn.spawnParticle(EnumParticleTypes.DRIP_WATER, d3, d5, d7, 0.0D, 0.0D, 0.0D);
                } else {
                    worldIn.spawnParticle(EnumParticleTypes.DRIP_LAVA, d3, d5, d7, 0.0D, 0.0D, 0.0D);
                }
            }
        }
    }

    @Override
    public boolean canDisplace(IBlockAccess world, BlockPos pos) {
        IBlockState blockState = world.getBlockState(pos);
        return !blockState.getMaterial().isLiquid() &&
                super.canDisplace(world, pos);
    }

    @Override
    public boolean displaceIfPossible(World world, BlockPos pos) {
        IBlockState blockState = world.getBlockState(pos);
        return !blockState.getMaterial().isLiquid() &&
                super.displaceIfPossible(world, pos);
    }

    @Override
    public int getFireSpreadSpeed(IBlockAccess world, BlockPos pos, EnumFacing face) {
        return flammable ? 30 : 0;
    }

    @Override
    public int getFlammability(IBlockAccess world, BlockPos pos, @Nullable EnumFacing face) {
        return flammability;
    }

    private static boolean isFlammable(IBlockAccess world, BlockPos pos) {
        IBlockState blockState = world.getBlockState(pos);
        Block block = blockState.getBlock();
        return block.isFlammable(world, pos, EnumFacing.UP);
    }

    @Override
    public boolean isFlammable(IBlockAccess world, BlockPos pos, EnumFacing face) {
        return flammable;
    }

    @Override
    public boolean isFireSource(World world, BlockPos pos, EnumFacing side) {
        return flammable && flammability == 0;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public Material getMaterial(IBlockState state) {
        // Fahrenheit 451 = 505.928 Kelvin
        // The temperature at which book-paper catches fire, and burns.
        if (temperature > 505) {
            return Material.LAVA;
        } else {
            return super.getMaterial(state);
        }
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
        super.updateTick(world, pos, state, rand);

        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();

        // Start fires if the fluid is lava-like
        if (getMaterial(state) == Material.LAVA) {
            int rangeUp = rand.nextInt(3);

            for (int i = 0; i < rangeUp; ++i) {
                x += rand.nextInt(3) - 1;
                ++y;
                z += rand.nextInt(3) - 1;
                IBlockState blockState = world.getBlockState(new BlockPos(x, y, z));
                if (blockState.getMaterial() == Material.AIR) {
                    if (isNeighborFlammable(world, x, y, z)) {
                        world.setBlockState(new BlockPos(x, y, z), Blocks.FIRE.getDefaultState());
                        return;
                    }
                } else if (blockState.getMaterial().blocksMovement()) {
                    return;
                }
            }

            if (rangeUp == 0) {
                int startX = x;
                int startZ = z;

                for (int i = 0; i < 3; ++i) {
                    x = startX + rand.nextInt(3) - 1;
                    z = startZ + rand.nextInt(3) - 1;

                    BlockPos posAbove = new BlockPos(pos.getX(), y + 1, z);
                    if (world.isAirBlock(posAbove) && isFlammable(world, new BlockPos(x, y, z))) {
                        world.setBlockState(posAbove, Blocks.FIRE.getDefaultState());
                    }
                }
            }
        }

        // explode if very flammable and near fire
        int flammability = getFlammability(world, pos, null);
        if (flammability > 0) {
            // Explosion size is determined by flammability, up to size 4.
            float explosionSize = 4F * flammability / 300F;
            if (explosionSize > 1.0 && isNearFire(world, pos.getX(), pos.getY(), pos.getZ())) {
                world.setBlockState(pos, Blocks.FIRE.getDefaultState());
                world.newExplosion(null, pos.getX(), pos.getY(), pos.getZ(), explosionSize, true, true);
            }
        }
    }

    private static boolean isNeighborFlammable(World world, int x, int y, int z) {
        return isFlammable(world, new BlockPos(x - 1, y, z)) ||
                isFlammable(world, new BlockPos(x + 1, y, z)) ||
                isFlammable(world, new BlockPos(x, y, z - 1)) ||
                isFlammable(world, new BlockPos(x, y, z + 1)) ||
                isFlammable(world, new BlockPos(x, y - 1, z)) ||
                isFlammable(world, new BlockPos(x, y + 1, z));
    }

    private static boolean isNearFire(World world, int x, int y, int z) {
        AxisAlignedBB boundingBox = new AxisAlignedBB(x - 1, y - 1, z - 1, x + 1, y + 1, z + 1);
        return world.isFlammableWithin(boundingBox);
    }
}
