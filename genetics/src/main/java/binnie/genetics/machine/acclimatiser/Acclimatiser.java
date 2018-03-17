package binnie.genetics.machine.acclimatiser;

import com.google.common.base.Preconditions;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import net.minecraftforge.fluids.FluidRegistry;

import forestry.api.apiculture.EnumBeeChromosome;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.EnumTolerance;
import forestry.api.genetics.IChromosomeType;
import forestry.api.genetics.ISpeciesRoot;
import forestry.api.lepidopterology.EnumButterflyChromosome;

import binnie.core.BinnieCore;
import binnie.core.genetics.Tolerance;
import binnie.core.liquid.FluidContainerType;
import binnie.core.util.ItemStackUtil;
import binnie.genetics.api.acclimatiser.IToleranceType;

public class Acclimatiser {
	public static final int[] SLOT_RESERVE = new int[]{0, 1, 2, 3};
	public static final int SLOT_TARGET = 4;
	public static final int[] SLOT_ACCLIMATISER = new int[]{5, 6, 7};
	public static final int[] SLOT_DRONE = new int[]{8, 9, 10, 11};
	private static final List<ToleranceSystem> toleranceSystems = new ArrayList<>();
	private static final List<IToleranceType> toleranceTypes = new ArrayList<>();
	private static final Map<ItemStack, Float> temperatureItems = new HashMap<>();
	private static final Map<ItemStack, Float> humidityItems = new HashMap<>();

	@Nullable
	private static ToleranceSystem getToleranceSystem(final ItemStack stack, final ItemStack acclim) {
		final ISpeciesRoot root = AlleleManager.alleleRegistry.getSpeciesRoot(stack);
		if (root != null) {
			for (final ToleranceSystem system : Acclimatiser.toleranceSystems) {
				if (Objects.equals(root.getUID(), system.getSpeciesRootUid()) && system.getType().hasEffect(acclim)) {
					return system;
				}
			}
		}
		return null;
	}

	public static void addTolerance(IChromosomeType chromosome, IToleranceType type) {
		Preconditions.checkNotNull(chromosome, "chromosome must not be null");
		Preconditions.checkNotNull(type, "type must not be null");
		Acclimatiser.toleranceSystems.add(new ToleranceSystem(chromosome, type));
		Acclimatiser.toleranceTypes.add(type);
	}

	public static List<IToleranceType> getToleranceTypes() {
		return toleranceTypes;
	}

	public static float getTemperatureEffect(final ItemStack item) {
		for (final ItemStack stack : Acclimatiser.temperatureItems.keySet()) {
			if (ItemStackUtil.isItemEqual(stack, item, false, true)) {
				return Acclimatiser.temperatureItems.get(stack);
			}
		}
		return 0.0f;
	}

	public static float getHumidityEffect(final ItemStack item) {
		for (final ItemStack stack : Acclimatiser.humidityItems.keySet()) {
			if (ItemStackUtil.isItemEqual(stack, item, false, true)) {
				return Acclimatiser.humidityItems.get(stack);
			}
		}
		return 0.0f;
	}

	public static void addTemperatureItem(final ItemStack itemstack, final float amount) {
		if (itemstack.isEmpty()) {
			return;
		}
		Acclimatiser.temperatureItems.put(itemstack, amount);
	}

	public static void addHumidityItem(final ItemStack itemstack, final float amount) {
		if (itemstack.isEmpty()) {
			return;
		}
		Acclimatiser.humidityItems.put(itemstack, amount);
	}

	public static void setupRecipes() {
		if (BinnieCore.isApicultureActive()) {
			addTolerance(EnumBeeChromosome.HUMIDITY_TOLERANCE, ToleranceType.Humidity);
			addTolerance(EnumBeeChromosome.TEMPERATURE_TOLERANCE, ToleranceType.Temperature);
		}
		if (BinnieCore.isLepidopteryActive()) {
			addTolerance(EnumButterflyChromosome.HUMIDITY_TOLERANCE, ToleranceType.Humidity);
			addTolerance(EnumButterflyChromosome.TEMPERATURE_TOLERANCE, ToleranceType.Temperature);
		}
		addTemperatureItem(new ItemStack(Items.BLAZE_POWDER), 0.5f);
		addTemperatureItem(new ItemStack(Items.BLAZE_ROD), 0.75f);
		addTemperatureItem(new ItemStack(Items.LAVA_BUCKET), 0.75f);
		addTemperatureItem(new ItemStack(Items.SNOWBALL), -0.15f);
		addTemperatureItem(new ItemStack(Blocks.ICE), -0.75f);
		addHumidityItem(new ItemStack(Items.WATER_BUCKET), 0.75f);
		addHumidityItem(new ItemStack(Blocks.SAND), -0.15f);
		addTemperatureItem(FluidContainerType.CAN.getFilled(FluidRegistry.LAVA), 0.75f);
		addTemperatureItem(FluidContainerType.REFRACTORY.getFilled(FluidRegistry.LAVA), 0.75f);
		addHumidityItem(FluidContainerType.CAN.getFilled(FluidRegistry.WATER), 0.75f);
		addHumidityItem(FluidContainerType.REFRACTORY.getFilled(FluidRegistry.WATER), 0.75f);
		addHumidityItem(FluidContainerType.CAPSULE.getFilled(FluidRegistry.WATER), 0.75f);
	}

	public static boolean canAcclimatise(final ItemStack stack, final List<ItemStack> acclimatisers) {
		if (stack.isEmpty() || acclimatisers.isEmpty()) {
			return true;
		}
		for (final ItemStack acclim : acclimatisers) {
			if (canAcclimatise(stack, acclim)) {
				return true;
			}
		}
		return false;
	}

	public static boolean canAcclimatise(final ItemStack stack, final ItemStack acclim) {
		final ToleranceSystem system = getToleranceSystem(stack, acclim);
		return system != null && system.canAlter(stack, acclim);
	}

	public static ItemStack acclimatise(final ItemStack stack, final ItemStack acc) {
		final ToleranceSystem system = getToleranceSystem(stack, acc);
		if (system == null) {
			return stack;
		}
		return system.alter(stack, acc);
	}

	public static Tolerance alterTolerance(final Tolerance tol, final float effect) {
		int[] bounds = tol.getBounds();
		int[] range;
		if (effect < 0.0f) {
			range = new int[]{bounds[0] - 1, bounds[1]};
		} else {
			range = new int[]{bounds[0], bounds[1] + 1};
		}
		if (range[0] < -5) {
			range[0] = -5;
		}
		if (range[1] > 5) {
			range[1] = 5;
		}
		final EnumTolerance[] up = {EnumTolerance.NONE, EnumTolerance.UP_1, EnumTolerance.UP_2, EnumTolerance.UP_3, EnumTolerance.UP_4, EnumTolerance.UP_5};
		final EnumTolerance[] down = {EnumTolerance.NONE, EnumTolerance.DOWN_1, EnumTolerance.DOWN_2, EnumTolerance.DOWN_3, EnumTolerance.DOWN_4, EnumTolerance.DOWN_5};
		final EnumTolerance[] both = {EnumTolerance.NONE, EnumTolerance.BOTH_1, EnumTolerance.BOTH_2, EnumTolerance.BOTH_3, EnumTolerance.BOTH_4, EnumTolerance.BOTH_5};
		if (range[0] == 0) {
			return Tolerance.get(up[range[1]]);
		}
		if (range[1] == 0) {
			return Tolerance.get(down[-range[0]]);
		}
		final int avg = (int) ((-range[0] + range[1]) / 2.0f + 0.6f);
		return Tolerance.get(both[avg]);
	}
}
