package binnie.core.gui.database;

import forestry.api.genetics.IClassification;

import binnie.core.api.gui.IWidget;
import binnie.core.gui.controls.listbox.ControlListBox;

class ControlBranchBox extends ControlListBox<IClassification> {
	public ControlBranchBox(IWidget parent, int x, int y, int width, int height) {
		super(parent, x, y, width, height, 12);
	}

	@Override
	public IWidget createOption(IClassification value, int y) {
		return new ControlBranchBoxOption(this.getContent(), value, y);
	}
}
