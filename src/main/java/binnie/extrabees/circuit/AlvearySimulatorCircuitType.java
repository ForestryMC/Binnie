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
		LowVoltage.logic.setModifier(EnumBeeModifier.PRODUCTION, 1.5f, 5.0f);
		HighVoltage.logic.setModifier(EnumBeeModifier.PRODUCTION, 2.5f, 10.0f);
		Plant.logic.setModifier(EnumBeeModifier.FLOWERING, 1.5f, 5.0f);
		Death.logic.setModifier(EnumBeeModifier.LIFESPAN, 0.8f, 0.2f);
		Life.logic.setModifier(EnumBeeModifier.LIFESPAN, 1.5f, 5.0f);
		Nether.logic.setModifier(EnumBeeBooleanModifier.Hellish);
		Mutation.logic.setModifier(EnumBeeModifier.MUTATION, 1.5f, 5.0f);
		Inhibitor.logic.setModifier(EnumBeeModifier.TERRITORY, 0.4f, 0.1f);
		Inhibitor.logic.setModifier(EnumBeeModifier.PRODUCTION, 0.9f, 0.5f);
		Territory.logic.setModifier(EnumBeeModifier.TERRITORY, 1.5f, 5.0f);
		for (final AlvearySimulatorCircuitType type : values()) {
			type.logic.setModifier(EnumBeeModifier.GENETIC_DECAY, 1.5f, 10.0f);
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
		return this.logic.getModifier(EnumBeeModifier.TERRITORY, currentModifier);
	}

	@Override
	public float getMutationModifier(@Nonnull final IBeeGenome genome, @Nonnull final IBeeGenome mate, final float currentModifier) {
		return this.logic.getModifier(EnumBeeModifier.MUTATION, currentModifier);
	}

	@Override
	public float getLifespanModifier(@Nonnull final IBeeGenome genome, @Nullable final IBeeGenome mate, final float currentModifier) {
		return this.logic.getModifier(EnumBeeModifier.LIFESPAN, currentModifier);
	}

	@Override
	public float getProductionModifier(@Nonnull final IBeeGenome genome, final float currentModifier) {
		return this.logic.getModifier(EnumBeeModifier.PRODUCTION, currentModifier);
	}

	@Override
	public float getFloweringModifier(@Nonnull final IBeeGenome genome, final float currentModifier) {
		return this.logic.getModifier(EnumBeeModifier.FLOWERING, currentModifier);
	}

	@Override
	public float getGeneticDecay(@Nonnull final IBeeGenome genome, final float currentModifier) {
		return this.logic.getModifier(EnumBeeModifier.GENETIC_DECAY, currentModifier);
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
