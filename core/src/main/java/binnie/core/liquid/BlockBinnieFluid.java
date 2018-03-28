package binnie.core.liquid;

import forestry.api.core.IItemModelRegister;
import forestry.api.core.IModelManager;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
        double d0 = (double)pos.getX();
        double d1 = (double)pos.getY();
        double d2 = (double)pos.getZ();

        if (this.blockMaterial == Material.WATER)
        {
            int i = stateIn.getValue(LEVEL);

            if (i > 0 && i < 8)
            {
                if (rand.nextInt(64) == 0)
                {
                    worldIn.playSound(d0 + 0.5D, d1 + 0.5D, d2 + 0.5D, SoundEvents.BLOCK_WATER_AMBIENT, SoundCategory.BLOCKS, rand.nextFloat() * 0.25F + 0.75F, rand.nextFloat() + 0.5F, false);
                }
            }
            else if (rand.nextInt(10) == 0)
            {
                worldIn.spawnParticle(EnumParticleTypes.SUSPENDED, d0 + (double)rand.nextFloat(), d1 + (double)rand.nextFloat(), d2 + (double)rand.nextFloat(), 0.0D, 0.0D, 0.0D);
            }
        }

        if (this.blockMaterial == Material.LAVA && worldIn.getBlockState(pos.up()).getMaterial() == Material.AIR && !worldIn.getBlockState(pos.up()).isOpaqueCube())
        {
            if (rand.nextInt(100) == 0)
            {
                double d8 = d0 + (double)rand.nextFloat();
                double d4 = d1 + stateIn.getBoundingBox(worldIn, pos).maxY;
                double d6 = d2 + (double)rand.nextFloat();
                worldIn.spawnParticle(EnumParticleTypes.LAVA, d8, d4, d6, 0.0D, 0.0D, 0.0D);
                worldIn.playSound(d8, d4, d6, SoundEvents.BLOCK_LAVA_POP, SoundCategory.BLOCKS, 0.2F + rand.nextFloat() * 0.2F, 0.9F + rand.nextFloat() * 0.15F, false);
            }

            if (rand.nextInt(200) == 0)
            {
                worldIn.playSound(d0, d1, d2, SoundEvents.BLOCK_LAVA_AMBIENT, SoundCategory.BLOCKS, 0.2F + rand.nextFloat() * 0.2F, 0.9F + rand.nextFloat() * 0.15F, false);
            }
        }

        if (rand.nextInt(10) == 0 && worldIn.getBlockState(pos.down()).isTopSolid())
        {
            Material material = worldIn.getBlockState(pos.down(2)).getMaterial();

            if (!material.blocksMovement() && !material.isLiquid())
            {
                double d3 = d0 + (double)rand.nextFloat();
                double d5 = d1 - 1.05D;
                double d7 = d2 + (double)rand.nextFloat();

                if (this.blockMaterial == Material.WATER)
                {
                    worldIn.spawnParticle(EnumParticleTypes.DRIP_WATER, d3, d5, d7, 0.0D, 0.0D, 0.0D);
                }
                else
                {
                    worldIn.spawnParticle(EnumParticleTypes.DRIP_LAVA, d3, d5, d7, 0.0D, 0.0D, 0.0D);
                }
            }
        }
    }
}
