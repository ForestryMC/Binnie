package binnie.extrabees.apiary.machine;

import binnie.core.Mods;
import binnie.core.circuits.BinnieCircuit;
import binnie.core.craftgui.minecraft.IMachineInformation;
import binnie.core.genetics.BeeModifierLogic;
import binnie.core.genetics.EnumBeeBooleanModifier;
import binnie.core.genetics.EnumBeeModifier;
import binnie.core.machines.Machine;
import binnie.core.machines.inventory.ComponentInventorySlots;
import binnie.core.machines.inventory.SlotValidator;
import binnie.core.machines.power.ComponentPowerReceptor;
import binnie.extrabees.apiary.ComponentBeeModifier;
import binnie.extrabees.apiary.ComponentExtraBeeGUI;
import binnie.extrabees.core.ExtraBeeGUID;
import binnie.extrabees.core.ExtraBeeTexture;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeListener;
import forestry.api.apiculture.IBeeModifier;
import forestry.api.circuits.ChipsetManager;
import forestry.api.circuits.ICircuit;
import forestry.api.circuits.ICircuitBoard;
import forestry.api.circuits.ICircuitLayout;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class AlvearyStimulator {
	public static int slotCircuit = 0;

	public enum CircuitType implements IBeeModifier {
		LowVoltage(3, 10),
		HighVoltage(5, 20),
		Plant(10, 10),
		Death(6, 10),
		Life(11, 10),
		Nether(7, 15),
		Mutation(4, 15),
		Inhibitor(1, 10),
		Territory(2, 10);

		static {
			CircuitType.LowVoltage.logic.setModifier(EnumBeeModifier.PRODUCTION, 1.5f, 5.0f);
			CircuitType.HighVoltage.logic.setModifier(EnumBeeModifier.PRODUCTION, 2.5f, 10.0f);
			CircuitType.Plant.logic.setModifier(EnumBeeModifier.FLOWERING, 1.5f, 5.0f);
			CircuitType.Death.logic.setModifier(EnumBeeModifier.LIFESPAN, 0.8f, 0.2f);
			CircuitType.Life.logic.setModifier(EnumBeeModifier.LIFESPAN, 1.5f, 5.0f);
			CircuitType.Nether.logic.setModifier(EnumBeeBooleanModifier.HELLISH);
			CircuitType.Mutation.logic.setModifier(EnumBeeModifier.MUTATION, 1.5f, 5.0f);
			CircuitType.Inhibitor.logic.setModifier(EnumBeeModifier.TERRITORY, 0.4f, 0.1f);
			CircuitType.Inhibitor.logic.setModifier(EnumBeeModifier.PRODUCTION, 0.9f, 0.5f);
			CircuitType.Territory.logic.setModifier(EnumBeeModifier.TERRITORY, 1.5f, 5.0f);
			for (CircuitType type : values()) {
				type.logic.setModifier(EnumBeeModifier.GENETIC_DECAY, 1.5f, 10.0f);
			}
		}

		public int recipe;
		public int power;

		protected BeeModifierLogic logic;

		CircuitType(int recipe, int power) {
			logic = new BeeModifierLogic();
			this.recipe = recipe;
			this.power = power;
		}

		public void createCircuit(ICircuitLayout layout) {
			StimulatorCircuit circuit = new StimulatorCircuit(this, layout);
			for (EnumBeeModifier modifier : EnumBeeModifier.values()) {
				float mod = logic.getModifier(modifier, 1.0f);
				if (mod == 1.0f) {
					continue;
				}

				if (mod > 1.0f) {
					int increase = (int) ((mod - 1.0f) * 100.0f);
					circuit.addTooltipString("Increases " + modifier.getName() + " by " + increase + "%");
				} else {
					int decrease = (int) ((1.0f - mod) * 100.0f);
					circuit.addTooltipString("Decreases " + modifier.getName() + " by " + decrease + "%");
				}
			}
		}

		@Override
		public float getTerritoryModifier(IBeeGenome genome, float currentModifier) {
			return logic.getModifier(EnumBeeModifier.TERRITORY, currentModifier);
		}

		@Override
		public float getMutationModifier(IBeeGenome genome, IBeeGenome mate, float currentModifier) {
			return logic.getModifier(EnumBeeModifier.MUTATION, currentModifier);
		}

		@Override
		public float getLifespanModifier(IBeeGenome genome, IBeeGenome mate, float currentModifier) {
			return logic.getModifier(EnumBeeModifier.LIFESPAN, currentModifier);
		}

		@Override
		public float getProductionModifier(IBeeGenome genome, float currentModifier) {
			return logic.getModifier(EnumBeeModifier.PRODUCTION, currentModifier);
		}

		@Override
		public float getFloweringModifier(IBeeGenome genome, float currentModifier) {
			return logic.getModifier(EnumBeeModifier.FLOWERING, currentModifier);
		}

		@Override
		public float getGeneticDecay(IBeeGenome genome, float currentModifier) {
			return logic.getModifier(EnumBeeModifier.GENETIC_DECAY, currentModifier);
		}

		@Override
		public boolean isSealed() {
			return logic.getModifier(EnumBeeBooleanModifier.SEALED);
		}

		@Override
		public boolean isSelfLighted() {
			return logic.getModifier(EnumBeeBooleanModifier.SELF_LIGHTED);
		}

		@Override
		public boolean isSunlightSimulated() {
			return logic.getModifier(EnumBeeBooleanModifier.SUNLIGHT_STIMULATED);
		}

		@Override
		public boolean isHellish() {
			return logic.getModifier(EnumBeeBooleanModifier.HELLISH);
		}
	}

	public static class PackageAlvearyStimulator extends AlvearyMachine.AlvearyPackage implements IMachineInformation {
		public PackageAlvearyStimulator() {
			super("stimulator", ExtraBeeTexture.AlvearyStimulator.getTexture(), true);
		}

		@Override
		public void createMachine(Machine machine) {
			new ComponentExtraBeeGUI(machine, ExtraBeeGUID.AlvearyStimulator);
			ComponentInventorySlots inventory = new ComponentInventorySlots(machine);
			inventory.addSlot(AlvearyStimulator.slotCircuit, "circuit");
			inventory.getSlot(AlvearyStimulator.slotCircuit).setValidator(new SlotValidatorCircuit());
			ComponentPowerReceptor power = new ComponentPowerReceptor(machine);
			new ComponentStimulatorModifier(machine);
		}
	}

	public static class SlotValidatorCircuit extends SlotValidator {
		public SlotValidatorCircuit() {
			super(SlotValidator.IconCircuit);
		}

		@Override
		public boolean isValid(ItemStack itemStack) {
			return itemStack != null && ChipsetManager.circuitRegistry.isChipset(itemStack);
		}

		@Override
		public String getTooltip() {
			return "Forestry Circuits";
		}
	}

	public static class ComponentStimulatorModifier extends ComponentBeeModifier implements IBeeModifier, IBeeListener {
		protected float powerUsage;
		protected boolean powered;
		protected StimulatorCircuit[] modifiers;

		public ComponentStimulatorModifier(Machine machine) {
			super(machine);
			powerUsage = 0.0f;
			powered = false;
			modifiers = new StimulatorCircuit[0];
		}

		@Override
		public void onUpdate() {
			super.onUpdate();
			modifiers = getCircuits();
			powerUsage = 0.0f;
			for (StimulatorCircuit beeMod : modifiers) {
				powerUsage += beeMod.getPowerUsage();
			}
			powered = getUtil().hasEnergyMJ(powerUsage);
		}

		public ICircuitBoard getHiveFrame() {
			if (!getUtil().isSlotEmpty(AlvearyStimulator.slotCircuit)) {
				return ChipsetManager.circuitRegistry.getCircuitboard(getUtil().getStack(AlvearyStimulator.slotCircuit));
			}
			return null;
		}

		public StimulatorCircuit[] getCircuits() {
			ICircuitBoard board = getHiveFrame();
			if (board == null) {
				return new StimulatorCircuit[0];
			}

			ICircuit[] circuits = board.getCircuits();
			List<IBeeModifier> mod = new ArrayList<>();
			for (ICircuit circuit : circuits) {
				if (circuit instanceof StimulatorCircuit) {
					mod.add((IBeeModifier) circuit);
				}
			}
			return mod.toArray(new StimulatorCircuit[0]);
		}

		@Override
		public float getTerritoryModifier(IBeeGenome genome, float currentModifier) {
			float mod = 1.0f;
			if (!powered) {
				return mod;
			}

			for (IBeeModifier beeMod : modifiers) {
				mod *= beeMod.getTerritoryModifier(genome, mod);
			}
			return mod;
		}

		@Override
		public float getMutationModifier(IBeeGenome genome, IBeeGenome mate, float currentModifier) {
			float mod = 1.0f;
			if (!powered) {
				return mod;
			}

			for (IBeeModifier beeMod : modifiers) {
				mod *= beeMod.getMutationModifier(genome, mate, mod);
			}
			return mod;
		}

		@Override
		public float getLifespanModifier(IBeeGenome genome, IBeeGenome mate, float currentModifier) {
			float mod = 1.0f;
			if (!powered) {
				return mod;
			}

			for (IBeeModifier beeMod : modifiers) {
				mod *= beeMod.getLifespanModifier(genome, mate, mod);
			}
			return mod;
		}

		@Override
		public float getProductionModifier(IBeeGenome genome, float currentModifier) {
			float mod = 1.0f;
			if (!powered) {
				return mod;
			}

			for (IBeeModifier beeMod : modifiers) {
				mod *= beeMod.getProductionModifier(genome, mod);
			}
			return mod;
		}

		@Override
		public float getFloweringModifier(IBeeGenome genome, float currentModifier) {
			float mod = 1.0f;
			if (!powered) {
				return mod;
			}

			for (IBeeModifier beeMod : modifiers) {
				mod *= beeMod.getFloweringModifier(genome, mod);
			}
			return mod;
		}

		@Override
		public float getGeneticDecay(IBeeGenome genome, float currentModifier) {
			float mod = 1.0f;
			if (!powered) {
				return mod;
			}

			for (IBeeModifier beeMod : modifiers) {
				mod *= beeMod.getGeneticDecay(genome, mod);
			}
			return mod;
		}

		@Override
		public boolean isSealed() {
			if (!powered) {
				return false;
			}

			for (IBeeModifier beeMod : modifiers) {
				if (beeMod.isSealed()) {
					return true;
				}
			}
			return false;
		}

		@Override
		public boolean isSelfLighted() {
			if (!powered) {
				return false;
			}

			for (IBeeModifier beeMod : modifiers) {
				if (beeMod.isSelfLighted()) {
					return true;
				}
			}
			return false;
		}

		@Override
		public boolean isSunlightSimulated() {
			if (!powered) {
				return false;
			}

			for (IBeeModifier beeMod : modifiers) {
				if (beeMod.isSunlightSimulated()) {
					return true;
				}
			}
			return false;
		}

		@Override
		public boolean isHellish() {
			if (!powered) {
				return false;
			}

			for (IBeeModifier beeMod : modifiers) {
				if (beeMod.isHellish()) {
					return true;
				}
			}
			return false;
		}

		@Override
		public void wearOutEquipment(int amount) {
			getUtil().useEnergyMJ(powerUsage);
		}
	}

	public static class StimulatorCircuit extends BinnieCircuit implements IBeeModifier {
		protected CircuitType type;

		public StimulatorCircuit(CircuitType type, ICircuitLayout layout) {
			super("stimulator." + type.toString().toLowerCase(), 4, layout, Mods.forestry.item("thermionicTubes"), type.recipe);
			this.type = type;
		}

		public int getPowerUsage() {
			return type.power;
		}

		@Override
		public float getTerritoryModifier(IBeeGenome genome, float currentModifier) {
			return type.getTerritoryModifier(genome, currentModifier);
		}

		@Override
		public float getMutationModifier(IBeeGenome genome, IBeeGenome mate, float currentModifier) {
			return type.getMutationModifier(genome, mate, currentModifier);
		}

		@Override
		public float getLifespanModifier(IBeeGenome genome, IBeeGenome mate, float currentModifier) {
			return type.getLifespanModifier(genome, mate, currentModifier);
		}

		@Override
		public float getProductionModifier(IBeeGenome genome, float currentModifier) {
			return type.getProductionModifier(genome, currentModifier);
		}

		@Override
		public float getFloweringModifier(IBeeGenome genome, float currentModifier) {
			return type.getFloweringModifier(genome, currentModifier);
		}

		@Override
		public boolean isSealed() {
			return type.isSealed();
		}

		@Override
		public boolean isSelfLighted() {
			return type.isSelfLighted();
		}

		@Override
		public boolean isSunlightSimulated() {
			return type.isSunlightSimulated();
		}

		@Override
		public boolean isHellish() {
			return type.isHellish();
		}

		@Override
		public float getGeneticDecay(IBeeGenome genome, float currentModifier) {
			return type.getGeneticDecay(genome, currentModifier);
		}
	}
}
