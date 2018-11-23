package binnie.design.api;

import java.util.Collection;
import java.util.List;

public interface IDesignManager {
	void registerDesignSystem(IDesignSystem system);

	Collection<IDesignSystem> getDesignSystems();

	boolean registerDesign(int index, IDesign design);

	int getDesignIndex(IDesign design);

	IDesign getDesign(int index);

	boolean registerDesignCategory(IDesignCategory category);

	Collection<IDesignCategory> getAllDesignCategories();

	List<IDesign> getSortedDesigns();
}
