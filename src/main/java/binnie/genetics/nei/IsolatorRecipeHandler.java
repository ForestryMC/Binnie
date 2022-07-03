package binnie.genetics.nei;

import binnie.Binnie;
import binnie.botany.Botany;
import binnie.core.BinnieCore;
import binnie.core.Mods;
import binnie.core.genetics.Gene;
import binnie.core.nei.NEIUtils;
import binnie.core.nei.PositionedFluidTank;
import binnie.core.nei.PositionedStackAdv;
import binnie.core.nei.RecipeHandlerBase;
import binnie.genetics.Genetics;
import binnie.genetics.item.GeneticsItems;
import binnie.genetics.item.ItemSequence;
import codechicken.lib.gui.GuiDraw;
import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IChromosomeType;
import forestry.api.genetics.ISpeciesRoot;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

public class IsolatorRecipeHandler extends RecipeHandlerBase {

    private static final List<IsolatorRecipe> recipes = new ArrayList<>();

    @Override
    public void prepare() {
        if (BinnieCore.isApicultureActive()) {
            recipes.add(new IsolatorRecipe(Mods.forestry.stack("beeDroneGE", 1, OreDictionary.WILDCARD_VALUE)));
            recipes.add(new IsolatorRecipe(Mods.forestry.stack("beePrincessGE", 1, OreDictionary.WILDCARD_VALUE)));
            recipes.add(new IsolatorRecipe(Mods.forestry.stack("beeQueenGE", 1, OreDictionary.WILDCARD_VALUE)));
            recipes.add(new IsolatorRecipe(Mods.forestry.stack("beeLarvaeGE", 1, OreDictionary.WILDCARD_VALUE)));
        }
        if (BinnieCore.isArboricultureActive()) {
            recipes.add(new IsolatorRecipe(Mods.forestry.stack("sapling", 1, OreDictionary.WILDCARD_VALUE)));
            recipes.add(new IsolatorRecipe(Mods.forestry.stack("pollenFertile", 1, OreDictionary.WILDCARD_VALUE)));
        }
        if (BinnieCore.isLepidopteryActive()) {
            recipes.add(new IsolatorRecipe(Mods.forestry.stack("butterflyGE", 1, OreDictionary.WILDCARD_VALUE)));
            recipes.add(new IsolatorRecipe(Mods.forestry.stack("caterpillarGE", 1, OreDictionary.WILDCARD_VALUE)));
            recipes.add(new IsolatorRecipe(Mods.forestry.stack("serumGE", 1, OreDictionary.WILDCARD_VALUE)));
        }
        if (BinnieCore.isBotanyActive()) {
            recipes.add(new IsolatorRecipe(new ItemStack(Botany.flowerItem, 1, OreDictionary.WILDCARD_VALUE)));
            recipes.add(new IsolatorRecipe(new ItemStack(Botany.seed, 1, OreDictionary.WILDCARD_VALUE)));
            recipes.add(new IsolatorRecipe(new ItemStack(Botany.pollen, 1, OreDictionary.WILDCARD_VALUE)));
        }
    }

    @Override
    public String getOverlayIdentifier() {
        return "genetics.isolator";
    }

    @Override
    public String getGuiTexture() {
        return "genetics:textures/gui/nei/isolator.png";
    }

    @Override
    public String getRecipeName() {
        return StatCollector.translateToLocal("genetics.machine.machine.isolator");
    }

    @Override
    public void loadTransferRects() {
        this.addTransferRect(87, 27, 24, 17);
    }

    @Override
    public void drawBackground(int recipe) {
        changeToGuiTexture();
        GuiDraw.drawTexturedModalRect(0, 0, 5, 11, 166, 74);
    }

    @Override
    public void drawExtras(int recipe) {
        this.drawProgressBar(87, 27, 176, 0, 24, 17, 40, 0);
    }

