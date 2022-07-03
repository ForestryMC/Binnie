package binnie.genetics.machine.acclimatiser;

import binnie.Binnie;
import binnie.botany.api.EnumFlowerChromosome;
import binnie.core.BinnieCore;
import binnie.core.Mods;
import binnie.core.genetics.Tolerance;
import forestry.api.apiculture.EnumBeeChromosome;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.EnumTolerance;
import forestry.api.genetics.IChromosomeType;
import forestry.api.genetics.ISpeciesRoot;
import forestry.api.lepidopterology.EnumButterflyChromosome;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class Acclimatiser {
    public static final int[] SLOT_RESERVE = new int[] {0, 1, 2, 3};
    public static final int[] SLOT_ACCLIMATISER = new int[] {5, 6, 7};
    public static final int[] SLOT_DONE = new int[] {8, 9, 10, 11};
    public static final int SLOT_TARGET = 4;
    public static final float ENERGY_PER_TICK = 2.0f;

    public static Map<ItemStack, Float> temperatureItems = new HashMap<>();
    public static Map<ItemStack, Float> humidityItems = new HashMap<>();

    private static List<ToleranceSystem> toleranceSystems = new ArrayList<>();

    private static ToleranceSystem getToleranceSystem(ItemStack stack, ItemStack acclim) {
        ISpeciesRoot root = AlleleManager.alleleRegistry.getSpeciesRoot(stack);
        if (root == null) {
            return null;
        }

        for (ToleranceSystem system : Acclimatiser.toleranceSystems) {
            if (root.getUID().equals(system.uid) && system.type.hasEffect(acclim)) {
                return system;
            }
        }
        return null;
    }

    public static void addTolerance(String uid, IChromosomeType chromosome, ToleranceType type) {
        Acclimatiser.toleranceSystems.add(new ToleranceSystem(uid, chromosome, type));
    }

    public static float getTemperatureEffect(ItemStack item) {
        for (ItemStack stack : Acclimatiser.temperatureItems.keySet()) {
            if (stack.isItemEqual(item)) {
                return Acclimatiser.temperatureItems.get(stack);
            }
        }
        return 0.0f;
    }

    public static float getHumidityEffect(ItemStack item) {
        for (ItemStack stack : Acclimatiser.humidityItems.keySet()) {
            if (stack.isItemEqual(item)) {
                return Acclimatiser.humidityItems.get(stack);
            }
        }
        return 0.0f;
    }

    public static void addTemperatureItem(ItemStack itemStack, float amount) {
        if (itemStack == null) {
            return;
        }
        Acclimatiser.temperatureItems.put(itemStack, amount);
    }

    public static void addHumidityItem(ItemStack itemStack, float amount) {
        if (itemStack == null) {
            return;
        }
        Acclimatiser.humidityItems.put(itemStack, amount);
    }

    public static void setupRecipes() {
        if (BinnieCore.isApicultureActive()) {
            addTolerance(
                    Binnie.Genetics.getBeeRoot().getUID(),
                    EnumBeeChromosome.HUMIDITY_TOLERANCE,
                    ToleranceType.Humidity);
            addTolerance(
                    Binnie.Genetics.getBeeRoot().getUID(),
                    EnumBeeChromosome.TEMPERATURE_TOLERANCE,
                    ToleranceType.Temperature);
        }

        if (BinnieCore.isLepidopteryActive()) {
            addTolerance(
                    Binnie.Genetics.getButterflyRoot().getUID(),
                    EnumButterflyChromosome.HUMIDITY_TOLERANCE,
                    ToleranceType.Humidity);
            addTolerance(
                    Binnie.Genetics.getButterflyRoot().getUID(),
                    EnumButterflyChromosome.TEMPERATURE_TOLERANCE,
                    ToleranceType.Temperature);
        }

        if (BinnieCore.isBotanyActive()) {
            addTolerance(
                    Binnie.Genetics.getFlowerRoot().getUID(),
                    EnumFlowerChromosome.HUMIDITY_TOLERANCE,
                    ToleranceType.Humidity);
            addTolerance(
                    Binnie.Genetics.getFlowerRoot().getUID(),
                    EnumFlowerChromosome.TEMPERATURE_TOLERANCE,
                    ToleranceType.Temperature);
            addTolerance(Binnie.Genetics.getFlowerRoot().getUID(), EnumFlowerChromosome.PH_TOLERANCE, ToleranceType.PH);
        }

        addTemperatureItem(new ItemStack(Items.blaze_powder), 0.5f);
        addTemperatureItem(new ItemStack(Items.blaze_rod), 0.75f);
        addTemperatureItem(new ItemStack(Items.lava_bucket), 0.75f);
        addTemperatureItem(new ItemStack(Items.snowball), -0.15f);
        addTemperatureItem(new ItemStack(Blocks.ice), -0.75f);
        addHumidityItem(new ItemStack(Items.water_bucket), 0.75f);
        addHumidityItem(new ItemStack(Blocks.sand), -0.15f);
        addTemperatureItem(Mods.forestry.stack("canLava"), 0.75f);
        addTemperatureItem(Mods.forestry.stack("refractoryLava"), 0.75f);
        addHumidityItem(Mods.forestry.stack("canWater"), 0.75f);
        addHumidityItem(Mods.forestry.stack("refractoryWater"), 0.75f);
        addHumidityItem(Mods.forestry.stack("waxCapsuleWater"), 0.75f);
    }

    public static boolean canAcclimatise(ItemStack stack, List<ItemStack> acclimatisers) {
        if (stack == null || acclimatisers.isEmpty()) {
            return true;
        }

        for (ItemStack acclim : acclimatisers) {
            if (canAcclimatise(stack, acclim)) {
                return true;
            }
        }
        return false;
    }

    public static boolean canAcclimatise(ItemStack stack, ItemStack acclim) {
        ToleranceSystem system = getToleranceSystem(stack, acclim);
        return system != null && system.canAlter(stack, acclim);
    }

    public static ItemStack acclimatise(ItemStack stack, ItemStack acc) {
        ToleranceSystem system = getToleranceSystem(stack, acc);
        return system.alter(stack, acc);
    }

    public static Tolerance alterTolerance(Tolerance tol, float effect) {
        int[] is = tol.getBounds();
        int[] range;
        if (effect < 0.0f) {
            range = new int[] {is[0] - 1, is[1]};
        } else {
            range = new int[] {is[0], is[1] + 1};
        }

        if (range[0] < -5) {
            range[0] = -5;
        }
        if (range[1] > 5) {
            range[1] = 5;
        }

        EnumTolerance[] up = {
            EnumTolerance.NONE,
            EnumTolerance.UP_1,
            EnumTolerance.UP_2,
            EnumTolerance.UP_3,
            EnumTolerance.UP_4,
            EnumTolerance.UP_5
        };
        EnumTolerance[] down = {
            EnumTolerance.NONE,
            EnumTolerance.DOWN_1,
            EnumTolerance.DOWN_2,
            EnumTolerance.DOWN_3,
            EnumTolerance.DOWN_4,
            EnumTolerance.DOWN_5
        };
        EnumTolerance[] both = {
            EnumTolerance.NONE,
            EnumTolerance.BOTH_1,
            EnumTolerance.BOTH_2,
            EnumTolerance.BOTH_3,
            EnumTolerance.BOTH_4,
            EnumTolerance.BOTH_5
        };

        if (range[0] == 0) {
            return Tolerance.get(up[range[1]]);
        }
        if (range[1] == 0) {
            return Tolerance.get(down[-range[0]]);
        }

        int avg = (int) ((-range[0] + range[1]) / 2.0f + 0.6f);
        return Tolerance.get(both[avg]);
    }
}
