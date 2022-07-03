package binnie.extratrees.craftgui;

import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.events.EventMouse;
import binnie.core.craftgui.geometry.IArea;
import binnie.core.craftgui.minecraft.Window;
import net.minecraft.nbt.NBTTagCompound;

public class ControlTankSlot extends ControlSlotFluid {
    protected int tankID;

    public ControlTankSlot(IWidget parent, int x, int y, int i) {
        super(parent, x, y, null);
        tankID = 0;
        tankID = i;
        addSelfEventHandler(new EventMouse.Down.Handler() {
            @Override
            public void onEvent(EventMouse.Down event) {
                if (event.getButton() == 0) {
                    NBTTagCompound nbt = new NBTTagCompound();
                    nbt.setByte("id", (byte) tankID);
                    Window.get(getWidget()).sendClientAction("tank-click", nbt);
                }
            }
        });
    }

    @Override
    public void onUpdateClient() {
        fluidStack = Window.get(this).getContainer().getTankInfo(tankID).liquid;
        int height = (int) (16.0f * (((fluidStack == null) ? 0 : fluidStack.amount) / 1000.0f));
        itemDisplay.setCroppedZone(itemDisplay, new IArea(0.0f, 16 - height, 16.0f, 16.0f));
        super.onUpdateClient();
    }

    @Override
    public void onRenderBackground() {
        super.onRenderBackground();
    }
}
