package binnie.genetics.nei;

import binnie.core.nei.NEIUtils;
import binnie.core.nei.PositionedFluidTank;
import binnie.core.nei.RecipeHandlerBase;
import binnie.genetics.api.IIncubatorRecipe;
import binnie.genetics.machine.incubator.Incubator;
import codechicken.lib.gui.GuiDraw;
import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.FluidStack;

public class IncubatorRecipeHandler extends RecipeHandlerBase {

    @Override
    public String getOverlayIdentifier() {
        return "genetics.incubator";
    }

    @Override
    public String getGuiTexture() {
        return "genetics:textures/gui/nei/incubator.png";
    }

    @Override
    public String getRecipeName() {
        return StatCollector.translateToLocal("genetics.machine.labMachine.incubator");
    }

    @Override
    public void loadTransferRects() {
        this.addTransferRect(75, 27, 24, 17);
    }

    @Override
    public void drawBackground(int recipe) {
        changeToGuiTexture();
        GuiDraw.drawTexturedModalRect(0, 0, 5, 11, 166, 76);
    }

    @Override
    public void drawExtras(int recipe) {
        this.drawProgressBar(75, 27, 176, 0, 24, 17, 40, 0);
        GuiDraw.drawStringC(StatCollector.translateToLocal("genetics.nei.tip.loss"), 62, 5, 0xFFFFFF);
        GuiDraw.drawStringC(((CachedIncubatorRecipe) arecipes.get(recipe)).lossChance, 62, 15, 0xFFFFFF);
    }

    @Override
    public void loadAllRecipes() {
        for (IIncubatorRecipe recipe : Incubator.RECIPES) {
            this.arecipes.add(new CachedIncubatorRecipe(recipe));
        }
    }

    @Override
    public void loadCraftingRecipes(ItemStack result) {
        for (IIncubatorRecipe recipe : Incubator.RECIPES) {
            if (NEIServerUtils.areStacksSameTypeCrafting(recipe.getExpectedOutput(), result)) {
                this.arecipes.add(new CachedIncubatorRecipe(recipe));
            }
        }
    }

    @Override
    public void loadCraftingRecipes(FluidStack result) {
        for (IIncubatorRecipe recipe : Incubator.RECIPES) {
            if (NEIUtils.areFluidsSameType(recipe.getOutput(), result)) {
                this.arecipes.add(new CachedIncubatorRecipe(recipe));
            }
        }
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient) {
        for (IIncubatorRecipe recipe : Incubator.RECIPES) {
            if (NEIServerUtils.areStacksSameTypeCrafting(recipe.getInputStack(), ingredient)) {
                this.arecipes.add(new CachedIncubatorRecipe(recipe));
            }
        }
    }

    @Override
    public void loadUsageRecipes(FluidStack ingredient) {
        for (IIncubatorRecipe recipe : Incubator.RECIPES) {
            if (NEIUtils.areFluidsSameType(recipe.getInput(), ingredient)) {
                this.arecipes.add(new CachedIncubatorRecipe(recipe));
            }
        }
    }

    public class CachedIncubatorRecipe extends CachedBaseRecipe {
        public List<PositionedFluidTank> tanks = new ArrayList<>();
        public PositionedStack input;
        public PositionedStack output;
        public String lossChance;
        public String tickChance;

        public CachedIncubatorRecipe(IIncubatorRecipe recipe) {
            if (recipe.getInput() != null) {
                FluidStack fluidStack = recipe.getInput();

                if (fluidStack.amount == 0) {
                    this.tanks.add(
                            new PositionedFluidTank(
                                    new FluidStack(fluidStack.getFluid(), 1), 100, new Rectangle(28, 6, 16, 58)) {
                                @Override
                                public List<String> handleTooltip(List<String> currenttip) {
                                    List<String> tip = super.handleTooltip(currenttip);
                                    tip.add(StatCollector.translateToLocal("genetics.nei.tip.noConsume"));
                                    return tip;
                                }
                            });
                } else this.tanks.add(new PositionedFluidTank(fluidStack, 100, new Rectangle(28, 6, 16, 58)));
            }

            if (recipe.getOutput() != null) {
                this.tanks.add(new PositionedFluidTank(recipe.getOutput(), 100, new Rectangle(130, 6, 16, 58)));
            }

            if (recipe.getInputStack() != null) {
                this.input = new PositionedStack(recipe.getInputStack(), 53, 27);
            }

            if (recipe.getExpectedOutput() != null) {
                this.output = new PositionedStack(recipe.getExpectedOutput(), 105, 27);
            }

            this.lossChance = recipe.getLossChance() * 100 + "%";
        }

        @Override
        public List<PositionedFluidTank> getFluidTanks() {
            return this.tanks;
        }

        @Override
        public PositionedStack getIngredient() {
            return this.input;
        }

        @Override
        public PositionedStack getResult() {
            return this.output;
        }
    }
}
