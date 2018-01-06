package binnie.core.gui.minecraft.control;

import java.text.NumberFormat;

import binnie.core.ModId;
import binnie.core.api.gui.Alignment;
import binnie.core.api.gui.IArea;
import binnie.core.api.gui.IPoint;
import binnie.core.api.gui.IWidget;
import binnie.core.gui.Attribute;
import binnie.core.gui.CraftGUI;
import binnie.core.gui.ITooltip;
import binnie.core.gui.Tooltip;
import binnie.core.gui.controls.core.Control;
import binnie.core.gui.geometry.Point;
import binnie.core.gui.minecraft.MinecraftTooltip;
import binnie.core.gui.minecraft.Window;
import binnie.core.gui.renderer.RenderUtil;
import binnie.core.gui.resource.textures.CraftGUITexture;
import binnie.core.machines.Machine;
import binnie.core.machines.power.IProcess;
import binnie.core.util.I18N;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ControlEnergyBar extends Control implements ITooltip {
	public static boolean isError;
	private final Alignment direction;

	public ControlEnergyBar(final IWidget parent, final int x, final int y, final int width, final int height, final Alignment direction) {
		super(parent, x, y, width, height);
		this.direction = direction;
		this.addAttribute(Attribute.MOUSE_OVER);
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
	@SideOnly(Side.CLIENT)
	public void getTooltip(final Tooltip tooltip, ITooltipFlag tooltipFlag) {
		tooltip.add(I18N.localise(ModId.CORE, "gui.energy.bar"));
		NumberFormat numberFormat = I18N.getNumberFormat();
		String storedEnergy = numberFormat.format(this.getStoredEnergy());
		String maxEnergy = numberFormat.format(this.getMaxEnergy());
		String energyString = TextFormatting.GRAY + storedEnergy + "/" + maxEnergy + " RF";
		tooltip.add(energyString);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getHelpTooltip(final Tooltip tooltip, ITooltipFlag tooltipFlag) {
		tooltip.add(I18N.localise(ModId.CORE, "gui.energy.bar"));
		if (tooltipFlag.isAdvanced()) {
			String currentFormat = I18N.localise(ModId.CORE, "gui.energy.amount.current");
			NumberFormat numberFormat = I18N.getNumberFormat();
			String currentString = currentFormat.replace("$MAX$", numberFormat.format(this.getStoredEnergy()))
					.replace("$PERCENT$", I18N.getPercentFormat().format(this.getPercentage() / 100.0));
			tooltip.add(TextFormatting.GRAY + currentString);
			String maxEnergy = numberFormat.format(this.getMaxEnergy());
			tooltip.add(TextFormatting.GRAY + I18N.localise(ModId.CORE, "gui.energy.capacity", maxEnergy));
			final IProcess process = Machine.getInterface(IProcess.class, Window.get(this).getInventory());
			if (process != null) {
				String energyPerTick = numberFormat.format((int) process.getEnergyPerTick());
				tooltip.add(TextFormatting.GRAY + I18N.localise(ModId.CORE, "gui.energy.cost.per.tick", energyPerTick));
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onRenderBackground(int guiWidth, int guiHeight) {
		CraftGUI.RENDER.texture(CraftGUITexture.ENERGY_BAR_BACK, this.getArea());
		final float percentage = this.getPercentage() / 100.0f;
		int colourFromPercentage = this.getColourFromPercentage(percentage);
		RenderUtil.setColour(colourFromPercentage);
		final IArea area = this.getArea();
		switch (this.direction) {
			case TOP:
			case BOTTOM: {
				IPoint fullSize = area.size();
				final int height = Math.round(fullSize.yPos() * percentage);
				area.setSize(new Point(fullSize.xPos(), height));
				area.setYPos(fullSize.yPos() - height);
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
			final int c = -1442840576 + MinecraftTooltip.getOutline(Tooltip.Type.HELP);
			RenderUtil.drawGradientRect(this.getArea().inset(1), c, c);
		} else if (ControlEnergyBar.isError) {
			final int c = -1442840576 + MinecraftTooltip.getOutline(MinecraftTooltip.Type.ERROR);
			RenderUtil.drawGradientRect(this.getArea().inset(1), c, c);
		}
		CraftGUI.RENDER.texture(CraftGUITexture.ENERGY_BAR_GLOW, area);
		GlStateManager.color(1, 1, 1, 1);
		CraftGUI.RENDER.texture(CraftGUITexture.ENERGY_BAR_GLASS, this.getArea());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onRenderForeground(int guiWidth, int guiHeight) {
		if (this.isMouseOver() && Window.get(this).getGui().isHelpMode()) {
			final IArea area = this.getArea();
			RenderUtil.setColour(MinecraftTooltip.getOutline(Tooltip.Type.HELP));
			CraftGUI.RENDER.texture(CraftGUITexture.OUTLINE, area.outset(1));
		} else if (ControlEnergyBar.isError) {
			final IArea area = this.getArea();
			RenderUtil.setColour(MinecraftTooltip.getOutline(MinecraftTooltip.Type.ERROR));
			CraftGUI.RENDER.texture(CraftGUITexture.OUTLINE, area.outset(1));
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
