package binnie.extratrees.liquid;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import binnie.core.liquid.DrinkManager;
import binnie.extratrees.alcohol.GlasswareType;
import binnie.extratrees.alcohol.ICocktailIngredient;
import binnie.extratrees.alcohol.ICocktailIngredientProvider;
import binnie.extratrees.alcohol.drink.DrinkLiquid;

public enum Cocktail {
	Bellini("Bellini", GlasswareType.FLUTE, 15974764),
	BlackRussian("Black Russian", GlasswareType.OLD_FASHIONED, 3347992),
	BloodyMary("Bloody Mary", GlasswareType.HIGHBALL, 12660480),
	Cosmopolitan("Cosmopolitan", GlasswareType.COCKTAIL, 14811136),
	CubaLibre("Cuba Libre", GlasswareType.HIGHBALL, 9044996),
	FrenchConnection("French Connection", GlasswareType.OLD_FASHIONED, 16166183),
	GodFather("God Father", GlasswareType.OLD_FASHIONED, 13134109),
	GodMother("God Mother", GlasswareType.OLD_FASHIONED, 14644737),
	Grasshopper("Grasshopper", GlasswareType.COCKTAIL, 4441194),
	French75("French 75", GlasswareType.FLUTE, 15521949),
	HarveyWallbanger("Harvey Wallbanger", GlasswareType.HIGHBALL, 16371968),
	HemingwaySpecial("Hemingway Special", GlasswareType.COCKTAIL, 15201205),
	HorsesNeck("Horse's Neck", GlasswareType.OLD_FASHIONED, 16692480),
	IrishCoffee("Irish Coffee", GlasswareType.WINE, 4460034),
	Kir("Kir", GlasswareType.WINE, 16004638),
	Bramble("Bramble", GlasswareType.OLD_FASHIONED, 12804974),
	B52("B-52", GlasswareType.SHOT, 12934656),
	DarkNStormy("Dark 'N' Stormy", GlasswareType.HIGHBALL, 12215848),
	DirtyMartini("Dirty Martini", GlasswareType.COCKTAIL, 14332499),
	ExpressoMartini("Expresso Martini", GlasswareType.COCKTAIL, 6498346),
	FrenchMartini("French Martini", GlasswareType.COCKTAIL, 12660045),
	Kamikaze("Kamikaze", GlasswareType.COCKTAIL, 14801069),
	LemonDropMartini("Lemon Drop Martini", GlasswareType.COCKTAIL, 16375437),
	PiscoSour("Pisco Sour", GlasswareType.OLD_FASHIONED, 15394463),
	RussianSpringPunch("Russian Spring Punch", GlasswareType.HIGHBALL, 11805740),
	SpritzVeneziano("Spritz Veneziano", GlasswareType.OLD_FASHIONED, 15355648),
	TommysMargarita("Tommy's Margartita", GlasswareType.COCKTAIL, 14867592),
	Vesper("Vesper", GlasswareType.COCKTAIL, 15658732),
	SexOnTheBeach("Sex on the Beach", GlasswareType.HIGHBALL, 16677426);

	static final Map<String, ICocktailIngredient> cocktailIngredients = new HashMap<>();

