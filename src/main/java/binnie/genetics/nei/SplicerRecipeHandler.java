package binnie.genetics.nei;

import binnie.botany.Botany;
import binnie.core.BinnieCore;
import binnie.core.Mods;
import binnie.core.nei.NEIUtils;
import binnie.core.nei.PositionedFluidTank;
import binnie.core.nei.RecipeHandlerBase;
import binnie.genetics.Genetics;
import binnie.genetics.api.IItemSerum;
import binnie.genetics.item.GeneticLiquid;
import codechicken.lib.gui.GuiDraw;
import codechicken.nei.ItemList;
import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.ISpeciesRoot;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

public class SplicerRecipeHandler extends RecipeHandlerBase {

    private static final List<SplicerRecipe> recipes = new ArrayList<>();

    private void createInoculatorRecipeForNEI(ItemStack dnaManipulable) {
        recipes.add(
                new SplicerRecipe(dnaManipulable, new ItemStack(Genetics.itemSerum, 1, OreDictionary.WILDCARD_VALUE)));
        recipes.add(new SplicerRecipe(
                dnaManipulable, new ItemStack(Genetics.itemSerumArray, 1, OreDictionary.WILDCARD_VALUE)));
    }

    @Override
    public void prepare() {
        if (BinnieCore.isApicultureActive())
            createInoculatorRecipeForNEI(Mods.forestry.stack("beeLarvaeGE", 1, OreDictionary.WILDCARD_VALUE));
        if (BinnieCore.isArboricultureActive())
            createInoculatorRecipeForNEI(Mods.forestry.stack("pollenFertile", 1, OreDictionary.WILDCARD_VALUE));
        if (BinnieCore.isLepidopteryActive())
            createInoculatorRecipeForNEI(Mods.forestry.stack("serumGE", 1, OreDictionary.WILDCARD_VALUE));
        if (BinnieCore.isBotanyActive())
            createInoculatorRecipeForNEI(new ItemStack(Botany.pollen, 1, OreDictionary.WILDCARD_VALUE));
    }

    @Override
    public String getOverlayIdentifier() {
        return "genetics.splicer";
    }

    @Override
    public String getGuiTexture() {
        return "genetics:textures/gui/nei/splicer.png";
    }

    @Override
    public String getRecipeName() {
        return StatCollector.translateToLocal("genetics.machine.advMachine.splicer");
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
        for (SplicerRecipe recipe : recipes) {
            this.arecipes.add(new CachedSplicer(recipe));
        }
    }

    @Override
    public void loadCraftingRecipes(ItemStack result) {
        for (SplicerRecipe recipe : recipes) {
            if (NEIServerUtils.areStacksSameTypeCrafting(recipe.getDnaManipulable(), result)) {
                this.arecipes.add(new CachedSplicer(recipe));
            }
        }
    }

    @Override
    public void loadUsageRecipes(FluidStack ingredient) {
        if (NEIUtils.areFluidsSameType(GeneticLiquid.BacteriaVector.get(0), ingredient)) {
            this.loadAllRecipes();
        }
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient) {
        for (SplicerRecipe recipe : recipes) {
            if (NEIServerUtils.areStacksSameTypeCrafting(recipe.getDnaManipulable(), ingredient)
                    || NEIServerUtils.areStacksSameTypeCrafting(recipe.getSerum(), ingredient)) {
                this.arecipes.add(new CachedSplicer(recipe));
            }
        }
    }

    private static class SplicerRecipe {
        private final ItemStack dnaManipulable;
        private final ItemStack serum;

        public SplicerRecipe(ItemStack dnaManipulable, ItemStack serum) {
            this.dnaManipulable = dnaManipulable;
            this.serum = serum;
        }

        public ItemStack getDnaManipulable() {
            return this.dnaManipulable;
        }

        public ItemStack getSerum() {
            return this.serum;
        }
    }

    public class CachedSplicer extends CachedBaseRecipe {

        public PositionedFluidTank bacteriaTank;
        public PositionedStack serum;
        public PositionedStack input;
        public PositionedStack output;
        public List<PositionedStack> ingredients = new ArrayList<>();

        public CachedSplicer(SplicerRecipe recipe) {
            if (recipe.getSerum() != null && recipe.getDnaManipulable() != null) {
                ISpeciesRoot root1 = AlleleManager.alleleRegistry.getSpeciesRoot(recipe.getDnaManipulable());
                List<ItemStack> serums = new ArrayList<>();

                for (ItemStack serumStack :
                        ItemList.itemMap.get(recipe.getSerum().getItem())) {
                    if (serumStack.getTagCompound() != null) {
                        IItemSerum itemSerum = (IItemSerum) serumStack.getItem();
                        ISpeciesRoot root2 = itemSerum.getSpeciesRoot(serumStack);
                        if (root1 == root2) serums.add(serumStack.copy());
                    }
                }

                this.serum = new PositionedStack(serums, 53, 40);
                this.input = new PositionedStack(recipe.getDnaManipulable(), 53, 14);
                this.output = new PositionedStack(recipe.getDnaManipulable(), 105, 27);

                this.ingredients.add(this.serum);
                this.ingredients.add(this.input);
            }
        }

        @Override
        public List<PositionedStack> getIngredients() {
            this.input.setPermutationToRender(cycleticks / 40 % this.input.items.length);
            this.serum.setPermutationToRender(cycleticks / 40 % this.serum.items.length);
            return this.ingredients;
        }

        @Override
        public PositionedStack getResult() {
            this.output.setPermutationToRender(cycleticks / 40 % this.output.items.length);
            return this.output;
        }

        @Override
        public PositionedFluidTank getFluidTank() {
            return this.bacteriaTank;
        }
    }
}
