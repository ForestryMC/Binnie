package binnie.core.gui.database;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.genetics.IAlleleSpecies;
import forestry.api.genetics.IClassification;

import binnie.core.gui.IWidget;
import binnie.core.gui.controls.ControlText;
import binnie.core.gui.controls.ControlTextCentered;
import binnie.core.gui.events.EventValueChanged;
import binnie.core.util.I18N;

@SideOnly(Side.CLIENT)
public class PageBranchSpecies extends PageBranch {
	private ControlText pageBranchSpecies_title;
	private ControlSpeciesBox pageBranchSpecies_speciesList;

	/*@Mod.EventHandler
		public void onHandleEvent(final EventValueChanged<IAlleleSpecies> event) {
	}*/

	public PageBranchSpecies(final IWidget parent, final DatabaseTab tab) {
		super(parent, tab);
		this.pageBranchSpecies_title = new ControlTextCentered(this, 8, I18N.localise(DatabaseConstants.SPECIES_KEY));
		this.addEventHandler(new EventValueChanged.Handler() {
			@Override
			public void onEvent(final EventValueChanged event) {
				if (event.isOrigin(PageBranchSpecies.this.pageBranchSpecies_speciesList)) {
					((WindowAbstractDatabase) PageBranchSpecies.this.getTopParent()).gotoSpecies((IAlleleSpecies) event.getValue());
				}
			}
		});
		this.pageBranchSpecies_speciesList = new ControlSpeciesBox(this, 4, 20, 136, 152);
	}

	@Override
	public void onValueChanged(final IClassification branch) {
		this.pageBranchSpecies_speciesList.setBranch(branch);
	}
}
