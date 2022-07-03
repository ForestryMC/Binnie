package binnie.core.craftgui.database;

import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.listbox.ControlListBox;
import forestry.api.genetics.IClassification;

class ControlBranchBox extends ControlListBox<IClassification> {
    @Override
    public IWidget createOption(IClassification value, int y) {
        return new ControlBranchBoxOption(getContent(), value, y);
    }

    public ControlBranchBox(IWidget parent, float x, float y, float width, float height) {
        super(parent, x, y, width, height, 12.0f);
    }
}
