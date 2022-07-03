package binnie.botany.api;

public enum EnumAcidity {
    ACID,
    NEUTRAL,
    ALKALINE;

    public String getID() {
        return name().toLowerCase();
    }
}
