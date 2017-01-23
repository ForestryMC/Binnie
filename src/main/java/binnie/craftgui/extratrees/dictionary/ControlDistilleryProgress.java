package binnie.craftgui.extratrees.dictionary;

import binnie.core.machines.Machine;
import binnie.craftgui.core.CraftGUI;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.geometry.IArea;
import binnie.craftgui.core.geometry.IPoint;
import binnie.craftgui.core.geometry.Position;
import binnie.craftgui.events.EventMouse;
import binnie.craftgui.minecraft.Window;
import binnie.craftgui.minecraft.control.ControlProgressBase;
import binnie.craftgui.resource.Texture;
import binnie.craftgui.resource.minecraft.StandardTexture;
import binnie.extratrees.core.ExtraTreeTexture;
import binnie.extratrees.machines.Distillery;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;
import org.lwjgl.opengl.GL11;

public class ControlDistilleryProgress extends ControlProgressBase {
	static Texture DistilleryBase = new StandardTexture(43, 0, 58, 66, ExtraTreeTexture.Gui);
	static Texture DistilleryOverlay = new StandardTexture(139, 0, 18, 66, ExtraTreeTexture.Gui);
	static Texture LiquidFlow = new StandardTexture(101, 0, 38, 66, ExtraTreeTexture.Gui);
	static Texture Output = new StandardTexture(68, 66, 17, 7, ExtraTreeTexture.Gui);

	@Override
	public void onRenderBackground() {
		CraftGUI.render.texture(ControlDistilleryProgress.DistilleryBase, new IPoint(0.0f, 0.0f));
		CraftGUI.render.texturePercentage(ControlDistilleryProgress.LiquidFlow, new IArea(18.0f, 0.0f, 38.0f, 66.0f), Position.Left, this.progress);
		final Distillery.ComponentDistilleryLogic component = Machine.getInterface(Distillery.ComponentDistilleryLogic.class, Window.get(this).getInventory());
		FluidStack stack = null;
		if (component != null) {
			stack = component.currentFluid;
		}
		if (stack != null) {
			for (int y = 0; y < 4; ++y) {
				CraftGUI.render.fluid(new IPoint(1.0f, 1 + y * 16), stack);
			}
		}
	}

	@Override
	public void onRenderForeground() {
		Distillery.ComponentDistilleryLogic distilleryLogic = Machine.getInterface(Distillery.ComponentDistilleryLogic.class, Window.get(this).getInventory());
		if (distilleryLogic != null) {
			final int level = distilleryLogic.level;
			CraftGUI.render.texture(ControlDistilleryProgress.Output, new IPoint(47.0f, 14 + level * 15));
			CraftGUI.render.texture(ControlDistilleryProgress.DistilleryOverlay, new IPoint(0.0f, 0.0f));
		}
	}

	protected ControlDistilleryProgress(final IWidget parent, final float x, final float y) {
		super(parent, x, y, 58.0f, 66.0f);
		this.addSelfEventHandler(new EventMouse.Down.Handler() {
			@Override
			public void onEvent(final EventMouse.Down event) {
				int distillationLevel = -1;
				if (new IArea(45.0f, 8.0f, 19.0f, 11.0f).contains(ControlDistilleryProgress.this.getRelativeMousePosition())) {
					distillationLevel = 0;
				} else if (new IArea(45.0f, 23.0f, 19.0f, 11.0f).contains(ControlDistilleryProgress.this.getRelativeMousePosition())) {
					distillationLevel = 1;
				} else if (new IArea(45.0f, 38.0f, 19.0f, 11.0f).contains(ControlDistilleryProgress.this.getRelativeMousePosition())) {
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

}
