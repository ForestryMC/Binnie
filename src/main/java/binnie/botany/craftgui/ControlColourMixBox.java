// 
// Decompiled by Procyon v0.5.30
// 

package binnie.botany.craftgui;

import binnie.core.craftgui.controls.scroll.ControlScrollableContent;
import binnie.core.craftgui.controls.listbox.ControlList;
import binnie.core.craftgui.IWidget;
import binnie.botany.api.IColourMix;
import binnie.core.craftgui.controls.listbox.ControlListBox;

public class ControlColourMixBox extends ControlListBox<IColourMix>
{
	private int index;
	private Type type;

	@Override
	public IWidget createOption(final IColourMix value, final int y) {
		return new ControlColourMixItem(((ControlScrollableContent<ControlList<IColourMix>>) this).getContent(), value, y);
	}

	public ControlColourMixBox(final IWidget parent, final int x, final int y, final int width, final int height, final Type type) {
		super(parent, x, y, width, height, 12.0f);
		this.type = type;
	}

	enum Type
	{
		Resultant,
		Further;
	}
}
