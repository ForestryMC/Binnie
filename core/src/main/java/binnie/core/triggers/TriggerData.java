package binnie.core.triggers;

import javax.annotation.Nullable;
import java.util.Map;

import buildcraft.api.statements.ITriggerExternal;

public class TriggerData implements Map.Entry<ITriggerExternal, Boolean>
{
	@Nullable
	private final ITriggerExternal key;
	private Boolean value;

	public TriggerData(@Nullable ITriggerExternal key, Boolean value) {
		if (key == null) {
			throw new NullPointerException();
		}
		this.key = key;
		this.value = value;
	}

	@Override
	public ITriggerExternal getKey() {
		return this.key;
	}

	@Override
	public Boolean getValue() {
		return this.value;
	}

	@Override
	public Boolean setValue(final Boolean value) {
		Boolean old = this.value;
		this.value = value;
		return old;
	}
}
