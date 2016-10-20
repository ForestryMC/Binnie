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
        final float mult = this.modifiers.get(modifier)[0];
        final float max = this.modifiers.get(modifier)[1];
        if (max >= 1.0f) {
            if (max <= currentModifier) {
                return 1.0f;
            }
            return Math.min(max / currentModifier, mult);
        } else {
            if (max >= currentModifier) {
                return 1.0f;
            }
            return Math.max(max / currentModifier, mult);
        }
    }

    public boolean getModifier(final EnumBeeBooleanModifier modifier) {
        return this.booleanModifiers.contains(modifier);
    }

    public void setModifier(final EnumBeeBooleanModifier modifier) {
        this.booleanModifiers.add(modifier);
    }

    public void setModifier(final EnumBeeModifier modifier, final float mult, final float max) {
        this.modifiers.put(modifier, new Float[]{mult, max});
    }
}
