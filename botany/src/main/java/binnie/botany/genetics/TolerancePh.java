package binnie.botany.genetics;

import net.minecraft.item.ItemStack;

import binnie.botany.api.BotanyAPI;
import binnie.botany.api.gardening.EnumFertiliserType;
import binnie.botany.api.gardening.IGardeningManager;
import binnie.genetics.api.acclimatiser.IToleranceType;

public class TolerancePh implements IToleranceType {
	@Override
	public float getEffect(ItemStack stack) {
		IGardeningManager gardening = BotanyAPI.gardening;
		if (gardening.isFertiliser(EnumFertiliserType.ACID, stack)) {
			return -0.5f * gardening.getFertiliserStrength(stack);
		}
		if (gardening.isFertiliser(EnumFertiliserType.ALKALINE, stack)) {
			return 0.5f * gardening.getFertiliserStrength(stack);
		}
		return 0;
	}
}
