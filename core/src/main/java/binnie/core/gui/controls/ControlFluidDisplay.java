package binnie.core.gui.controls;

import javax.annotation.Nullable;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.api.gui.IPoint;
import binnie.core.api.gui.IWidget;
import binnie.core.gui.Attribute;
import binnie.core.gui.ITooltip;
import binnie.core.gui.Tooltip;
import binnie.core.gui.controls.core.Control;
import binnie.core.gui.geometry.Point;
import binnie.core.gui.minecraft.Window;
import binnie.core.gui.renderer.RenderUtil;

public class ControlFluidDisplay extends Control implements ITooltip {
	@Nullable
	private FluidStack fluidStack;
	private boolean hastooltip;

	public ControlFluidDisplay(IWidget parent, int x, int y) {
		this(parent, x, y, 16);
	}

	public ControlFluidDisplay(IWidget parent, int x, int y, FluidStack stack, boolean tooltip) {
		this(parent, x, y, 16);
		this.setFluidStack(stack);
		if (tooltip) {
			this.setTooltip();
		}
	}

	public ControlFluidDisplay(IWidget parent, int x, int y, int size) {
		super(parent, x, y, size, size);
		this.fluidStack = null;
		this.hastooltip = false;
	}

	public void setTooltip() {
		this.hastooltip = true;
		this.addAttribute(Attribute.MOUSE_OVER);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onRenderForeground(int guiWidth, int guiHeight) {
		if (this.fluidStack == null) {
			return;
		}
		IPoint relativeToWindow = this.getAbsolutePosition().sub(this.getTopParent().getPosition());
		if (relativeToWindow.xPos() > Window.get(this).getSize().xPos() + 100 || relativeToWindow.yPos() > Window.get(this).getSize().yPos() + 100) {
			return;
		}
		if (this.fluidStack != null) {
			Fluid fluid = this.fluidStack.getFluid();
			int hex = fluid.getColor(this.fluidStack);
			int r = (hex & 0xFF0000) >> 16;
			int g = (hex & 0xFF00) >> 8;
			int b = hex & 0xFF;
			ResourceLocation iconRL = this.fluidStack.getFluid().getStill();
			TextureAtlasSprite icon = FMLClientHandler.instance().getClient().getTextureMapBlocks().getAtlasSprite(iconRL.toString());
			GlStateManager.color(r / 255.0f, g / 255.0f, b / 255.0f, 1.0f);
			GlStateManager.enableBlend();
			GlStateManager.blendFunc(770, 771);
			if (this.getSize().xPos() != 16) {
				GlStateManager.pushMatrix();
				float scale = this.getSize().xPos() / 16.0f;
				GlStateManager.scale(scale, scale, 1.0f);
				RenderUtil.drawSprite(Point.ZERO, icon);
				GlStateManager.popMatrix();
			} else {
				RenderUtil.drawSprite(Point.ZERO, icon);
			}
			GlStateManager.disableBlend();
		}
	}

	public void setFluidStack(@Nullable FluidStack fluidStack) {
		this.fluidStack = fluidStack;
	}

	@Override
	public void getTooltip(Tooltip tooltip, ITooltipFlag tooltipFlag) {
		if (this.hastooltip && this.fluidStack != null) {
			tooltip.add(this.fluidStack.getLocalizedName());
		}
	}
}
