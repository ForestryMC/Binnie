package binnie.extratrees.carpentry;

import binnie.extratrees.api.ICarpentryInterface;
import binnie.extratrees.api.IDesign;
import binnie.extratrees.api.IDesignCategory;
import binnie.extratrees.api.IDesignMaterial;
import binnie.extratrees.api.ILayout;
import binnie.extratrees.api.IPattern;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CarpentryInterface implements ICarpentryInterface {
	static Map<Integer, IDesignMaterial> woodMap = new LinkedHashMap<>();
	static Map<Integer, IDesign> designMap = new LinkedHashMap<>();
	static Map<String, IDesignCategory> designCategories = new HashMap<>();

	@Override
	public boolean registerCarpentryWood(final int index, final IDesignMaterial wood) {
		return CarpentryInterface.woodMap.put(index, wood) == null;
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
		return CarpentryInterface.designMap.put(index, wood) == null;
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
		return CarpentryInterface.designCategories.put(category.getId(), category) == null;
	}

	@Override
	public IDesignCategory getDesignCategory(final String id) {
		return CarpentryInterface.designCategories.get(id);
	}

	@Override
	public Collection<IDesignCategory> getAllDesignCategories() {
		final List<IDesignCategory> categories = new ArrayList<>();
		for (final IDesignCategory category : CarpentryInterface.designCategories.values()) {
			if (category.getDesigns().size() > 0) {
				categories.add(category);
			}
		}
		return categories;
	}

	@Override
	public List<IDesign> getSortedDesigns() {
		final List<IDesign> designs = new ArrayList<>();
		for (final IDesignCategory category : this.getAllDesignCategories()) {
			designs.addAll(category.getDesigns());
		}
		return designs;
	}

	@Override
	@Nullable
	public IDesignMaterial getWoodMaterial(final ItemStack stack) {
		for (final Map.Entry<Integer, IDesignMaterial> entry : CarpentryInterface.woodMap.entrySet()) {
			for (boolean fireproof : new boolean[]{true, false}) {
				final ItemStack key = entry.getValue().getStack(fireproof);
				if (key.isItemEqual(stack)) {
					return entry.getValue();
				}
			}
		}
		return null;
	}
}
