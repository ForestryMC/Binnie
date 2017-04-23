// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.craftgui.database;

import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.ControlText;
import binnie.core.craftgui.controls.ControlTextCentered;
import binnie.core.craftgui.events.EventValueChanged;
import cpw.mods.fml.common.Mod;
import forestry.api.genetics.IAlleleSpecies;
import forestry.api.genetics.IClassification;

public class PageBranchSpecies extends PageBranch
{
	private ControlText pageBranchSpecies_title;
	private ControlSpeciesBox pageBranchSpecies_speciesList;

	@Mod.EventHandler
	public void onHandleEvent(final EventValueChanged<IAlleleSpecies> event) {
	}

	public PageBranchSpecies(final IWidget parent, final DatabaseTab tab) {
		super(parent, tab);
		pageBranchSpecies_title = new ControlTextCentered(this, 8.0f, "Species");
		addEventHandler(new EventValueChanged.Handler() {
			@Override
			public void onEvent(final EventValueChanged event) {
				if (event.isOrigin(pageBranchSpecies_speciesList)) {
					((WindowAbstractDatabase) getSuperParent()).gotoSpecies((IAlleleSpecies) event.getValue());
				}
			}
		});
		pageBranchSpecies_speciesList = new ControlSpeciesBox(this, 4.0f, 20.0f, 136.0f, 152.0f);
	}

	@Override
	public void onValueChanged(final IClassification branch) {
		pageBranchSpecies_speciesList.setBranch(branch);
	}
}
