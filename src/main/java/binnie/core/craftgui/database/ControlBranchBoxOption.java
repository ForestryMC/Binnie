package binnie.core.craftgui.database;

import binnie.core.craftgui.controls.listbox.ControlList;
import binnie.core.craftgui.controls.listbox.ControlTextOption;
import forestry.api.genetics.IClassification;

class ControlBranchBoxOption extends ControlTextOption<IClassification> {
    public ControlBranchBoxOption(ControlList<IClassification> controlList, IClassification option, int y) {
        super(
                controlList,
                option,
                (option.getName() == null)
                        ? option.getScientific()
                        : (option.getName().contains(".") ? option.getScientific() : option.getName()),
                y);
    }
}
