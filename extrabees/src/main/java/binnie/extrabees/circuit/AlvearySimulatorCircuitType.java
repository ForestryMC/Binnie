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

	LOW_VOLTAGE(3, 10),
	HIGH_VOLTAGE(5, 20),
	PLANT(10, 10),
	DEATH(6, 10),
	LIFE(11, 10),
	NETHER(7, 15),
	MUTATION(4, 15),
	INHIBITOR(1, 10),
	TERRITORY(2, 10);

	static {
		LOW_VOLTAGE.logic.setModifier(EnumBeeModifier.PRODUCTION, 1.5f, 5.0f);
		HIGH_VOLTAGE.logic.setModifier(EnumBeeModifier.PRODUCTION, 2.5f, 10.0f);
		PLANT.logic.setModifier(EnumBeeModifier.FLOWERING, 1.5f, 5.0f);
		DEATH.logic.setModifier(EnumBeeModifier.LIFESPAN, 0.8f, 0.2f);
		LIFE.logic.setModifier(EnumBeeModifier.LIFESPAN, 1.5f, 5.0f);
		NETHER.logic.setModifier(EnumBeeBooleanModifier.Hellish);
		MUTATION.logic.setModifier(EnumBeeModifier.MUTATION, 1.5f, 5.0f);
		INHIBITOR.logic.setModifier(EnumBeeModifier.TERRITORY, 0.4f, 0.1f);
		INHIBITOR.logic.setModifier(EnumBeeModifier.PRODUCTION, 0.9f, 0.5f);
		TERRITORY.logic.setModifier(EnumBeeModifier.TERRITORY, 1.5f, 5.0f);
		for (final AlvearySimulatorCircuitType type : values()) {
			type.logic.setModifier(EnumBeeModifier.GENETIC_DECAY, 1.5f, 10.0f);
		}
	}

	private final int recipe;
	private final int power;
	private final BeeModifierLogic logic;

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
		for (final EnumBeeBooleanModifier modifier : EnumBeeBooleanModifier.values()) {
			if (this.logic.getModifier(modifier)) {
				circuit.addTooltip(modifier.getName());
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

	public int getRecipe() {
		return recipe;
	}

	public int getPower() {
		return power;
	}
}
