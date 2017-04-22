package binnie.core.machines.inventory;

import binnie.core.util.*;

public abstract class Validator<T> implements IValidator<T> {
	public abstract String getTooltip();
}
