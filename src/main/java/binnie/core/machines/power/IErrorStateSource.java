package binnie.core.machines.power;

import javax.annotation.Nullable;

public interface IErrorStateSource {
	@Nullable
	ErrorState canWork();

	@Nullable
	ErrorState canProgress();
}
