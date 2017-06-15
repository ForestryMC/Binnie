package binnie.extratrees.machines.craftgui;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.nbt.NBTTagCompound;

import net.minecraftforge.fluids.FluidStack;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.events.EventMouse;
import binnie.core.craftgui.geometry.Area;
import binnie.core.craftgui.geometry.Point;
import binnie.core.craftgui.geometry.Position;
import binnie.core.craftgui.minecraft.Window;
import binnie.core.craftgui.minecraft.control.ControlProgressBase;
import binnie.core.craftgui.renderer.RenderUtil;
import binnie.core.craftgui.resource.Texture;
import binnie.core.craftgui.resource.minecraft.StandardTexture;
import binnie.core.machines.Machine;
import binnie.extratrees.core.ExtraTreeTexture;
import binnie.extratrees.machines.distillery.DistilleryLogic;

public class ControlDistilleryProgress extends ControlProgressBase {
	public static final Texture DISTILLERY_BASE = new StandardTexture(43, 0, 58, 66, ExtraTreeTexture.Gui);
	public static final Texture DISTILLERY_OVERLAY = new StandardTexture(139, 0, 18, 66, ExtraTreeTexture.Gui);
	public static final Texture LIQUID_FLOW = new StandardTexture(101, 0, 38, 66, ExtraTreeTexture.Gui);
	public static final Texture OUTPUT = new StandardTexture(68, 66, 17, 7, ExtraTreeTexture.Gui);

	protected ControlDistilleryProgress(final IWidget parent, final int x, final int y) {
		super(parent, x, y, 58, 66);
		this.addSelfEventHandler(new EventMouse.Down.Handler() {
			@Override
			public void onEvent(final EventMouse.Down event) {
				int distillationLevel = -1;
				if (new Area(45, 8, 19, 11).contains(ControlDistilleryProgress.this.getRelativeMousePosition())) {
					distillationLevel = 0;
				} else if (new Area(45, 23, 19, 11).contains(ControlDistilleryProgress.this.getRelativeMousePosition())) {
					distillationLevel = 1;
				} else if (new Area(45, 38, 19, 11).contains(ControlDistilleryProgress.this.getRelativeMousePosition())) {
					distillationLevel = 2;
				}
				if (distillationLevel >= 0) {
					final NBTTagCompound nbt = new NBTTagCompound();
					nbt.setByte("i", (byte) distillationLevel);
					Window.get(ControlDistilleryProgress.this.getWidget()).sendClientAction("still-level", nbt);
				}
			}
		});
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onRenderBackground(int guiWidth, int guiHeight) {
		CraftGUI.render.texture(DISTILLERY_BASE, Point.ZERO);
		CraftGUI.render.texturePercentage(LIQUID_FLOW, new Area(18, 0, 38, 66), Position.LEFT, this.progress);
		final DistilleryLogic component = Machine.getInterface(DistilleryLogic.class, Window.get(this).getInventory());
		FluidStack stack = null;
		if (component != null) {
			stack = component.currentFluid;
		}
		if (stack != null) {
			for (int y = 0; y < 4; ++y) {
				RenderUtil.drawFluid(new Point(1, 1 + y * 16), stack);
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onRenderForeground(int guiWidth, int guiHeight) {
		DistilleryLogic distilleryLogic = Machine.getInterface(DistilleryLogic.class, Window.get(this).getInventory());
		if (distilleryLogic != null) {
			final int level = distilleryLogic.level;
			GlStateManager.enableAlpha();
			CraftGUI.render.texture(OUTPUT, new Point(47, 14 + level * 15));
			CraftGUI.render.texture(DISTILLERY_OVERLAY, Point.ZERO);
		}
	}
}
