package binnie.extratrees.blocks.decor;

import binnie.extratrees.wood.WoodManager;
import binnie.extratrees.wood.planks.IPlankType;
import net.minecraft.item.ItemStack;

public class MultiFenceRecipePattern {
	public static final MultiFenceRecipePattern[] VALUES = new MultiFenceRecipePattern[]{
		new MultiFenceRecipePattern(0, 2, "010" + "0 0" + "   "),
		new MultiFenceRecipePattern(0, 1, "000" + "0 0" + "   "),
		new MultiFenceRecipePattern(1, 2, "010" + "0 0" + " 1 "),
		new MultiFenceRecipePattern(1, 1, "000" + "0 0" + " 0 "),
		new MultiFenceRecipePattern(2, 2, " 1 " + "0 0" + "010"),
		new MultiFenceRecipePattern(2, 1, " 0 " + "0 0" + "000")
	};

	private final String pattern;
	private final int size;
	private final int typeCount;

	private MultiFenceRecipePattern(int size, int typeCount, String pattern) {
		this.size = size;
		this.typeCount = typeCount;
		this.pattern = pattern;
	}

	public String getPattern() {
		return pattern;
	}

	public int getTypeCount() {
		return typeCount;
	}

	public boolean matches(String recipePattern) {
		return pattern.contains(recipePattern);
	}

	public ItemStack createFence(IPlankType plank, IPlankType plankSecondary) {
		return WoodManager.getFence(plank, plankSecondary, new FenceType(size, false, false), 3);
	}
}
