package binnie.craftgui.mod.database;

import binnie.craftgui.controls.ControlText;
import binnie.craftgui.controls.ControlTextCentered;
import binnie.craftgui.core.IWidget;
import forestry.api.genetics.IAlleleSpecies;

public class PageSpeciesMutations extends PageSpecies {
    private ControlText pageSpeciesFurther_Title;
    private ControlMutationBox pageSpeciesFurther_List;

    public PageSpeciesMutations(final IWidget parent, final DatabaseTab tab) {
        super(parent, tab);
        this.pageSpeciesFurther_Title = new ControlTextCentered(this, 8.0f, "Further Mutations");
        this.pageSpeciesFurther_List = new ControlMutationBox(this, 4, 20, 136, 152, ControlMutationBox.Type.Further);
    }

    @Override
    public void onValueChanged(final IAlleleSpecies species) {
        this.pageSpeciesFurther_List.setSpecies(species);
    }
}
