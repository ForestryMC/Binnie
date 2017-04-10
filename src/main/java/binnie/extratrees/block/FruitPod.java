package binnie.extratrees.block;

import java.util.Locale;

public enum FruitPod {
	Cocoa,
	Banana,
	Coconut,
	Plantain,
	RedBanana,
	Papayimar;

	FruitPod() {
	}
	
	public String getModelName() {
		return name().toLowerCase(Locale.ENGLISH);
	}

}
