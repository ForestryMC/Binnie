package binnie.core.craftgui.database;

import net.minecraft.util.text.TextFormatting;

import binnie.Binnie;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.ControlText;
import binnie.core.craftgui.controls.ControlTextCentered;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.genetics.BreedingSystem;

public class PageBreederStats extends Control {
	String player;

	public PageBreederStats(final IWidget parent, final int w, final int h, final String player) {
		super(parent, 0, 0, w, h);
		this.player = player;
		final ControlText pageBranchOverview_branchName = new ControlTextCentered(this, 8, TextFormatting.UNDERLINE + Binnie.LANGUAGE.localise("binniecore.gui.database.breeder.stats"));
		final BreedingSystem system = ((WindowAbstractDatabase) this.getTopParent()).getBreedingSystem();
	}
}
