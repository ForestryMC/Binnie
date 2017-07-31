package binnie.core.gui.database;

import net.minecraft.util.text.TextFormatting;

import binnie.core.genetics.BreedingSystem;
import binnie.core.gui.IWidget;
import binnie.core.gui.controls.ControlText;
import binnie.core.gui.controls.ControlTextCentered;
import binnie.core.gui.controls.core.Control;
import binnie.core.util.I18N;

public class PageBreederStats extends Control {
	String player;

	public PageBreederStats(final IWidget parent, final int w, final int h, final String player) {
		super(parent, 0, 0, w, h);
		this.player = player;
		final ControlText pageBranchOverview_branchName = new ControlTextCentered(this, 8, TextFormatting.UNDERLINE + I18N.localise(DatabaseConstants.BREEDER_KEY + ".stats"));
		final BreedingSystem system = ((WindowAbstractDatabase) this.getTopParent()).getBreedingSystem();
	}
}
