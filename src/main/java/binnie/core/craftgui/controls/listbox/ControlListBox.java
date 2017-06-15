package binnie.core.craftgui.controls.listbox;

import java.util.Collection;

import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.core.IControlValue;
import binnie.core.craftgui.controls.scroll.ControlScrollableContent;
import binnie.core.craftgui.events.EventKey;
import binnie.core.util.IValidator;

public class ControlListBox<T> extends ControlScrollableContent<ControlList<T>> implements IControlValue<T> {
	public ControlListBox(final IWidget parent, final int x, final int y, final int w, final int h, final int scrollBarSize) {
		super(parent, x, y, w, h, scrollBarSize);
	}

	@Override
	public void initialise() {
		ControlList<T> content = this.getContent();
		T defaultValue = content == null ? null : content.getDefaultValue();
		ControlList<T> child = new ControlList<>(this, 1, 1, this.width() - 2 - this.scrollBarSize, this.height() - 2, defaultValue);
		this.setScrollableContent(child);
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
