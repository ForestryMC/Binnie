package binnie.genetics.nei;

import binnie.Binnie;
import binnie.botany.Botany;
import binnie.core.BinnieCore;
import binnie.core.Mods;
import binnie.core.nei.NEIUtils;
import binnie.core.nei.PositionedFluidTank;
import binnie.core.nei.RecipeHandlerBase;
import binnie.genetics.item.GeneticLiquid;
import binnie.genetics.item.GeneticsItems;
import codechicken.lib.gui.GuiDraw;
import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import forestry.api.apiculture.EnumBeeType;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

public class GenepoolRecipeHandler extends RecipeHandlerBase {

    private static final List<GenepoolRecipe> recipes = new ArrayList<>();

    @Override
    public void prepare() {
        if (BinnieCore.isApicultureActive()) {
            recipes.add(new GenepoolRecipe(Mods.forestry.stack("beeDroneGE", 1, OreDictionary.WILDCARD_VALUE)));
            recipes.add(new GenepoolRecipe(Mods.forestry.stack("beePrincessGE", 1, OreDictionary.WILDCARD_VALUE)));
            recipes.add(new GenepoolRecipe(Mods.forestry.stack("beeQueenGE", 1, OreDictionary.WILDCARD_VALUE)));
            recipes.add(new GenepoolRecipe(Mods.forestry.stack("beeLarvaeGE", 1, OreDictionary.WILDCARD_VALUE)));
        }
        if (BinnieCore.isArboricultureActive()) {
            recipes.add(new GenepoolRecipe(Mods.forestry.stack("sapling", 1, OreDictionary.WILDCARD_VALUE)));
            recipes.add(new GenepoolRecipe(Mods.forestry.stack("pollenFertile", 1, OreDictionary.WILDCARD_VALUE)));
        }
        if (BinnieCore.isLepidopteryActive()) {
            recipes.add(new GenepoolRecipe(Mods.forestry.stack("butterflyGE", 1, OreDictionary.WILDCARD_VALUE)));
            recipes.add(new GenepoolRecipe(Mods.forestry.stack("caterpillarGE", 1, OreDictionary.WILDCARD_VALUE)));
            recipes.add(new GenepoolRecipe(Mods.forestry.stack("serumGE", 1, OreDictionary.WILDCARD_VALUE)));
        }
        if (BinnieCore.isBotanyActive()) {
            recipes.add(new GenepoolRecipe(new ItemStack(Botany.flowerItem, 1, OreDictionary.WILDCARD_VALUE)));
            recipes.add(new GenepoolRecipe(new ItemStack(Botany.seed, 1, OreDictionary.WILDCARD_VALUE)));
            recipes.add(new GenepoolRecipe(new ItemStack(Botany.pollen, 1, OreDictionary.WILDCARD_VALUE)));
        }
    }

    @Override
    public String getOverlayIdentifier() {
        return "genetics.genepool";
    }

    @Override
    public String getGuiTexture() {
        return "genetics:textures/gui/nei/genepool.png";
    }

    @Override
    public String getRecipeName() {
        return StatCollector.translateToLocal("genetics.machine.labMachine.genepool");
    }

    @Override
    public void loadTransferRects() {
        this.addTransferRect(75, 38, 24, 17);
    }

    @Override
    public void drawBackground(int recipe) {
        changeToGuiTexture();
        GuiDraw.drawTexturedModalRect(0, 0, 5, 11, 166, 74);
    }

    @Override
    public void drawExtras(int recipe) {
        this.drawProgressBar(75, 38, 176, 0, 24, 17, 40, 0);
    }

    @Override
    public void loadAllRecipes() {
        for (GenepoolRecipe recipe : recipes) {
            this.arecipes.add(new CachedGenepoolRecipe(recipe));
        }
    }

    @Override
    public void loadCraftingRecipes(FluidStack result) {
        if (NEIUtils.areFluidsSameType(GeneticLiquid.RawDNA.get(0), result)) {
            this.loadAllRecipes();
        }
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient) {
        if (NEIServerUtils.areStacksSameTypeCrafting(GeneticsItems.Enzyme.get(1), ingredient)) {
            this.loadAllRecipes();
        } else {
            for (GenepoolRecipe recipe : recipes) {
                if (NEIServerUtils.areStacksSameTypeCrafting(recipe.getInput(), ingredient)) {
                    this.arecipes.add(new CachedGenepoolRecipe(recipe));
                }
            }
        }
    }

    @Override
    public void loadUsageRecipes(FluidStack ingredient) {
        if (NEIUtils.areFluidsSameType(Binnie.Liquid.getLiquidStack("bioethanol", 0), ingredient)) {
            this.loadAllRecipes();
        }
    }

    private static class GenepoolRecipe {
        private final ItemStack inputStack;

        public GenepoolRecipe(ItemStack inputStack) {
            this.inputStack = inputStack;
        }

        public FluidStack getRawDNA() {
            int amount;
            EnumBeeType type = Binnie.Genetics.getBeeRoot().getType(this.inputStack);

            switch (type) {
                case QUEEN:
                    amount = 50;
                    break;
                case PRINCESS:
                    amount = 30;
                    break;
                default:
                    amount = 10;
                    break;
            }

            return GeneticLiquid.RawDNA.get(amount);
        }

        public ItemStack getEnzyme() {
            return GeneticsItems.Enzyme.get(1);
        }

        public FluidStack getEthanol() {
            return Binnie.Liquid.getLiquidStack("bioethanol", (int) (getRawDNA().amount * 1.2));
        }

        public ItemStack getInput() {
            return this.inputStack;
        }
    }

    public class CachedGenepoolRecipe extends CachedBaseRecipe {
        public List<PositionedFluidTank> tanks = new ArrayList<>();
        public List<PositionedStack> ingredients = new ArrayList<>();
        public PositionedStack input;
        public PositionedStack enzyme;

        public CachedGenepoolRecipe(GenepoolRecipe recipe) {
            if (recipe.getInput() != null) {
                this.tanks.add(new PositionedFluidTank(recipe.getEthanol(), 100, new Rectangle(38, 6, 16, 58)));
                this.tanks.add(new PositionedFluidTank(recipe.getRawDNA(), 100, new Rectangle(119, 6, 16, 58)));
                this.enzyme = new PositionedStack(recipe.getEnzyme(), 58, 6);
                this.input = new PositionedStack(recipe.getInput(), 78, 6);
                this.ingredients.add(this.enzyme);
                this.ingredients.add(this.input);
            }
        }

        @Override
        public List<PositionedStack> getIngredients() {
            return getCycledIngredients(cycleticks / 40, this.ingredients);
        }

        @Override
        public List<PositionedFluidTank> getFluidTanks() {
            return this.tanks;
        }

        @Override
        public PositionedStack getResult() {
            return null;
        }
    }
}
