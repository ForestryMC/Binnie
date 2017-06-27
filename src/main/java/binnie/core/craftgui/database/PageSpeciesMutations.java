package binnie.core.craftgui.database;

import forestry.api.genetics.IAlleleSpecies;

import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.ControlText;
import binnie.core.craftgui.controls.ControlTextCentered;
import binnie.core.util.I18N;

public class PageSpeciesMutations extends PageSpecies {
	private ControlText pageSpeciesFurther_Title;
	private ControlMutationBox pageSpeciesFurther_List;

	public PageSpeciesMutations(final IWidget parent, final DatabaseTab tab) {
		super(parent, tab);
		this.pageSpeciesFurther_Title = new ControlTextCentered(this, 8, I18N.localise("binniecore.gui.database.mutations.further"));
		this.pageSpeciesFurther_List = new ControlMutationBox(this, 4, 20, 136, 152, ControlMutationBox.Type.Further);
	}

	@Override
	public void onValueChanged(final IAlleleSpecies species) {
		this.pageSpeciesFurther_List.setSpecies(species);
	}
}
