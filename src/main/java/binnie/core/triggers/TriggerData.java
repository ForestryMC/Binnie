package binnie.core.triggers;

import buildcraft.api.statements.ITriggerExternal;

import java.util.Map;

public class TriggerData implements Map.Entry<ITriggerExternal, Boolean> {
	private final ITriggerExternal key;
	private Boolean value;

	public TriggerData(final ITriggerExternal key, final Boolean value) {
		if (key == null) {
			throw new NullPointerException();
		}
		this.key = key;
		this.value = value;
	}

	@Override
	public ITriggerExternal getKey() {
		return key;
	}

	@Override
	public Boolean getValue() {
		return value;
	}

	@Override
	public Boolean setValue(final Boolean value) {
		final Boolean old = this.value;
		this.value = value;
		return old;
	}
}
