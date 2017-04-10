package binnie.extratrees.block.decor;

import binnie.extratrees.block.IPlankType;
import binnie.extratrees.block.WoodManager;
import net.minecraft.item.ItemStack;

public class MultiFenceRecipePattern{
	public static MultiFenceRecipePattern[] VALUES;
	
	static{
		VALUES = new MultiFenceRecipePattern[] {
			new MultiFenceRecipePattern(0, "010"+"0 0"+"   "),
			new MultiFenceRecipePattern(0, "000"+"0 0"+"   "),
			new MultiFenceRecipePattern(1, "010"+"0 0"+" 1 "),
			new MultiFenceRecipePattern(1, "000"+"0 0"+" 0 "),
			new MultiFenceRecipePattern(2, " 0 "+"1 1"+"101"),
			new MultiFenceRecipePattern(2, " 0 "+"0 0"+"000")
		};
	}
	
	private final String pattern;
	private final int size;
	
	private MultiFenceRecipePattern(int size, String pattern) {
		this.size = size;
		this.pattern = pattern;
	}
	
	public boolean matches(String recipePattern){
		return pattern.contains(recipePattern);
	}
	
	public ItemStack createFence(IPlankType plank, IPlankType plankSecondary){
		return WoodManager.getFence(plank, plankSecondary, new FenceType(size, false, false), 4);
	}
}
