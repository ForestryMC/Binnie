package binnie.core.craftgui.database;

import forestry.api.genetics.IAlleleSpecies;

import binnie.Binnie;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.ControlText;
import binnie.core.craftgui.controls.ControlTextCentered;

public class PageSpeciesResultant extends PageSpecies {
	private ControlText pageSpeciesResultant_Title;
	private ControlMutationBox pageSpeciesResultant_List;

	public PageSpeciesResultant(final IWidget parent, final DatabaseTab tab) {
		super(parent, tab);
		this.pageSpeciesResultant_Title = new ControlTextCentered(this, 8, Binnie.LANGUAGE.localise("binniecore.gui.database.mutations.resultant"));
		this.pageSpeciesResultant_List = new ControlMutationBox(this, 4, 20, 136, 152, ControlMutationBox.Type.Resultant);
	}

	@Override
	public void onValueChanged(final IAlleleSpecies species) {
		this.pageSpeciesResultant_List.setSpecies(species);
	}
}
