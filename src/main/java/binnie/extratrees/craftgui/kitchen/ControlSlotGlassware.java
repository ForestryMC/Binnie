package binnie.extratrees.craftgui.kitchen;

import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.core.IControlValue;
import binnie.core.craftgui.minecraft.control.ControlSlotBase;
import binnie.extratrees.alcohol.Glassware;
import net.minecraft.item.ItemStack;

// TODO unused class?
public class ControlSlotGlassware extends ControlSlotBase implements IControlValue<Glassware> {
	Glassware glassware;

	public ControlSlotGlassware(IWidget parent, int x, int y, Glassware glassware) {
		super(parent, x, y);
		this.glassware = glassware;
	}

	@Override
	public Glassware getValue() {
		return glassware;
	}

	@Override
	public void setValue(Glassware value) {
		glassware = value;
	}

	@Override
	public ItemStack getItemStack() {
		return glassware.get(1);
	}
}
