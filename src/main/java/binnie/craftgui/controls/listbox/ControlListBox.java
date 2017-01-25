package binnie.craftgui.controls.listbox;

import binnie.core.util.IValidator;
import binnie.craftgui.controls.core.IControlValue;
import binnie.craftgui.controls.scroll.ControlScrollableContent;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.events.EventKey;

import java.util.Collection;

public class ControlListBox<T> extends ControlScrollableContent<ControlList<T>> implements IControlValue<T> {
	public ControlListBox(final IWidget parent, final int x, final int y, final int w, final int h, final int scrollBarSize) {
		super(parent, x, y, w, h, scrollBarSize);
	}

	@Override
	public void initialise() {
		this.setScrollableContent(new ControlList<>(this, 1, 1, this.w() - 2 - this.scrollBarSize, this.h() - 2));
		this.addEventHandler(new EventKey.Down.Handler() {
			@Override
			public void onEvent(final EventKey.Down event) {
				if (ControlListBox.this.calculateIsMouseOver()) {
					int currentIndex = ControlListBox.this.getContent().getCurrentIndex();
					if (event.getKey() == 208) {
						if (++currentIndex >= ControlListBox.this.getContent().getOptions().size()) {
							currentIndex = 0;
						}
					} else if (event.getKey() == 200 && --currentIndex < 0) {
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
		return new ControlOption<>(this.getContent(), value, y);
	}

	public void setValidator(final IValidator<IWidget> validator) {
		this.getContent().setValidator(validator);
	}
}
