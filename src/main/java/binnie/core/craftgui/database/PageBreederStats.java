// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.craftgui.database;

import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.ControlText;
import binnie.core.craftgui.controls.ControlTextCentered;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.genetics.BreedingSystem;

public class PageBreederStats extends Control
{
	String player;

	public PageBreederStats(IWidget parent, int w, int h, String player) {
		super(parent, 0.0f, 0.0f, w, h);
		this.player = player;
		ControlText pageBranchOverview_branchName = new ControlTextCentered(this, 8.0f, "§nStats§r");
		BreedingSystem system = ((WindowAbstractDatabase) getSuperParent()).getBreedingSystem();
	}
}
