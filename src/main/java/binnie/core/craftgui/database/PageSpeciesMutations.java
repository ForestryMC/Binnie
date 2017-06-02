package binnie.core.craftgui.database;

import binnie.core.BinnieCore;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.ControlText;
import binnie.core.craftgui.controls.ControlTextCentered;
import binnie.core.util.I18N;
import forestry.api.genetics.IAlleleSpecies;

public class PageSpeciesMutations extends PageSpecies {
	private ControlText furtherTitle;
	private ControlMutationBox furtherList;

	public PageSpeciesMutations(IWidget parent, DatabaseTab tab) {
		super(parent, tab);
		furtherTitle = new ControlTextCentered(this, 8.0f, I18N.localise(BinnieCore.instance, "gui.database.furtherMutations"));
		furtherList = new ControlMutationBox(this, 4, 20, 136, 152, ControlMutationBox.Type.FURTHER);
	}

	@Override
	public void onValueChanged(IAlleleSpecies species) {
		furtherList.setSpecies(species);
	}
}
