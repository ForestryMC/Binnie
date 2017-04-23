// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.craftgui.database;

import binnie.core.craftgui.controls.listbox.ControlList;
import binnie.core.craftgui.controls.listbox.ControlOption;
import binnie.core.craftgui.minecraft.Window;
import binnie.core.genetics.BreedingSystem;
import forestry.api.genetics.IAlleleSpecies;
import forestry.api.genetics.IMutation;

class ControlMutationItem extends ControlOption<IMutation>
{
	private ControlDatabaseIndividualDisplay itemWidget1;
	private ControlDatabaseIndividualDisplay itemWidget2;
	private ControlDatabaseIndividualDisplay itemWidget3;
	private ControlMutationSymbol addSymbol;
	private ControlMutationSymbol arrowSymbol;

	public ControlMutationItem(final ControlList<IMutation> controlList, final IMutation option, final IAlleleSpecies species, final int y) {
		super(controlList, option, y);
		itemWidget1 = new ControlDatabaseIndividualDisplay(this, 4.0f, 4.0f);
		itemWidget2 = new ControlDatabaseIndividualDisplay(this, 44.0f, 4.0f);
		itemWidget3 = new ControlDatabaseIndividualDisplay(this, 104.0f, 4.0f);
		addSymbol = new ControlMutationSymbol(this, 24, 4, 0);
		arrowSymbol = new ControlMutationSymbol(this, 64, 4, 1);
		final boolean isNEI = ((WindowAbstractDatabase) getSuperParent()).isNEI();
		final BreedingSystem system = ((WindowAbstractDatabase) getSuperParent()).getBreedingSystem();
		if (getValue() != null) {
			final boolean isMutationDiscovered = system.isMutationDiscovered(getValue(), Window.get(this).getWorld(), Window.get(this).getUsername());
			IAlleleSpecies allele = null;
			EnumDiscoveryState state = null;
			allele = getValue().getAllele0();
			state = ((isNEI || isMutationDiscovered) ? EnumDiscoveryState.Show : ((species == allele) ? EnumDiscoveryState.Show : EnumDiscoveryState.Undetermined));
			itemWidget1.setSpecies(allele, state);
			allele = getValue().getAllele1();
			state = ((isNEI || isMutationDiscovered) ? EnumDiscoveryState.Show : ((species == allele) ? EnumDiscoveryState.Show : EnumDiscoveryState.Undetermined));
			itemWidget2.setSpecies(allele, state);
			allele = (IAlleleSpecies) getValue().getTemplate()[0];
			state = ((isNEI || isMutationDiscovered) ? EnumDiscoveryState.Show : ((species == allele) ? EnumDiscoveryState.Show : EnumDiscoveryState.Undetermined));
			itemWidget3.setSpecies(allele, state);
			addSymbol.setValue(getValue());
			arrowSymbol.setValue(getValue());
		}
	}
}
