package binnie.extrabees.apiary;

import binnie.core.Mods;
import binnie.core.genetics.BeeModifierLogic;
import binnie.core.genetics.EnumBeeBooleanModifier;
import binnie.core.genetics.EnumBeeModifier;
import binnie.core.util.I18N;
import cpw.mods.fml.common.registry.GameRegistry;
import forestry.api.apiculture.IBee;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeHousing;
import forestry.api.apiculture.IBeeModifier;
import forestry.api.apiculture.IHiveFrame;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public enum EnumHiveFrame implements IHiveFrame, IBeeModifier {
    Cocoa,
    Cage,
    Soul,
    Clay,
    Debug;

    protected Item item;
    protected int maxDamage;
    protected BeeModifierLogic logic;

    EnumHiveFrame() {
        maxDamage = 240;
        logic = new BeeModifierLogic();
    }

    public static void init() {
        EnumHiveFrame.Cocoa.logic.setModifier(EnumBeeModifier.LIFESPAN, 0.75f, 0.25f);
        EnumHiveFrame.Cocoa.logic.setModifier(EnumBeeModifier.PRODUCTION, 1.5f, 5.0f);
        EnumHiveFrame.Cage.logic.setModifier(EnumBeeModifier.TERRITORY, 0.5f, 0.1f);
        EnumHiveFrame.Cage.logic.setModifier(EnumBeeModifier.LIFESPAN, 0.75f, 0.5f);
        EnumHiveFrame.Cage.logic.setModifier(EnumBeeModifier.PRODUCTION, 0.75f, 0.5f);
        EnumHiveFrame.Soul.logic.setModifier(EnumBeeModifier.MUTATION, 1.5f, 5.0f);
        EnumHiveFrame.Soul.logic.setModifier(EnumBeeModifier.LIFESPAN, 0.75f, 0.5f);
        EnumHiveFrame.Soul.logic.setModifier(EnumBeeModifier.PRODUCTION, 0.25f, 0.1f);
        EnumHiveFrame.Soul.setMaxDamage(80);
        EnumHiveFrame.Clay.logic.setModifier(EnumBeeModifier.LIFESPAN, 1.5f, 5.0f);
        EnumHiveFrame.Clay.logic.setModifier(EnumBeeModifier.MUTATION, 0.5f, 0.2f);
        EnumHiveFrame.Clay.logic.setModifier(EnumBeeModifier.PRODUCTION, 0.75f, 0.2f);
        EnumHiveFrame.Debug.logic.setModifier(EnumBeeModifier.LIFESPAN, 1.0E-4f, 1.0E-4f);

        GameRegistry.addRecipe(
                new ItemStack(EnumHiveFrame.Cocoa.item),
                " c ",
                "cFc",
                " c ",
                'F',
                Mods.forestry.stack("frameImpregnated"),
                'c',
                new ItemStack(Items.dye, 1, 3));
        GameRegistry.addShapelessRecipe(
                new ItemStack(EnumHiveFrame.Cage.item), Mods.forestry.stack("frameImpregnated"), Blocks.iron_bars);
        GameRegistry.addShapelessRecipe(
                new ItemStack(EnumHiveFrame.Soul.item), Mods.forestry.stack("frameImpregnated"), Blocks.soul_sand);
        GameRegistry.addRecipe(
                new ItemStack(EnumHiveFrame.Clay.item),
                " c ",
                "cFc",
                " c ",
                'F',
                Mods.forestry.stack("frameImpregnated"),
                'c',
                Items.clay_ball);
    }

    public void setMaxDamage(int damage) {
        maxDamage = damage;
    }

    @Override
    public ItemStack frameUsed(IBeeHousing house, ItemStack frame, IBee queen, int wear) {
        frame.setItemDamage(frame.getItemDamage() + wear);
        if (frame.getItemDamage() >= frame.getMaxDamage()) {
            return null;
        }
        return frame;
    }

    @Override
    public float getTerritoryModifier(IBeeGenome genome, float currentModifier) {
        return logic.getModifier(EnumBeeModifier.TERRITORY, currentModifier);
    }

    @Override
    public float getMutationModifier(IBeeGenome genome, IBeeGenome mate, float currentModifier) {
        return logic.getModifier(EnumBeeModifier.MUTATION, currentModifier);
    }

    @Override
    public float getLifespanModifier(IBeeGenome genome, IBeeGenome mate, float currentModifier) {
        return logic.getModifier(EnumBeeModifier.LIFESPAN, currentModifier);
    }

    @Override
    public float getProductionModifier(IBeeGenome genome, float currentModifier) {
        return logic.getModifier(EnumBeeModifier.PRODUCTION, currentModifier);
    }

    @Override
    public float getFloweringModifier(IBeeGenome genome, float currentModifier) {
        return logic.getModifier(EnumBeeModifier.FLOWERING, currentModifier);
    }

    @Override
    public float getGeneticDecay(IBeeGenome genome, float currentModifier) {
        return logic.getModifier(EnumBeeModifier.GENETIC_DECAY, currentModifier);
    }

    @Override
    public boolean isSealed() {
        return logic.getModifier(EnumBeeBooleanModifier.SEALED);
    }

    @Override
    public boolean isSelfLighted() {
        return logic.getModifier(EnumBeeBooleanModifier.SELF_LIGHTED);
    }

    @Override
    public boolean isSunlightSimulated() {
        return logic.getModifier(EnumBeeBooleanModifier.SUNLIGHT_STIMULATED);
    }

    @Override
    public boolean isHellish() {
        return logic.getModifier(EnumBeeBooleanModifier.HELLISH);
    }

    public String getName() {
        return I18N.localise("extrabees.item.frame." + toString().toLowerCase());
    }

    @Override
    public IBeeModifier getBeeModifier() {
        return this;
    }
}
