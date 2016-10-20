// 
// Decompiled by Procyon v0.5.30
// 

package binnie.craftgui.extratrees.kitchen;

import net.minecraft.item.ItemStack;
import binnie.craftgui.core.IWidget;
import binnie.extratrees.alcohol.Glassware;
import binnie.craftgui.controls.core.IControlValue;
import binnie.craftgui.minecraft.control.ControlSlotBase;

public class ControlSlotGlassware extends ControlSlotBase implements IControlValue<Glassware>
{
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
