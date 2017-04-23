// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.craftgui.database;

import binnie.Binnie;
import binnie.core.AbstractMod;
import binnie.core.craftgui.ITooltipHelp;
import binnie.core.craftgui.Tooltip;

public class DatabaseTab implements ITooltipHelp
{
	private AbstractMod mod;
	private String unloc;
	private int colour;

	public DatabaseTab(final AbstractMod mod, final String unloc, final int colour) {
		this.mod = mod;
		this.unloc = unloc;
		this.colour = colour;
	}

	@Override
	public String toString() {
		return Binnie.Language.localise(mod, "gui.database.tab." + unloc);
	}

	@Override
	public void getHelpTooltip(final Tooltip tooltip) {
		tooltip.add(Binnie.Language.localiseOrBlank(mod, "gui.database.tab." + unloc + ".help"));
	}
}
