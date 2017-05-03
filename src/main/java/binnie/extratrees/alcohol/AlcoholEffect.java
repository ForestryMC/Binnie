package binnie.extratrees.alcohol;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class AlcoholEffect {
	public static void makeDrunk(EntityPlayer player, float strength) {
		int existingStrength = player.isPotionActive(Potion.confusion) ? player.getActivePotionEffect(Potion.confusion).getAmplifier() : 0;
		int existingTime = player.isPotionActive(Potion.confusion) ? player.getActivePotionEffect(Potion.confusion).getDuration() : 0;
		int time = (int) (100.0 * Math.sqrt(strength)) + existingTime;

		float intensity = 0.1f * strength + existingStrength + existingTime / 500;
		if (time < 5) {
			time = 5;
		}

		float slowIntense = (intensity - 10.0f) / 4.0f;
		if (slowIntense < 0.0f) {
			slowIntense = 0.0f;
		}

		float blindIntense = (intensity - 25.0f) / 2.0f;
		if (blindIntense < 0.0f) {
			blindIntense = 0.0f;
		}

		player.addPotionEffect(new PotionEffect(Potion.confusion.id, time, (int) intensity, false));
		if (slowIntense > 0.0f) {
			player.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, time, (int) slowIntense, false));
		}
	}
}
