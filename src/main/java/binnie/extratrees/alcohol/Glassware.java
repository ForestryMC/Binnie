package binnie.extratrees.alcohol;

import binnie.Binnie;
import binnie.Constants;
import binnie.extratrees.ExtraTrees;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Locale;

public enum Glassware {
	BeerMug(480, 10, 14, " # ", "   ", "   "),
	Pint(600, 6, 20, " # ", " # ", "# #"),
	Snifter(510, 14, 11, " # ", "   ", "# #"),
	Flute(180, 13, 15, "# #", "# #", "# #"),
	Cocktail(240, 20, 8, " # ", "# #", "# #"),
	Cordial(60, 15, 7, "###", " # ", "# #"),
	Collins(360, 8, 18, " # ", " # ", "   "),
	Highball(240, 10, 14, " # ", " # ", "###"),
	Hurricane(450, 10, 18, "   ", "# #", " # "),
	Margarita(360, 18, 9, "###", "   ", "# #"),
	OldFashioned(240, 13, 8, " # ", "   ", "###"),
	Wine(240, 17, 10, " # ", "# #", "   "),
	Shot(30, 13, 7, "###", " # ", " # "),
	Sherry(60, 17, 7, " # ", "# #", "###"),
	Coupe(180, 19, 8, " # ", "# #", " # ");

	float contentBottom;
	float contentHeight;
	private int capacity;
	private ModelResourceLocation location;
	private String recipeRow1, recipeRow2, recipeRow3;
	private int recipeGlassCost;

	Glassware(final int capacity, final int contentBottom, final int contentHeight, String recipeRow1, String recipeRow2, String recipeRow3) {
		this.capacity = capacity;
		this.contentBottom = contentBottom / 32.0f;
		this.contentHeight = contentHeight / 32.0f;
		location = new ModelResourceLocation(Constants.EXTRA_TREES_MOD_ID + ":glassware/" + getName(), "inventory");
		this.recipeRow1 = recipeRow1;
		this.recipeRow2 = recipeRow2;
		this.recipeRow3 = recipeRow3;
		recipeGlassCost = (recipeRow1 + recipeRow2 + recipeRow3).replaceAll("#", "").length() * 100;
	}

	public String getName() {
		return name().toLowerCase(Locale.ENGLISH);
	}

	public String getName(@Nullable String liquid) {
		if (liquid == null) {
			return ExtraTrees.proxy.localise("item.glassware." + this.name().toLowerCase());
		}
		return Binnie.LANGUAGE.localise(ExtraTrees.instance, "item.glassware." + this.name().toLowerCase() + ".usage", liquid);
	}

	public int getCapacity() {
		return this.capacity;
	}

	public ModelResourceLocation getLocation() {
		return location;
	}

	public ItemStack get(final int i) {
		return ExtraTrees.alcohol().drink.getStack(this, null);
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
		return new Object[]{recipeRow1, recipeRow2, recipeRow3, '#', castingItem};
	}

	public int getRecipeGlassCost() {
		return recipeGlassCost;
	}
}
