package binnie.core.gui.database;

import binnie.core.api.genetics.IBreedingSystem;
import binnie.core.gui.controls.listbox.ControlList;
import binnie.core.gui.controls.listbox.ControlOption;
import binnie.core.gui.minecraft.Window;
import forestry.api.genetics.IAlleleSpecies;
import forestry.api.genetics.IMutation;

import javax.annotation.Nullable;

class ControlMutationItem extends ControlOption<IMutation> {

	public ControlMutationItem(final ControlList<IMutation> controlList, final IMutation option, @Nullable final IAlleleSpecies species, final int y) {
		super(controlList, option, y);
		ControlIndividualDisplay firstIndividual = new ControlIndividualDisplay(this, 4, 4);
		ControlIndividualDisplay secondIndividual = new ControlIndividualDisplay(this, 44, 4);
		ControlIndividualDisplay resultIndividual = new ControlIndividualDisplay(this, 104, 4);
		ControlMutationSymbol addSymbol = new ControlMutationSymbol(this, 24, 4, 0);
		ControlMutationSymbol arrowSymbol = new ControlMutationSymbol(this, 64, 4, 1);
		final boolean isMaster = ((WindowAbstractDatabase) this.getTopParent()).isMaster();
		final IBreedingSystem system = ((WindowAbstractDatabase) this.getTopParent()).getBreedingSystem();
		if (this.getValue() != null) {
			final boolean isMutationDiscovered = system.isMutationDiscovered(this.getValue(), Window.get(this).getWorld(), Window.get(this).getUsername());
			IAlleleSpecies allele = this.getValue().getAllele0();
			EnumDiscoveryState state = ((isMaster || isMutationDiscovered) ? EnumDiscoveryState.SHOW : ((species.equals(allele)) ? EnumDiscoveryState.SHOW : EnumDiscoveryState.UNDETERMINED));
			firstIndividual.setSpecies(allele, state);
			allele = this.getValue().getAllele1();
			state = ((isMaster || isMutationDiscovered) ? EnumDiscoveryState.SHOW : ((species.equals(allele)) ? EnumDiscoveryState.SHOW : EnumDiscoveryState.UNDETERMINED));
			secondIndividual.setSpecies(allele, state);
			allele = (IAlleleSpecies) this.getValue().getTemplate()[0];
			state = ((isMaster || isMutationDiscovered) ? EnumDiscoveryState.SHOW : ((species.equals(allele)) ? EnumDiscoveryState.SHOW : EnumDiscoveryState.UNDETERMINED));
			resultIndividual.setSpecies(allele, state);
			addSymbol.setValue(this.getValue());
			arrowSymbol.setValue(this.getValue());
		}
	}
}
