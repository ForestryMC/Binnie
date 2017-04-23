// 
// Decompiled by Procyon v0.5.30
// 

package binnie.genetics.craftgui;

import cpw.mods.fml.relauncher.Side;
import net.minecraft.inventory.IInventory;
import net.minecraft.entity.player.EntityPlayer;
import binnie.core.craftgui.minecraft.Window;

public abstract class WindowMachine extends Window
{
	public WindowMachine(final int width, final int height, final EntityPlayer player, final IInventory inventory, final Side side) {
		super(width, height, player, inventory, side);
	}

	public abstract String getTitle();

	@Override
	public void initialiseClient() {
		this.setTitle(this.getTitle());
	}
}
