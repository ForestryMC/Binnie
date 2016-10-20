package binnie.extratrees.alcohol;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;

public class AlcoholEffect {
    public static void makeDrunk(final EntityPlayer player, final float strength) {
        final int existingStrength = player.isPotionActive(MobEffects.NAUSEA) ? player.getActivePotionEffect(MobEffects.NAUSEA).getAmplifier() : 0;
        final int existingTime = player.isPotionActive(MobEffects.NAUSEA) ? player.getActivePotionEffect(MobEffects.NAUSEA).getDuration() : 0;
        int time = (int) (100.0 * Math.sqrt(strength)) + existingTime;
        final float intensity = 0.1f * strength + existingStrength + existingTime / 500;
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
    }
}