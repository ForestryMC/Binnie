package binnie.core.machines.errors;

import javax.annotation.Nullable;

public interface IErrorStateSource {
	@Nullable
	ErrorState canWork();

	@Nullable
	ErrorState canProgress();
}
