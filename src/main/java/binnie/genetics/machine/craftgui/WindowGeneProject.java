package binnie.genetics.machine.craftgui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;

import net.minecraftforge.fml.relauncher.Side;

import binnie.core.AbstractMod;
import binnie.core.gui.minecraft.Window;
import binnie.genetics.Genetics;

public class WindowGeneProject extends Window {
	public WindowGeneProject(final EntityPlayer player, final IInventory inventory, final Side side) {
		super(100, 100, player, inventory, side);
	}

	@Override
	protected AbstractMod getMod() {
		return Genetics.instance;
	}

	@Override
	protected String getBackgroundTextureName() {
		return "GeneProjects";
	}

	@Override
	public void initialiseClient() {
		this.setTitle("Gene Projects");
	}
}
