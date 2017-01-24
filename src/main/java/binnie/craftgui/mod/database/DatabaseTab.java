package binnie.craftgui.mod.database;

import binnie.Binnie;
import binnie.core.AbstractMod;
import binnie.craftgui.core.ITooltipHelp;
import binnie.craftgui.core.Tooltip;

public class DatabaseTab implements ITooltipHelp {
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
		return Binnie.LANGUAGE.localise(this.mod, "gui.database.tab." + this.unloc);
	}

	@Override
	public void getHelpTooltip(final Tooltip tooltip) {
		tooltip.add(Binnie.LANGUAGE.localiseOrBlank(this.mod, "gui.database.tab." + this.unloc + ".help"));
	}
}
