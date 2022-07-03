package binnie.genetics.machine.acclimatiser;

import binnie.botany.gardening.Gardening;
import net.minecraft.item.ItemStack;

public enum ToleranceType {
    Temperature,
    Humidity,
    PH;

    public float getEffect(ItemStack stack) {
        switch (this) {
            case Temperature:
                return Acclimatiser.getTemperatureEffect(stack);

            case Humidity:
                return Acclimatiser.getHumidityEffect(stack);

            case PH:
                if (Gardening.isAcidFertiliser(stack)) {
                    return -0.5f * Gardening.getFertiliserStrength(stack);
                }
                if (Gardening.isAlkalineFertiliser(stack)) {
                    return 0.5f * Gardening.getFertiliserStrength(stack);
                }
        }
        return 0.0f;
    }

    public boolean hasEffect(ItemStack stack) {
        return getEffect(stack) != 0.0f;
    }
}
