package binnie.extratrees.api;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;

import net.minecraft.item.ItemStack;

public interface ICarpentryInterface {
	boolean registerCarpentryWood(final int index, final IDesignMaterial material);

	int getCarpentryWoodIndex(final IDesignMaterial material);

	IDesignMaterial getWoodMaterial(final int p0);

	boolean registerDesign(final int p0, final IDesign p1);

	int getDesignIndex(final IDesign p0);

	IDesign getDesign(final int p0);

	ILayout getLayout(final IPattern p0, final boolean p1);

	@Nullable
	IDesignMaterial getWoodMaterial(final ItemStack stack);

	boolean registerDesignCategory(final IDesignCategory p0);

	IDesignCategory getDesignCategory(final String p0);

	Collection<IDesignCategory> getAllDesignCategories();

	List<IDesign> getSortedDesigns();
}
