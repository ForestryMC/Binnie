package binnie.craftgui.mod.database;

import binnie.core.genetics.BreedingSystem;
import binnie.craftgui.controls.listbox.ControlList;
import binnie.craftgui.controls.listbox.ControlOption;
import binnie.craftgui.minecraft.Window;
import forestry.api.genetics.IAlleleSpecies;
import forestry.api.genetics.IMutation;

class ControlMutationItem extends ControlOption<IMutation> {
    private ControlDatabaseIndividualDisplay itemWidget1;
    private ControlDatabaseIndividualDisplay itemWidget2;
    private ControlDatabaseIndividualDisplay itemWidget3;
    private ControlMutationSymbol addSymbol;
    private ControlMutationSymbol arrowSymbol;

    public ControlMutationItem(final ControlList<IMutation> controlList, final IMutation option, final IAlleleSpecies species, final int y) {
        super(controlList, option, y);
        this.itemWidget1 = new ControlDatabaseIndividualDisplay(this, 4.0f, 4.0f);
        this.itemWidget2 = new ControlDatabaseIndividualDisplay(this, 44.0f, 4.0f);
        this.itemWidget3 = new ControlDatabaseIndividualDisplay(this, 104.0f, 4.0f);
        this.addSymbol = new ControlMutationSymbol(this, 24, 4, 0);
        this.arrowSymbol = new ControlMutationSymbol(this, 64, 4, 1);
        final boolean isNEI = ((WindowAbstractDatabase) this.getSuperParent()).isNEI();
        final BreedingSystem system = ((WindowAbstractDatabase) this.getSuperParent()).getBreedingSystem();
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
