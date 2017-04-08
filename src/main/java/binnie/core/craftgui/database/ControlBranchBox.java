package binnie.core.craftgui.database;

import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.listbox.ControlListBox;
import forestry.api.genetics.IClassification;

class ControlBranchBox extends ControlListBox<IClassification> {
	@Override
	public IWidget createOption(final IClassification value, final int y) {
		return new ControlBranchBoxOption(this.getContent(), value, y);
	}

	public ControlBranchBox(final IWidget parent, final int x, final int y, final int width, final int height) {
		super(parent, x, y, width, height, 12);
	}
}
