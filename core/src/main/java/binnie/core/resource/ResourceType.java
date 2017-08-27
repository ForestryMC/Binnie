package binnie.core.resource;

public enum ResourceType {
	ITEM("items"),
	Block("blocks"),
	GUI("gui"),
	FX("fx"),
	ENTITY("entities");

	private final String name;

	ResourceType(final String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return this.name;
	}
}
