package binnie.genetics.api;

import binnie.genetics.api.acclimatiser.IAcclimatiserManager;
import binnie.genetics.api.analyst.IAnalystManager;

import javax.annotation.Nullable;

public class GeneticsApi {
	@Nullable
	public static IAcclimatiserManager acclimatiserManager;

	@Nullable
	public static IAnalystManager analystManager;
}
