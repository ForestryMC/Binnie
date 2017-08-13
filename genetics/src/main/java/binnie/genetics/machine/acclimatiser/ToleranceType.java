package binnie.genetics.machine.acclimatiser;

import binnie.botany.api.BotanyAPI;
import binnie.botany.api.gardening.EnumFertiliserType;
import binnie.botany.api.gardening.IGardeningManager;
import net.minecraft.item.ItemStack;

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
				IGardeningManager gardening = BotanyAPI.gardening;
				if (gardening.isFertiliser(EnumFertiliserType.ACID, stack)) {
					return -0.5f * gardening.getFertiliserStrength(stack);
				}
				if (gardening.isFertiliser(EnumFertiliserType.ALKALINE, stack)) {
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
