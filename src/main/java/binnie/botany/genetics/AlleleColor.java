package binnie.botany.genetics;

import forestry.api.genetics.IAlleleInteger;

public class AlleleColor implements IAlleleInteger {
    private String uid;
    private int value;
    private EnumFlowerColor color;
    private boolean dominant = true;

    public AlleleColor(EnumFlowerColor color, String uid, int value) {
        this.color = color;
        this.uid = uid;
        this.value = value;
    }

    @Override
    public String getUID() {
        return uid;
    }

    @Override
    public boolean isDominant() {
        return dominant;
    }

    @Override
    public String getName() {
        return color.getName();
    }

    @Override
    public int getValue() {
        return value;
    }

    public EnumFlowerColor getColor() {
        return color;
    }

    @Override
    public String getUnlocalizedName() {
        return getUID();
    }
}
