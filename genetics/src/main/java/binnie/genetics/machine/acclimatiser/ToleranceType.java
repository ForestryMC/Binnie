package binnie.genetics.machine.acclimatiser;

import net.minecraft.item.ItemStack;

import binnie.genetics.api.acclimatiser.IToleranceType;

public enum ToleranceType implements IToleranceType {
	Temperature,
	Humidity;

	public float getEffect(ItemStack stack) {
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
