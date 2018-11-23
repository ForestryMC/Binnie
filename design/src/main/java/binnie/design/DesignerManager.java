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
	private static final Map<Integer, IDesign> designMap = new LinkedHashMap<>();
	private static final Map<String, IDesignCategory> designCategories = new HashMap<>();

	private final List<IDesignSystem> systems = new ArrayList<>();

	DesignerManager() {

	}

	public void registerDesignSystem(IDesignSystem system) {
		this.systems.add(system);
	}

	public Collection<IDesignSystem> getDesignSystems() {
		return this.systems;
	}

	@Override
	public boolean registerDesign(int index, IDesign design) {
		return designMap.put(index, design) == null;
	}

	@Override
	public int getDesignIndex(IDesign design) {
		for (Integer integer : designMap.keySet()) {
			if (designMap.get(integer).equals(design)) {
				return integer;
			}
		}
		return -1;
	}

	@Override
	public IDesign getDesign(int index) {
		return designMap.get(index);
	}

	@Override
	public boolean registerDesignCategory(IDesignCategory category) {
		return designCategories.put(category.getId(), category) == null;
	}

	@Override
	public Collection<IDesignCategory> getAllDesignCategories() {
		List<IDesignCategory> categories = new ArrayList<>();
		for (IDesignCategory category : designCategories.values()) {
			if (category.getDesigns().size() > 0) {
				categories.add(category);
			}
		}
		return categories;
	}

	@Override
	public List<IDesign> getSortedDesigns() {
		List<IDesign> designs = new ArrayList<>();
		for (IDesignCategory category : this.getAllDesignCategories()) {
			designs.addAll(category.getDesigns());
		}
		return designs;
	}
}
