package binnie.core.craftgui.database;

import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.ControlText;
import binnie.core.craftgui.controls.ControlTextCentered;
import forestry.api.genetics.IAlleleSpecies;

public class PageSpeciesResultant extends PageSpecies {
	private ControlText pageSpeciesResultantTitle;
	private ControlMutationBox pageSpeciesResultantList;

	public PageSpeciesResultant(IWidget parent, DatabaseTab tab) {
		super(parent, tab);
		pageSpeciesResultantTitle = new ControlTextCentered(this, 8.0f, "Resultant Mutations");
		pageSpeciesResultantList = new ControlMutationBox(this, 4, 20, 136, 152, ControlMutationBox.Type.Resultant);
	}

	@Override
	public void onValueChanged(IAlleleSpecies species) {
		pageSpeciesResultantList.setSpecies(species);
	}
}
