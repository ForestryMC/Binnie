package binnie.craftgui.mod.database;

import binnie.craftgui.controls.listbox.ControlList;
import binnie.craftgui.controls.listbox.ControlListBox;
import binnie.craftgui.controls.scroll.ControlScrollableContent;
import binnie.craftgui.core.IWidget;
import forestry.api.genetics.IClassification;

class ControlBranchBox extends ControlListBox<IClassification> {
    @Override
    public IWidget createOption(final IClassification value, final int y) {
        return new ControlBranchBoxOption(((ControlScrollableContent<ControlList<IClassification>>) this).getContent(), value, y);
    }

    public ControlBranchBox(final IWidget parent, final float x, final float y, final float width, final float height) {
        super(parent, x, y, width, height, 12.0f);
    }
}
