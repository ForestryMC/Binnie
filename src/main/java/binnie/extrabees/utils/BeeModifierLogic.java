package binnie.extrabees.utils;

import forestry.core.utils.Translator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BeeModifierLogic {

	public BeeModifierLogic() {
		this.modifiers = new HashMap<>();
		this.booleanModifiers = new ArrayList<>();
	}

	private Map<EnumBeeModifier, Float[]> modifiers;
	private List<EnumBeeBooleanModifier> booleanModifiers;

	public float getModifier(final EnumBeeModifier modifier, final float currentModifier) {
		if (!this.modifiers.containsKey(modifier)) {
			return 1.0f;
		}
		final float mult = this.modifiers.get(modifier)[0];
		final float limit = this.modifiers.get(modifier)[1];
		if (limit >= 1.0f) {
			if (limit <= currentModifier) {
				return 1.0f;
			}
			return Math.min(limit / currentModifier, mult);
		} else {
			if (limit >= currentModifier) {
				return 1.0f;
			}
			return Math.max(limit / currentModifier, mult);
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

	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		for (Map.Entry<EnumBeeModifier, Float[]> modifier : modifiers.entrySet()) {
			final EnumBeeModifier key = modifier.getKey();
			final Float[] value = modifier.getValue();
			final String modifierName = Translator.translateToLocal(key.getName());
			Float multiplier = value[0];
			Float limit = value[1];
			if (multiplier < 0.01) {
				multiplier = 0f;
			}
			if (limit < 0.01) {
				limit = 0f;
			}
			final String formatKey = limit >= 1.0f ? "bee.modifier.format.max" : "bee.modifier.format.min";
			final String valueString = Translator.translateToLocalFormatted(formatKey, multiplier, limit);
			tooltip.add(modifierName + ": " + valueString);
		}
	}

}
