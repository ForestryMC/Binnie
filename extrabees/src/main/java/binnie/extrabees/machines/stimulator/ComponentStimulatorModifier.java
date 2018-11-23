package binnie.extrabees.machines.stimulator;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;

import net.minecraftforge.common.util.Constants;

import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeListener;
import forestry.api.apiculture.IBeeModifier;
import forestry.api.circuits.ChipsetManager;
import forestry.api.circuits.ICircuit;
import forestry.api.circuits.ICircuitBoard;

import binnie.core.machines.Machine;
import binnie.core.machines.network.INetwork;
import binnie.extrabees.circuit.StimulatorCircuit;
import binnie.extrabees.utils.ComponentBeeModifier;

public class ComponentStimulatorModifier extends ComponentBeeModifier implements
	INetwork.TilePacketSync,
	IBeeModifier,
	IBeeListener {
	private float powerUsage;
	private boolean powered;
	private StimulatorCircuit[] modifiers;

	public ComponentStimulatorModifier(Machine machine) {
		super(machine);
		this.powerUsage = 0.0f;
		this.powered = false;
		this.modifiers = new StimulatorCircuit[0];
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		StimulatorCircuit[] oldModifiers = modifiers;
		this.modifiers = this.getCircuits();
		this.powerUsage = 0.0f;
		for (StimulatorCircuit beeMod : this.modifiers) {
			this.powerUsage += beeMod.getPowerUsage();
		}
		boolean oldPowered = powered;
		this.powered = this.getUtil().hasEnergyMJ(this.powerUsage);
		if (powered != oldPowered || oldModifiers.length != modifiers.length || !Arrays.equals(oldModifiers, modifiers)) {
			this.getUtil().refreshBlock();
		}
	}

	@Nullable
	private ICircuitBoard getCircuit() {
		if (!this.getUtil().isSlotEmpty(AlvearyStimulator.SLOT_CIRCUIT)) {
			return ChipsetManager.circuitRegistry.getCircuitBoard(this.getUtil().getStack(AlvearyStimulator.SLOT_CIRCUIT));
		}
		return null;
	}

	private StimulatorCircuit[] getCircuits() {
		ICircuitBoard board = this.getCircuit();
		if (board == null) {
			return new StimulatorCircuit[0];
		}
		ICircuit[] circuits = board.getCircuits();
		List<StimulatorCircuit> mod = new ArrayList<>();
		for (ICircuit circuit : circuits) {
			if (circuit instanceof StimulatorCircuit) {
				mod.add((StimulatorCircuit) circuit);
			}
		}
		return mod.toArray(new StimulatorCircuit[0]);
	}

	@Override
	public float getTerritoryModifier(IBeeGenome genome, float currentModifier) {
		float mod = 1.0f;
		if (!this.powered) {
			return mod;
		}
		for (IBeeModifier beeMod : this.modifiers) {
			mod *= beeMod.getTerritoryModifier(genome, mod);
		}
		return mod;
	}

	@Override
	public float getMutationModifier(IBeeGenome genome, IBeeGenome mate, float currentModifier) {
		float mod = 1.0f;
		if (!this.powered) {
			return mod;
		}
		for (IBeeModifier beeMod : this.modifiers) {
			mod *= beeMod.getMutationModifier(genome, mate, mod);
		}
		return mod;
	}

	@Override
	public float getLifespanModifier(IBeeGenome genome, @Nullable IBeeGenome mate, float currentModifier) {
		float mod = 1.0f;
		if (!this.powered) {
			return mod;
		}
		for (IBeeModifier beeMod : this.modifiers) {
			mod *= beeMod.getLifespanModifier(genome, mate, mod);
		}
		return mod;
	}

	@Override
	public float getProductionModifier(IBeeGenome genome, float currentModifier) {
		float mod = 1.0f;
		if (!this.powered) {
			return mod;
		}
		for (IBeeModifier beeMod : this.modifiers) {
			mod *= beeMod.getProductionModifier(genome, mod);
		}
		return mod;
	}

	@Override
	public float getFloweringModifier(IBeeGenome genome, float currentModifier) {
		float mod = 1.0f;
		if (!this.powered) {
			return mod;
		}
		for (IBeeModifier beeMod : this.modifiers) {
			mod *= beeMod.getFloweringModifier(genome, mod);
		}
		return mod;
	}

	@Override
	public float getGeneticDecay(IBeeGenome genome, float currentModifier) {
		float mod = 1.0f;
		if (!this.powered) {
			return mod;
		}
		for (IBeeModifier beeMod : this.modifiers) {
			mod *= beeMod.getGeneticDecay(genome, mod);
		}
		return mod;
	}

	@Override
	public boolean isSealed() {
		if (!this.powered) {
			return false;
		}
		for (IBeeModifier beeMod : this.modifiers) {
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
		for (IBeeModifier beeMod : this.modifiers) {
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
		for (IBeeModifier beeMod : this.modifiers) {
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
		for (IBeeModifier beeMod : this.modifiers) {
			if (beeMod.isHellish()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void wearOutEquipment(int amount) {
		this.getUtil().useEnergyMJ(this.powerUsage);
	}

	@Override
	public void syncToNBT(NBTTagCompound nbt) {
		NBTTagList circuitsTag = new NBTTagList();
		for (StimulatorCircuit modifier : modifiers) {
			circuitsTag.appendTag(new NBTTagString(modifier.getUID()));
		}
		nbt.setTag("modifiers", circuitsTag);
		nbt.setBoolean("powered", powered);
	}

	@Override
	public void syncFromNBT(NBTTagCompound nbt) {
		NBTTagList circuitsTag = nbt.getTagList("modifiers", Constants.NBT.TAG_STRING);
		LinkedList<StimulatorCircuit> circuits = new LinkedList<>();
		for (int i = 0; i < circuitsTag.tagCount(); i++) {
			ICircuit circuit = ChipsetManager.circuitRegistry.getCircuit(circuitsTag.getStringTagAt(i));
			if (!(circuit instanceof StimulatorCircuit)) {
				continue;
			}
			circuits.add((StimulatorCircuit) circuit);
		}
		this.modifiers = circuits.toArray(new StimulatorCircuit[0]);
		powered = nbt.getBoolean("powered");
	}
}
