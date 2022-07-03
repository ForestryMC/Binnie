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
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class Gardening {
    public static Map<ItemStack, Integer> fertiliserAcid = new LinkedHashMap<>();
    public static Map<ItemStack, Integer> fertiliserAlkaline = new LinkedHashMap<>();
    public static Map<ItemStack, Integer> fertiliserNutrient = new LinkedHashMap<>();

    public static boolean isSoil(Block block) {
        return block instanceof IBlockSoil;
    }

    public static boolean isSoil(Item item) {
        return item instanceof ItemBlock && isSoil(((ItemBlock) item).field_150939_a);
    }

    public static EnumMoisture getNaturalMoisture(World world, int x, int y, int z) {
        float bias = getBiomeMoisture(world.getBiomeGenForCoords(x, z), y);
        for (int dx = -1; dx < 2; ++dx) {
            for (int dz = -1; dz < 2; ++dz) {
                if (dx != 0 || dz != 0) {
                    if (world.getBlock(x + dx, y, z + dz) == Blocks.sand) {
                        bias -= 1.5;
                    } else if (world.getBlock(x + dx, y, z + dz) == Blocks.water) {
                        bias += 1.5;
                    }
                }
            }
        }

        if (world.isRaining() && world.canBlockSeeTheSky(x, y + 1, z) && world.getPrecipitationHeight(x, z) <= y + 1) {
            bias += 1.5;
        }

        for (int dx = -1; dx < 2; ++dx) {
            for (int dz = -1; dz < 2; ++dz) {
                if (dx != 0 || dz != 0) {
                    if (world.getBlock(x + dx, y, z + dz) == Blocks.gravel && bias > 0.0f) {
                        bias *= 0.4f;
                    }
                }
            }
        }

        if (bias <= -1.0f) {
            return EnumMoisture.DRY;
        }
        return (bias >= 1.0f) ? EnumMoisture.DAMP : EnumMoisture.NORMAL;
    }

    public static EnumAcidity getNaturalPH(World world, int x, int y, int z) {
        float bias = getBiomePH(world.getBiomeGenForCoords(x, z), y);
        if (bias <= -1.0f) {
            return EnumAcidity.ACID;
        }
        return (bias >= 1.0f) ? EnumAcidity.ALKALINE : EnumAcidity.NEUTRAL;
    }

    public static float getBiomeMoisture(BiomeGenBase biome, int H) {
        double R = biome.rainfall;
        double T = biome.temperature;
        double m =
                3.2 * (R - 0.5) - 0.4 * (1.0 + T + 0.5 * T * T) + 1.1 - 1.6 * (T - 0.9) * (T - 0.9) - 0.002 * (H - 64);
        if (m == 0.0) {
            return (float) m;
        }
        if (m < 0.0) {
            return (float) -Math.sqrt(m * m);
        }
        return (float) Math.sqrt(m * m);
    }

    public static float getBiomePH(BiomeGenBase biome, int H) {
        double R = biome.rainfall;
        double T = biome.temperature;
        return (float) (-3.0 * (R - 0.5)
                + 0.5 * (T - 0.699999988079071) * (T - 0.699999988079071)
                + 0.02f * (H - 64)
                - 0.15000000596046448);
    }

    public static void createSoil(
            World world, int x, int y, int z, EnumSoilType soil, EnumMoisture moisture, EnumAcidity acidity) {
        int meta = moisture.ordinal() + acidity.ordinal() * 3;
        world.setBlock(x, y, z, getSoilBlock(soil), meta, 2);
    }

    public static boolean plant(World world, int x, int y, int z, IFlower flower, GameProfile owner) {
        boolean set = world.setBlock(x, y, z, Botany.flower, 0, 2);
        if (!set) {
            return false;
        }

        TileEntity tileFlower = world.getTileEntity(x, y, z);
        TileEntity below = world.getTileEntity(x, y - 1, z);
        if (tileFlower != null && tileFlower instanceof TileEntityFlower) {
            if (below instanceof TileEntityFlower) {
                ((TileEntityFlower) tileFlower).setSection(((TileEntityFlower) below).getSection());
            } else {
                ((TileEntityFlower) tileFlower).create(flower, owner);
            }
        }

        tryGrowSection(world, x, y, z);
        return true;
    }

    public static void tryGrowSection(World world, int x, int y, int z) {
        if (!BinnieCore.proxy.isSimulating(world)) {
            return;
        }

        TileEntity flower = world.getTileEntity(x, y, z);
        if (flower == null || !(flower instanceof TileEntityFlower)) {
            return;
        }

        IFlower iflower = ((TileEntityFlower) flower).getFlower();
        int section = ((TileEntityFlower) flower).getSection();
        if (iflower == null
                || section >= iflower.getGenome().getPrimary().getType().getSections() - 1
                || iflower.getAge() <= 0) {
            return;
        }

        world.setBlock(x, y + 1, z, Botany.flower, 0, 2);
        TileEntity tileFlower = world.getTileEntity(x, y + 1, z);
        if (tileFlower != null && tileFlower instanceof TileEntityFlower) {
            ((TileEntityFlower) tileFlower).setSection(section + 1);
        }
    }

    public static void onGrowFromSeed(World world, int x, int y, int z) {
        tryGrowSection(world, x, y, z);
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

    public static boolean isAcidFertiliser(ItemStack itemstack) {
        for (ItemStack stack : getAcidFertilisers()) {
            if (stack != null && stack.isItemEqual(itemstack)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isAlkalineFertiliser(ItemStack itemstack) {
        for (ItemStack stack : getAlkalineFertilisers()) {
            if (stack != null && stack.isItemEqual(itemstack)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isNutrientFertiliser(ItemStack itemstack) {
        for (ItemStack stack : getNutrientFertilisers()) {
            if (stack != null && stack.isItemEqual(itemstack)) {
                return true;
            }
        }
        return false;
    }

    public static int getFertiliserStrength(ItemStack stack) {
        if (stack == null) {
            return 1;
        }

        for (Map.Entry<ItemStack, Integer> entry : Gardening.fertiliserAcid.entrySet()) {
            if (entry.getKey().isItemEqual(stack)) {
                return entry.getValue();
            }
        }

        for (Map.Entry<ItemStack, Integer> entry : Gardening.fertiliserAlkaline.entrySet()) {
            if (entry.getKey().isItemEqual(stack)) {
                return entry.getValue();
            }
        }

        for (Map.Entry<ItemStack, Integer> entry : Gardening.fertiliserNutrient.entrySet()) {
            if (entry.getKey().isItemEqual(stack)) {
                return entry.getValue();
            }
        }
        return 1;
    }

    public static boolean canTolerate(IFlower flower, World world, int x, int y, int z) {
        if (flower == null || flower.getGenome() == null) {
            return false;
        }

        int soilMeta = world.getBlockMetadata(x, y - 1, z);
        BiomeGenBase biome = world.getBiomeGenForCoords(x, z);
        return canTolerate(
                flower,
                EnumAcidity.values()[soilMeta / 3 % 3],
                EnumMoisture.values()[soilMeta % 3],
                EnumTemperature.getFromValue(biome.temperature));
    }

    public static EnumSoilType getSoilType(World world, int x, int y, int z) {
        if (world.getBlock(x, y, z) instanceof IBlockSoil) {
            return ((IBlockSoil) world.getBlock(x, y, z)).getType(world, x, y, z);
        }
        return EnumSoilType.SOIL;
    }

    public static Block getSoilBlock(EnumSoilType type) {
        return getSoilBlock(type, false);
    }

    public static Block getSoilBlock(EnumSoilType type, boolean weedkill) {
        switch (type) {
            case FLOWERBED:
                return weedkill ? Botany.flowerbedNoWeed : Botany.flowerbed;

            case LOAM:
                return weedkill ? Botany.loamNoWeed : Botany.loam;
        }
        return weedkill ? Botany.soilNoWeed : Botany.soil;
    }

    public static boolean canTolerate(IFlower flower, EnumAcidity ePH, EnumMoisture eMoisture, EnumTemperature eTemp) {
        return flower.getGenome().canTolerate(ePH)
                && flower.getGenome().canTolerate(eMoisture)
                && flower.getGenome().canTolerate(eTemp);
    }

    public static boolean isWeedkiller(ItemStack heldItem) {
        return heldItem != null && heldItem.isItemEqual(BotanyItems.Weedkiller.get(1));
    }

    public static boolean addWeedKiller(World world, int x, int y, int z) {
        if (!(world.getBlock(x, y, z) instanceof IBlockSoil)) {
            return false;
        }

        EnumSoilType type = getSoilType(world, x, y, z);
        Block block = getSoilBlock(type, true);
        boolean done = world.setBlock(x, y, z, block, world.getBlockMetadata(x, y, z), 2);
        if (done && BlockPlant.isWeed(world, x, y + 1, z)) {
            world.setBlockToAir(x, y + 1, z);
        }
        return done;
    }
}
