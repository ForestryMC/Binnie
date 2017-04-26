package binnie.core.craftgui.minecraft.control;

import binnie.core.BinnieCore;
import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.ITooltip;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.Tooltip;
import binnie.core.craftgui.WidgetAttribute;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.events.EventMouse;
import binnie.core.craftgui.geometry.IArea;
import binnie.core.craftgui.geometry.IPoint;
import binnie.core.craftgui.minecraft.MinecraftTooltip;
import binnie.core.craftgui.minecraft.Window;
import binnie.core.craftgui.resource.minecraft.CraftGUITexture;
import binnie.core.machines.Machine;
import binnie.core.machines.inventory.MachineSide;
import binnie.core.machines.inventory.TankSlot;
import binnie.core.machines.power.ITankMachine;
import binnie.core.machines.power.TankInfo;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.Fluid;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class ControlLiquidTank extends Control implements ITooltip {
	public static List<Integer> tankError = new ArrayList<>();
	private int tankID;
	private boolean horizontal;

	public ControlLiquidTank(IWidget parent, int x, int y) {
		this(parent, x, y, false);
	}

	public ControlLiquidTank(IWidget parent, int x, int y, boolean horizontal) {
		super(parent, x, y, horizontal ? 60.0f : 18.0f, horizontal ? 18.0f : 60.0f);
		tankID = 0;
		this.horizontal = horizontal;
		addAttribute(WidgetAttribute.MouseOver);
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

	public void setTankID(int tank) {
		tankID = tank;
	}

	public TankInfo getTank() {
		return Window.get(this).getContainer().getTankInfo(tankID);
	}

	public boolean isTankValid() {
		return !getTank().isEmpty();
	}

	public int getTankCapacity() {
		return (int) getTank().getCapacity();
	}

	@Override
	public void onRenderBackground() {
		CraftGUI.Render.texture(horizontal ? CraftGUITexture.HorizontalLiquidTank : CraftGUITexture.LiquidTank, IPoint.ZERO);
		if (isMouseOver() && Window.get(this).getGui().isHelpMode()) {
			int c = 0xaa000000 + MinecraftTooltip.getOutline(Tooltip.Type.Help);
			CraftGUI.Render.gradientRect(getArea().inset(1), c, c);
		} else if (ControlLiquidTank.tankError.contains(tankID)) {
			int c = 0xaa000000 + MinecraftTooltip.getOutline(MinecraftTooltip.Type.Error);
			CraftGUI.Render.gradientRect(getArea().inset(1), c, c);
		} else if (getSuperParent().getMousedOverWidget() == this) {
			if (Window.get(this).getGui().getDraggedItem() != null) {
				CraftGUI.Render.gradientRect(getArea().inset(1), 0xaaff9999, 0xaaff9999);
			} else {
				CraftGUI.Render.gradientRect(getArea().inset(1), 0x80ffffff, 0x80ffffff);
			}
		}

		if (isTankValid()) {
			float height = horizontal ? 16.0f : 58.0f;
			int squaled = (int) (height * (getTank().getAmount() / getTank().getCapacity()));
			int yPos = (int) height + 1;
			Fluid fluid = getTank().liquid.getFluid();
			int hex = fluid.getColor(getTank().liquid);
			int r = (hex & 0xFF0000) >> 16;
			int g = (hex & 0xFF00) >> 8;
			int b = hex & 0xFF;
			GL11.glColor4f(r / 255.0f, g / 255.0f, b / 255.0f, 1.0f);
			GL11.glEnable(3042);
			GL11.glBlendFunc(770, 771);
			IPoint pos = getAbsolutePosition();
			IPoint offset = new IPoint(0.0f, height - squaled);
			IArea limited = getArea().inset(1);
			if (horizontal) {
				limited.setSize(new IPoint(limited.w() - 1.0f, limited.h()));
			}

			CraftGUI.Render.limitArea(new IArea(limited.pos().add(pos).add(offset), limited.size().sub(offset)));
			GL11.glEnable(3089);
			BinnieCore.proxy.bindTexture(TextureMap.locationItemsTexture);
			for (int y = 0; y < height; y += 16) {
				for (int x = 0; x < (horizontal ? 58 : 16); x += 16) {
					IIcon icon = fluid.getIcon();
					CraftGUI.Render.iconBlock(new IPoint(1 + x, 1 + y), icon);
				}
			}

			GL11.glDisable(3089);
			GL11.glDisable(3042);
		}
	}

	@Override
	public void onRenderForeground() {
		CraftGUI.Render.texture(horizontal ? CraftGUITexture.HorizontalLiquidTankOverlay : CraftGUITexture.LiquidTankOverlay, IPoint.ZERO);
		if (isMouseOver() && Window.get(this).getGui().isHelpMode()) {
			IArea area = getArea();
			CraftGUI.Render.colour(MinecraftTooltip.getOutline(Tooltip.Type.Help));
			CraftGUI.Render.texture(CraftGUITexture.Outline, area.outset(1));
		}

		if (ControlLiquidTank.tankError.contains(tankID)) {
			IArea area = getArea();
			CraftGUI.Render.colour(MinecraftTooltip.getOutline(MinecraftTooltip.Type.Error));
			CraftGUI.Render.texture(CraftGUITexture.Outline, area.outset(1));
		}
	}

	@Override
	public void getHelpTooltip(Tooltip tooltip) {
		if (getTankSlot() == null) {
			return;
		}

		TankSlot slot = getTankSlot();
		tooltip.add(slot.getName());
		tooltip.add("Capacity: " + getTankCapacity() + " mB");
		tooltip.add("Insert Side: " + MachineSide.asString(slot.getInputSides()));
		tooltip.add("Extract Side: " + MachineSide.asString(slot.getOutputSides()));
		if (slot.isReadOnly()) {
			tooltip.add("Output Only Tank");
		}
		tooltip.add("Accepts: " + ((slot.getValidator() == null) ? "Any Item" : slot.getValidator().getTooltip()));
	}

	@Override
	public void getTooltip(Tooltip tooltip) {
		if (isTankValid()) {
			int percentage = (int) (100.0 * getTank().getAmount() / getTankCapacity());
			tooltip.add(getTank().getName());
			tooltip.add(percentage + "% full");
			tooltip.add((int) getTank().getAmount() + " mB");
			return;
		}
		tooltip.add("Empty");
	}

	private TankSlot getTankSlot() {
		ITankMachine tank = Machine.getInterface(ITankMachine.class, Window.get(this).getInventory());
		return (tank != null) ? tank.getTankSlot(tankID) : null;
	}
}
