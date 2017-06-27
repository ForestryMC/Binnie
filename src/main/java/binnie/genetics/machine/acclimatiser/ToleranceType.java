package binnie.genetics.machine.acclimatiser;

import net.minecraft.item.ItemStack;

import binnie.botany.api.IGardeningManager;
import binnie.botany.core.BotanyCore;

public enum ToleranceType {
	Temperature,
	Humidity,
	PH;

	public float getEffect(final ItemStack stack) {
		switch (this) {
			case Temperature: {
				return Acclimatiser.getTemperatureEffect(stack);
			}
			case Humidity: {
				return Acclimatiser.getHumidityEffect(stack);
			}
			case PH: {
				IGardeningManager gardening = BotanyCore.getGardening();
				if (gardening.isAcidFertiliser(stack)) {
					return -0.5f * gardening.getFertiliserStrength(stack);
				}
				if (gardening.isAlkalineFertiliser(stack)) {
					return 0.5f * gardening.getFertiliserStrength(stack);
				}
				break;
			}
		}
		return 0.0f;
	}

	public boolean hasEffect(final ItemStack stack) {
		return this.getEffect(stack) != 0.0f;
	}
}
