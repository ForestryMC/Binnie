package binnie.core.craftgui.controls.listbox;

import binnie.core.craftgui.controls.ControlText;
import binnie.core.craftgui.events.EventHandler;
import binnie.core.craftgui.events.EventWidget;
import binnie.core.craftgui.geometry.TextJustification;

public class ControlTextOption<T> extends ControlOption<T> {
    protected ControlText textWidget;

    public ControlTextOption(ControlList<T> controlList, T option, String optionName, int y) {
        super(controlList, option, y);
        textWidget = null;
        textWidget = new ControlText(this, getArea(), optionName, TextJustification.MIDDLE_CENTER);

        mouseHandler.setOrigin(EventHandler.Origin.Self, this);
        addEventHandler(mouseHandler);
    }

    public ControlTextOption(ControlList<T> controlList, T option, int y) {
        this(controlList, option, option.toString(), y);
    }

    private EventWidget.ChangeColour.Handler mouseHandler = new EventWidget.ChangeColour.Handler() {
        @Override
        public void onEvent(EventWidget.ChangeColour event) {
            textWidget.setColor(getColor());
        }
    };

    public String getText() {
        return textWidget.getValue();
    }
}