    @Override
    public void loadAllRecipes() {
        for (IsolatorRecipe recipe : recipes) {
            this.arecipes.add(new CachedIsolatorRecipe(recipe));
        }
    }

    @Override
    public void loadCraftingRecipes(ItemStack result) {
        if (NEIServerUtils.areStacksSameTypeCrafting(new ItemStack(Genetics.itemSequencer), result)) {
            for (IsolatorRecipe recipe : recipes) {
                this.arecipes.add(new CachedIsolatorRecipe(recipe));
            }
        }
    }

    @Override
    public void loadUsageRecipes(FluidStack ingredient) {
        if (NEIUtils.areFluidsSameType(Binnie.Liquid.getLiquidStack("bioethanol", 0), ingredient)) {
            this.loadAllRecipes();
        }
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient) {
        if (NEIServerUtils.areStacksSameTypeCrafting(GeneticsItems.EmptySequencer.get(1), ingredient)
                || NEIServerUtils.areStacksSameTypeCrafting(GeneticsItems.Enzyme.get(1), ingredient)) {
            this.loadAllRecipes();
        } else {
            for (IsolatorRecipe recipe : recipes) {
                if (NEIServerUtils.areStacksSameTypeCrafting(recipe.getInput(), ingredient)) {
                    this.arecipes.add(new CachedIsolatorRecipe(recipe));
                }
            }
        }
    }

    private static class IsolatorRecipe {

        private final ItemStack input;

        public IsolatorRecipe(ItemStack input) {
            this.input = input;
        }

        public ItemStack getInput() {
            return this.input;
        }

        public ItemStack getOutputSequencer() {
            ISpeciesRoot root = AlleleManager.alleleRegistry.getSpeciesRoot(this.input);

            IChromosomeType speciesChromosomeType = root.getKaryotypeKey();
            IAllele[] defaultTemplate = root.getDefaultTemplate();
            IAllele species = defaultTemplate[speciesChromosomeType.ordinal()];

            return ItemSequence.create(new Gene(species, speciesChromosomeType, root));
        }

        public FluidStack getEthanol() {
            return Binnie.Liquid.getLiquidStack("bioethanol", 10);
        }

        public ItemStack getEnzyme() {
            return GeneticsItems.Enzyme.get(1);
        }

        public ItemStack getEmptySequencer() {
            return GeneticsItems.EmptySequencer.get(1);
        }
    }

    public class CachedIsolatorRecipe extends CachedBaseRecipe {

        public PositionedFluidTank ethanolTank;
        public PositionedStack emptySequencer;
        public PositionedStack input;
        public PositionedStack enzyme;
        public PositionedStack output;
        public List<PositionedStack> ingredients = new ArrayList<>();

        public CachedIsolatorRecipe(IsolatorRecipe recipe) {
            this.ethanolTank = new PositionedFluidTank(recipe.getEthanol(), 100, new Rectangle(40, 6, 16, 58));
            this.emptySequencer = new PositionedStack(recipe.getEmptySequencer(), 65, 27);
            this.enzyme = new PositionedStack(recipe.getEnzyme(), 65, 48);

            if (recipe.getInput() != null) {
                this.input = new PositionedStackAdv(recipe.getInput(), 65, 6)
                        .addToTooltip(StatCollector.translateToLocal("genetics.nei.tip.loss") + " 5%");
                this.output = new PositionedStack(recipe.getOutputSequencer(), 117, 27);

                this.ingredients.add(this.emptySequencer);
                this.ingredients.add(this.input);
                this.ingredients.add(this.enzyme);
            }
        }

        @Override
        public List<PositionedStack> getIngredients() {
            return getCycledIngredients(cycleticks / 40, this.ingredients);
        }

        @Override
        public PositionedStack getResult() {
            this.output.setPermutationToRender(cycleticks / 40 % this.output.items.length);
            return this.output;
        }

        @Override
        public PositionedFluidTank getFluidTank() {
            return this.ethanolTank;
        }
    }
}
