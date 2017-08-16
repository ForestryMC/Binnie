package binnie.genetics.api;

import javax.annotation.Nullable;

import binnie.genetics.api.acclimatiser.IAcclimatiserManager;
import binnie.genetics.api.analyst.IAnalystManager;

public class GeneticsApi {
	@Nullable
	public static IAcclimatiserManager acclimatiserManager;

	@Nullable
	public static IAnalystManager analystManager;
}
