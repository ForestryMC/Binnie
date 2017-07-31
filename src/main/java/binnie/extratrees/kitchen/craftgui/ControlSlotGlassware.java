package binnie.extratrees.kitchen.craftgui;

import net.minecraft.item.ItemStack;

import binnie.core.gui.IWidget;
import binnie.core.gui.controls.core.IControlValue;
import binnie.core.gui.minecraft.control.ControlSlotBase;
import binnie.extratrees.alcohol.Glassware;

public class ControlSlotGlassware extends ControlSlotBase implements IControlValue<Glassware> {
	Glassware glassware;

	public ControlSlotGlassware(final IWidget parent, final int x, final int y, final Glassware glassware) {
		super(parent, x, y);
		this.glassware = glassware;
	}

	@Override
	public Glassware getValue() {
		return this.glassware;
	}

	@Override
	public void setValue(final Glassware value) {
		this.glassware = value;
	}

	@Override
	public ItemStack getItemStack() {
		return this.glassware.get(1);
	}
}
