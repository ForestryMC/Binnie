package binnie.botany.craftgui;

import binnie.botany.api.IColorMix;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.listbox.ControlListBox;

public class ControlColorMixBox extends ControlListBox<IColorMix> {
	private int index;
	private Type type;

	public ControlColorMixBox(final IWidget parent, final int x, final int y, final int width, final int height, final Type type) {
		super(parent, x, y, width, height, 12);
		this.type = type;
	}

	@Override
	public IWidget createOption(final IColorMix value, final int y) {
		return new ControlColorMixItem(this.getContent(), value, y);
	}

	enum Type {
		Resultant,
		Further,
	}
}
