package binnie.core.gui.controls.listbox;

import java.util.Collection;

import binnie.core.api.gui.IWidget;
import binnie.core.gui.controls.core.IControlValue;
import binnie.core.gui.controls.scroll.ControlScrollableContent;
import binnie.core.gui.events.EventKey;
import binnie.core.util.IValidator;

public class ControlListBox<T> extends ControlScrollableContent<ControlList<T>> implements IControlValue<T> {
	public ControlListBox(final IWidget parent, final int x, final int y, final int w, final int h, final int scrollBarSize) {
		super(parent, x, y, w, h, scrollBarSize);

		ControlList<T> content = this.getContent();
		T defaultValue = content == null ? null : content.getDefaultValue();
		ControlList<T> child = new ControlList<>(this, 1, 1, this.getWidth() - 2 - this.getScrollBarSize(), this.getHeight() - 2, defaultValue);
		this.setScrollableContent(child);
		this.addEventHandler(EventKey.Down.class, event -> {
			if (this.calculateIsMouseOver()) {
				int currentIndex = this.getContent().getCurrentIndex();
				if (event.getKey() == 208) {
					if (++currentIndex >= this.getContent().getOptions().size()) {
						currentIndex = 0;
					}
				} else if (event.getKey() == 200 && --currentIndex < 0) {
					currentIndex = this.getContent().getOptions().size() - 1;
				}
				this.getContent().setIndex(currentIndex);
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
