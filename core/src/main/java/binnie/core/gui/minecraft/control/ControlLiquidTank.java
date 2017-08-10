package binnie.core.gui.minecraft.control;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.nbt.NBTTagCompound;

import net.minecraftforge.fluids.Fluid;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.lwjgl.opengl.GL11;

import binnie.core.BinnieCore;
import binnie.core.gui.Attribute;
import binnie.core.gui.CraftGUI;
import binnie.core.gui.ITooltip;
import binnie.core.gui.IWidget;
import binnie.core.gui.Tooltip;
import binnie.core.gui.controls.core.Control;
import binnie.core.gui.events.EventMouse;
import binnie.core.gui.geometry.Area;
import binnie.core.gui.geometry.Point;
import binnie.core.gui.minecraft.GuiCraftGUI;
import binnie.core.gui.minecraft.MinecraftTooltip;
import binnie.core.gui.minecraft.Window;
import binnie.core.gui.renderer.RenderUtil;
import binnie.core.gui.resource.minecraft.CraftGUITexture;
import binnie.core.machines.Machine;
import binnie.core.machines.inventory.MachineSide;
import binnie.core.machines.inventory.TankSlot;
import binnie.core.machines.power.ITankMachine;
import binnie.core.machines.power.TankInfo;

public class ControlLiquidTank extends Control implements ITooltip {
	public static List<Integer> tankError = new ArrayList<>();
	private int tankID;
	private boolean horizontal;

	public ControlLiquidTank(final IWidget parent, final int x, final int y) {
		this(parent, x, y, false);
	}

	public ControlLiquidTank(final IWidget parent, final int x, final int y, final boolean horizontal) {
		super(parent, x, y, horizontal ? 60 : 18, horizontal ? 18 : 60);
		this.tankID = 0;
		this.horizontal = false;
		this.horizontal = horizontal;
		this.addAttribute(Attribute.MOUSE_OVER);
		this.addSelfEventHandler(EventMouse.Down.class, event -> {
			if (event.getButton() == 0) {
				final NBTTagCompound nbt = new NBTTagCompound();
				nbt.setByte("id", (byte) ControlLiquidTank.this.tankID);
				Window.get(ControlLiquidTank.this.getWidget()).sendClientAction("tank-click", nbt);
			}
		});
	}

	public void setTankID(final int tank) {
		this.tankID = tank;
	}

	public TankInfo getTank() {
		return Window.get(this).getContainer().getTankInfo(this.tankID);
	}

	public boolean isTankValid() {
		return !this.getTank().isEmpty();
	}

