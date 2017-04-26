// 
// Decompiled by Procyon v0.5.30
// 

package binnie.genetics.craftgui;

import binnie.genetics.Genetics;
import binnie.core.AbstractMod;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.inventory.IInventory;
import net.minecraft.entity.player.EntityPlayer;
import binnie.core.craftgui.minecraft.Window;

public class WindowGeneProject extends Window
{
	public WindowGeneProject(final EntityPlayer player, final IInventory inventory, final Side side) {
		super(100.0f, 100.0f, player, inventory, side);
	}

	@Override
	protected AbstractMod getMod() {
		return Genetics.instance;
	}

	@Override
	protected String getName() {
		return "GeneProjects";
	}

	@Override
	public void initialiseClient() {
		this.setTitle("Gene Projects");
	}
}
