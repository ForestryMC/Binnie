package binnie.botany.craftgui;

import binnie.botany.api.IColourMix;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.listbox.ControlListBox;

public class ControlColorMixBox extends ControlListBox<IColourMix> {
    private Type type;

    public ControlColorMixBox(IWidget parent, int x, int y, int width, int height, Type type) {
        super(parent, x, y, width, height, 12.0f);
        this.type = type;
    }

    @Override
    public IWidget createOption(IColourMix value, int y) {
        return new ControlColorMixItem(getContent(), value, y);
    }

    enum Type {
        Resultant,
        Further
    }
}
