package binnie.extratrees.machines.brewery.window;

import java.util.Collections;

import net.minecraft.item.ItemStack;

import net.minecraftforge.fluids.FluidStack;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.lwjgl.opengl.GL11;

import binnie.core.gui.Attribute;
import binnie.core.gui.CraftGUI;
import binnie.core.api.gui.IWidget;
import binnie.core.gui.geometry.Area;
import binnie.core.gui.geometry.Point;
import binnie.core.gui.minecraft.Window;
import binnie.core.gui.minecraft.control.ControlProgressBase;
import binnie.core.gui.renderer.RenderUtil;
import binnie.core.gui.resource.textures.Texture;
import binnie.core.gui.resource.textures.StandardTexture;
import binnie.core.machines.Machine;
import binnie.core.util.ItemStackSet;
import binnie.extratrees.core.ExtraTreeTexture;
import binnie.extratrees.machines.brewery.BreweryLogic;
import binnie.extratrees.machines.brewery.recipes.BreweryRecipeManager;

public class ControlBreweryProgress extends ControlProgressBase {
	static Texture Brewery = new StandardTexture(0, 69, 34, 39, ExtraTreeTexture.GUI);
	static Texture BreweryOverlay = new StandardTexture(34, 69, 34, 39, ExtraTreeTexture.GUI);

	protected ControlBreweryProgress(final IWidget parent, final int x, final int y) {
		super(parent, x, y, 34, 39);
		this.addAttribute(Attribute.MOUSE_OVER);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onRenderBackground(int guiWidth, int guiHeight) {
		CraftGUI.RENDER.texture(ControlBreweryProgress.Brewery, Point.ZERO);
		final BreweryLogic logic = Machine.getInterface(BreweryLogic.class, Window.get(this).getInventory());
		if (logic == null || logic.currentCrafting == null || logic.currentCrafting.inputFluid == null) {
			return;
		}
		FluidStack output = BreweryRecipeManager.getOutput(logic.currentCrafting);
		FluidStack input = logic.currentCrafting.inputFluid;
		final int fermentedHeight = (int) (32.0f * logic.getProgress() / 100.0f);
		CraftGUI.RENDER.limitArea(new Area(new Point(1, 6).add(this.getAbsolutePosition()), new Point(32, 32 - fermentedHeight)), guiWidth, guiHeight);
		GL11.glEnable(GL11.GL_SCISSOR_TEST);
		RenderUtil.drawFluid(new Point(1, 6), input);
		RenderUtil.drawFluid(new Point(17, 6), input);
		RenderUtil.drawFluid(new Point(1, 22), input);
		RenderUtil.drawFluid(new Point(17, 22), input);
		GL11.glDisable(GL11.GL_SCISSOR_TEST);
		CraftGUI.RENDER.limitArea(new Area(new Point(1, 38 - fermentedHeight).add(this.getAbsolutePosition()), new Point(32, fermentedHeight)), guiWidth, guiHeight);
		GL11.glEnable(GL11.GL_SCISSOR_TEST);
		RenderUtil.drawFluid(new Point(1, 6), output);
		RenderUtil.drawFluid(new Point(17, 6), output);
		RenderUtil.drawFluid(new Point(1, 22), output);
		RenderUtil.drawFluid(new Point(17, 22), output);
		GL11.glDisable(GL11.GL_SCISSOR_TEST);
		final ItemStackSet stacks = new ItemStackSet();
		Collections.addAll(stacks, logic.currentCrafting.inputGrains);
		stacks.add(logic.currentCrafting.ingredient);
		int x = 1;
		int y = 6;
		for (final ItemStack stack : stacks) {
			RenderUtil.drawItem(new Point(x, y), stack);
			x += 16;
			if (x > 18) {
				x = 1;
				y += 16;
			}
		}
	}

	@Override
	public void onRenderForeground(int guiWidth, int guiHeight) {
	}
}
