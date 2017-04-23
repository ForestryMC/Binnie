// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.craftgui.database;

import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.ControlText;
import binnie.core.craftgui.controls.ControlTextCentered;
import forestry.api.genetics.IAlleleSpecies;

public class PageSpeciesResultant extends PageSpecies
{
	private ControlText pageSpeciesResultant_Title;
	private ControlMutationBox pageSpeciesResultant_List;

	public PageSpeciesResultant(final IWidget parent, final DatabaseTab tab) {
		super(parent, tab);
		pageSpeciesResultant_Title = new ControlTextCentered(this, 8.0f, "Resultant Mutations");
		pageSpeciesResultant_List = new ControlMutationBox(this, 4, 20, 136, 152, ControlMutationBox.Type.Resultant);
	}

	@Override
	public void onValueChanged(final IAlleleSpecies species) {
		pageSpeciesResultant_List.setSpecies(species);
	}
}
