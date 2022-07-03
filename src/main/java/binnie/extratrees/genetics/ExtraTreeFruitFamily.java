package binnie.extratrees.genetics;

import forestry.api.genetics.IFruitFamily;

public enum ExtraTreeFruitFamily implements IFruitFamily {
    Berry("Berries", "berry", "berri"),
    Citrus("Citrus", "citrus", "citrus");

    protected String name;
    protected String uid;
    protected String scientific;

    ExtraTreeFruitFamily(String name, String uid, String scientific) {
        this.name = name;
        this.uid = uid;
        this.scientific = scientific;
    }

    @Override
    public String getUID() {
        return "binnie.family." + uid;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getScientific() {
        return scientific;
    }

    @Override
    public String getDescription() {
        return name;
    }
}
