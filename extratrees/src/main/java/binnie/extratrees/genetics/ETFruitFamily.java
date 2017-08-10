package binnie.extratrees.genetics;

import forestry.api.genetics.IFruitFamily;

public enum ETFruitFamily implements IFruitFamily {
	Berry("Berries", "berry", "berri"),
	Citrus("Citrus", "citrus", "citrus");

	String name;
	String uid;
	String scientific;

	ETFruitFamily(final String name, final String uid, final String scientific) {
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
