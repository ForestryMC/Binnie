package binnie.craftgui.extratrees.kitchen;

import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.geometry.IArea;
import binnie.craftgui.events.EventMouse;
import binnie.craftgui.minecraft.Window;
import net.minecraft.nbt.NBTTagCompound;

public class ControlTankSlot extends ControlSlotFluid {
    int tankID;

    public ControlTankSlot(final IWidget parent, final int x, final int y, final int i) {
        super(parent, x, y, null);
        this.tankID = 0;
        this.tankID = i;
        this.addSelfEventHandler(new EventMouse.Down.Handler() {
            @Override
            public void onEvent(final EventMouse.Down event) {
                if (event.getButton() == 0) {
                    final NBTTagCompound nbt = new NBTTagCompound();
                    nbt.setByte("id", (byte) ControlTankSlot.this.tankID);
                    Window.get(ControlTankSlot.this.getWidget()).sendClientAction("tank-click", nbt);
                }
            }
        });
    }

    @Override
    public void onUpdateClient() {
        this.fluidStack = Window.get(this).getContainer().getTankInfo(this.tankID).liquid;
        final int height = (int) (16.0f * (((this.fluidStack == null) ? 0 : this.fluidStack.amount) / 1000.0f));
        this.itemDisplay.setCroppedZone(this.itemDisplay, new IArea(0.0f, 16 - height, 16.0f, 16.0f));
        super.onUpdateClient();
    }

    @Override
    public void onRenderBackground() {
        super.onRenderBackground();
    }
}
