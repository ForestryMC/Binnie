package binnie.extratrees.craftgui.dictionary;

import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.WidgetAttribute;
import binnie.core.craftgui.geometry.IArea;
import binnie.core.craftgui.geometry.IPoint;
import binnie.core.craftgui.minecraft.Window;
import binnie.core.craftgui.minecraft.control.ControlProgressBase;
import binnie.core.craftgui.resource.Texture;
import binnie.core.craftgui.resource.minecraft.StandardTexture;
import binnie.core.machines.Machine;
import binnie.core.util.ItemStackSet;
import binnie.extratrees.core.ExtraTreeTexture;
import binnie.extratrees.machines.Brewery;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.FluidStack;
import org.lwjgl.opengl.GL11;

import java.util.Collections;

public class ControlBreweryProgress extends ControlProgressBase {
	protected static Texture brewery = new StandardTexture(0, 69, 34, 39, ExtraTreeTexture.Gui);
	protected static Texture breweryOverlay = new StandardTexture(34, 69, 34, 39, ExtraTreeTexture.Gui);

	protected ControlBreweryProgress(IWidget parent, float x, float y) {
		super(parent, x, y, 34.0f, 39.0f);
		addAttribute(WidgetAttribute.MOUSE_OVER);
	}

	@Override
	public void onRenderBackground() {
		CraftGUI.Render.texture(ControlBreweryProgress.brewery, new IPoint(0.0f, 0.0f));
		Brewery.ComponentBreweryLogic logic = Machine.getInterface(Brewery.ComponentBreweryLogic.class, Window.get(this).getInventory());
		if (logic.currentCrafting == null || logic.currentCrafting.currentInput == null) {
			return;
		}

		int fermentedHeight = (int) (32.0f * logic.getProgress() / 100.0f);
		CraftGUI.Render.limitArea(new IArea(new IPoint(1.0f, 6.0f).add(getAbsolutePosition()), new IPoint(32.0f, 32 - fermentedHeight)));
		GL11.glEnable(3089);
		renderFluid(logic.currentCrafting.currentInput, new IPoint(1.0f, 6.0f));
		renderFluid(logic.currentCrafting.currentInput, new IPoint(17.0f, 6.0f));
		renderFluid(logic.currentCrafting.currentInput, new IPoint(1.0f, 22.0f));
		renderFluid(logic.currentCrafting.currentInput, new IPoint(17.0f, 22.0f));
		GL11.glDisable(3089);
		CraftGUI.Render.limitArea(new IArea(new IPoint(1.0f, 38 - fermentedHeight).add(getAbsolutePosition()), new IPoint(32.0f, fermentedHeight)));
		GL11.glEnable(3089);
		renderFluid(binnie.extratrees.machines.Brewery.getOutput(logic.currentCrafting), new IPoint(1.0f, 6.0f));
		renderFluid(binnie.extratrees.machines.Brewery.getOutput(logic.currentCrafting), new IPoint(17.0f, 6.0f));
		renderFluid(binnie.extratrees.machines.Brewery.getOutput(logic.currentCrafting), new IPoint(1.0f, 22.0f));
		renderFluid(binnie.extratrees.machines.Brewery.getOutput(logic.currentCrafting), new IPoint(17.0f, 22.0f));
		GL11.glDisable(3089);

		ItemStackSet stacks = new ItemStackSet();
		Collections.addAll(stacks, logic.currentCrafting.inputs);
		stacks.add(logic.currentCrafting.ingr);
		int x = 1;
		int y = 6;
		for (ItemStack stack : stacks) {
			CraftGUI.Render.item(new IPoint(x, y), stack);
			x += 16;
			if (x > 18) {
				x = 1;
				y += 16;
			}
		}
	}

	@Override
	public void onRenderForeground() {
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
}
