package binnie.extrabees.circuit;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeModifier;
import forestry.api.circuits.ICircuitLayout;

import binnie.extrabees.utils.BeeModifierLogic;
import binnie.extrabees.utils.EnumBeeBooleanModifier;
import binnie.extrabees.utils.EnumBeeModifier;

public enum AlvearySimulatorCircuitType implements IBeeModifier {

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
		LowVoltage.logic.setModifier(EnumBeeModifier.Production, 1.5f, 5.0f);
		HighVoltage.logic.setModifier(EnumBeeModifier.Production, 2.5f, 10.0f);
		Plant.logic.setModifier(EnumBeeModifier.Flowering, 1.5f, 5.0f);
		Death.logic.setModifier(EnumBeeModifier.Lifespan, 0.8f, 0.2f);
		Life.logic.setModifier(EnumBeeModifier.Lifespan, 1.5f, 5.0f);
		Nether.logic.setModifier(EnumBeeBooleanModifier.Hellish);
		Mutation.logic.setModifier(EnumBeeModifier.Mutation, 1.5f, 5.0f);
		Inhibitor.logic.setModifier(EnumBeeModifier.Territory, 0.4f, 0.1f);
		Inhibitor.logic.setModifier(EnumBeeModifier.Production, 0.9f, 0.5f);
		Territory.logic.setModifier(EnumBeeModifier.Territory, 1.5f, 5.0f);
		for (final AlvearySimulatorCircuitType type : values()) {
			type.logic.setModifier(EnumBeeModifier.GeneticDecay, 1.5f, 10.0f);
		}
	}

	public int recipe;
	public int power;
	BeeModifierLogic logic;

	AlvearySimulatorCircuitType(final int recipe, final int power) {
		this.logic = new BeeModifierLogic();
		this.recipe = recipe;
		this.power = power;
	}

	public void createCircuit(final ICircuitLayout layout) {
		final StimulatorCircuit circuit = new StimulatorCircuit(this, layout);
		for (final EnumBeeModifier modifier : EnumBeeModifier.values()) {
			final float mod = this.logic.getModifier(modifier, 1.0f);
			if (mod != 1.0f) {
				if (mod > 1.0f) {
					final int increase = (int) ((mod - 1.0f) * 100.0f);
					circuit.addTooltip("Increases " + modifier.getName() + " by " + increase + "%");
				} else {
					final int decrease = (int) ((1.0f - mod) * 100.0f);
					circuit.addTooltip("Decreases " + modifier.getName() + " by " + decrease + "%");
				}
			}
		}
	}

	@Override
	public float getTerritoryModifier(@Nonnull final IBeeGenome genome, final float currentModifier) {
		return this.logic.getModifier(EnumBeeModifier.Territory, currentModifier);
	}

	@Override
	public float getMutationModifier(@Nonnull final IBeeGenome genome, @Nonnull final IBeeGenome mate, final float currentModifier) {
		return this.logic.getModifier(EnumBeeModifier.Mutation, currentModifier);
	}

	@Override
	public float getLifespanModifier(@Nonnull final IBeeGenome genome, @Nullable final IBeeGenome mate, final float currentModifier) {
		return this.logic.getModifier(EnumBeeModifier.Lifespan, currentModifier);
	}

	@Override
	public float getProductionModifier(@Nonnull final IBeeGenome genome, final float currentModifier) {
		return this.logic.getModifier(EnumBeeModifier.Production, currentModifier);
	}

	@Override
	public float getFloweringModifier(@Nonnull final IBeeGenome genome, final float currentModifier) {
		return this.logic.getModifier(EnumBeeModifier.Flowering, currentModifier);
	}

	@Override
	public float getGeneticDecay(@Nonnull final IBeeGenome genome, final float currentModifier) {
		return this.logic.getModifier(EnumBeeModifier.GeneticDecay, currentModifier);
	}

	@Override
	public boolean isSealed() {
		return this.logic.getModifier(EnumBeeBooleanModifier.Sealed);
	}

	@Override
	public boolean isSelfLighted() {
		return this.logic.getModifier(EnumBeeBooleanModifier.SelfLighted);
	}

	@Override
	public boolean isSunlightSimulated() {
		return this.logic.getModifier(EnumBeeBooleanModifier.SunlightStimulated);
	}

	@Override
	public boolean isHellish() {
		return this.logic.getModifier(EnumBeeBooleanModifier.Hellish);
	}
}
