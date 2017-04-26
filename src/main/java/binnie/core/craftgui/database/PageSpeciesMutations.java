package binnie.core.craftgui.database;

import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.ControlText;
import binnie.core.craftgui.controls.ControlTextCentered;
import forestry.api.genetics.IAlleleSpecies;

public class PageSpeciesMutations extends PageSpecies {
	private ControlText pageSpeciesFurtherTitle;
	private ControlMutationBox pageSpeciesFurtherList;

	public PageSpeciesMutations(IWidget parent, DatabaseTab tab) {
		super(parent, tab);
		pageSpeciesFurtherTitle = new ControlTextCentered(this, 8.0f, "Further Mutations");
		pageSpeciesFurtherList = new ControlMutationBox(this, 4, 20, 136, 152, ControlMutationBox.Type.Further);
	}

	@Override
	public void onValueChanged(IAlleleSpecies species) {
		pageSpeciesFurtherList.setSpecies(species);
	}
}
