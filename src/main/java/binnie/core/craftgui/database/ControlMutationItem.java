package binnie.core.craftgui.database;

import binnie.core.craftgui.controls.listbox.ControlList;
import binnie.core.craftgui.controls.listbox.ControlOption;
import binnie.core.craftgui.minecraft.Window;
import binnie.core.genetics.BreedingSystem;
import forestry.api.genetics.IAlleleSpecies;
import forestry.api.genetics.IMutation;

class ControlMutationItem extends ControlOption<IMutation> {
    public ControlMutationItem(ControlList<IMutation> controlList, IMutation option, IAlleleSpecies species, int y) {
        super(controlList, option, y);
        ControlDatabaseIndividualDisplay itemWidget1 = new ControlDatabaseIndividualDisplay(this, 4.0f, 4.0f);
        ControlDatabaseIndividualDisplay itemWidget2 = new ControlDatabaseIndividualDisplay(this, 44.0f, 4.0f);
        ControlDatabaseIndividualDisplay itemWidget3 = new ControlDatabaseIndividualDisplay(this, 104.0f, 4.0f);
        ControlMutationSymbol addSymbol = new ControlMutationSymbol(this, 24, 4, 0);
        ControlMutationSymbol arrowSymbol = new ControlMutationSymbol(this, 64, 4, 1);
        boolean isNEI = ((WindowAbstractDatabase) getSuperParent()).isNEI();
        BreedingSystem system = ((WindowAbstractDatabase) getSuperParent()).getBreedingSystem();
        Window window = Window.get(this);

        if (getValue() == null) {
            return;
        }

        boolean isMutationDiscovered = system.isMutationDiscovered(getValue(), window.getWorld(), window.getUsername());
        IAlleleSpecies allele;
        EnumDiscoveryState state;
        allele = getValue().getAllele0();
        state = getState(isNEI, isMutationDiscovered, species, allele);
        itemWidget1.setSpecies(allele, state);

        allele = getValue().getAllele1();
        state = getState(isNEI, isMutationDiscovered, species, allele);
        itemWidget2.setSpecies(allele, state);

        allele = (IAlleleSpecies) getValue().getTemplate()[0];
        state = getState(isNEI, isMutationDiscovered, species, allele);
        itemWidget3.setSpecies(allele, state);

        addSymbol.setValue(getValue());
        arrowSymbol.setValue(getValue());
    }

    private EnumDiscoveryState getState(
            boolean isNEI, boolean isMutationDiscovered, IAlleleSpecies species, IAlleleSpecies allele) {
        return (isNEI || isMutationDiscovered || species == allele)
                ? EnumDiscoveryState.SHOW
                : EnumDiscoveryState.UNDETERMINED;
    }
}
