package binnie.craftgui.extratrees.kitchen;

import binnie.craftgui.controls.core.IControlValue;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.minecraft.control.ControlSlotBase;
import binnie.extratrees.alcohol.Glassware;
import net.minecraft.item.ItemStack;

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
