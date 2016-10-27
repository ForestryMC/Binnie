package binnie.craftgui.extratrees.kitchen;

import binnie.craftgui.controls.core.Control;
import binnie.craftgui.core.Attribute;
import binnie.craftgui.core.CraftGUI;
import binnie.craftgui.core.ITooltip;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.Tooltip;
import binnie.craftgui.core.geometry.IPoint;
import binnie.craftgui.minecraft.Window;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.client.FMLClientHandler;
import org.lwjgl.opengl.GL11;

public class ControlFluidDisplay extends Control implements ITooltip {
	FluidStack itemStack;
	public boolean hastooltip;

	public void setTooltip() {
		this.hastooltip = true;
		this.addAttribute(Attribute.MouseOver);
	}

	public ControlFluidDisplay(final IWidget parent, final float f, final float y) {
		this(parent, f, y, 16.0f);
	}

	public ControlFluidDisplay(final IWidget parent, final float f, final float y, final FluidStack stack, final boolean tooltip) {
		this(parent, f, y, 16.0f);
		this.setItemStack(stack);
		if (tooltip) {
			this.setTooltip();
		}
	}

	public ControlFluidDisplay(final IWidget parent, final float x, final float y, final float size) {
		super(parent, x, y, size, size);
		this.itemStack = null;
		this.hastooltip = false;
	}

	@Override
	public void onRenderForeground() {
		if (this.itemStack == null) {
			return;
		}
		final IPoint relativeToWindow = this.getAbsolutePosition().sub(this.getSuperParent().getPosition());
		if (relativeToWindow.x() > Window.get(this).getSize().x() + 100.0f || relativeToWindow.y() > Window.get(this).getSize().y() + 100.0f) {
			return;
		}
		if (this.itemStack != null) {
			final Fluid fluid = this.itemStack.getFluid();
			final int hex = fluid.getColor(this.itemStack);
			final int r = (hex & 0xFF0000) >> 16;
			final int g = (hex & 0xFF00) >> 8;
			final int b = hex & 0xFF;
			final ResourceLocation iconRL = this.itemStack.getFluid().getStill();
			TextureAtlasSprite icon = FMLClientHandler.instance().getClient().getTextureMapBlocks().getAtlasSprite(iconRL.toString());
			GL11.glColor4f(r / 255.0f, g / 255.0f, b / 255.0f, 1.0f);
			GL11.glEnable(3042);
			GL11.glBlendFunc(770, 771);
			if (this.getSize().x() != 16.0f) {
				GL11.glPushMatrix();
				final float scale = this.getSize().x() / 16.0f;
				GL11.glScalef(scale, scale, 1.0f);
				CraftGUI.Render.iconBlock(IPoint.ZERO, icon);
				GL11.glPopMatrix();
			} else {
				CraftGUI.Render.iconBlock(IPoint.ZERO, icon);
			}
			GL11.glDisable(3042);
		}
	}

	public void setItemStack(final FluidStack itemStack) {
		this.itemStack = itemStack;
	}

	@Override
	public void getTooltip(final Tooltip tooltip) {
		if (this.hastooltip && this.itemStack != null) {
			tooltip.add(this.itemStack.getLocalizedName());
		}
	}
}
