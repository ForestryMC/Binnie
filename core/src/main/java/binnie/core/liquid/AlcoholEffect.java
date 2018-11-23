package binnie.core.liquid;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;

public class AlcoholEffect {
	public static void makeDrunk(EntityPlayer player, float strength) {
		PotionEffect potionEffect = player.getActivePotionEffect(MobEffects.NAUSEA);
		int existingStrength = potionEffect != null ? potionEffect.getAmplifier() : 0;
		int existingTime = potionEffect != null ? potionEffect.getDuration() : 0;
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
		player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, time, (int) intensity, false, true));
		if (slowIntense > 0.0f) {
			player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, time, (int) slowIntense, false, true));
		}
		if (blindIntense > 0.0f) {
			player.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, time, (int) blindIntense, false, true));
		}
	}
}