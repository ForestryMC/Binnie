package binnie.genetics.craftgui;

import binnie.core.AbstractMod;
import binnie.core.craftgui.minecraft.Window;
import binnie.genetics.Genetics;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;

// TODO unused class?
public class WindowGenomeAssembler extends WindowMachine {
	public WindowGenomeAssembler(EntityPlayer player, IInventory inventory, Side side) {
		super(320, 240, player, inventory, side);
	}

	public static Window create(EntityPlayer player, IInventory inventory, Side side) {
		return new WindowGenomeAssembler(player, inventory, side);
	}

	@Override
	public String getTitle() {
		return "Genome Assembler";
	}

	@Override
	protected AbstractMod getMod() {
		return Genetics.instance;
	}

	@Override
	protected String getName() {
		return "GenomeAssembler";
	}
}
