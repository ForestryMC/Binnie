package binnie.core.craftgui.minecraft.control;

import javax.annotation.Nullable;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.IInventory;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.craftgui.Attribute;
import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.ITooltip;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.Tooltip;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.geometry.Area;
import binnie.core.craftgui.geometry.Point;
import binnie.core.craftgui.geometry.Position;
import binnie.core.craftgui.minecraft.MinecraftTooltip;
import binnie.core.craftgui.minecraft.Window;
import binnie.core.craftgui.renderer.RenderUtil;
import binnie.core.craftgui.resource.minecraft.CraftGUITexture;
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
		this.addAttribute(Attribute.MouseOver);
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
		CraftGUI.render.texture(CraftGUITexture.EnergyBarBack, this.getArea());
		final float percentage = this.getPercentage() / 100.0f;
		int colourFromPercentage = this.getColourFromPercentage(percentage);
		RenderUtil.setColour(colourFromPercentage);
		final Area area = this.getArea();
		switch (this.direction) {
			case Top:
			case BOTTOM: {
				final int height = Math.round(area.size().y() * percentage);
				area.setSize(new Point(area.size().x(), height));
				break;
			}
			case LEFT:
			case RIGHT: {
				final int width = Math.round(area.size().x() * percentage);
				area.setSize(new Point(width, area.size().y()));
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
		CraftGUI.render.texture(CraftGUITexture.EnergyBarGlow, area);
		GlStateManager.color(1, 1, 1, 1);
		CraftGUI.render.texture(CraftGUITexture.EnergyBarGlass, this.getArea());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onRenderForeground(int guiWidth, int guiHeight) {
		if (this.isMouseOver() && Window.get(this).getGui().isHelpMode()) {
			final Area area = this.getArea();
			RenderUtil.setColour(MinecraftTooltip.getOutline(Tooltip.Type.Help));
			CraftGUI.render.texture(CraftGUITexture.Outline, area.outset(1));
		} else if (ControlEnergyBar.isError) {
			final Area area = this.getArea();
			RenderUtil.setColour(MinecraftTooltip.getOutline(MinecraftTooltip.Type.Error));
			CraftGUI.render.texture(CraftGUITexture.Outline, area.outset(1));
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
