package binnie.extratrees.carpentry;

import binnie.extratrees.api.*;
import net.minecraft.item.ItemStack;

import java.util.*;

public class CarpentryInterface implements ICarpentryInterface {
    static Map<Integer, IDesignMaterial> woodMap;
    static Map<Integer, IDesign> designMap;
    static Map<String, IDesignCategory> designCategories;

    @Override
    public boolean registerCarpentryWood(final int index, final IDesignMaterial wood) {
        return wood != null && CarpentryInterface.woodMap.put(index, wood) == null;
    }

    @Override
    public int getCarpentryWoodIndex(final IDesignMaterial wood) {
        for (final Integer integer : CarpentryInterface.woodMap.keySet()) {
            if (CarpentryInterface.woodMap.get(integer).equals(wood)) {
                return integer;
            }
        }
        return -1;
    }

    @Override
    public IDesignMaterial getWoodMaterial(final int index) {
        return CarpentryInterface.woodMap.get(index);
    }

    @Override
    public boolean registerDesign(final int index, final IDesign wood) {
        return wood != null && CarpentryInterface.designMap.put(index, wood) == null;
    }

    @Override
    public int getDesignIndex(final IDesign wood) {
        for (final Integer integer : CarpentryInterface.designMap.keySet()) {
            if (CarpentryInterface.designMap.get(integer).equals(wood)) {
                return integer;
            }
        }
        return -1;
    }

    @Override
    public IDesign getDesign(final int index) {
        return CarpentryInterface.designMap.get(index);
    }

    @Override
    public ILayout getLayout(final IPattern pattern, final boolean inverted) {
        return Layout.get(pattern, inverted);
    }

    @Override
    public boolean registerDesignCategory(final IDesignCategory category) {
        return category != null && category.getId() != null && CarpentryInterface.designCategories.put(category.getId(), category) == null;
    }

    @Override
    public IDesignCategory getDesignCategory(final String id) {
        return CarpentryInterface.designCategories.get(id);
    }

    @Override
    public Collection<IDesignCategory> getAllDesignCategories() {
        final List<IDesignCategory> categories = new ArrayList<IDesignCategory>();
        for (final IDesignCategory category : CarpentryInterface.designCategories.values()) {
            if (category.getDesigns().size() > 0) {
                categories.add(category);
            }
        }
        return categories;
    }

    @Override
    public List<IDesign> getSortedDesigns() {
        final List<IDesign> designs = new ArrayList<IDesign>();
        for (final IDesignCategory category : this.getAllDesignCategories()) {
            designs.addAll(category.getDesigns());
        }
        return designs;
    }

    @Override
    public IDesignMaterial getWoodMaterial(final ItemStack stack) {
        if (stack == null) {
            return null;
        }
        for (final Map.Entry<Integer, IDesignMaterial> entry : CarpentryInterface.woodMap.entrySet()) {
            final ItemStack key = entry.getValue().getStack();
            if (key == null) {
                continue;
            }
            if (key.isItemEqual(stack)) {
                return entry.getValue();
            }
        }
        return null;
    }

    static {
        CarpentryInterface.woodMap = new LinkedHashMap<Integer, IDesignMaterial>();
        CarpentryInterface.designMap = new LinkedHashMap<Integer, IDesign>();
        CarpentryInterface.designCategories = new HashMap<String, IDesignCategory>();
    }
}
