package binnie.extratrees.craftgui.dictionary;

import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.events.EventMouse;
import binnie.core.craftgui.geometry.IArea;
import binnie.core.craftgui.geometry.IPoint;
import binnie.core.craftgui.geometry.Position;
import binnie.core.craftgui.minecraft.Window;
import binnie.core.craftgui.minecraft.control.ControlProgressBase;
import binnie.core.craftgui.resource.Texture;
import binnie.core.craftgui.resource.minecraft.StandardTexture;
import binnie.core.machines.Machine;
import binnie.extratrees.core.ExtraTreeTexture;
import binnie.extratrees.machines.Distillery;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;
import org.lwjgl.opengl.GL11;

public class ControlDistilleryProgress extends ControlProgressBase {
	protected static Texture distilleryBase = new StandardTexture(43, 0, 58, 66, ExtraTreeTexture.Gui);
	protected static Texture distilleryOverlay = new StandardTexture(139, 0, 18, 66, ExtraTreeTexture.Gui);
	protected static Texture liquidFlow = new StandardTexture(101, 0, 38, 66, ExtraTreeTexture.Gui);
	protected static Texture output = new StandardTexture(68, 66, 17, 7, ExtraTreeTexture.Gui);

	protected ControlDistilleryProgress(IWidget parent, float x, float y) {
		super(parent, x, y, 58.0f, 66.0f);
		addSelfEventHandler(new MouseDownHandler());
	}

	@Override
	public void onRenderBackground() {
		CraftGUI.Render.texture(ControlDistilleryProgress.distilleryBase, new IPoint(0.0f, 0.0f));
		CraftGUI.Render.texturePercentage(ControlDistilleryProgress.liquidFlow, new IArea(18.0f, 0.0f, 38.0f, 66.0f), Position.LEFT, progress);
		Distillery.ComponentDistilleryLogic component = Machine.getInterface(Distillery.ComponentDistilleryLogic.class, Window.get(this).getInventory());
		FluidStack stack = null;
		if (component != null) {
			stack = component.currentFluid;
		}

		if (stack != null) {
			for (int y = 0; y < 4; ++y) {
				renderFluid(stack, new IPoint(1.0f, 1 + y * 16));
			}
		}
	}

	@Override
	public void onRenderForeground() {
		int level = Machine.getInterface(Distillery.ComponentDistilleryLogic.class, Window.get(this).getInventory()).level;
		CraftGUI.Render.texture(output, new IPoint(47.0f, 14 + level * 15));
		CraftGUI.Render.texture(distilleryOverlay, new IPoint(0.0f, 0.0f));
	}

	public void renderFluid(FluidStack fluid, IPoint pos) {
		int hex = fluid.getFluid().getColor(fluid);
		int r = (hex & 0xFF0000) >> 16;
		int g = (hex & 0xFF00) >> 8;
		int b = hex & 0xFF;
		GL11.glColor4f(r / 255.0f, g / 255.0f, b / 255.0f, 1.0f);
		GL11.glEnable(3042);
		GL11.glBlendFunc(770, 771);
		CraftGUI.Render.iconBlock(pos, fluid.getFluid().getIcon());
		GL11.glDisable(3042);
	}

	private class MouseDownHandler extends EventMouse.Down.Handler {
		@Override
		public void onEvent(EventMouse.Down event) {
			int distillationLevel = -1;
			if (new IArea(45.0f, 8.0f, 19.0f, 11.0f).contains(getRelativeMousePosition())) {
				distillationLevel = 0;
			} else if (new IArea(45.0f, 23.0f, 19.0f, 11.0f).contains(getRelativeMousePosition())) {
				distillationLevel = 1;
			} else if (new IArea(45.0f, 38.0f, 19.0f, 11.0f).contains(getRelativeMousePosition())) {
				distillationLevel = 2;
			}

			if (distillationLevel >= 0) {
				NBTTagCompound nbt = new NBTTagCompound();
				nbt.setByte("i", (byte) distillationLevel);
				Window.get(getWidget()).sendClientAction("still-level", nbt);
			}
		}
	}
}