	static {
		Cocktail.Bellini.add(Alcohol.SparklingWine, 2);
		Cocktail.Bellini.add(Juice.Peach, 1);
		Cocktail.BlackRussian.add(Spirit.Vodka, 5);
		Cocktail.BlackRussian.add(Liqueur.Coffee, 2);
		Cocktail.BloodyMary.add(Spirit.Vodka, 3);
		Cocktail.BloodyMary.add(Juice.Tomato, 6);
		Cocktail.BloodyMary.add(Juice.Lemon, 1);
		Cocktail.Cosmopolitan.add(Spirit.Vodka, 3);
		Cocktail.Cosmopolitan.add(Liqueur.Orange, 1);
		Cocktail.Cosmopolitan.add(Juice.Lime, 1);
		Cocktail.Cosmopolitan.add(Juice.Cranberry, 2);
		Cocktail.CubaLibre.add(MiscFluid.CarbonatedWater, 12);
		Cocktail.CubaLibre.add(Spirit.WhiteRum, 5);
		Cocktail.CubaLibre.add(Juice.Lime, 1);
		Cocktail.FrenchConnection.add(Spirit.Brandy, 1);
		Cocktail.FrenchConnection.add(Liqueur.Almond, 1);
		Cocktail.GodFather.add(Spirit.Whiskey, 1);
		Cocktail.GodFather.add(Liqueur.Almond, 1);
		Cocktail.GodMother.add(Spirit.Vodka, 1);
		Cocktail.GodFather.add(Liqueur.Almond, 1);
		Cocktail.Grasshopper.add(Liqueur.Mint, 1);
		Cocktail.Grasshopper.add(Liqueur.Chocolate, 1);
		Cocktail.Grasshopper.add(MiscFluid.Cream, 1);
		Cocktail.French75.add(Spirit.Gin, 2);
		Cocktail.French75.add(Juice.Lemon, 1);
		Cocktail.French75.add(Alcohol.SparklingWine, 4);
		Cocktail.HarveyWallbanger.add(Spirit.Vodka, 3);
		Cocktail.HarveyWallbanger.add(Liqueur.Herbal, 1);
		Cocktail.HarveyWallbanger.add(Juice.Orange, 6);
		Cocktail.HemingwaySpecial.add(Spirit.WhiteRum, 4);
		Cocktail.HemingwaySpecial.add(Juice.Grapefruit, 3);
		Cocktail.HemingwaySpecial.add(Liqueur.Cherry, 1);
		Cocktail.HemingwaySpecial.add(Juice.Lime, 1);
		Cocktail.HorsesNeck.add(Spirit.Brandy, 1);
		Cocktail.HorsesNeck.add(MiscFluid.GingerAle, 3);
		Cocktail.IrishCoffee.add(Spirit.Whiskey, 2);
		Cocktail.IrishCoffee.add(MiscFluid.Coffee, 4);
		Cocktail.IrishCoffee.add(MiscFluid.Cream, 2);
		Cocktail.Kir.add(Alcohol.WhiteWine, 9);
		Cocktail.Kir.add(Liqueur.Blackcurrant, 1);
		Cocktail.Bramble.add(Spirit.Gin, 4);
		Cocktail.Bramble.add(Juice.Lemon, 2);
		Cocktail.Bramble.add(MiscFluid.SugarSyrup, 1);
		Cocktail.Bramble.add(Liqueur.Blackberry, 2);
		Cocktail.B52.add(Liqueur.Coffee, 1);
		Cocktail.B52.add(Liqueur.Orange, 1);
		Cocktail.DarkNStormy.add(Spirit.DarkRum, 1);
		Cocktail.DarkNStormy.add(MiscFluid.GingerAle, 3);
		Cocktail.DirtyMartini.add(Spirit.Vodka, 6);
		Cocktail.DirtyMartini.add(Spirit.FortifiedWine, 1);
		Cocktail.DirtyMartini.add(Juice.Olive, 1);
		Cocktail.ExpressoMartini.add(Spirit.Vodka, 5);
		Cocktail.ExpressoMartini.add(Liqueur.Coffee, 1);
		Cocktail.ExpressoMartini.add(MiscFluid.SugarSyrup, 1);
		Cocktail.ExpressoMartini.add(MiscFluid.Coffee, 1);
		Cocktail.FrenchMartini.add(Spirit.Vodka, 3);
		Cocktail.FrenchMartini.add(Liqueur.Raspberry, 1);
		Cocktail.FrenchMartini.add(Juice.Pineapple, 1);
		Cocktail.Kamikaze.add(Spirit.Vodka, 1);
		Cocktail.Kamikaze.add(Liqueur.Orange, 1);
		Cocktail.Kamikaze.add(Juice.Lime, 1);
		Cocktail.LemonDropMartini.add(Spirit.Vodka, 5);
		Cocktail.LemonDropMartini.add(Liqueur.Orange, 4);
		Cocktail.LemonDropMartini.add(Juice.Lemon, 3);
		Cocktail.PiscoSour.add(Spirit.Brandy, 8);
		Cocktail.PiscoSour.add(Juice.Lemon, 4);
		Cocktail.PiscoSour.add(MiscFluid.SugarSyrup, 3);
		Cocktail.RussianSpringPunch.add(Spirit.Vodka, 5);
		Cocktail.RussianSpringPunch.add(Juice.Lemon, 5);
		Cocktail.RussianSpringPunch.add(Liqueur.Blackcurrant, 3);
		Cocktail.RussianSpringPunch.add(MiscFluid.SugarSyrup, 1);
		Cocktail.SpritzVeneziano.add(Alcohol.SparklingWine, 6);
		Cocktail.SpritzVeneziano.add(MiscFluid.CarbonatedWater, 1);
		Cocktail.TommysMargarita.add(Spirit.Tequila, 6);
		Cocktail.TommysMargarita.add(Juice.Lime, 2);
		Cocktail.TommysMargarita.add(MiscFluid.AgaveNectar, 1);
		Cocktail.Vesper.add(Spirit.Gin, 8);
		Cocktail.Vesper.add(Spirit.Vodka, 2);
		Cocktail.Vesper.add(Spirit.FortifiedWine, 1);
		Cocktail.SexOnTheBeach.add(Spirit.Vodka, 2);
		Cocktail.SexOnTheBeach.add(Liqueur.Peach, 1);
		Cocktail.SexOnTheBeach.add(Juice.Orange, 2);
		Cocktail.SexOnTheBeach.add(Juice.Cranberry, 2);
	}

