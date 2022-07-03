package binnie.genetics.nei;

import binnie.core.nei.NEIUtils;
import binnie.core.nei.PositionedFluidTank;
import binnie.core.nei.PositionedStackAdv;
import binnie.core.nei.RecipeHandlerBase;
import binnie.genetics.Genetics;
import binnie.genetics.api.IItemSerum;
import binnie.genetics.genetics.Engineering;
import binnie.genetics.item.GeneticLiquid;
import binnie.genetics.item.ItemSequence;
import codechicken.lib.gui.GuiDraw;
import codechicken.nei.ItemList;
import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

public class PolymeriserRecipeHandler extends RecipeHandlerBase {

    private static final List<PolymeriserRecipe> recipes = new ArrayList<>();

    @Override
    public void prepare() {
        recipes.add(new PolymeriserRecipe(new ItemStack(Genetics.itemSequencer, 1, OreDictionary.WILDCARD_VALUE)));
        recipes.add(new PolymeriserRecipe(new ItemStack(Genetics.itemSerum, 1, OreDictionary.WILDCARD_VALUE)));
        recipes.add(new PolymeriserRecipe(new ItemStack(Genetics.itemSerumArray, 1, OreDictionary.WILDCARD_VALUE)));
    }

    @Override
    public String getOverlayIdentifier() {
        return "genetics.polymeriser";
    }

    @Override
    public String getGuiTexture() {
        return "genetics:textures/gui/nei/polymeriser.png";
    }

    @Override
    public String getRecipeName() {
        return StatCollector.translateToLocal("genetics.machine.machine.polymeriser");
    }

    @Override
    public void loadTransferRects() {
        this.addTransferRect(95, 27, 24, 17);
    }

    @Override
    public void drawBackground(int recipe) {
        changeToGuiTexture();
        GuiDraw.drawTexturedModalRect(0, 0, 5, 11, 166, 74);
    }

    @Override
    public void drawExtras(int recipe) {
        this.drawProgressBar(95, 27, 176, 0, 24, 17, 40, 0);
    }

    @Override
    public void loadAllRecipes() {
        for (PolymeriserRecipe recipe : recipes) {
            this.arecipes.add(new CachedPolymeriserRecipe(recipe));
        }
    }

    @Override
    public void loadCraftingRecipes(ItemStack result) {
        for (PolymeriserRecipe recipe : recipes) {
            if (NEIServerUtils.areStacksSameTypeCrafting(recipe.getOutput(), result)) {
                this.arecipes.add(new CachedPolymeriserRecipe(recipe));
            }
        }
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient) {
        if (NEIServerUtils.areStacksSameTypeCrafting(new ItemStack(Items.gold_nugget), ingredient)) {
            this.loadAllRecipes();
        } else {
            for (PolymeriserRecipe recipe : recipes) {
                if (NEIServerUtils.areStacksSameTypeCrafting(recipe.getSerum(), ingredient)) {
                    this.arecipes.add(new CachedPolymeriserRecipe(recipe));
                }
            }
        }
    }

    @Override
    public void loadUsageRecipes(FluidStack ingredient) {
        if (NEIUtils.areFluidsSameType(GeneticLiquid.BacteriaPoly.get(0), ingredient)
                || NEIUtils.areFluidsSameType(GeneticLiquid.RawDNA.get(0), ingredient)) {
            this.loadAllRecipes();
        }
    }

    private static class PolymeriserRecipe {

        private final ItemStack serum;

        public PolymeriserRecipe(ItemStack serum) {
            this.serum = serum;
        }

        public ItemStack getSerum() {
            return this.serum;
        }

        public ItemStack getOutput() {
            return this.serum;
        }
    }

    public class CachedPolymeriserRecipe extends CachedBaseRecipe {

        public PositionedStack input;
        public PositionedStack output;
        public PositionedStack goldNugget;
        public PositionedFluidTank bacteriaPoly;
        public PositionedFluidTank rawDNA;
        public List<PositionedStack> ingredients = new ArrayList<>();
        public List<PositionedFluidTank> tanks = new ArrayList<>();

        public CachedPolymeriserRecipe(PolymeriserRecipe recipe) {
            if (recipe.getSerum() != null) {

                List<ItemStack> inputs = new ArrayList<>();
                List<ItemStack> outputs = new ArrayList<>();
                List<FluidStack> bacteriaPolyTanks = new ArrayList<>();
                List<FluidStack> rawDNATanks = new ArrayList<>();

                for (ItemStack itemStack :
                        ItemList.itemMap.get(recipe.getSerum().getItem())) {
                    if (itemStack.getTagCompound() != null) {
                        ItemStack temp = itemStack.copy();
                        if (itemStack.getItem() instanceof IItemSerum) {
                            temp.setItemDamage(itemStack.getMaxDamage());
                        }

                        inputs.add(temp);
                        int bacteriaPolyAmount = 10
                                * temp.getItemDamage()
                                * (temp.getTagCompound() == null ? 1 : Engineering.getGenes(temp).length);
                        bacteriaPolyTanks.add(GeneticLiquid.BacteriaPoly.get(bacteriaPolyAmount));
                        rawDNATanks.add(GeneticLiquid.RawDNA.get(bacteriaPolyAmount * 5));
                    }
                }

                this.input = new PositionedStack(inputs, 73, 27);
                this.goldNugget = new PositionedStackAdv(new ItemStack(Items.gold_nugget), 73, 48)
                        .addToTooltip(StatCollector.translateToLocalFormatted("genetics.nei.tip.processSpeed", 5));
                this.ingredients.add(this.input);
                this.ingredients.add(this.goldNugget);

                this.bacteriaPoly =
                        new PositionedFluidTank(bacteriaPolyTanks, 10000, new Rectangle(27, 6, 16, 58), null, null);
                this.rawDNA = new PositionedFluidTank(rawDNATanks, 20000, new Rectangle(48, 6, 16, 58), null, null);
                this.tanks.add(this.bacteriaPoly);
                this.tanks.add(this.rawDNA);

                for (ItemStack itemStack :
                        ItemList.itemMap.get(recipe.getOutput().getItem())) {
                    if (itemStack.getTagCompound() != null) {
                        ItemStack temp = itemStack.copy();
                        if (recipe.getOutput().getItem() instanceof ItemSequence) {
                            temp.setItemDamage(0);
                        }
                        outputs.add(temp);
                    }
                }
                this.output = new PositionedStack(outputs, 125, 27);
            }
        }

        @Override
        public List<PositionedStack> getIngredients() {
            this.input.setPermutationToRender(cycleticks / 40 % this.input.items.length);
            this.bacteriaPoly.setPermutationToRender(cycleticks / 40 % this.bacteriaPoly.tanks.length);
            this.rawDNA.setPermutationToRender(cycleticks / 40 % this.rawDNA.tanks.length);
            return this.ingredients;
        }

        @Override
        public PositionedStack getResult() {
            this.output.setPermutationToRender(cycleticks / 40 % this.output.items.length);
            return this.output;
        }

        @Override
        public List<PositionedFluidTank> getFluidTanks() {
            return this.tanks;
        }
    }
}
