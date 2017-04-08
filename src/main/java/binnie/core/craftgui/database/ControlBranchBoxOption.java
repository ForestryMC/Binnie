package binnie.core.craftgui.database;

import binnie.core.craftgui.controls.listbox.ControlList;
import binnie.core.craftgui.controls.listbox.ControlTextOption;
import forestry.api.genetics.IClassification;

class ControlBranchBoxOption extends ControlTextOption<IClassification> {
	public ControlBranchBoxOption(final ControlList<IClassification> controlList, final IClassification option, final int y) {
		super(controlList, option, option.getName().contains(".") ? option.getScientific() : option.getName(), y);
	}
}
