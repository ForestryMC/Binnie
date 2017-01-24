package binnie.craftgui.genetics.machine;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraftforge.fml.relauncher.Side;

public class WindowGeneBankNEI extends WindowGeneBank {
	public WindowGeneBankNEI(final EntityPlayer player, final IInventory inventory, final Side side) {
		super(player, inventory, side, true);
	}
}
