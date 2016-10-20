package binnie.extratrees.api;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface IToolHammer {
    boolean isActive(final ItemStack p0);

    void onHammerUsed(final ItemStack p0, final EntityPlayer p1);
}
