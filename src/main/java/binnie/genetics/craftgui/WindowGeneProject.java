package binnie.genetics.craftgui;

import binnie.core.AbstractMod;
import binnie.core.craftgui.minecraft.Window;
import binnie.genetics.Genetics;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;

// TODO unused class?
public class WindowGeneProject extends Window {
	public WindowGeneProject(EntityPlayer player, IInventory inventory, Side side) {
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
		setTitle("Gene Projects");
	}
}
