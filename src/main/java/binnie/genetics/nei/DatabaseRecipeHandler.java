package binnie.genetics.nei;

import binnie.core.nei.RecipeHandlerBase;
import binnie.genetics.Genetics;
import binnie.genetics.item.GeneticsItems;
import binnie.genetics.item.ItemSerumArray;
import codechicken.lib.gui.GuiDraw;
import codechicken.nei.ItemList;
import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraftforge.oredict.OreDictionary;

public class DatabaseRecipeHandler extends RecipeHandlerBase {

    private static final List<DatabaseRecipe> recipes = new ArrayList<>();

    @Override
    public void prepare() {
        recipes.add(new DatabaseRecipe(new ItemStack(Genetics.itemSerum, 1, OreDictionary.WILDCARD_VALUE)));
        recipes.add(new DatabaseRecipe(new ItemStack(Genetics.itemSerumArray, 1, OreDictionary.WILDCARD_VALUE)));
    }

    @Override
    public String getOverlayIdentifier() {
        return "genetics.database";
    }

    @Override
    public String getGuiTexture() {
        return "genetics:textures/gui/nei/database.png";
    }

    @Override
    public String getRecipeName() {
        return StatCollector.translateToLocal("genetics.item.database.0.name");
    }

    @Override
    public void loadTransferRects() {
        this.addTransferRect(75, 7, 24, 17);
    }

    @Override
    public void drawBackground(int recipe) {
        changeToGuiTexture();
        GuiDraw.drawTexturedModalRect(0, 0, 5, 11, 166, 74);
    }

    @Override
    public void drawExtras(int recipe) {
        this.drawProgressBar(75, 7, 176, 0, 24, 17, 40, 0);

        List<String> desc = GuiDraw.fontRenderer.listFormattedStringToWidth(
                StatCollector.translateToLocalFormatted("genetics.nei.tip.databaseDesc"), 140);
        for (int i = 0; i < desc.size(); i++) {
            GuiDraw.drawStringC(desc.get(i), 88, 44 + 12 * (i - 1), 0xFFFFFF);
        }
    }

    @Override
    public void loadAllRecipes() {
        for (DatabaseRecipe recipe : recipes) {
            this.arecipes.add(new CachedDatabaseRecipe(recipe));
        }
    }

    @Override
    public void loadCraftingRecipes(ItemStack result) {
        for (DatabaseRecipe recipe : recipes) {
            if (NEIServerUtils.areStacksSameTypeCrafting(recipe.getSerum(), result)) {
                this.arecipes.add(new CachedDatabaseRecipe(recipe));
            }
        }
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient) {
        for (DatabaseRecipe recipe : recipes) {
            if (NEIServerUtils.areStacksSameTypeCrafting(recipe.getEmptySerum(), ingredient)) {
                this.arecipes.add(new CachedDatabaseRecipe(recipe));
            }
        }
    }

    private static class DatabaseRecipe {

        private final ItemStack serum;

        public DatabaseRecipe(ItemStack serum) {
            this.serum = serum;
        }

        public ItemStack getSerum() {
            return this.serum;
        }

        public ItemStack getEmptySerum() {
            return this.serum.getItem() instanceof ItemSerumArray
                    ? GeneticsItems.EmptyGenome.get(1)
                    : GeneticsItems.EmptySerum.get(1);
        }
    }

    public class CachedDatabaseRecipe extends CachedBaseRecipe {

        public PositionedStack serum;
        public PositionedStack emptySerum;

        public CachedDatabaseRecipe(DatabaseRecipe recipe) {
            if (recipe.getSerum() != null) {
                List<ItemStack> serums = new ArrayList<>();

                for (ItemStack serumStack :
                        ItemList.itemMap.get(recipe.getSerum().getItem())) {
                    if (serumStack.getTagCompound() != null) {
                        ItemStack temp = serumStack.copy();
                        temp.setItemDamage(temp.getMaxDamage());
                        serums.add(temp);
                    }
                }

                this.emptySerum = new PositionedStack(recipe.getEmptySerum(), 53, 7);
                this.serum = new PositionedStack(serums, 105, 7);
            }
        }

        @Override
        public PositionedStack getIngredient() {
            this.serum.setPermutationToRender(cycleticks / 40 % this.serum.items.length);
            return this.serum;
        }

        @Override
        public PositionedStack getResult() {
            return this.emptySerum;
        }
    }
}
