package binnie.core.gui.database;

import forestry.api.genetics.IAlleleSpecies;

import binnie.core.gui.IWidget;
import binnie.core.gui.controls.ControlText;
import binnie.core.gui.controls.ControlTextCentered;
import binnie.core.util.I18N;

public class PageSpeciesMutations extends PageSpecies {
	private ControlText title;
	private ControlMutationBox list;

	public PageSpeciesMutations(final IWidget parent, final DatabaseTab tab) {
		super(parent, tab);
		this.title = new ControlTextCentered(this, 8, I18N.localise(DatabaseConstants.MUTATIONS_KEY+ ".further"));
		this.list = new ControlMutationBox(this, 4, 20, 136, 152, ControlMutationBox.Type.Further);
	}

	@Override
	public void onValueChanged(final IAlleleSpecies species) {
		this.list.setSpecies(species);
	}
}
