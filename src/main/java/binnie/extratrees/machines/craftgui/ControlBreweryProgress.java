package binnie.extratrees.machines.craftgui;

import binnie.core.craftgui.Attribute;
import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.geometry.Area;
import binnie.core.craftgui.geometry.Point;
import binnie.core.craftgui.minecraft.Window;
import binnie.core.craftgui.minecraft.control.ControlProgressBase;
import binnie.core.craftgui.renderer.RenderUtil;
import binnie.core.craftgui.resource.Texture;
import binnie.core.craftgui.resource.minecraft.StandardTexture;
import binnie.core.machines.Machine;
import binnie.core.util.ItemStackSet;
import binnie.extratrees.core.ExtraTreeTexture;
import binnie.extratrees.machines.brewery.BreweryLogic;
import binnie.extratrees.machines.brewery.BreweryRecipes;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import java.util.Collections;

public class ControlBreweryProgress extends ControlProgressBase {
	static Texture Brewery = new StandardTexture(0, 69, 34, 39, ExtraTreeTexture.Gui);
	static Texture BreweryOverlay = new StandardTexture(34, 69, 34, 39, ExtraTreeTexture.Gui);

	protected ControlBreweryProgress(final IWidget parent, final int x, final int y) {
		super(parent, x, y, 34, 39);
		this.addAttribute(Attribute.MouseOver);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onRenderBackground(int guiWidth, int guiHeight) {
		CraftGUI.render.texture(ControlBreweryProgress.Brewery, Point.ZERO);
		final BreweryLogic logic = Machine.getInterface(BreweryLogic.class, Window.get(this).getInventory());
		if (logic == null || logic.currentCrafting == null || logic.currentCrafting.inputFluid == null) {
			return;
		}
		final int fermentedHeight = (int) (32.0f * logic.getProgress() / 100.0f);
		CraftGUI.render.limitArea(new Area(new Point(1, 6).add(this.getAbsolutePosition()), new Point(32, 32 - fermentedHeight)), guiWidth, guiHeight);
		GL11.glEnable(GL11.GL_SCISSOR_TEST);
		RenderUtil.drawFluid(new Point(1, 6), logic.currentCrafting.inputFluid);
		RenderUtil.drawFluid(new Point(17, 6), logic.currentCrafting.inputFluid);
		RenderUtil.drawFluid(new Point(1, 22), logic.currentCrafting.inputFluid);
		RenderUtil.drawFluid(new Point(17, 22), logic.currentCrafting.inputFluid);
		GL11.glDisable(GL11.GL_SCISSOR_TEST);
		CraftGUI.render.limitArea(new Area(new Point(1, 38 - fermentedHeight).add(this.getAbsolutePosition()), new Point(32, fermentedHeight)), guiWidth, guiHeight);
		GL11.glEnable(GL11.GL_SCISSOR_TEST);
		RenderUtil.drawFluid(new Point(1, 6), BreweryRecipes.getOutput(logic.currentCrafting));
		RenderUtil.drawFluid(new Point(17, 6), BreweryRecipes.getOutput(logic.currentCrafting));
		RenderUtil.drawFluid(new Point(1, 22), BreweryRecipes.getOutput(logic.currentCrafting));
		RenderUtil.drawFluid(new Point(17, 22), BreweryRecipes.getOutput(logic.currentCrafting));
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
