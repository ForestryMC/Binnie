package binnie.extratrees.api;

import java.util.Collection;
import java.util.List;
import net.minecraft.item.ItemStack;

public interface ICarpentryInterface {
    boolean registerCarpentryWood(int index, IDesignMaterial wood);

    int getCarpentryWoodIndex(IDesignMaterial wood);

    IDesignMaterial getWoodMaterial(int index);

    boolean registerDesign(int index, IDesign wood);

    int getDesignIndex(IDesign wood);

    IDesign getDesign(int p0);

    ILayout getLayout(IPattern pattern, boolean inverted);

    IDesignMaterial getWoodMaterial(ItemStack stack);

    boolean registerDesignCategory(IDesignCategory category);

    IDesignCategory getDesignCategory(String id);

    Collection<IDesignCategory> getAllDesignCategories();

    List<IDesign> getSortedDesigns();
}
