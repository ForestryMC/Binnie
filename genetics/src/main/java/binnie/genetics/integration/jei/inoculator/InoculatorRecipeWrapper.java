package binnie.genetics.integration.jei.inoculator;

import javax.annotation.Nullable;
import java.awt.Color;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Map;

import org.apache.commons.lang3.time.DurationFormatUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.fluids.FluidStack;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.genetics.item.GeneticLiquid;
import binnie.genetics.machine.inoculator.InoculatorLogic;
import binnie.genetics.machine.splicer.SplicerLogic;

import mezz.jei.api.gui.IGuiIngredient;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;

public class InoculatorRecipeWrapper implements IRecipeWrapper {
	private static final FluidStack BACTERIA_VECTOR = GeneticLiquid.BacteriaVector.get(InoculatorLogic.BACTERIA_PER_PROCESS);

	private final ItemStack inputSerum;
	private final ItemStack wildcardTarget;
	private final boolean splicer;
	@Nullable
	private Map<Integer, ? extends IGuiIngredient<ItemStack>> currentIngredients;

	public InoculatorRecipeWrapper(ItemStack inputSerum, ItemStack wildcardTarget) {
		this(inputSerum, wildcardTarget, false);
	}

	protected InoculatorRecipeWrapper(ItemStack inputSerum, ItemStack wildcardTarget, boolean splicer) {
		this.inputSerum = inputSerum;
		this.wildcardTarget = wildcardTarget;
		this.splicer = splicer;
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInputs(ItemStack.class, Arrays.asList(
			inputSerum,
			wildcardTarget
		));

		ingredients.setOutput(ItemStack.class, wildcardTarget);

		if (!splicer) {
			ingredients.setInput(FluidStack.class, BACTERIA_VECTOR);
		}
	}

	public void setCurrentIngredients(Map<Integer, ? extends IGuiIngredient<ItemStack>> currentIngredients) {
		this.currentIngredients = currentIngredients;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
		if (currentIngredients == null) {
			return;
		}

		ItemStack inputSerum = null;
		ItemStack targetStack = null;

		IGuiIngredient<ItemStack> guiIngredient = currentIngredients.get(0);
		if (guiIngredient != null) {
			inputSerum = guiIngredient.getDisplayedIngredient();
		}
		guiIngredient = currentIngredients.get(1);
		if (guiIngredient != null) {
			targetStack = guiIngredient.getDisplayedIngredient();
		}

		if (inputSerum != null && targetStack != null) {
			int power;
			int processTicks;
			if (splicer) {
				int numGenes = SplicerLogic.getGenesToUse(inputSerum, targetStack);
				power = SplicerLogic.getProcessEnergy(numGenes);
				processTicks = SplicerLogic.getProcessLength(numGenes);
			} else {
				power = InoculatorLogic.getProcessBaseEnergy(inputSerum);
				processTicks = InoculatorLogic.getProcessLength(inputSerum);
			}

			NumberFormat decimalFormat = NumberFormat.getIntegerInstance(MinecraftForgeClient.getLocale());
			String powerString = decimalFormat.format(power) + " RF";
			int xPower = 15 + (recipeWidth - minecraft.fontRenderer.getStringWidth(powerString)) / 2;
			minecraft.fontRenderer.drawString(powerString, xPower, 5, Color.gray.getRGB());

			// todo: find a localized duration formatter
			String processTime = DurationFormatUtils.formatDurationWords(processTicks * 1000 / 20, true, true);
			int xTime = 15 + (recipeWidth - minecraft.fontRenderer.getStringWidth(processTime)) / 2;
			minecraft.fontRenderer.drawString(processTime, xTime, 45, Color.gray.getRGB());
		}
	}

	public ItemStack getInputSerum() {
		return inputSerum;
	}

	public ItemStack getWildcardTarget() {
		return wildcardTarget;
	}
}
