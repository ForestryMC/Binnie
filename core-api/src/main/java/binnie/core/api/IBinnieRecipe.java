package binnie.core.api;

import java.util.Collection;

public interface IBinnieRecipe {

	Collection<Object> getInputs();

	Collection<Object> getOutputs();
}
