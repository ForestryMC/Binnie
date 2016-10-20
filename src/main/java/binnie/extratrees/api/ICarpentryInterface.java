package binnie.extratrees.api;

import net.minecraft.item.ItemStack;

import java.util.Collection;
import java.util.List;

public interface ICarpentryInterface {
    boolean registerCarpentryWood(final int p0, final IDesignMaterial p1);

    int getCarpentryWoodIndex(final IDesignMaterial p0);

    IDesignMaterial getWoodMaterial(final int p0);

    boolean registerDesign(final int p0, final IDesign p1);

    int getDesignIndex(final IDesign p0);

    IDesign getDesign(final int p0);

    ILayout getLayout(final IPattern p0, final boolean p1);

    IDesignMaterial getWoodMaterial(final ItemStack p0);

    boolean registerDesignCategory(final IDesignCategory p0);

    IDesignCategory getDesignCategory(final String p0);

    Collection<IDesignCategory> getAllDesignCategories();

    List<IDesign> getSortedDesigns();
}
