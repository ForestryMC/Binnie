package binnie.core.craftgui.controls.listbox;

import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.core.IControlValue;
import binnie.core.craftgui.controls.scroll.ControlScrollableContent;
import binnie.core.craftgui.events.EventKey;
import binnie.core.util.IValidator;
import java.util.Collection;

public class ControlListBox<T> extends ControlScrollableContent<ControlList<T>> implements IControlValue<T> {
    public ControlListBox(IWidget parent, float x, float y, float w, float h, float scrollBarSize) {
        super(parent, x, y, w, h, scrollBarSize);
    }

    @Override
    public void initialise() {
        setScrollableContent(new ControlList<>(this, 1.0f, 1.0f, w() - 2.0f - scrollBarSize, h() - 2.0f));
        addEventHandler(new EventKey.Down.Handler() {
            @Override
            public void onEvent(EventKey.Down event) {
                if (calculateIsMouseOver()) {
                    int currentIndex = getContent().getCurrentIndex();
                    if (event.getKey() == 208) {
                        if (++currentIndex >= getContent().getOptions().size()) {
                            currentIndex = 0;
                        }
                    } else if (event.getKey() == 200 && --currentIndex < 0) {
                        currentIndex = getContent().getOptions().size() - 1;
                    }
                    getContent().setIndex(currentIndex);
                }
            }
        });
    }

    public IWidget createOption(T value, int y) {
        return new ControlOption<>(getContent(), value, y);
    }

    @Override
    public T getValue() {
        return getContent().getValue();
    }

    @Override
    public void setValue(T value) {
        getContent().setValue(value);
    }

    public void setOptions(Collection<T> options) {
        getContent().setOptions(options);
    }

    public void setValidator(IValidator<IWidget> validator) {
        getContent().setValidator(validator);
    }
}
