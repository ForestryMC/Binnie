package binnie.core.api;

import javax.annotation.Nullable;
import java.util.Collection;

public interface ICraftingManager<T extends IBinnieRecipe> {
	/**
	 * Add a new recipe to the crafting provider.
	 *
	 * @return <tt>true</tt> if this collection changed as a result of the call
	 */
	boolean addRecipe(T recipe);

	/**
	 * Remove a specific recipe from the crafting provider.
	 *
	 * @return <tt>true</tt> if an element was removed as a result of this call
	 */
	boolean removeRecipe(T recipe);

	/**
	 * @return an unmodifiable collection of all recipes registered to the crafting provider.
	 */
	Collection<T> recipes();

	/**
	 * @return null if this type of recipe has no jei integration
	 */
	@Nullable
	String getJEICategory();

	@Nullable
	Object getJeiWrapper(T recipe);
}
