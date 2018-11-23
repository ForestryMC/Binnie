package binnie.extratrees.genetics.fruits;

import forestry.api.genetics.IFruitFamily;

public enum ETFruitFamily implements IFruitFamily {
	BERRY("Berries", "berry", "berri"),
	CITRUS("Citrus", "citrus", "citrus");

	private final String name;
	private final String uid;
	private final String scientific;

	ETFruitFamily(String name, String uid, String scientific) {
		this.name = name;
		this.uid = uid;
		this.scientific = scientific;
	}

	@Override
	public String getUID() {
		return "binnie.family." + this.uid;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public String getScientific() {
		return this.scientific;
	}

	@Override
	public String getDescription() {
		return this.name;
	}
}
