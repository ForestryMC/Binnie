package binnie.genetics.nei;

import binnie.core.nei.RecipeHandlerBase;
import binnie.genetics.Genetics;
import binnie.genetics.item.GeneticsItems;
import codechicken.lib.gui.GuiDraw;
import codechicken.nei.ItemList;
import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class SequencerRecipeHandler extends RecipeHandlerBase {

    @Override
    public String getOverlayIdentifier() {
        return "genetics.sequencer";
    }

    @Override
    public String getGuiTexture() {
        return "genetics:textures/gui/nei/sequencer.png";
    }

    @Override
    public String getRecipeName() {
        return StatCollector.translateToLocal("genetics.machine.machine.sequencer");
    }

    @Override
    public void loadTransferRects() {
        this.addTransferRect(75, 19, 24, 17);
    }

    @Override
    public void drawBackground(int recipe) {
        changeToGuiTexture();
        GuiDraw.drawTexturedModalRect(0, 0, 5, 11, 166, 74);
    }

    @Override
    public void drawExtras(int recipe) {
        this.drawProgressBar(75, 19, 176, 0, 24, 17, 40, 0);
    }

    @Override
    public void loadAllRecipes() {
        this.arecipes.add(new CachedSequencerRecipe());
    }

    @Override
    public void loadCraftingRecipes(ItemStack result) {
        if (NEIServerUtils.areStacksSameTypeCrafting(GeneticsItems.EmptySequencer.get(1), result)) {
            this.loadAllRecipes();
        }
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient) {
        if (NEIServerUtils.areStacksSameTypeCrafting(new ItemStack(Genetics.itemSequencer), ingredient)
                || NEIServerUtils.areStacksSameTypeCrafting(GeneticsItems.FluorescentDye.get(1), ingredient)
                || NEIServerUtils.areStacksSameTypeCrafting(new ItemStack(Genetics.database), ingredient)) {
            this.loadAllRecipes();
        }
    }

    public class CachedSequencerRecipe extends CachedBaseRecipe {

        public PositionedStack seq;
        public PositionedStack fluorescentDye = new PositionedStack(GeneticsItems.FluorescentDye.get(1), 53, 40);
        public PositionedStack database = new PositionedStack(new ItemStack(Genetics.database), 105, 19);
        public PositionedStack emptySeq = new PositionedStack(GeneticsItems.EmptySequencer.get(1), 105, 40);
        public List<PositionedStack> ingredients = new ArrayList<>();
        public List<PositionedStack> results = new ArrayList<>();

        public CachedSequencerRecipe() {
            List<ItemStack> seqList = new ArrayList<>();

            for (ItemStack itemStack : ItemList.itemMap.get(Genetics.itemSequencer)) {
                for (int i = Genetics.itemSequencer.getMaxDamage(); i >= 0; i--) {
                    ItemStack temp = itemStack.copy();
                    temp.setItemDamage(i);
                    seqList.add(temp);
                }
            }

            this.seq = new PositionedStack(seqList, 53, 19);

            this.ingredients.add(this.seq);
            this.ingredients.add(this.fluorescentDye);

            this.results.add(this.database);
            this.results.add(this.emptySeq);
        }

        @Override
        public PositionedStack getResult() {
            return null;
        }

        @Override
        public List<PositionedStack> getIngredients() {
            this.seq.setPermutationToRender(cycleticks / 40 % this.seq.items.length);
            return this.ingredients;
        }

        @Override
        public List<PositionedStack> getOtherStacks() {
            return this.results;
        }
    }
}
