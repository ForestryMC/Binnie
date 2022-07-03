package binnie.botany.api;

public enum EnumSoilType {
    SOIL,
    LOAM,
    FLOWERBED;

    public String getID() {
        return name().toLowerCase();
    }
}
