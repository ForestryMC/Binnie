package binnie.extratrees.machines.distillery.window;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.nbt.NBTTagCompound;

import net.minecraftforge.fluids.FluidStack;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.gui.CraftGUI;
import binnie.core.gui.IWidget;
import binnie.core.gui.events.EventMouse;
import binnie.core.gui.geometry.Area;
import binnie.core.gui.geometry.Point;
import binnie.core.gui.geometry.Position;
import binnie.core.gui.minecraft.Window;
import binnie.core.gui.minecraft.control.ControlProgressBase;
import binnie.core.gui.renderer.RenderUtil;
import binnie.core.gui.resource.Texture;
import binnie.core.gui.resource.minecraft.StandardTexture;
import binnie.core.machines.Machine;
import binnie.extratrees.core.ExtraTreeTexture;
import binnie.extratrees.machines.distillery.DistilleryLogic;

public class ControlDistilleryProgress extends ControlProgressBase {
	public static final Texture DISTILLERY_BASE = new StandardTexture(43, 0, 58, 66, ExtraTreeTexture.GUI);
	public static final Texture DISTILLERY_OVERLAY = new StandardTexture(139, 0, 18, 66, ExtraTreeTexture.GUI);
	public static final Texture LIQUID_FLOW = new StandardTexture(101, 0, 38, 66, ExtraTreeTexture.GUI);
	public static final Texture OUTPUT = new StandardTexture(68, 66, 17, 7, ExtraTreeTexture.GUI);

	protected ControlDistilleryProgress(final IWidget parent, final int x, final int y) {
		super(parent, x, y, 58, 66);
		this.addSelfEventHandler(EventMouse.Down.class, event -> {
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
		});
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onRenderBackground(int guiWidth, int guiHeight) {
		CraftGUI.RENDER.texture(DISTILLERY_BASE, Point.ZERO);
		CraftGUI.RENDER.texturePercentage(LIQUID_FLOW, new Area(18, 0, 38, 66), Position.LEFT, this.progress);
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
			CraftGUI.RENDER.texture(OUTPUT, new Point(47, 14 + level * 15));
			CraftGUI.RENDER.texture(DISTILLERY_OVERLAY, Point.ZERO);
		}
	}
}
