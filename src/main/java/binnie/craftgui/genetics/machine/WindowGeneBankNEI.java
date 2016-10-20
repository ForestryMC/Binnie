// 
// Decompiled by Procyon v0.5.30
// 

package binnie.craftgui.genetics.machine;

import binnie.craftgui.minecraft.Window;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.inventory.IInventory;
import net.minecraft.entity.player.EntityPlayer;

public class WindowGeneBankNEI
{
	public static Window create(final EntityPlayer player, final IInventory inventory, final Side side) {
		return new WindowGeneBank(player, inventory, side, true);
	}
}
