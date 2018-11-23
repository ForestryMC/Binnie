package binnie.core.gui.database;

import forestry.api.genetics.IAlleleSpecies;

import binnie.core.api.gui.IWidget;
import binnie.core.gui.controls.ControlText;
import binnie.core.gui.controls.ControlTextCentered;
import binnie.core.util.I18N;

public class PageSpeciesResultant extends PageSpecies {
	private final ControlText title;
	private final ControlMutationBox list;

	public PageSpeciesResultant(IWidget parent, DatabaseTab tab) {
		super(parent, tab);
		this.title = new ControlTextCentered(this, 8, I18N.localise(DatabaseConstants.MUTATIONS_KEY + ".resultant"));
		this.list = new ControlMutationBox(this, 4, 20, 136, 152, ControlMutationBox.Type.Resultant);
	}

	@Override
	public void onValueChanged(IAlleleSpecies species) {
		this.list.setSpecies(species);
	}
}
