package binnie.core.genetics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BeeModifierLogic {
	private Map<EnumBeeModifier, Float[]> modifiers;
	private List<EnumBeeBooleanModifier> booleanModifiers;

	public BeeModifierLogic() {
		this.modifiers = new HashMap<EnumBeeModifier, Float[]>();
		this.booleanModifiers = new ArrayList<EnumBeeBooleanModifier>();
	}

	public float getModifier(final EnumBeeModifier modifier, final float currentModifier) {
		if (!this.modifiers.containsKey(modifier)) {
			return 1.0f;
		}

		Float[] values = modifiers.get(modifier);
		final float mult = values[0];
		final float max = values[1];
		if (max >= 1.0f) {
			if (max <= currentModifier) {
				return 1.0f;
			}
			return Math.min(max / currentModifier, mult);
		}

		if (max >= currentModifier) {
			return 1.0f;
		}
		return Math.max(max / currentModifier, mult);
	}

	public boolean getModifier(final EnumBeeBooleanModifier modifier) {
		return booleanModifiers.contains(modifier);
	}

	public void setModifier(final EnumBeeBooleanModifier modifier) {
		booleanModifiers.add(modifier);
	}

	public void setModifier(final EnumBeeModifier modifier, final float mult, final float max) {
		modifiers.put(modifier, new Float[] { mult, max });
	}
}
