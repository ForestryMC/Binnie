package binnie.core.craftgui.database;

import binnie.core.AbstractMod;
import binnie.core.craftgui.ITooltipHelp;
import binnie.core.craftgui.Tooltip;
import binnie.core.util.I18N;

public class DatabaseTab implements ITooltipHelp {
	private AbstractMod mod;
	private String unloc;

	public DatabaseTab(AbstractMod mod, String unloc) {
		this.mod = mod;
		this.unloc = unloc;
	}

	@Override
	public String toString() {
		return I18N.localise(mod, "gui.database.tab." + unloc);
	}

	@Override
	public void getHelpTooltip(Tooltip tooltip) {
		tooltip.add(I18N.localiseOrBlank(mod, "gui.database.tab." + unloc + ".help"));
	}
}
