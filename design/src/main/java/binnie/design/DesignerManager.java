package binnie.design;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import binnie.design.api.IDesign;
import binnie.design.api.IDesignCategory;
import binnie.design.api.IDesignManager;
import binnie.design.api.IDesignSystem;

public final class DesignerManager implements IDesignManager {
	private static Map<Integer, IDesign> designMap = new LinkedHashMap<>();
	private static Map<String, IDesignCategory> designCategories = new HashMap<>();

	private final List<IDesignSystem> systems = new ArrayList<>();

	DesignerManager() {

	}

	public void registerDesignSystem(final IDesignSystem system) {
		this.systems.add(system);
	}

	public Collection<IDesignSystem> getDesignSystems() {
		return this.systems;
	}

	@Override
	public boolean registerDesign(final int index, final IDesign design) {
		return designMap.put(index, design) == null;
	}

	@Override
	public int getDesignIndex(final IDesign design) {
		for (final Integer integer : designMap.keySet()) {
			if (designMap.get(integer).equals(design)) {
				return integer;
			}
		}
		return -1;
	}

	@Override
	public IDesign getDesign(final int index) {
		return designMap.get(index);
	}

	@Override
	public boolean registerDesignCategory(final IDesignCategory category) {
		return designCategories.put(category.getId(), category) == null;
	}

	@Override
	public Collection<IDesignCategory> getAllDesignCategories() {
		final List<IDesignCategory> categories = new ArrayList<>();
		for (final IDesignCategory category : designCategories.values()) {
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
}
