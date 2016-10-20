package binnie.craftgui.extratrees.dictionary;

import binnie.core.machines.Machine;
import binnie.core.machines.TileEntityMachine;
import binnie.core.machines.component.IComponentRecipe;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.events.EventMouse;
import binnie.craftgui.minecraft.Window;
import binnie.craftgui.minecraft.control.ControlSlotBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class ControlRecipeSlot extends ControlSlotBase {
    public ControlRecipeSlot(final IWidget parent, final int x, final int y) {
        super(parent, x, y, 50);
        this.addSelfEventHandler(new EventMouse.Down.Handler() {
            @Override
            public void onEvent(final EventMouse.Down event) {
                final TileEntity tile = (TileEntity) Window.get(ControlRecipeSlot.this.getWidget()).getInventory();
                if (tile == null || !(tile instanceof TileEntityMachine)) {
                    return;
                }
                final NBTTagCompound nbt = new NBTTagCompound();
                Window.get(ControlRecipeSlot.this.getWidget()).sendClientAction("recipe", nbt);
            }
        });
        this.setRotating();
    }

    @Override
    public ItemStack getItemStack() {
        final IComponentRecipe recipe = Machine.getInterface(IComponentRecipe.class, Window.get(this).getInventory());
        return recipe.isRecipe() ? recipe.getProduct() : null;
    }
}
