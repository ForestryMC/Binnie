package binnie.core.gui.database;

import forestry.api.genetics.IClassification;

import binnie.core.gui.controls.listbox.ControlList;
import binnie.core.gui.controls.listbox.ControlTextOption;

class ControlBranchBoxOption extends ControlTextOption<IClassification> {
	public ControlBranchBoxOption(ControlList<IClassification> controlList, IClassification option, int y) {
		super(controlList, option, option.getName().contains(".") ? option.getScientific() : option.getName(), y);
	}
}
