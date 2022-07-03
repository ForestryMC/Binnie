package binnie.genetics.nei;

import binnie.botany.Botany;
import binnie.core.BinnieCore;
import binnie.core.Mods;
import binnie.core.nei.PositionedStackAdv;
import binnie.core.nei.RecipeHandlerBase;
import binnie.genetics.machine.acclimatiser.Acclimatiser;
import binnie.genetics.machine.acclimatiser.ToleranceType;
import codechicken.lib.gui.GuiDraw;
import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraftforge.oredict.OreDictionary;

public class AcclimatiserRecipeHandler extends RecipeHandlerBase {

    private static final List<AcclimatiserRecipe> recipes = new ArrayList<>();

    @Override
    public void prepare() {
        Acclimatiser.temperatureItems.forEach((resource, effect) -> {
            List<ItemStack> targets = new ArrayList<>();
            if (BinnieCore.isApicultureActive()) {
                targets.add(Mods.forestry.stack("beeDroneGE", 1, OreDictionary.WILDCARD_VALUE));
                targets.add(Mods.forestry.stack("beePrincessGE", 1, OreDictionary.WILDCARD_VALUE));
                targets.add(Mods.forestry.stack("beeQueenGE", 1, OreDictionary.WILDCARD_VALUE));
                targets.add(Mods.forestry.stack("beeLarvaeGE", 1, OreDictionary.WILDCARD_VALUE));
            }
            if (BinnieCore.isLepidopteryActive()) {
                targets.add(Mods.forestry.stack("butterflyGE", 1, OreDictionary.WILDCARD_VALUE));
                targets.add(Mods.forestry.stack("caterpillarGE", 1, OreDictionary.WILDCARD_VALUE));
                targets.add(Mods.forestry.stack("serumGE", 1, OreDictionary.WILDCARD_VALUE));
            }
            if (BinnieCore.isBotanyActive()) {
                targets.add(new ItemStack(Botany.flowerItem, 1, OreDictionary.WILDCARD_VALUE));
                targets.add(new ItemStack(Botany.seed, 1, OreDictionary.WILDCARD_VALUE));
                targets.add(new ItemStack(Botany.pollen, 1, OreDictionary.WILDCARD_VALUE));
            }
            recipes.add(new AcclimatiserRecipe(targets, resource, effect, ToleranceType.Temperature));
        });
        Acclimatiser.humidityItems.forEach((resource, effect) -> {
            List<ItemStack> targets = new ArrayList<>();
            if (BinnieCore.isApicultureActive()) {
                targets.add(Mods.forestry.stack("beeDroneGE", 1, OreDictionary.WILDCARD_VALUE));
                targets.add(Mods.forestry.stack("beePrincessGE", 1, OreDictionary.WILDCARD_VALUE));
                targets.add(Mods.forestry.stack("beeQueenGE", 1, OreDictionary.WILDCARD_VALUE));
                targets.add(Mods.forestry.stack("beeLarvaeGE", 1, OreDictionary.WILDCARD_VALUE));
            }
            if (BinnieCore.isLepidopteryActive()) {
                targets.add(Mods.forestry.stack("butterflyGE", 1, OreDictionary.WILDCARD_VALUE));
                targets.add(Mods.forestry.stack("caterpillarGE", 1, OreDictionary.WILDCARD_VALUE));
                targets.add(Mods.forestry.stack("serumGE", 1, OreDictionary.WILDCARD_VALUE));
            }
            if (BinnieCore.isBotanyActive()) {
                targets.add(new ItemStack(Botany.flowerItem, 1, OreDictionary.WILDCARD_VALUE));
                targets.add(new ItemStack(Botany.seed, 1, OreDictionary.WILDCARD_VALUE));
                targets.add(new ItemStack(Botany.pollen, 1, OreDictionary.WILDCARD_VALUE));
            }
            recipes.add(new AcclimatiserRecipe(targets, resource, effect, ToleranceType.Humidity));
        });
    }

    @Override
    public String getOverlayIdentifier() {
        return "genetics.acclimatiser";
    }

    @Override
    public String getGuiTexture() {
        return "genetics:textures/gui/nei/acclimatiser.png";
    }

    @Override
    public String getRecipeName() {
        return StatCollector.translateToLocal("genetics.machine.labMachine.acclimatiser");
    }

    @Override
    public void loadTransferRects() {
        this.addTransferRect(75, 20, 18, 7);
    }

    @Override
    public void drawBackground(int recipe) {
        changeToGuiTexture();
        GuiDraw.drawTexturedModalRect(0, 0, 5, 11, 166, 76);
    }

    @Override
    public void loadAllRecipes() {
        for (AcclimatiserRecipe recipe : recipes) {
            this.arecipes.add(new CachedAcclimatiserRecipe(recipe));
        }
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient) {
        for (AcclimatiserRecipe recipe : recipes) {
            if (NEIServerUtils.areStacksSameTypeCrafting(recipe.getResource(), ingredient)) {
                this.arecipes.add(new CachedAcclimatiserRecipe(recipe));
            } else {
                for (ItemStack itemStack : recipe.getTarget()) {
                    if (NEIServerUtils.areStacksSameTypeCrafting(itemStack, ingredient)) {
                        this.arecipes.add(new CachedAcclimatiserRecipe(recipe));
                        break;
                    }
                }
            }
        }
    }

    private static class AcclimatiserRecipe {
        private final ItemStack[] target;
        private final ItemStack resource;
        private final float effect;
        private final ToleranceType type;

        public AcclimatiserRecipe(Object target, ItemStack resource, float effect, ToleranceType type) {
            this.target = NEIServerUtils.extractRecipeItems(target);
            this.resource = resource;
            this.effect = effect;
            this.type = type;
        }

        public ItemStack getResource() {
            return this.resource;
        }

        public ItemStack[] getTarget() {
            return this.target;
        }

        public float getEffect() {
            return this.effect;
        }

        public ToleranceType getType() {
            return this.type;
        }
    }

    public class CachedAcclimatiserRecipe extends CachedBaseRecipe {

        public List<PositionedStack> target = new ArrayList<>();
        public PositionedStack resource;
        public float effect;
        public ToleranceType type;
        public List<PositionedStack> ingredients = new ArrayList<>();

        public CachedAcclimatiserRecipe(AcclimatiserRecipe recipe) {
            if (recipe.getTarget() != null) {
                for (int i = 0; i < recipe.getTarget().length; i++) {
                    target.add(new PositionedStack(
                            recipe.getTarget()[i], 31 + 18 * (i > 5 ? i % 6 : i), 29 + 18 * (i / 6)));
                }
                this.resource = new PositionedStackAdv(recipe.getResource(), 76, 2)
                        .addToTooltip(StatCollector.translateToLocal("genetics.nei.tip."
                                + (recipe.getType().equals(ToleranceType.Temperature) ? "temperature" : "humidity")))
                        .addToTooltip(
                                StatCollector.translateToLocalFormatted("genetics.nei.tip.effect", recipe.getEffect()));
                this.effect = recipe.getEffect();
                this.type = recipe.getType();

                this.ingredients.addAll(this.target);
                this.ingredients.add(this.resource);
            }
        }

        @Override
        public List<PositionedStack> getIngredients() {
            return getCycledIngredients(cycleticks / 40, this.ingredients);
        }

        @Override
        public PositionedStack getResult() {
            return null;
        }
    }
}
