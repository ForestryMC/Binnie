package binnie.extratrees.genetics.fruits;

import java.util.Locale;

public enum FruitPod {
	COCOA,
	BANANA,
	COCONUT,
	PLANTAIN,
	RED_BANANA,
	PAPAYIMAR;

	FruitPod() {
	}

	public String getModelName() {
		return name().toLowerCase(Locale.ENGLISH);
	}

}
