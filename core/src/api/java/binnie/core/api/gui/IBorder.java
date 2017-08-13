package binnie.core.api.gui;

import java.awt.Point;

public interface IBorder {
	int t();

	int b();

	int l();

	int r();

	int t(int n);

	int b(int n);

	int l(int n);

	int r(int n);

	boolean isNonZero();

	@Deprecated
	Point tl();

	@Deprecated
	Point tr();

	@Deprecated
	Point bl();

	@Deprecated
	Point br();

	IBorder add(IBorder o);
}
