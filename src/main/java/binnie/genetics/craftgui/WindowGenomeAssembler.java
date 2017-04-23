// 
// Decompiled by Procyon v0.5.30
// 

package binnie.genetics.craftgui;

import binnie.genetics.Genetics;
import binnie.core.AbstractMod;
import binnie.core.craftgui.minecraft.Window;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.inventory.IInventory;
import net.minecraft.entity.player.EntityPlayer;

public class WindowGenomeAssembler extends WindowMachine
{
	public static Window create(final EntityPlayer player, final IInventory inventory, final Side side) {
		return new WindowGenomeAssembler(player, inventory, side);
	}

	public WindowGenomeAssembler(final EntityPlayer player, final IInventory inventory, final Side side) {
		super(320, 240, player, inventory, side);
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
