package binnie.core.genetics;

import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.EnumTolerance;
import forestry.api.genetics.IAllele;

public enum Tolerance {
    None(0, 0),
    Both1(-1, 1),
    Both2(-2, 2),
    Both3(-3, 3),
    Both4(-4, 4),
    Both5(-5, 5),
    Up1(0, 1),
    Up2(0, 2),
    Up3(0, 3),
    Up4(0, 4),
    Up5(0, 5),
    Down1(-1, 0),
    Down2(-2, 0),
    Down3(-3, 0),
    Down4(-4, 0),
    Down5(-5, 0);

    private int[] bounds;

    Tolerance(int a, int b) {
        bounds = new int[] {a, b};
    }

    public String getUID() {
        return "forestry.tolerance" + toString();
    }

    public int[] getBounds() {
        return bounds;
    }

    public static Tolerance get(EnumTolerance tol) {
        return values()[tol.ordinal()];
    }

    public IAllele getAllele() {
        return AlleleManager.alleleRegistry.getAllele(getUID());
    }

    public <T extends Enum<T>> boolean canTolerate(T base, T test) {
        return test.ordinal() <= base.ordinal() + bounds[1] && test.ordinal() >= base.ordinal() + bounds[0];
    }

    public static <T extends Enum<T>> boolean canTolerate(T base, T test, EnumTolerance tol) {
        return get(tol).canTolerate(base, test);
    }
}
