package binnie.craftgui.extratrees.dictionary;

import binnie.core.machines.Machine;
import binnie.core.util.ItemStackSet;
import binnie.craftgui.core.Attribute;
import binnie.craftgui.core.CraftGUI;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.geometry.IArea;
import binnie.craftgui.core.geometry.IPoint;
import binnie.craftgui.core.renderer.RenderUtil;
import binnie.craftgui.minecraft.Window;
import binnie.craftgui.minecraft.control.ControlProgressBase;
import binnie.craftgui.resource.Texture;
import binnie.craftgui.resource.minecraft.StandardTexture;
import binnie.extratrees.core.ExtraTreeTexture;
import binnie.extratrees.machines.brewery.BreweryLogic;
import binnie.extratrees.machines.brewery.BreweryRecipes;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

import java.util.Collections;

public class ControlBreweryProgress extends ControlProgressBase {
	static Texture Brewery = new StandardTexture(0, 69, 34, 39, ExtraTreeTexture.Gui);
	static Texture BreweryOverlay = new StandardTexture(34, 69, 34, 39, ExtraTreeTexture.Gui);

	@Override
	public void onRenderBackground(int guiWidth, int guiHeight) {
		CraftGUI.render.texture(ControlBreweryProgress.Brewery, IPoint.ZERO);
		final BreweryLogic logic = Machine.getInterface(BreweryLogic.class, Window.get(this).getInventory());
		if (logic == null || logic.currentCrafting == null || logic.currentCrafting.inputFluid == null) {
			return;
		}
		final int fermentedHeight = (int) (32.0f * logic.getProgress() / 100.0f);
		CraftGUI.render.limitArea(new IArea(new IPoint(1, 6).add(this.getAbsolutePosition()), new IPoint(32, 32 - fermentedHeight)), guiWidth, guiHeight);
		GL11.glEnable(GL11.GL_SCISSOR_TEST);
		RenderUtil.drawFluid(new IPoint(1, 6), logic.currentCrafting.inputFluid);
		RenderUtil.drawFluid(new IPoint(17, 6), logic.currentCrafting.inputFluid);
		RenderUtil.drawFluid(new IPoint(1, 22), logic.currentCrafting.inputFluid);
		RenderUtil.drawFluid(new IPoint(17, 22), logic.currentCrafting.inputFluid);
		GL11.glDisable(GL11.GL_SCISSOR_TEST);
		CraftGUI.render.limitArea(new IArea(new IPoint(1, 38 - fermentedHeight).add(this.getAbsolutePosition()), new IPoint(32, fermentedHeight)), guiWidth, guiHeight);
		GL11.glEnable(GL11.GL_SCISSOR_TEST);
		RenderUtil.drawFluid(new IPoint(1, 6), BreweryRecipes.getOutput(logic.currentCrafting));
		RenderUtil.drawFluid(new IPoint(17, 6), BreweryRecipes.getOutput(logic.currentCrafting));
		RenderUtil.drawFluid(new IPoint(1, 22), BreweryRecipes.getOutput(logic.currentCrafting));
		RenderUtil.drawFluid(new IPoint(17, 22), BreweryRecipes.getOutput(logic.currentCrafting));
		GL11.glDisable(GL11.GL_SCISSOR_TEST);
		final ItemStackSet stacks = new ItemStackSet();
		Collections.addAll(stacks, logic.currentCrafting.inputGrains);
		stacks.add(logic.currentCrafting.ingredient);
		int x = 1;
		int y = 6;
		for (final ItemStack stack : stacks) {
			RenderUtil.drawItem(new IPoint(x, y), stack);
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

	protected ControlBreweryProgress(final IWidget parent, final int x, final int y) {
		super(parent, x, y, 34, 39);
		this.addAttribute(Attribute.MouseOver);
	}

}
