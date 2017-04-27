package binnie.genetics.machine;

import binnie.Binnie;
import binnie.botany.api.EnumFlowerChromosome;
import binnie.botany.gardening.Gardening;
import binnie.core.BinnieCore;
import binnie.core.Mods;
import binnie.core.craftgui.minecraft.IMachineInformation;
import binnie.core.genetics.Gene;
import binnie.core.genetics.Tolerance;
import binnie.core.machines.IMachine;
import binnie.core.machines.Machine;
import binnie.core.machines.TileEntityMachine;
import binnie.core.machines.inventory.ComponentInventorySlots;
import binnie.core.machines.inventory.ComponentInventoryTransfer;
import binnie.core.machines.inventory.InventorySlot;
import binnie.core.machines.inventory.SlotValidator;
import binnie.core.machines.power.ComponentPowerReceptor;
import binnie.core.machines.power.ComponentProcessIndefinate;
import binnie.core.machines.power.ErrorState;
import binnie.genetics.core.GeneticsGUI;
import binnie.genetics.core.GeneticsTexture;
import forestry.api.apiculture.EnumBeeChromosome;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.EnumTolerance;
import forestry.api.genetics.IAlleleTolerance;
import forestry.api.genetics.IChromosomeType;
import forestry.api.genetics.IGenome;
import forestry.api.genetics.IIndividual;
import forestry.api.genetics.ISpeciesRoot;
import forestry.api.lepidopterology.EnumButterflyChromosome;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Acclimatiser {
	public static int[] slotReserve = new int[]{0, 1, 2, 3};
	public static int[] slotAcclimatiser = new int[]{5, 6, 7};
	public static int[] slotDone = new int[]{8, 9, 10, 11};
	public static int slotTarget = 4;

	protected static Map<ItemStack, Float> temperatureItems = new HashMap<>();
	protected static Map<ItemStack, Float> humidityItems = new HashMap<>();

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
			addTolerance(Binnie.Genetics.getBeeRoot().getUID(), EnumBeeChromosome.HUMIDITY_TOLERANCE, ToleranceType.Humidity);
			addTolerance(Binnie.Genetics.getBeeRoot().getUID(), EnumBeeChromosome.TEMPERATURE_TOLERANCE, ToleranceType.Temperature);
		}

		if (BinnieCore.isLepidopteryActive()) {
			addTolerance(Binnie.Genetics.getButterflyRoot().getUID(), EnumButterflyChromosome.HUMIDITY_TOLERANCE, ToleranceType.Humidity);
			addTolerance(Binnie.Genetics.getButterflyRoot().getUID(), EnumButterflyChromosome.TEMPERATURE_TOLERANCE, ToleranceType.Temperature);
		}

		if (BinnieCore.isBotanyActive()) {
			addTolerance(Binnie.Genetics.getFlowerRoot().getUID(), EnumFlowerChromosome.HUMIDITY_TOLERANCE, ToleranceType.Humidity);
			addTolerance(Binnie.Genetics.getFlowerRoot().getUID(), EnumFlowerChromosome.TEMPERATURE_TOLERANCE, ToleranceType.Temperature);
			addTolerance(Binnie.Genetics.getFlowerRoot().getUID(), EnumFlowerChromosome.PH_TOLERANCE, ToleranceType.PH);
		}

		addTemperatureItem(new ItemStack(Items.blaze_powder), 0.5f);
		addTemperatureItem(new ItemStack(Items.blaze_rod), 0.75f);
		addTemperatureItem(new ItemStack(Items.lava_bucket), 0.75f);
		addTemperatureItem(new ItemStack(Items.snowball), -0.15f);
		addTemperatureItem(new ItemStack(Blocks.ice), -0.75f);
		addHumidityItem(new ItemStack(Items.water_bucket), 0.75f);
		addHumidityItem(new ItemStack(Blocks.sand), -0.15f);
		addTemperatureItem(Mods.Forestry.stack("canLava"), 0.75f);
		addTemperatureItem(Mods.Forestry.stack("refractoryLava"), 0.75f);
		addHumidityItem(Mods.Forestry.stack("canWater"), 0.75f);
		addHumidityItem(Mods.Forestry.stack("refractoryWater"), 0.75f);
		addHumidityItem(Mods.Forestry.stack("waxCapsuleWater"), 0.75f);
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
			range = new int[]{is[0] - 1, is[1]};
		} else {
			range = new int[]{is[0], is[1] + 1};
		}

		if (range[0] < -5) {
			range[0] = -5;
		}
		if (range[1] > 5) {
			range[1] = 5;
		}

		EnumTolerance[] up = {EnumTolerance.NONE, EnumTolerance.UP_1, EnumTolerance.UP_2, EnumTolerance.UP_3, EnumTolerance.UP_4, EnumTolerance.UP_5};
		EnumTolerance[] down = {EnumTolerance.NONE, EnumTolerance.DOWN_1, EnumTolerance.DOWN_2, EnumTolerance.DOWN_3, EnumTolerance.DOWN_4, EnumTolerance.DOWN_5};
		EnumTolerance[] both = {EnumTolerance.NONE, EnumTolerance.BOTH_1, EnumTolerance.BOTH_2, EnumTolerance.BOTH_3, EnumTolerance.BOTH_4, EnumTolerance.BOTH_5};

		if (range[0] == 0) {
			return Tolerance.get(up[range[1]]);
		}
		if (range[1] == 0) {
			return Tolerance.get(down[-range[0]]);
		}

		int avg = (int) ((-range[0] + range[1]) / 2.0f + 0.6f);
		return Tolerance.get(both[avg]);
	}

	public enum ToleranceType {
		Temperature,
		Humidity,
		PH;

		public float getEffect(ItemStack stack) {
			switch (this) {
				case Temperature:
					return Acclimatiser.getTemperatureEffect(stack);

				case Humidity:
					return Acclimatiser.getHumidityEffect(stack);

				case PH:
					if (Gardening.isAcidFertiliser(stack)) {
						return -0.5f * Gardening.getFertiliserStrength(stack);
					}
					if (Gardening.isAlkalineFertiliser(stack)) {
						return 0.5f * Gardening.getFertiliserStrength(stack);
					}
					break;
			}
			return 0.0f;
		}

		public boolean hasEffect(ItemStack stack) {
			return getEffect(stack) != 0.0f;
		}
	}

	public static class PackageAcclimatiser extends GeneticMachine.PackageGeneticBase implements IMachineInformation {
		public PackageAcclimatiser() {
			super("acclimatiser", GeneticsTexture.Acclimatiser, 0x966a49, true);
		}

		@Override
		public void createMachine(Machine machine) {
			new ComponentGeneticGUI(machine, GeneticsGUI.Acclimatiser);
			ComponentInventorySlots inventory = new ComponentInventorySlots(machine);
			inventory.addSlot(4, "process");
			inventory.getSlot(4).setValidator(new SlotValidator.Individual());
			inventory.getSlot(4).setReadOnly();
			inventory.getSlot(4).forbidExtraction();

			for (InventorySlot slot : inventory.addSlotArray(Acclimatiser.slotReserve, "input")) {
				slot.forbidExtraction();
				slot.setValidator(new SlotValidator.Individual());
			}

			for (InventorySlot slot : inventory.addSlotArray(Acclimatiser.slotDone, "output")) {
				slot.setReadOnly();
				slot.setValidator(new SlotValidator.Individual());
			}

			for (InventorySlot slot : inventory.addSlotArray(Acclimatiser.slotAcclimatiser, "acclimatiser")) {
				slot.setValidator(new ValidatorAcclimatiserItem());
			}

			ComponentInventoryTransfer transfer = new ComponentInventoryTransfer(machine);
			transfer.addRestock(Acclimatiser.slotReserve, 4, 1);
			transfer.addStorage(4, Acclimatiser.slotDone, new ComponentInventoryTransfer.Condition() {
				@Override
				public boolean fufilled(ItemStack stack) {
					return !Acclimatiser.canAcclimatise(stack, machine.getMachineUtil().getNonNullStacks(Acclimatiser.slotAcclimatiser));
				}
			});

			new ComponentPowerReceptor(machine, 5000);
			new ComponentAcclimatiserLogic(machine);
		}

		@Override
		public TileEntity createTileEntity() {
			return new TileEntityMachine(this);
		}
	}

	public static class ComponentAcclimatiserLogic extends ComponentProcessIndefinate {
		public ComponentAcclimatiserLogic(IMachine machine) {
			super(machine, 2.0f);
		}

		@Override
		public ErrorState canWork() {
			if (getUtil().getStack(4) == null) {
				return new ErrorState.NoItem("No Individual to Acclimatise", 4);
			}
			if (getUtil().getNonNullStacks(Acclimatiser.slotAcclimatiser).isEmpty()) {
				return new ErrorState.NoItem("No Acclimatising Items", Acclimatiser.slotAcclimatiser);
			}
			return super.canWork();
		}

		@Override
		public ErrorState canProgress() {
			if (!Acclimatiser.canAcclimatise(getUtil().getStack(4), getUtil().getNonNullStacks(Acclimatiser.slotAcclimatiser))) {
				return new ErrorState.InvalidItem("Cannot Acclimatise this individual with these items", 4);
			}
			return super.canProgress();
		}

		@Override
		protected boolean inProgress() {
			return canWork() == null;
		}

		@Override
		protected void onTickTask() {
			super.onTickTask();
			if (getUtil().getRandom().nextInt(100) == 0) {
				attemptAcclimatisation();
			}
		}

		protected void attemptAcclimatisation() {
			List<ItemStack> acclms = new ArrayList<ItemStack>();
			for (ItemStack s : getUtil().getNonNullStacks(Acclimatiser.slotAcclimatiser)) {
				if (Acclimatiser.canAcclimatise(getUtil().getStack(4), s)) {
					acclms.add(s);
				}
			}

			ItemStack acc = acclms.get(getUtil().getRandom().nextInt(acclms.size()));
			ItemStack acclimed = Acclimatiser.acclimatise(getUtil().getStack(4), acc);
			if (acclimed != null) {
				getUtil().setStack(4, acclimed);
				boolean removed = false;
				for (int i : Acclimatiser.slotAcclimatiser) {
					if (!removed && getUtil().getStack(i) != null && getUtil().getStack(i).isItemEqual(acc)) {
						getUtil().decreaseStack(i, 1);
						removed = true;
					}
				}
			}
		}
	}

	public static class ValidatorAcclimatiserItem extends SlotValidator {
		public ValidatorAcclimatiserItem() {
			super(null);
		}

		@Override
		public boolean isValid(ItemStack stack) {
			for (ToleranceType type : ToleranceType.values()) {
				if (type.hasEffect(stack)) {
					return true;
				}
			}
			return false;
		}

		@Override
		public String getTooltip() {
			return "Acclimatising Items";
		}
	}

	private static class ToleranceSystem {
		String uid;
		IChromosomeType chromosomeOrdinal;
		ToleranceType type;

		private ToleranceSystem(String uid, IChromosomeType chromosomeOrdinal, ToleranceType type) {
			this.uid = uid;
			this.chromosomeOrdinal = chromosomeOrdinal;
			this.type = type;
		}

		public boolean canAlter(ItemStack stack, ItemStack acclim) {
			IIndividual member = AlleleManager.alleleRegistry.getIndividual(stack);
			IGenome genome = member.getGenome();
			IAlleleTolerance tolAllele = (IAlleleTolerance) genome.getActiveAllele(chromosomeOrdinal);
			Tolerance tol = Tolerance.get(tolAllele.getValue());
			float effect = type.getEffect(acclim);
			return (effect > 0.0f && tol.getBounds()[1] < 5) || (effect < 0.0f && tol.getBounds()[0] > -5);
		}

		public ItemStack alter(ItemStack stack, ItemStack acc) {
			Random rand = new Random();
			float effect = type.getEffect(acc);
			if (rand.nextFloat() > Math.abs(effect)) {
				return stack;
			}

			IIndividual member = AlleleManager.alleleRegistry.getIndividual(stack);
			IGenome genome = member.getGenome();
			IAlleleTolerance tolAllele = (IAlleleTolerance) genome.getActiveAllele(chromosomeOrdinal);
			Tolerance tol = Tolerance.get(tolAllele.getValue());
			Tolerance newTol = Acclimatiser.alterTolerance(tol, effect);
			if (rand.nextFloat() > 1.0f / (-newTol.getBounds()[0] + newTol.getBounds()[1])) {
				return stack;
			}
			ISpeciesRoot root = AlleleManager.alleleRegistry.getSpeciesRoot(stack);
			Inoculator.setGene(new Gene(newTol.getAllele(), chromosomeOrdinal, root), stack, rand.nextInt(2));
			return stack;
		}
	}
}
