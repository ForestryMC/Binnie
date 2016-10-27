package binnie.craftgui.genetics.machine;

import binnie.craftgui.minecraft.Window;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraftforge.fml.relauncher.Side;

public class WindowGeneBankNEI {
	public static Window create(final EntityPlayer player, final IInventory inventory, final Side side) {
		return new WindowGeneBank(player, inventory, side, true);
	}
}
