package binnie.genetics.nei;

import binnie.botany.Botany;
import binnie.core.BinnieCore;
import binnie.core.Mods;
import binnie.core.nei.RecipeHandlerBase;
import binnie.genetics.Genetics;
import binnie.genetics.item.GeneticsItems;
import binnie.genetics.item.ItemSequence;
import codechicken.lib.gui.GuiDraw;
import codechicken.nei.ItemList;
import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraftforge.oredict.OreDictionary;

public class AnalyserRecipeHandler extends RecipeHandlerBase {

    private static final List<AnalyserRecipe> recipes = new ArrayList<>();

    @Override
    public void prepare() {
        recipes.add(new AnalyserRecipe(new ItemStack(Genetics.itemSequencer, 1, OreDictionary.WILDCARD_VALUE)));

        if (BinnieCore.isApicultureActive()) {
            recipes.add(new AnalyserRecipe(Mods.forestry.stack("beeDroneGE", 1, OreDictionary.WILDCARD_VALUE)));
            recipes.add(new AnalyserRecipe(Mods.forestry.stack("beePrincessGE", 1, OreDictionary.WILDCARD_VALUE)));
            recipes.add(new AnalyserRecipe(Mods.forestry.stack("beeQueenGE", 1, OreDictionary.WILDCARD_VALUE)));
            recipes.add(new AnalyserRecipe(Mods.forestry.stack("beeLarvaeGE", 1, OreDictionary.WILDCARD_VALUE)));
        }
        if (BinnieCore.isArboricultureActive()) {
            recipes.add(new AnalyserRecipe(Mods.forestry.stack("sapling", 1, OreDictionary.WILDCARD_VALUE)));
            recipes.add(new AnalyserRecipe(Mods.forestry.stack("pollenFertile", 1, OreDictionary.WILDCARD_VALUE)));
        }
        if (BinnieCore.isLepidopteryActive()) {
            recipes.add(new AnalyserRecipe(Mods.forestry.stack("butterflyGE", 1, OreDictionary.WILDCARD_VALUE)));
            recipes.add(new AnalyserRecipe(Mods.forestry.stack("caterpillarGE", 1, OreDictionary.WILDCARD_VALUE)));
            recipes.add(new AnalyserRecipe(Mods.forestry.stack("serumGE", 1, OreDictionary.WILDCARD_VALUE)));
        }
        if (BinnieCore.isBotanyActive()) {
            recipes.add(new AnalyserRecipe(new ItemStack(Botany.flowerItem, 1, OreDictionary.WILDCARD_VALUE)));
            recipes.add(new AnalyserRecipe(new ItemStack(Botany.seed, 1, OreDictionary.WILDCARD_VALUE)));
            recipes.add(new AnalyserRecipe(new ItemStack(Botany.pollen, 1, OreDictionary.WILDCARD_VALUE)));
        }
    }

    @Override
    public String getOverlayIdentifier() {
        return "genetics.analyser";
    }

    @Override
    public String getGuiTexture() {
        return "genetics:textures/gui/nei/analyser.png";
    }

    @Override
    public String getRecipeName() {
        return StatCollector.translateToLocal("genetics.machine.labMachine.analyser");
    }

    @Override
    public void loadTransferRects() {
        this.addTransferRect(50, 13, 66, 11);
        this.addTransferRect(50, 42, 66, 11);
        this.addTransferRect(50, 24, 24, 18);
        this.addTransferRect(92, 24, 24, 18);
    }

    @Override
    public void drawBackground(int recipe) {
        changeToGuiTexture();
        GuiDraw.drawTexturedModalRect(0, 0, 5, 11, 166, 74);
    }

    @Override
    public void drawExtras(int recipe) {
        this.drawProgressBar(50, 13, 176, 0, 66, 40, 40, 0);
    }

    @Override
    public void loadAllRecipes() {
        for (AnalyserRecipe recipe : recipes) {
            this.arecipes.add(new CachedAnalyser(recipe));
        }
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient) {
        if (NEIServerUtils.areStacksSameTypeCrafting(GeneticsItems.DNADye.get(0), ingredient)) {
            this.loadAllRecipes();
        } else {
            for (AnalyserRecipe recipe : recipes) {
                if (NEIServerUtils.areStacksSameTypeCrafting(recipe.getAnalysable(), ingredient)) {
                    this.arecipes.add(new CachedAnalyser(recipe));
                }
            }
        }
    }

    private static class AnalyserRecipe {
        private final ItemStack analysable;

        public AnalyserRecipe(ItemStack analysable) {
            this.analysable = analysable;
        }

        public ItemStack getDNADye() {
            return GeneticsItems.DNADye.get(1);
        }

        public ItemStack getAnalysable() {
            return this.analysable;
        }
    }

    public class CachedAnalyser extends CachedBaseRecipe {

        public PositionedStack analysable;
        public PositionedStack dnaDye;
        public List<PositionedStack> ingredients = new ArrayList<>();

        public CachedAnalyser(AnalyserRecipe recipe) {
            if (recipe.getAnalysable() != null) {
                List<ItemStack> analysableList = new ArrayList<>();

                if (recipe.getAnalysable().getItem() instanceof ItemSequence) {
                    for (ItemStack seq : ItemList.itemMap.get(Genetics.itemSequencer)) {
                        for (int i = Genetics.itemSequencer.getMaxDamage(); i >= 0; i--) {
                            ItemStack temp = seq.copy();
                            temp.setItemDamage(i);
                            analysableList.add(temp);
                        }
                    }
                } else {
                    for (ItemStack analysable :
                            ItemList.itemMap.get(recipe.getAnalysable().getItem())) {
                        if (analysable.getTagCompound() != null) {
                            analysableList.add(analysable.copy());
                        }
                    }
                }

                this.analysable = new PositionedStack(analysableList, 75, 25);
                this.dnaDye = new PositionedStack(recipe.getDNADye(), 75, 56);

                this.ingredients.add(this.analysable);
                this.ingredients.add(this.dnaDye);
            }
        }

        @Override
        public List<PositionedStack> getIngredients() {
            this.analysable.setPermutationToRender(cycleticks / 40 % this.analysable.items.length);
            return this.ingredients;
        }

        @Override
        public PositionedStack getResult() {
            return null;
        }
    }
}
