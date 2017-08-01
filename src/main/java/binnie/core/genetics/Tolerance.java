package binnie.core.genetics;

import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.EnumTolerance;
import forestry.api.genetics.IAllele;

public enum Tolerance {
	NONE(0, 0),
	BOTH_1(-1, 1),
	BOTH_2(-2, 2),
	BOTH_3(-3, 3),
	BOTH_4(-4, 4),
	BOTH_5(-5, 5),
	UP_1(0, 1),
	UP_2(0, 2),
	UP_3(0, 3),
	UP_4(0, 4),
	UP_5(0, 5),
	DOWN_1(-1, 0),
	DOWN_2(-2, 0),
	DOWN_3(-3, 0),
	DOWN_4(-4, 0),
	DOWN_5(-5, 0);

	private int[] bounds;

	Tolerance(final int a, final int b) {
		this.bounds = new int[]{a, b};
	}

	public static Tolerance get(final EnumTolerance tol) {
		return values()[tol.ordinal()];
	}

	public static <T extends Enum<T>> boolean canTolerate(final T base, final T test, final EnumTolerance tol) {
		return get(tol).canTolerate(base, test);
	}

	public String getUID() {
		return "forestry.tolerance" + this.toString();
	}

	public int[] getBounds() {
		return this.bounds;
	}

	public IAllele getAllele() {
		return AlleleManager.alleleRegistry.getAllele(this.getUID());
	}

	public <T extends Enum<T>> boolean canTolerate(final T base, final T test) {
		return test.ordinal() <= base.ordinal() + this.bounds[1] && test.ordinal() >= base.ordinal() + this.bounds[0];
	}
}
