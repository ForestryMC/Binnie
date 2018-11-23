package binnie.core.gui.database;

import javax.annotation.Nullable;

import forestry.api.genetics.IAlleleSpecies;
import forestry.api.genetics.IMutation;

import binnie.core.api.genetics.IBreedingSystem;
import binnie.core.gui.controls.listbox.ControlList;
import binnie.core.gui.controls.listbox.ControlOption;
import binnie.core.gui.minecraft.Window;

class ControlMutationItem extends ControlOption<IMutation> {

	public ControlMutationItem(ControlList<IMutation> controlList, IMutation option, @Nullable IAlleleSpecies species, int y) {
		super(controlList, option, y);
		ControlIndividualDisplay firstIndividual = new ControlIndividualDisplay(this, 4, 4);
		ControlIndividualDisplay secondIndividual = new ControlIndividualDisplay(this, 44, 4);
		ControlIndividualDisplay resultIndividual = new ControlIndividualDisplay(this, 104, 4);
		ControlMutationSymbol addSymbol = new ControlMutationSymbol(this, 24, 4, 0);
		ControlMutationSymbol arrowSymbol = new ControlMutationSymbol(this, 64, 4, 1);
		boolean isMaster = ((WindowAbstractDatabase) this.getTopParent()).isMaster();
		IBreedingSystem system = ((WindowAbstractDatabase) this.getTopParent()).getBreedingSystem();
		if (this.getValue() != null) {
			boolean isMutationDiscovered = system.isMutationDiscovered(this.getValue(), Window.get(this).getWorld(), Window.get(this).getUsername());
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
