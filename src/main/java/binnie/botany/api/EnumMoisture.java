package binnie.botany.api;

public enum EnumMoisture {
    DRY,
    NORMAL,
    DAMP;

    public String getID() {
        return name().toLowerCase();
    }
}
