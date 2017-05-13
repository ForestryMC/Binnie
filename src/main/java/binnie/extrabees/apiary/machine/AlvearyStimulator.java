package binnie.extrabees.apiary.machine;

import binnie.core.craftgui.minecraft.IMachineInformation;
import binnie.core.machines.Machine;
import binnie.core.machines.inventory.ComponentInventorySlots;
import binnie.core.machines.inventory.SlotValidator;
import binnie.core.machines.power.ComponentPowerReceptor;
import binnie.extrabees.apiary.ComponentBeeModifier;
import binnie.extrabees.apiary.ComponentExtraBeeGUI;
import binnie.extrabees.circuit.StimulatorCircuit;
import binnie.extrabees.client.ExtraBeeGUID;
import binnie.extrabees.client.ExtraBeeTexture;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeListener;
import forestry.api.apiculture.IBeeModifier;
import forestry.api.circuits.ChipsetManager;
import forestry.api.circuits.ICircuit;
import forestry.api.circuits.ICircuitBoard;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class AlvearyStimulator {
	public static int slotCircuit = 0;

	public static class PackageAlvearyStimulator extends AlvearyMachine.AlvearyPackage implements IMachineInformation {
		public PackageAlvearyStimulator() {
			super("stimulator", ExtraBeeTexture.AlvearyStimulator, true);
		}

		@Override
		public void createMachine(final Machine machine) {
			new ComponentExtraBeeGUI(machine, ExtraBeeGUID.AlvearyStimulator);
			final ComponentInventorySlots inventory = new ComponentInventorySlots(machine);
			inventory.addSlot(AlvearyStimulator.slotCircuit, "circuit").setValidator(new SlotValidatorCircuit());
			final ComponentPowerReceptor power = new ComponentPowerReceptor(machine);
			new ComponentStimulatorModifier(machine);
		}
	}

	public static class SlotValidatorCircuit extends SlotValidator {
		public SlotValidatorCircuit() {
			super(SlotValidator.spriteCircuit);
		}

		@Override
		public boolean isValid(final ItemStack itemStack) {
			return !itemStack.isEmpty() && ChipsetManager.circuitRegistry.isChipset(itemStack);
		}

		@Override
		public String getTooltip() {
			return "Forestry Circuits";
		}
	}

	public static class ComponentStimulatorModifier extends ComponentBeeModifier implements IBeeModifier, IBeeListener {
		float powerUsage;
		boolean powered;
		StimulatorCircuit[] modifiers;

		public ComponentStimulatorModifier(final Machine machine) {
			super(machine);
			this.powerUsage = 0.0f;
			this.powered = false;
			this.modifiers = new StimulatorCircuit[0];
		}

		@Override
		public void onUpdate() {
			super.onUpdate();
			this.modifiers = this.getCircuits();
			this.powerUsage = 0.0f;
			for (final StimulatorCircuit beeMod : this.modifiers) {
				this.powerUsage += beeMod.getPowerUsage();
			}
			this.powered = this.getUtil().hasEnergyMJ(this.powerUsage);
		}

		@Nullable
		public ICircuitBoard getHiveFrame() {
			if (!this.getUtil().isSlotEmpty(AlvearyStimulator.slotCircuit)) {
				return ChipsetManager.circuitRegistry.getCircuitBoard(this.getUtil().getStack(AlvearyStimulator.slotCircuit));
			}
			return null;
		}

		public StimulatorCircuit[] getCircuits() {
			final ICircuitBoard board = this.getHiveFrame();
			if (board == null) {
				return new StimulatorCircuit[0];
			}
			final ICircuit[] circuits = board.getCircuits();
			final List<StimulatorCircuit> mod = new ArrayList<>();
			for (final ICircuit circuit : circuits) {
				if (circuit instanceof StimulatorCircuit) {
					mod.add((StimulatorCircuit) circuit);
				}
			}
			return mod.toArray(new StimulatorCircuit[0]);
		}

		@Override
		public float getTerritoryModifier(final IBeeGenome genome, final float currentModifier) {
			float mod = 1.0f;
			if (!this.powered) {
				return mod;
			}
			for (final IBeeModifier beeMod : this.modifiers) {
				mod *= beeMod.getTerritoryModifier(genome, mod);
			}
			return mod;
		}

		@Override
		public float getMutationModifier(final IBeeGenome genome, final IBeeGenome mate, final float currentModifier) {
			float mod = 1.0f;
			if (!this.powered) {
				return mod;
			}
			for (final IBeeModifier beeMod : this.modifiers) {
				mod *= beeMod.getMutationModifier(genome, mate, mod);
			}
			return mod;
		}

		@Override
		public float getLifespanModifier(final IBeeGenome genome, @Nullable final IBeeGenome mate, final float currentModifier) {
			float mod = 1.0f;
			if (!this.powered) {
				return mod;
			}
			for (final IBeeModifier beeMod : this.modifiers) {
				mod *= beeMod.getLifespanModifier(genome, mate, mod);
			}
			return mod;
		}

		@Override
		public float getProductionModifier(final IBeeGenome genome, final float currentModifier) {
			float mod = 1.0f;
			if (!this.powered) {
				return mod;
			}
			for (final IBeeModifier beeMod : this.modifiers) {
				mod *= beeMod.getProductionModifier(genome, mod);
			}
			return mod;
		}

		@Override
		public float getFloweringModifier(final IBeeGenome genome, final float currentModifier) {
			float mod = 1.0f;
			if (!this.powered) {
				return mod;
			}
			for (final IBeeModifier beeMod : this.modifiers) {
				mod *= beeMod.getFloweringModifier(genome, mod);
			}
			return mod;
		}

		@Override
		public float getGeneticDecay(final IBeeGenome genome, final float currentModifier) {
			float mod = 1.0f;
			if (!this.powered) {
				return mod;
			}
			for (final IBeeModifier beeMod : this.modifiers) {
				mod *= beeMod.getGeneticDecay(genome, mod);
			}
			return mod;
		}

		@Override
		public boolean isSealed() {
			if (!this.powered) {
				return false;
			}
			for (final IBeeModifier beeMod : this.modifiers) {
				if (beeMod.isSealed()) {
					return true;
				}
			}
			return false;
		}

		@Override
		public boolean isSelfLighted() {
			if (!this.powered) {
				return false;
			}
			for (final IBeeModifier beeMod : this.modifiers) {
				if (beeMod.isSelfLighted()) {
					return true;
				}
			}
			return false;
		}

		@Override
		public boolean isSunlightSimulated() {
			if (!this.powered) {
				return false;
			}
			for (final IBeeModifier beeMod : this.modifiers) {
				if (beeMod.isSunlightSimulated()) {
					return true;
				}
			}
			return false;
		}

		@Override
		public boolean isHellish() {
			if (!this.powered) {
				return false;
			}
			for (final IBeeModifier beeMod : this.modifiers) {
				if (beeMod.isHellish()) {
					return true;
				}
			}
			return false;
		}

		@Override
		public void wearOutEquipment(final int amount) {
			this.getUtil().useEnergyMJ(this.powerUsage);
		}
	}

}
