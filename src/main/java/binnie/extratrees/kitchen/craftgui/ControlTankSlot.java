package binnie.extratrees.kitchen.craftgui;

import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.geometry.Area;
import binnie.craftgui.events.EventMouse;
import binnie.craftgui.minecraft.Window;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
	@SideOnly(Side.CLIENT)
	public void onUpdateClient() {
		this.fluidStack = Window.get(this).getContainer().getTankInfo(this.tankID).liquid;
		final int height = 16 * (((this.fluidStack == null) ? 0 : this.fluidStack.amount) / 1000);
		this.itemDisplay.setCroppedZone(this.itemDisplay, new Area(0, 16 - height, 16, 16));
		super.onUpdateClient();
	}

	@Override
	public void onRenderBackground(int guiWidth, int guiHeight) {
		super.onRenderBackground(guiWidth, guiHeight);
	}
}
