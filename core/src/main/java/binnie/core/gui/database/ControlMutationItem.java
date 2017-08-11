package binnie.core.gui.database;

import javax.annotation.Nullable;

import binnie.core.api.genetics.IBreedingSystem;
import forestry.api.genetics.IAlleleSpecies;
import forestry.api.genetics.IMutation;

import binnie.core.gui.controls.listbox.ControlList;
import binnie.core.gui.controls.listbox.ControlOption;
import binnie.core.gui.minecraft.Window;

class ControlMutationItem extends ControlOption<IMutation> {
	private ControlDatabaseIndividualDisplay itemWidget1;
	private ControlDatabaseIndividualDisplay itemWidget2;
	private ControlDatabaseIndividualDisplay itemWidget3;
	private ControlMutationSymbol addSymbol;
	private ControlMutationSymbol arrowSymbol;

	public ControlMutationItem(final ControlList<IMutation> controlList, final IMutation option, @Nullable final IAlleleSpecies species, final int y) {
		super(controlList, option, y);
		this.itemWidget1 = new ControlDatabaseIndividualDisplay(this, 4, 4);
		this.itemWidget2 = new ControlDatabaseIndividualDisplay(this, 44, 4);
		this.itemWidget3 = new ControlDatabaseIndividualDisplay(this, 104, 4);
		this.addSymbol = new ControlMutationSymbol(this, 24, 4, 0);
		this.arrowSymbol = new ControlMutationSymbol(this, 64, 4, 1);
		final boolean isNEI = ((WindowAbstractDatabase) this.getTopParent()).isNEI();
		final IBreedingSystem system = ((WindowAbstractDatabase) this.getTopParent()).getBreedingSystem();
		if (this.getValue() != null) {
			final boolean isMutationDiscovered = system.isMutationDiscovered(this.getValue(), Window.get(this).getWorld(), Window.get(this).getUsername());
			IAlleleSpecies allele = null;
			EnumDiscoveryState state = null;
			allele = this.getValue().getAllele0();
			state = ((isNEI || isMutationDiscovered) ? EnumDiscoveryState.Show : ((species == allele) ? EnumDiscoveryState.Show : EnumDiscoveryState.Undetermined));
			this.itemWidget1.setSpecies(allele, state);
			allele = this.getValue().getAllele1();
			state = ((isNEI || isMutationDiscovered) ? EnumDiscoveryState.Show : ((species == allele) ? EnumDiscoveryState.Show : EnumDiscoveryState.Undetermined));
			this.itemWidget2.setSpecies(allele, state);
			allele = (IAlleleSpecies) this.getValue().getTemplate()[0];
			state = ((isNEI || isMutationDiscovered) ? EnumDiscoveryState.Show : ((species == allele) ? EnumDiscoveryState.Show : EnumDiscoveryState.Undetermined));
			this.itemWidget3.setSpecies(allele, state);
			this.addSymbol.setValue(this.getValue());
			this.arrowSymbol.setValue(this.getValue());
		}
	}
}
