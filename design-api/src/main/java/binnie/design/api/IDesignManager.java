package binnie.design.api;

import java.util.Collection;
import java.util.List;

public interface IDesignManager {
	void registerDesignSystem(final IDesignSystem system);

	Collection<IDesignSystem> getDesignSystems();

	boolean registerDesign(final int index, final IDesign design);

	int getDesignIndex(final IDesign design);

	IDesign getDesign(final int index);

	boolean registerDesignCategory(final IDesignCategory category);

	Collection<IDesignCategory> getAllDesignCategories();

	List<IDesign> getSortedDesigns();
}
