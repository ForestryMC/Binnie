package binnie.extratrees.nei;

import binnie.Binnie;
import binnie.core.nei.NEIUtils;
import binnie.core.nei.PositionedFluidTank;
import binnie.core.nei.RecipeHandlerBase;
import binnie.extratrees.item.ExtraTreeItems;
import binnie.extratrees.machines.lumbermill.Lumbermill;
import codechicken.lib.gui.GuiDraw;
import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.FluidStack;

public class NEIHandlerLumbermill extends RecipeHandlerBase {

    @Override
    public void prepare() {
        if (Lumbermill.recipes.isEmpty()) Lumbermill.calculateLumbermillProducts();
    }

    @Override
    public String getOverlayIdentifier() {
        return "extratrees.lumbermill";
    }

    @Override
    public String getGuiTexture() {
        return "extratrees:textures/gui/nei/lumbermill.png";
    }

    @Override
    public String getRecipeName() {
        return StatCollector.translateToLocal("extratrees.machine.machine.lumbermill");
    }

    @Override
    public void loadTransferRects() {
        this.addTransferRect(75, 27, 24, 17);
    }

    @Override
    public void drawBackground(int recipe) {
        changeToGuiTexture();
        GuiDraw.drawTexturedModalRect(0, 0, 5, 11, 166, 74);
    }

    @Override
    public void drawExtras(int recipe) {
        this.drawProgressBar(75, 27, 176, 0, 24, 17, 40, 0);
    }

    @Override
    public void loadAllRecipes() {
        Lumbermill.recipes.forEach(
                (wood, plank) -> this.arecipes.add(new CachedLumbermillRecipe(wood, Lumbermill.getPlankProduct(wood))));
    }

    @Override
    public void loadCraftingRecipes(ItemStack result) {
        if (NEIServerUtils.areStacksSameTypeCrafting(ExtraTreeItems.Sawdust.get(1), result)
                || (NEIServerUtils.areStacksSameTypeCrafting(ExtraTreeItems.Bark.get(1), result))) {
            this.loadAllRecipes();
        } else {
            for (Map.Entry<ItemStack, ItemStack> entry : Lumbermill.recipes.entrySet()) {
                if (NEIServerUtils.areStacksSameTypeCrafting(entry.getValue(), result)) {
                    this.arecipes.add(
                            new CachedLumbermillRecipe(entry.getKey(), Lumbermill.getPlankProduct(entry.getKey())));
                }
            }
        }
    }

    @Override
    public void loadUsageRecipes(FluidStack ingredient) {
        if (NEIUtils.areFluidsSameType(Binnie.Liquid.getLiquidStack("water", 0), ingredient)) {
            this.loadAllRecipes();
        }
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient) {
        for (Map.Entry<ItemStack, ItemStack> entry : Lumbermill.recipes.entrySet()) {
            if (NEIServerUtils.areStacksSameTypeCrafting(entry.getKey(), ingredient)) {
                this.arecipes.add(
                        new CachedLumbermillRecipe(entry.getKey(), Lumbermill.getPlankProduct(entry.getKey())));
            }
        }
    }

    public class CachedLumbermillRecipe extends CachedBaseRecipe {

        public PositionedFluidTank waterTank =
                new PositionedFluidTank(Binnie.Liquid.getLiquidStack("water", 300), 1000, new Rectangle(28, 6, 16, 58));
        public PositionedStack bark = new PositionedStack(ExtraTreeItems.Sawdust.get(1), 123, 6);
        public PositionedStack sawDust = new PositionedStack(ExtraTreeItems.Bark.get(1), 123, 48);
        public PositionedStack wood;
        public PositionedStack plank;
        public List<PositionedStack> results = new ArrayList<>();

        public CachedLumbermillRecipe(ItemStack wood, ItemStack plank) {
            this.plank = new PositionedStack(plank, 105, 27);
            this.wood = new PositionedStack(wood, 53, 27);

            this.results.add(this.plank);
            this.results.add(this.bark);
            this.results.add(this.sawDust);
        }

        @Override
        public PositionedFluidTank getFluidTank() {
            return this.waterTank;
        }

        @Override
        public PositionedStack getIngredient() {
            return this.wood;
        }

        @Override
        public List<PositionedStack> getOtherStacks() {
            return this.results;
        }

        @Override
        public PositionedStack getResult() {
            return null;
        }
    }
}
