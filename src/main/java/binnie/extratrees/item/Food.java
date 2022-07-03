package binnie.extratrees.item;

import binnie.Binnie;
import binnie.core.Mods;
import binnie.core.item.IItemMisc;
import binnie.core.util.I18N;
import binnie.extratrees.ExtraTrees;
import forestry.api.recipes.RecipeManagers;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.oredict.OreDictionary;

public enum Food implements IItemMisc {
    Crabapple(2),
    Orange(4),
    Kumquat(2),
    Lime(2),
    WildCherry(2),
    SourCherry(2),
    BlackCherry(2),
    Blackthorn(3),
    CherryPlum(3),
    Almond(1),
    Apricot(4),
    Grapefruit(4),
    Peach(4),
    Satsuma(3),
    BuddhaHand(3),
    Citron(3),
    FingerLime(3),
    KeyLime(2),
    Manderin(3),
    Nectarine(3),
    Pomelo(3),
    Tangerine(3),
    Pear(4),
    SandPear(2),
    Hazelnut(2),
    Butternut(1),
    Beechnut(0),
    Pecan(0),
    Banana(4),
    RedBanana(4),
    Plantain(2),
    BrazilNut(0),
    Fig(2),
    Acorn(0),
    Elderberry(1),
    Olive(1),
    GingkoNut(1),
    Coffee(0),
    OsangeOrange(1),
    Clove(0),
    Papayimar(8),
    Blackcurrant(2),
    Redcurrant(2),
    Blackberry(2),
    Raspberry(2),
    Blueberry(2),
    Cranberry(2),
    Juniper(0),
    Gooseberry(2),
    GoldenRaspberry(2),
    Coconut(2),
    Cashew(0),
    Avacado(2),
    Nutmeg(0),
    Allspice(0),
    Chilli(2),
    StarAnise(0),
    Mango(4),
    Starfruit(2),
    Candlenut(0);

    protected IIcon icon;
    protected int hunger;
    private List<String> ores;

    Food() {
        this(0);
    }

    Food(int hunger) {
        ores = new ArrayList<>();
        this.hunger = hunger;
    }

    public static void registerOreDictionary() {
        Food.Crabapple.ore("Apple").ore("Crabapple");
        Food.Orange.ore("Orange");
        Food.Kumquat.ore("Kumquat");
        Food.Lime.ore("Lime");
        Food.WildCherry.ore("Cherry").ore("WildCherry");
        Food.SourCherry.ore("Cherry").ore("SourCherry");
        Food.BlackCherry.ore("Cherry").ore("BlackCherry");
        Food.Blackthorn.ore("Blackthorn");
        Food.CherryPlum.ore("Plum").ore("CherryPlum");
        Food.Almond.ore("Almond");
        Food.Apricot.ore("Apricot");
        Food.Grapefruit.ore("Grapefruit");
        Food.Peach.ore("Peach");
        Food.Satsuma.ore("Satsuma").ore("Orange");
        Food.BuddhaHand.ore("BuddhaHand").ore("Citron");
        Food.Citron.ore("Citron");
        Food.FingerLime.ore("Lime").ore("FingerLime");
        Food.KeyLime.ore("KeyLime").ore("Lime");
        Food.Manderin.ore("Orange").ore("Manderin");
        Food.Nectarine.ore("Peach").ore("Nectarine");
        Food.Pomelo.ore("Pomelo");
        Food.Tangerine.ore("Tangerine").ore("Orange");
        Food.Pear.ore("Pear");
        Food.SandPear.ore("SandPear");
        Food.Hazelnut.ore("Hazelnut");
        Food.Butternut.ore("Butternut").ore("Walnut");
        Food.Beechnut.ore("Beechnut");
        Food.Pecan.ore("Pecan");
        Food.Banana.ore("Banana");
        Food.RedBanana.ore("RedBanana").ore("Banana");
        Food.Plantain.ore("Plantain");
        Food.BrazilNut.ore("BrazilNut");
        Food.Fig.ore("Fig");
        Food.Acorn.ore("Acorn");
        Food.Elderberry.ore("Elderberry");
        Food.Olive.ore("Olive");
        Food.GingkoNut.ore("GingkoNut");
        Food.Coffee.ore("Coffee");
        Food.OsangeOrange.ore("OsangeOrange");
        Food.Clove.ore("Clove");
        Food.Papayimar.ore("Papayimar");
        Food.Blackcurrant.ore("Blackcurrant");
        Food.Redcurrant.ore("Redcurrant");
        Food.Blackberry.ore("Blackberry");
        Food.Raspberry.ore("Raspberry");
        Food.Blueberry.ore("Blueberry");
        Food.Cranberry.ore("Cranberry");
        Food.Juniper.ore("Juniper");
        Food.Gooseberry.ore("Gooseberry");
        Food.GoldenRaspberry.ore("GoldenRaspberry");
        Food.Coconut.ore("Coconut");
        Food.Cashew.ore("Cashew");
        Food.Avacado.ore("Avacado");
        Food.Nutmeg.ore("Nutmeg");
        Food.Chilli.ore("Chilli");
        Food.StarAnise.ore("StarAnise");
        Food.Mango.ore("Mango");
        Food.Starfruit.ore("Starfruit");
        Food.Candlenut.ore("Candlenut");
    }

    public boolean isEdible() {
        return hunger > 0;
    }

    public int getHealth() {
        return hunger;
    }

    @Override
    public boolean isActive() {
        return true;
    }

    @Override
    public String getName(ItemStack itemStack) {
        return I18N.localise("for.extratrees.item.food." + name().toLowerCase());
    }

    @Override
    public ItemStack get(int count) {
        return new ItemStack(ExtraTrees.itemFood, count, ordinal());
    }

    @Override
    public IIcon getIcon(ItemStack itemStack) {
        return icon;
    }

    @Override
    public void registerIcons(IIconRegister register) {
        icon = ExtraTrees.proxy.getIcon(register, "food/" + toString());
    }

    @Override
    public void addInformation(List tooltip) {
        // ignored
    }

    public void addJuice(int time, int amount, int mulch) {
        RecipeManagers.squeezerManager.addRecipe(
                time,
                new ItemStack[] {get(1)},
                Binnie.Liquid.getLiquidStack("juice", amount),
                Mods.forestry.stack("mulch"),
                mulch);
    }

    public void addOil(int time, int amount, int mulch) {
        RecipeManagers.squeezerManager.addRecipe(
                time,
                new ItemStack[] {get(1)},
                Binnie.Liquid.getLiquidStack("seedoil", amount),
                Mods.forestry.stack("mulch"),
                mulch);
    }

    private Food ore(String string) {
        OreDictionary.registerOre("crop" + string, get(1));
        ores.add("crop" + string);
        return this;
    }

    public Collection<String> getOres() {
        return ores;
    }
}
