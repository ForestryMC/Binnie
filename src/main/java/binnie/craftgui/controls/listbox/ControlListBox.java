// 
// Decompiled by Procyon v0.5.30
// 

package binnie.craftgui.controls.listbox;

import binnie.core.util.IValidator;
import java.util.Collection;
import binnie.craftgui.events.EventKey;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.controls.core.IControlValue;
import binnie.craftgui.controls.scroll.ControlScrollableContent;

public class ControlListBox<T> extends ControlScrollableContent<ControlList<T>> implements IControlValue<T>
{
	public ControlListBox(final IWidget parent, final float x, final float y, final float w, final float h, final float scrollBarSize) {
		super(parent, x, y, w, h, scrollBarSize);
	}

	@Override
	public void initialise() {
		this.setScrollableContent(new ControlList<T>(this, 1.0f, 1.0f, this.w() - 2.0f - this.scrollBarSize, this.h() - 2.0f));
		this.addEventHandler(new EventKey.Down.Handler() {
			@Override
			public void onEvent(final EventKey.Down event) {
				if (ControlListBox.this.calculateIsMouseOver()) {
					int currentIndex = ControlListBox.this.getContent().getCurrentIndex();
					if (event.getKey() == 208) {
						if (++currentIndex >= ControlListBox.this.getContent().getOptions().size()) {
							currentIndex = 0;
						}
					}
					else if (event.getKey() == 200 && --currentIndex < 0) {
						currentIndex = ControlListBox.this.getContent().getOptions().size() - 1;
					}
					ControlListBox.this.getContent().setIndex(currentIndex);
				}
			}
		});
	}

	@Override
	public final T getValue() {
		return this.getContent().getValue();
	}

	@Override
	public final void setValue(final T value) {
		this.getContent().setValue(value);
	}

	public void setOptions(final Collection<T> options) {
		this.getContent().setOptions(options);
	}

	public IWidget createOption(final T value, final int y) {
		return new ControlOption<T>(this.getContent(), value, y);
	}

	public void setValidator(final IValidator<IWidget> validator) {
		this.getContent().setValidator(validator);
	}
}
