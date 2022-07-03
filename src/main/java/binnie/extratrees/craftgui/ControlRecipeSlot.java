package binnie.extratrees.craftgui;

import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.events.EventMouse;
import binnie.core.craftgui.minecraft.Window;
import binnie.core.craftgui.minecraft.control.ControlSlotBase;
import binnie.core.machines.Machine;
import binnie.core.machines.TileEntityMachine;
import binnie.core.machines.component.IComponentRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class ControlRecipeSlot extends ControlSlotBase {
    public ControlRecipeSlot(IWidget parent, int x, int y) {
        super(parent, x, y, 50);
        addSelfEventHandler(new MouseDownHandler());
        setRotating();
    }

    @Override
    public ItemStack getItemStack() {
        IComponentRecipe recipe =
                Machine.getInterface(IComponentRecipe.class, Window.get(this).getInventory());
        return recipe.isRecipe() ? recipe.getProduct() : null;
    }

    private class MouseDownHandler extends EventMouse.Down.Handler {
        @Override
        public void onEvent(EventMouse.Down event) {
            TileEntity tile = (TileEntity) Window.get(getWidget()).getInventory();
            if (tile == null || !(tile instanceof TileEntityMachine)) {
                return;
            }

            NBTTagCompound nbt = new NBTTagCompound();
            Window.get(getWidget()).sendClientAction("recipe", nbt);
        }
    }
}
