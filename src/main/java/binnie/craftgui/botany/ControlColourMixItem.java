package binnie.craftgui.botany;

import binnie.botany.api.IColourMix;
import binnie.core.genetics.BreedingSystem;
import binnie.craftgui.controls.listbox.ControlList;
import binnie.craftgui.controls.listbox.ControlOption;
import binnie.craftgui.mod.database.WindowAbstractDatabase;

public class ControlColourMixItem extends ControlOption<IColourMix> {
    ControlColourDisplay itemWidget1;
    ControlColourDisplay itemWidget2;
    ControlColourDisplay itemWidget3;
    ControlColourMixSymbol addSymbol;
    ControlColourMixSymbol arrowSymbol;

    public ControlColourMixItem(final ControlList<IColourMix> controlList, final IColourMix option, final int y) {
        super(controlList, option, y);
        this.itemWidget1 = new ControlColourDisplay(this, 4.0f, 4.0f);
        this.itemWidget2 = new ControlColourDisplay(this, 44.0f, 4.0f);
        this.itemWidget3 = new ControlColourDisplay(this, 104.0f, 4.0f);
        this.addSymbol = new ControlColourMixSymbol(this, 24, 4, 0);
        this.arrowSymbol = new ControlColourMixSymbol(this, 64, 4, 1);
        final BreedingSystem system = ((WindowAbstractDatabase) this.getSuperParent()).getBreedingSystem();
        if (this.getValue() != null) {
            this.itemWidget1.setValue(this.getValue().getColour1());
            this.itemWidget2.setValue(this.getValue().getColour2());
            this.itemWidget3.setValue(this.getValue().getResult());
            this.addSymbol.setValue(this.getValue());
            this.arrowSymbol.setValue(this.getValue());
        }
    }
}
