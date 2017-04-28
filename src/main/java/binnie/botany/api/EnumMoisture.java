package binnie.botany.api;

public enum EnumMoisture {
	Dry,
	Normal,
	Damp;

	public String getID() {
		return name().toLowerCase();
	}
}
