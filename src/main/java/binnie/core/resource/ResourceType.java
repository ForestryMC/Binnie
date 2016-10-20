package binnie.core.resource;

public enum ResourceType {
    Item("items"),
    Block("blocks"),
    Tile("tile"),
    GUI("gui"),
    FX("fx"),
    Entity("entities");

    String name;

    private ResourceType(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
