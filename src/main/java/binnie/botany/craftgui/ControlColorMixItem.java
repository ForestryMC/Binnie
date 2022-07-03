package binnie.botany.craftgui;

import binnie.botany.api.IColourMix;
import binnie.core.craftgui.controls.listbox.ControlList;
import binnie.core.craftgui.controls.listbox.ControlOption;

public class ControlColorMixItem extends ControlOption<IColourMix> {
    protected ControlColorDisplay itemWidget1;
    protected ControlColorDisplay itemWidget2;
    protected ControlColorDisplay itemWidget3;
    protected ControlColourMixSymbol addSymbol;
    protected ControlColourMixSymbol arrowSymbol;

    public ControlColorMixItem(ControlList<IColourMix> controlList, IColourMix option, int y) {
        super(controlList, option, y);
        itemWidget1 = new ControlColorDisplay(this, 4.0f, 4.0f);
        itemWidget2 = new ControlColorDisplay(this, 44.0f, 4.0f);
        itemWidget3 = new ControlColorDisplay(this, 104.0f, 4.0f);
        addSymbol = new ControlColourMixSymbol(this, 24, 4, 0);
        arrowSymbol = new ControlColourMixSymbol(this, 64, 4, 1);

        if (getValue() != null) {
            itemWidget1.setValue(getValue().getColor1());
            itemWidget2.setValue(getValue().getColor2());
            itemWidget3.setValue(getValue().getResult());
            addSymbol.setValue(getValue());
            arrowSymbol.setValue(getValue());
        }
    }
}
