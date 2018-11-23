package binnie.extratrees.alcohol;

import javax.annotation.Nullable;
import java.util.Locale;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import binnie.core.Constants;
import binnie.core.util.I18N;
import binnie.extratrees.modules.ModuleAlcohol;

public enum GlasswareType {
	BEER_MUG(480, 10, 14, " # ", "   ", "   "),
	PINT(600, 6, 20, " # ", " # ", "# #"),
	SNIFTER(510, 14, 11, " # ", "   ", "# #"),
	FLUTE(180, 13, 15, "# #", "# #", "# #"),
	COCKTAIL(240, 20, 8, " # ", "# #", "# #"),
	CORDIAL(60, 15, 7, "###", " # ", "# #"),
	COLLINS(360, 8, 18, " # ", " # ", "   "),
	HIGHBALL(240, 10, 14, " # ", " # ", "###"),
	HURRICANE(450, 10, 18, "   ", "# #", " # "),
	MARGARITA(360, 18, 9, "###", "   ", "# #"),
	OLD_FASHIONED(240, 13, 8, " # ", "   ", "###"),
	WINE(240, 17, 10, " # ", "# #", "   "),
	SHOT(30, 13, 7, "###", " # ", " # "),
	SHERRY(60, 17, 7, " # ", "# #", "###"),
	COUPE(180, 19, 8, " # ", "# #", " # ");

	public static final float SPRITE_PIXELS = 32.0F;
	//TODO: Add to config
	public static final int MB_PER_GLASS = 100;

	private final String name;
	private final float contentBottom;
	private final float contentHeight;
	private final int capacity;
	private final ModelResourceLocation modelLocation;
	private final String[] recipe;
	private final int recipeGlassCost;

	GlasswareType(int capacity, int contentBottom, int contentHeight, String... recipe) {
		this.capacity = capacity;
		this.contentBottom = contentBottom / SPRITE_PIXELS;
		this.contentHeight = contentHeight / SPRITE_PIXELS;
		this.recipe = recipe;
		StringBuilder builder = new StringBuilder();
		for (String s : recipe) {
			builder.append(s);
		}
		String recipeString = builder.toString();
		String recipeGlass = recipeString.replaceAll("#", "");
		recipeGlassCost = recipeGlass.length() * MB_PER_GLASS;

		name = name().toLowerCase(Locale.ENGLISH).replaceAll("_", "");
		modelLocation = new ModelResourceLocation(Constants.EXTRA_TREES_MOD_ID + ":glassware/" + name, "inventory");
	}

	public String getName() {
		return name;
	}

	public String getName(@Nullable String liquid) {
		if (liquid == null) {
			return I18N.localise("extratrees.item.glassware." + name);
		}
		return I18N.localise("extratrees.item.glassware." + name + ".usage", liquid);
	}

	public int getCapacity() {
		return this.capacity;
	}

	public ModelResourceLocation getModelLocation() {
		return modelLocation;
	}

	public ItemStack get(int i) {
		return ModuleAlcohol.drink.getStack(this, null);
	}

	public float getContentBottom() {
		return this.contentBottom;
	}

	public float getContentHeight() {
		return this.contentHeight;
	}

	public int getVolume() {
		return this.getCapacity();
	}

	public Object[] getRecipePattern(Item castingItem) {
		return new Object[]{recipe, '#', castingItem};
	}

	public int getRecipeGlassCost() {
		return recipeGlassCost;
	}
}
