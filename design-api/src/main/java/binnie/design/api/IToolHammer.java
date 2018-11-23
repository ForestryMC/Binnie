package binnie.design.api;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface IToolHammer {
	boolean isActive(ItemStack p0);

	void onHammerUsed(ItemStack p0, EntityPlayer p1);
}
