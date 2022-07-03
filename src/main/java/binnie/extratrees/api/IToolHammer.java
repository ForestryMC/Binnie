package binnie.extratrees.api;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface IToolHammer {
    boolean isActive(ItemStack stack);

    void onHammerUsed(ItemStack stack, EntityPlayer player);
}
