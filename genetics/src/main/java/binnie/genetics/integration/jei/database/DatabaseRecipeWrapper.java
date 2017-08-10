package binnie.genetics.integration.jei.database;

import java.awt.Color;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.genetics.Genetics;
import binnie.genetics.integration.jei.GeneticsJeiPlugin;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.ingredients.IIngredients;

public class DatabaseRecipeWrapper implements IRecipeWrapper {
	private final ItemStack input;
	private final List<ItemStack> outputs;
	private final IDrawableAnimated arrowAnimated;

	public DatabaseRecipeWrapper(ItemStack input, List<ItemStack> outputs) {
		this.input = input;
		this.outputs = outputs;

		this.arrowAnimated = GeneticsJeiPlugin.drawables.createArrowAnimated(20);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
		IDrawable arrow = GeneticsJeiPlugin.drawables.getArrow();
		arrow.draw(minecraft, 60, 4);
		arrowAnimated.draw(minecraft, 60, 4);

		String instructions = "Open the database and pick up an empty serum. Click the serum on a Sequenced gene to apply.";
		minecraft.fontRenderer.drawSplitString(instructions, 0, 40, recipeWidth, Color.gray.getRGB());
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInputs(ItemStack.class, Arrays.asList(
			input,
			new ItemStack(Genetics.items().database)
		));

		ingredients.setOutputLists(ItemStack.class, Collections.singletonList(outputs));
	}
}
