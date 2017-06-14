package binnie.genetics.integration.jei.isolator;

import binnie.genetics.Genetics;
import binnie.genetics.integration.jei.GeneticsJeiPlugin;
import binnie.genetics.integration.jei.RecipeUids;
import binnie.genetics.machine.isolator.Isolator;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.awt.Color;

public class IsolatorRecipeCategory extends BlankRecipeCategory<IsolatorRecipeWrapper> {
	private final IDrawableAnimated arrowAnimated;

	public IsolatorRecipeCategory() {
		this.arrowAnimated = GeneticsJeiPlugin.drawables.createArrowAnimated(56);
	}

	@Override
	public String getUid() {
		return RecipeUids.ISOLATOR;
	}

	@Override
	public String getTitle() {
		return "Gene Isolation";
	}

	@Override
	public String getModName() {
		return Genetics.instance.getModID();
	}

	@Override
	public IDrawable getBackground() {
		return GeneticsJeiPlugin.guiHelper.createBlankDrawable(130, 60);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void drawExtras(Minecraft minecraft) {
		IDrawable arrow = GeneticsJeiPlugin.drawables.getArrow();
		arrow.draw(minecraft, 69, 25);
		arrowAnimated.draw(minecraft, 69, 25);

		String randomText = "(Random)";
		int textWidth = minecraft.fontRendererObj.getStringWidth(randomText);
		minecraft.fontRendererObj.drawString(randomText, 102 - (textWidth / 2), 45, Color.gray.getRGB());
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, IsolatorRecipeWrapper recipeWrapper, IIngredients ingredients) {
		IDrawable tank = GeneticsJeiPlugin.drawables.getTank();
		IDrawable tankOverlay = GeneticsJeiPlugin.drawables.getTankOverlay();
		IGuiFluidStackGroup fluidStacks = recipeLayout.getFluidStacks();
		fluidStacks.init(Isolator.TANK_ETHANOL, true, 1, 1, 16, 58, 100, false, tankOverlay);
		fluidStacks.setBackground(Isolator.TANK_ETHANOL, tank);
		fluidStacks.set(ingredients);

		IGuiItemStackGroup itemStacks = recipeLayout.getItemStacks();
		itemStacks.init(0, true, 22, 0);
		itemStacks.init(1, true, 42, 21);
		itemStacks.init(2, true, 22, 42);
		itemStacks.init(3, false, 92, 21);
		itemStacks.set(ingredients);

		IDrawable slot = GeneticsJeiPlugin.guiHelper.getSlotDrawable();
		for (int i = 0; i <= 3; i++) {
			itemStacks.setBackground(i, slot);
		}
	}
}