	public String name;
	public GlasswareType glasswareType;
	public int colour;
	public Map<ICocktailIngredient, Integer> ingredients;

	Cocktail(final String name, final GlasswareType glasswareType, final int colour) {
		this.ingredients = new HashMap<>();
		this.name = name;
		this.glasswareType = glasswareType;
		this.colour = colour;
	}

	@Nullable
	public static Cocktail get(final Map<ICocktailIngredient, Integer> ingredients) {
		for (final Cocktail cocktail : values()) {
			boolean is = true;
			for (final Map.Entry<ICocktailIngredient, Integer> entry : ingredients.entrySet()) {
				if (!Objects.equals(cocktail.ingredients.get(entry.getKey()), entry.getValue())) {
					is = false;
				}
			}
			if (is) {
				return cocktail;
			}
		}
		return null;
	}

	public static void registerIngredient(final ICocktailIngredient ingredient) {
		String id = ingredient.getIdentifier().toLowerCase();
		Cocktail.cocktailIngredients.put(id, ingredient);
		DrinkLiquid liquid = new DrinkLiquid(ingredient.getDisplayName(), ingredient.getColor(), ingredient.getTransparency(), ingredient.getABV(), id);
		DrinkManager.registerDrinkLiquid(liquid);
	}

	public static void registerIngredient(ICocktailIngredientProvider ingredient) {
		registerIngredient(ingredient.getIngredient());
	}

	public static void registerIngredients(ICocktailIngredientProvider[] ingredients) {
		for (ICocktailIngredientProvider ingredient : ingredients) {
			registerIngredient(ingredient.getIngredient());
		}
	}

	public static ICocktailIngredient getIngredient(final String name2) {
		return Cocktail.cocktailIngredients.get(name2.toLowerCase());
	}

	public static boolean isIngredient(final String name) {
		return Cocktail.cocktailIngredients.containsKey(name.toLowerCase());
	}

	private void add(final ICocktailIngredient ingredient, final int ratio) {
		this.ingredients.put(ingredient, ratio);
	}

	private void add(final ICocktailIngredientProvider ingredient, final int ratio) {
		add(ingredient.getIngredient(), ratio);
	}
}
