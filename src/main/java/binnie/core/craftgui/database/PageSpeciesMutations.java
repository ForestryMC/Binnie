// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.craftgui.database;

import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.ControlText;
import binnie.core.craftgui.controls.ControlTextCentered;
import forestry.api.genetics.IAlleleSpecies;

public class PageSpeciesMutations extends PageSpecies
{
	private ControlText pageSpeciesFurther_Title;
	private ControlMutationBox pageSpeciesFurther_List;

	public PageSpeciesMutations(IWidget parent, DatabaseTab tab) {
		super(parent, tab);
		pageSpeciesFurther_Title = new ControlTextCentered(this, 8.0f, "Further Mutations");
		pageSpeciesFurther_List = new ControlMutationBox(this, 4, 20, 136, 152, ControlMutationBox.Type.Further);
	}

	@Override
	public void onValueChanged(IAlleleSpecies species) {
		pageSpeciesFurther_List.setSpecies(species);
	}
}
