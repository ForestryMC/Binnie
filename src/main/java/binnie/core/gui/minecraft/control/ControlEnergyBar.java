package binnie.core.gui.minecraft.control;

import javax.annotation.Nullable;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.IInventory;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.gui.Attribute;
import binnie.core.gui.CraftGUI;
import binnie.core.gui.ITooltip;
import binnie.core.gui.IWidget;
import binnie.core.gui.Tooltip;
import binnie.core.gui.controls.core.Control;
import binnie.core.gui.geometry.Area;
import binnie.core.gui.geometry.Point;
import binnie.core.gui.geometry.Position;
import binnie.core.gui.minecraft.MinecraftTooltip;
import binnie.core.gui.minecraft.Window;
import binnie.core.gui.renderer.RenderUtil;
import binnie.core.gui.resource.minecraft.CraftGUITexture;
import binnie.core.machines.Machine;
import binnie.core.machines.TileEntityMachine;
import binnie.core.machines.power.IPoweredMachine;
import binnie.core.machines.power.IProcess;

public class ControlEnergyBar extends Control implements ITooltip {
	public static boolean isError;
	private Position direction;

	public ControlEnergyBar(final IWidget parent, final int x, final int y, final int width, final int height, final Position direction) {
		super(parent, x, y, width, height);
		this.direction = direction;
		this.addAttribute(Attribute.MOUSE_OVER);
	}

	@Nullable
	public IPoweredMachine getClientPower() {
		final IInventory inventory = Window.get(this).getInventory();
		final TileEntityMachine machine = (inventory instanceof TileEntityMachine) ? (TileEntityMachine) inventory : null;
		if (machine == null) {
			return null;
		}
		return machine.getMachine().getInterface(IPoweredMachine.class);
	}

	public float getPercentage() {
		float percentage = 100.0f * this.getStoredEnergy() / this.getMaxEnergy();
		if (percentage > 100.0f) {
			percentage = 100.0f;
		}
		return percentage;
	}

	private float getStoredEnergy() {
		return Window.get(this).getContainer().getPowerInfo().getStoredEnergy();
	}

	private float getMaxEnergy() {
		return Window.get(this).getContainer().getPowerInfo().getMaxEnergy();
	}

	@Override
	public void getTooltip(final Tooltip tooltip) {
		tooltip.add((int) this.getPercentage() + "% charged");
		tooltip.add(this.getStoredEnergy() + "/" + this.getMaxEnergy() + " RF");
	}

	@Override
	public void getHelpTooltip(final Tooltip tooltip) {
		tooltip.add("Energy Bar");
		tooltip.add("Current: " + this.getStoredEnergy() + " RF (" + (int) this.getPercentage() + "%)");
		tooltip.add("Capacity: " + this.getMaxEnergy() + " RF");
		final IProcess process = Machine.getInterface(IProcess.class, Window.get(this).getInventory());
		if (process != null) {
			tooltip.add("Usage: " + (int) process.getEnergyPerTick() + " RF");
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onRenderBackground(int guiWidth, int guiHeight) {
		CraftGUI.RENDER.texture(CraftGUITexture.EnergyBarBack, this.getArea());
		final float percentage = this.getPercentage() / 100.0f;
		int colourFromPercentage = this.getColourFromPercentage(percentage);
		RenderUtil.setColour(colourFromPercentage);
		final Area area = this.getArea();
		switch (this.direction) {
			case Top:
			case BOTTOM: {
				final int height = Math.round(area.size().yPos() * percentage);
				area.setSize(new Point(area.size().xPos(), height));
				break;
			}
			case LEFT:
			case RIGHT: {
				final int width = Math.round(area.size().xPos() * percentage);
				area.setSize(new Point(width, area.size().yPos()));
				break;
			}
		}
		if (this.isMouseOver() && Window.get(this).getGui().isHelpMode()) {
			final int c = -1442840576 + MinecraftTooltip.getOutline(Tooltip.Type.Help);
			RenderUtil.drawGradientRect(this.getArea().inset(1), c, c);
		} else if (ControlEnergyBar.isError) {
			final int c = -1442840576 + MinecraftTooltip.getOutline(MinecraftTooltip.Type.Error);
			RenderUtil.drawGradientRect(this.getArea().inset(1), c, c);
		}
		CraftGUI.RENDER.texture(CraftGUITexture.EnergyBarGlow, area);
		GlStateManager.color(1, 1, 1, 1);
		CraftGUI.RENDER.texture(CraftGUITexture.EnergyBarGlass, this.getArea());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onRenderForeground(int guiWidth, int guiHeight) {
		if (this.isMouseOver() && Window.get(this).getGui().isHelpMode()) {
			final Area area = this.getArea();
			RenderUtil.setColour(MinecraftTooltip.getOutline(Tooltip.Type.Help));
			CraftGUI.RENDER.texture(CraftGUITexture.Outline, area.outset(1));
		} else if (ControlEnergyBar.isError) {
			final Area area = this.getArea();
			RenderUtil.setColour(MinecraftTooltip.getOutline(MinecraftTooltip.Type.Error));
			CraftGUI.RENDER.texture(CraftGUITexture.Outline, area.outset(1));
		}
	}

	public int getColourFromPercentage(final float percentage) {
		if (percentage > 0.5) {
			final int r = (int) ((1.0 - 2.0 * (percentage - 0.5)) * 255.0);
			return (r << 16) + 65280;
		} else {
			final int g = (int) (255.0f * (2.0f * percentage));
			return 16711680 + (g << 8);
		}
	}
}
