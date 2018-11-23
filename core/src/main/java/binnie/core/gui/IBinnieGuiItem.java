package binnie.core.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import binnie.core.BinnieCore;
import binnie.core.api.gui.IGuiItem;

public interface IBinnieGuiItem extends IGuiItem {
	@Override
	default void openGui(ItemStack itemStack, World world, EntityPlayer player) {
		BinnieCore.getBinnieProxy().openGui(getGuiID(itemStack), player, player.getPosition());
	}

	IBinnieGUID getGuiID(ItemStack itemStack);
}
