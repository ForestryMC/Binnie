// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.craftgui.controls.listbox;

import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.core.IControlValue;
import binnie.core.craftgui.controls.scroll.ControlScrollableContent;
import binnie.core.craftgui.events.EventKey;
import binnie.core.util.IValidator;

import java.util.Collection;

public class ControlListBox<T> extends ControlScrollableContent<ControlList<T>> implements IControlValue<T>
{
	public ControlListBox(final IWidget parent, final float x, final float y, final float w, final float h, final float scrollBarSize) {
		super(parent, x, y, w, h, scrollBarSize);
	}

	@Override
	public void initialise() {
		setScrollableContent(new ControlList<T>(this, 1.0f, 1.0f, w() - 2.0f - scrollBarSize, h() - 2.0f));
		addEventHandler(new EventKey.Down.Handler() {
			@Override
			public void onEvent(final EventKey.Down event) {
				if (calculateIsMouseOver()) {
					int currentIndex = getContent().getCurrentIndex();
					if (event.getKey() == 208) {
						if (++currentIndex >= getContent().getOptions().size()) {
							currentIndex = 0;
						}
					}
					else if (event.getKey() == 200 && --currentIndex < 0) {
						currentIndex = getContent().getOptions().size() - 1;
					}
					getContent().setIndex(currentIndex);
				}
			}
		});
	}

	@Override
	public final T getValue() {
		return getContent().getValue();
	}

	@Override
	public final void setValue(final T value) {
		getContent().setValue(value);
	}

	public void setOptions(final Collection<T> options) {
		getContent().setOptions(options);
	}

	public IWidget createOption(final T value, final int y) {
		return new ControlOption<T>(getContent(), value, y);
	}

	public void setValidator(final IValidator<IWidget> validator) {
		getContent().setValidator(validator);
	}
}
