package binnie.extratrees.kitchen.craftgui;

import binnie.core.api.gui.IWidget;
import binnie.core.gui.controls.core.IControlValue;
import binnie.core.gui.minecraft.control.ControlSlotBase;
import binnie.extratrees.alcohol.GlasswareType;
import net.minecraft.item.ItemStack;

public class ControlSlotGlassware extends ControlSlotBase implements IControlValue<GlasswareType> {
	private GlasswareType glasswareType;

	public ControlSlotGlassware(final IWidget parent, final int x, final int y, final GlasswareType glasswareType) {
		super(parent, x, y);
		this.glasswareType = glasswareType;
	}

	@Override
	public GlasswareType getValue() {
		return this.glasswareType;
	}

	@Override
	public void setValue(final GlasswareType value) {
		this.glasswareType = value;
	}

	@Override
	public ItemStack getItemStack() {
		return this.glasswareType.get(1);
	}
}
