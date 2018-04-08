package binnie.core.genetics;

import java.util.Locale;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;

import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAllele;

import javax.annotation.Nullable;

public enum Tolerance {
	NONE(0, 0),
	BOTH_1(-1, 1), BOTH_2(-2, 2), BOTH_3(-3, 3), BOTH_4(-4, 4), BOTH_5(-5, 5),
	UP_1(0, 1), UP_2(0, 2), UP_3(0, 3), UP_4(0, 4), UP_5(0, 5),
	DOWN_1(-1, 0), DOWN_2(-2, 0), DOWN_3(-3, 0), DOWN_4(-4, 0), DOWN_5(-5, 0);

	private final int[] bounds;
	private final String uid;

	Tolerance(final int a, final int b) {
		this.bounds = new int[]{a, b};
		this.uid = "forestry.tolerance" + WordUtils.capitalize(this.toString());
	}

	public static Tolerance get(final forestry.api.genetics.EnumTolerance tol) {
		return values()[tol.ordinal()];
	}

	public static <T extends Enum<T>> boolean canTolerate(final T base, final T test, final forestry.api.genetics.EnumTolerance tol) {
		return get(tol).canTolerate(base, test);
	}

	@Nullable
	private static Pattern PATTERN;

	private static Pattern GetPattern() {
		if (PATTERN == null) {
			PATTERN = Pattern.compile("_", Pattern.LITERAL);
		}
		return PATTERN;
	}

	@Override
	public String toString() {
		return GetPattern().matcher(super.toString().toLowerCase(Locale.ENGLISH)).replaceAll(StringUtils.EMPTY);
	}

	public String getUID() {
		return uid;
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
