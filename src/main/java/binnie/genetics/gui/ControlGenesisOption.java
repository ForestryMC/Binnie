// 
// Decompiled by Procyon v0.5.30
// 

package binnie.genetics.gui;

import binnie.core.craftgui.geometry.TextJustification;
import binnie.core.craftgui.geometry.IArea;
import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.controls.listbox.ControlList;
import binnie.core.genetics.Gene;
import binnie.core.craftgui.controls.listbox.ControlOption;

public class ControlGenesisOption extends ControlOption<Gene>
{
	public ControlGenesisOption(final ControlList<Gene> parent, final Gene gene, final int y) {
		super(parent, gene, y);
	}

	String getAlleleName() {
		return this.getValue().getAlleleName();
	}

	String getChromosomeName() {
		return this.getValue().getShortChromosomeName();
	}

	@Override
	public void onRenderBackground() {
		super.onRenderBackground();
		CraftGUI.Render.text(new IArea(0.0f, 0.0f, 70.0f, 22.0f), TextJustification.MiddleCenter, this.getChromosomeName(), this.getColour());
		CraftGUI.Render.text(new IArea(75.0f, 0.0f, 80.0f, 22.0f), TextJustification.MiddleCenter, this.getAlleleName(), this.getColour());
		CraftGUI.Render.solid(new IArea(70.0f, 2.0f, 1.0f, 16.0f), -16777216 + this.getColour());

	}
}