	public int getTankCapacity() {
		return (int) this.getTank().getCapacity();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onRenderBackground(int guiWidth, int guiHeight) {
		CraftGUI.RENDER.texture(this.horizontal ? CraftGUITexture.HORIZONTAL_LIQUID_TANK : CraftGUITexture.LIQUID_TANK, Point.ZERO);
		GuiCraftGUI gui = Window.get(this).getGui();
		if (this.isMouseOver() && gui.isHelpMode()) {
			final int c = -1442840576 + MinecraftTooltip.getOutline(Tooltip.Type.HELP);
			RenderUtil.drawGradientRect(this.getArea().inset(1), c, c);
		} else if (ControlLiquidTank.tankError.contains(this.tankID)) {
			final int c = -1442840576 + MinecraftTooltip.getOutline(MinecraftTooltip.Type.ERROR);
			RenderUtil.drawGradientRect(this.getArea().inset(1), c, c);
		} else if (this.getTopParent().getMousedOverWidget() == this) {
			if (!gui.getDraggedItem().isEmpty()) {
				RenderUtil.drawGradientRect(this.getArea().inset(1), -1426089575, -1426089575);
			} else {
				RenderUtil.drawGradientRect(this.getArea().inset(1), -2130706433, -2130706433);
			}
		}

		if (this.isTankValid()) {
			final int height = this.horizontal ? 16 : 58;
			final int squaled = Math.round(height * (this.getTank().getAmount() / this.getTank().getCapacity()));
			final int yPos = height + 1;
			final Fluid fluid = this.getTank().liquid.getFluid();
			final int hex = fluid.getColor(this.getTank().liquid);
			final int r = (hex & 0xFF0000) >> 16;
			final int g = (hex & 0xFF00) >> 8;
			final int b = hex & 0xFF;
			GlStateManager.color(r / 255.0f, g / 255.0f, b / 255.0f, 1.0f);
			GlStateManager.enableBlend();
			{
				GlStateManager.blendFunc(770, 771);
				final Point pos = this.getAbsolutePosition();
				final Point offset = new Point(0, height - squaled);
				final Area limited = this.getArea().inset(1);
				if (this.horizontal) {
					limited.setSize(new Point(limited.width() - 1, limited.height()));
				}
				CraftGUI.RENDER.limitArea(new Area(limited.pos().add(pos).add(offset), limited.size().sub(offset)), guiWidth, guiHeight);
				GL11.glEnable(GL11.GL_SCISSOR_TEST);
				{
					BinnieCore.getBinnieProxy().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
					for (int y = 0; y < height; y += 16) {
						for (int x = 0; x < (this.horizontal ? 58 : 16); x += 16) {
							final TextureAtlasSprite icon = BinnieCore.getBinnieProxy().getTextureAtlasSprite(fluid.getStill());
							RenderUtil.drawSprite(new Point(1 + x, 1 + y), icon);
						}
					}
				}
				GL11.glDisable(GL11.GL_SCISSOR_TEST);
			}
			GlStateManager.disableBlend();
			GlStateManager.color(1, 1, 1, 1);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onRenderForeground(int guiWidth, int guiHeight) {
		CraftGUI.RENDER.texture(this.horizontal ? CraftGUITexture.HORIZONTAL_LIQUID_TANK_OVERLAY : CraftGUITexture.LIQUID_TANK_OVERLAY, Point.ZERO);
		GuiCraftGUI gui = Window.get(this).getGui();
		if (this.isMouseOver() && gui.isHelpMode()) {
			final Area area = this.getArea();
			RenderUtil.setColour(MinecraftTooltip.getOutline(Tooltip.Type.HELP));
			CraftGUI.RENDER.texture(CraftGUITexture.OUTLINE, area.outset(1));
		}
		if (ControlLiquidTank.tankError.contains(this.tankID)) {
			final Area area = this.getArea();
			RenderUtil.setColour(MinecraftTooltip.getOutline(MinecraftTooltip.Type.ERROR));
			CraftGUI.RENDER.texture(CraftGUITexture.OUTLINE, area.outset(1));
		}
	}

	@Override
	public void getHelpTooltip(final Tooltip tooltip) {
		if (this.getTankSlot() != null) {
			final TankSlot slot = this.getTankSlot();
			tooltip.add(slot.getName());
			tooltip.add("Capacity: " + this.getTankCapacity() + " mB");
			tooltip.add("Insert Side: " + MachineSide.asString(slot.getInputSides()));
			tooltip.add("Extract Side: " + MachineSide.asString(slot.getOutputSides()));
			if (slot.isReadOnly()) {
				tooltip.add("Output Only Tank");
			}
			tooltip.add("Accepts: " + ((slot.getValidator() == null) ? "Any Item" : slot.getValidator().getTooltip()));
		}
	}

	@Override
	public void getTooltip(final Tooltip tooltip, ITooltipFlag tooltipFlag) {
		if (this.isTankValid()) {
			final int percentage = (int) (100.0 * this.getTank().getAmount() / this.getTankCapacity());
			tooltip.add(this.getTank().getName());
			tooltip.add(percentage + "% full");
			tooltip.add((int) this.getTank().getAmount() + " mB");
			return;
		}
		tooltip.add("Empty");
	}

	@Nullable
	private TankSlot getTankSlot() {
		final ITankMachine tank = Machine.getInterface(ITankMachine.class, Window.get(this).getInventory());
		return (tank != null) ? tank.getTankSlot(this.tankID) : null;
	}
}
