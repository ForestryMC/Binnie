package binnie.extratrees.kitchen.craftgui;

import net.minecraft.nbt.NBTTagCompound;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.api.gui.IWidget;
import binnie.core.gui.events.EventMouse;
import binnie.core.gui.geometry.Area;
import binnie.core.gui.minecraft.Window;

public class ControlTankSlot extends ControlSlotFluid {
	private final int tankID;

	public ControlTankSlot(IWidget parent, int x, int y, int i) {
		super(parent, x, y, null);
		this.tankID = i;
		this.addSelfEventHandler(EventMouse.Down.class, event -> {
			if (event.getButton() == 0) {
				NBTTagCompound nbt = new NBTTagCompound();
				nbt.setByte("id", (byte) ControlTankSlot.this.tankID);
				Window.get(ControlTankSlot.this.getWidget()).sendClientAction("tank-click", nbt);
			}
		});
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onUpdateClient() {
		this.fluidStack = Window.get(this).getContainer().getTankInfo(this.tankID).getLiquid();
		int height = 16 * (((this.fluidStack == null) ? 0 : this.fluidStack.amount) / 1000);
		this.itemDisplay.setCroppedZone(this.itemDisplay, new Area(0, 16 - height, 16, 16));
		super.onUpdateClient();
	}
}
