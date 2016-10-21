package binnie.botany.gardening;

import binnie.botany.Botany;
import binnie.botany.api.EnumAcidity;
import binnie.botany.api.EnumMoisture;
import binnie.botany.api.EnumSoilType;
import binnie.botany.api.IFlower;
import binnie.botany.api.gardening.IBlockSoil;
import binnie.botany.flower.TileEntityFlower;
import binnie.botany.items.BotanyItems;
import binnie.core.BinnieCore;
import com.mojang.authlib.GameProfile;
import forestry.api.core.EnumTemperature;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class Gardening {
    public static Map<ItemStack, Integer> fertiliserAcid;
    public static Map<ItemStack, Integer> fertiliserAlkaline;
    public static Map<ItemStack, Integer> fertiliserNutrient;

    public static boolean isSoil(final Block block) {
        return block instanceof IBlockSoil;
    }

    public static boolean isSoil(final Item item) {
        return item instanceof ItemBlock && isSoil(((ItemBlock) item).getBlock());
    }

    public static boolean isSoil(final ItemStack item) {
        return isSoil(item.getItem());
    }

//	public static EnumMoisture getNaturalMoisture(final World world, final BlockPos pos) {
//		float bias = getBiomeMoisture(world.getBiome(pos), pos.getY());
//		for (int dx = -1; dx < 2; ++dx) {
//			for (int dz = -1; dz < 2; ++dz) {
//				if (dx != 0 || dz != 0) {
//					if (world.getBlock(x + dx, y, z + dz) == Blocks.sand) {
//						bias -= 1.5;
//					}
//					else if (world.getBlock(x + dx, y, z + dz) == Blocks.water) {
//						bias += 1.5;
//					}
//				}
//			}
//		}
//		if (world.isRaining() && world.canBlockSeeTheSky(x, y + 1, z) && world.getPrecipitationHeight(x, z) <= y + 1) {
//			bias += 1.5;
//		}
//		for (int dx = -1; dx < 2; ++dx) {
//			for (int dz = -1; dz < 2; ++dz) {
//				if (dx != 0 || dz != 0) {
//					if (world.getBlock(x + dx, y, z + dz) == Blocks.gravel && bias > 0.0f) {
//						bias *= 0.4f;
//					}
//				}
//			}
//		}
//		return (bias <= -1.0f) ? EnumMoisture.Dry : ((bias >= 1.0f) ? EnumMoisture.Damp : EnumMoisture.Normal);
//	}

    public static EnumAcidity getNaturalPH(final World world, final BlockPos pos) {
        final float bias = getBiomePH(world.getBiome(pos), pos.getY());
        return (bias <= -1.0f) ? EnumAcidity.Acid : ((bias >= 1.0f) ? EnumAcidity.Alkaline : EnumAcidity.Neutral);
    }

    public static float getBiomeMoisture(final Biome biome, final int H) {
        final double R = biome.getRainfall();
        final double T = biome.getTemperature();
        final double m = 3.2 * (R - 0.5) - 0.4 * (1.0 + T + 0.5 * T * T) + 1.1 - 1.6 * (T - 0.9) * (T - 0.9) - 0.002 * (H - 64);
        return (float) ((m == 0.0) ? m : ((m < 0.0) ? (-Math.sqrt(m * m)) : Math.sqrt(m * m)));
    }

    public static float getBiomePH(final Biome biome, final int H) {
        final double R = biome.getRainfall();
        final double T = biome.getTemperature();
        return (float) (-3.0 * (R - 0.5) + 0.5 * (T - 0.699999988079071) * (T - 0.699999988079071) + 0.02f * (H - 64) - 0.15000000596046448);
    }

//	public static void createSoil(final World world, final BlockPos pos, final EnumSoilType soil, final EnumMoisture moisture, final EnumAcidity acidity) {
//		final int meta = moisture.ordinal() + acidity.ordinal() * 3;
//		world.setBlockState(x, y, z, getSoilBlock(soil), meta, 2);
//	}

    public static boolean plant(final World world, final BlockPos pos, final IFlower flower, final GameProfile owner) {
        final boolean set = world.setBlockState(pos, Botany.flower.getDefaultState());
        if (!set) {
            return false;
        }
        final TileEntity tileFlower = world.getTileEntity(pos);
        final TileEntity below = world.getTileEntity(pos.down());
        if (tileFlower != null && tileFlower instanceof TileEntityFlower) {
            if (below instanceof TileEntityFlower) {
                ((TileEntityFlower) tileFlower).setSection(((TileEntityFlower) below).getSection());
            } else {
                ((TileEntityFlower) tileFlower).create(flower, owner);
            }
        }
        tryGrowSection(world, pos);
        return true;
    }

    public static void tryGrowSection(final World world, final BlockPos pos) {
        if (!BinnieCore.proxy.isSimulating(world)) {
            return;
        }
        final TileEntity flower = world.getTileEntity(pos);
        if (flower != null && flower instanceof TileEntityFlower) {
            final IFlower iflower = ((TileEntityFlower) flower).getFlower();
            final int section = ((TileEntityFlower) flower).getSection();
            if (iflower != null && section < iflower.getGenome().getPrimary().getType().getSections() - 1 && iflower.getAge() > 0) {
                world.setBlockState(pos.up(), Botany.flower.getDefaultState());
                final TileEntity tileFlower = world.getTileEntity(pos.up());
                if (tileFlower != null && tileFlower instanceof TileEntityFlower) {
                    ((TileEntityFlower) tileFlower).setSection(section + 1);
                }
            }
        }
    }

    public static void onGrowFromSeed(final World world, final BlockPos pos) {
        tryGrowSection(world, pos);
    }

    public static Collection<ItemStack> getAcidFertilisers() {
        return Gardening.fertiliserAcid.keySet();
    }

    public static Collection<ItemStack> getAlkalineFertilisers() {
        return Gardening.fertiliserAlkaline.keySet();
    }

    public static Collection<ItemStack> getNutrientFertilisers() {
        return Gardening.fertiliserNutrient.keySet();
    }

    public static boolean isAcidFertiliser(final ItemStack itemstack) {
        for (final ItemStack stack : getAcidFertilisers()) {
            if (stack != null && stack.isItemEqual(itemstack)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isAlkalineFertiliser(final ItemStack itemstack) {
        for (final ItemStack stack : getAlkalineFertilisers()) {
            if (stack != null && stack.isItemEqual(itemstack)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isNutrientFertiliser(final ItemStack itemstack) {
        for (final ItemStack stack : getNutrientFertilisers()) {
            if (stack != null && stack.isItemEqual(itemstack)) {
                return true;
            }
        }
        return false;
    }

    public static int getFertiliserStrength(final ItemStack stack) {
        if (stack == null) {
            return 1;
        }
        for (final Map.Entry<ItemStack, Integer> entry : Gardening.fertiliserAcid.entrySet()) {
            if (entry.getKey().isItemEqual(stack)) {
                return entry.getValue();
            }
        }
        for (final Map.Entry<ItemStack, Integer> entry : Gardening.fertiliserAlkaline.entrySet()) {
            if (entry.getKey().isItemEqual(stack)) {
                return entry.getValue();
            }
        }
        for (final Map.Entry<ItemStack, Integer> entry : Gardening.fertiliserNutrient.entrySet()) {
            if (entry.getKey().isItemEqual(stack)) {
                return entry.getValue();
            }
        }
        return 1;
    }

//	public static boolean canTolerate(final IFlower flower, final World world, final BlockPos pos) {
//		if (flower == null || flower.getGenome() == null) {
//			return false;
//		}
//		final Block soil = world.getBlock(x, y - 1, z);
//		final int soilMeta = world.getBlockMetadata(x, y - 1, z);
//		final BiomeGenBase biome = world.getBiomeGenForCoords(x, z);
//		return canTolerate(flower, EnumAcidity.values()[soilMeta / 3 % 3], EnumMoisture.values()[soilMeta % 3], EnumTemperature.getFromValue(biome.temperature));
//	}
//
//	public static EnumSoilType getSoilType(final World world, final BlockPos pos) {
//		if (world.getBlock(x, y, z) instanceof IBlockSoil) {
//			return ((IBlockSoil) world.getBlock(x, y, z)).getType(world, x, y, z);
//		}
//		return EnumSoilType.SOIL;
//	}

    public static Block getSoilBlock(final EnumSoilType type) {
        return getSoilBlock(type, false);
    }

    public static Block getSoilBlock(final EnumSoilType type, final boolean weedkill) {
        switch (type) {
            case FLOWERBED: {
                return weedkill ? Botany.flowerbedNoWeed : Botany.flowerbed;
            }
            case LOAM: {
                return weedkill ? Botany.loamNoWeed : Botany.loam;
            }
            default: {
                return weedkill ? Botany.soilNoWeed : Botany.soil;
            }
        }
    }

    public static boolean canTolerate(final IFlower flower, final EnumAcidity ePH, final EnumMoisture eMoisture, final EnumTemperature eTemp) {
        return flower.getGenome().canTolerate(ePH) && flower.getGenome().canTolerate(eMoisture) && flower.getGenome().canTolerate(eTemp);
    }

    public static boolean isWeedkiller(final ItemStack heldItem) {
        return heldItem != null && heldItem.isItemEqual(BotanyItems.Weedkiller.get(1));
    }

//	public static boolean addWeedKiller(final World world, final BlockPos pos) {
//		if (!(world.getBlockState(pos).getBlock() instanceof IBlockSoil)) {
//			return false;
//		}
//		final EnumSoilType type = getSoilType(world, pos);
//		final Block block = getSoilBlock(type, true);
//		final boolean done = world.setBlock(x, y, z, block, world.getBlockMetadata(x, y, z), 2);
//		if (done && BlockPlant.isWeed(world, pos.up())) {
//			world.setBlockToAir(pos.up());
//		}
//		return done;
//	}

    static {
        Gardening.fertiliserAcid = new LinkedHashMap<>();
        Gardening.fertiliserAlkaline = new LinkedHashMap<>();
        Gardening.fertiliserNutrient = new LinkedHashMap<>();
    }
}
