package binnie.genetics.machine.acclimatiser;

import binnie.genetics.api.acclimatiser.IToleranceType;
import net.minecraft.item.ItemStack;

public enum ToleranceType implements IToleranceType {
	Temperature,
	Humidity;

	public float getEffect(final ItemStack stack) {
		switch (this) {
			case Temperature: {
				return Acclimatiser.getTemperatureEffect(stack);
			}
			case Humidity: {
				return Acclimatiser.getHumidityEffect(stack);
			}
		}
		return 0.0f;
	}
}
