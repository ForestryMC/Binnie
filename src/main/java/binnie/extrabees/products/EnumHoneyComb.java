// 
// Decompiled by Procyon v0.5.30
// 

package binnie.extrabees.products;

import net.minecraftforge.oredict.OreDictionary;
import binnie.extrabees.ExtraBees;
import forestry.api.recipes.RecipeManagers;
import java.util.LinkedHashMap;
import net.minecraft.item.ItemStack;
import java.util.Map;
import binnie.core.item.IItemEnum;

public enum EnumHoneyComb implements IItemEnum
{
	BARREN(7564356, 12762791),
	ROTTEN(4084257, 11652233),
	BONE(12895407, 14606017),
	OIL(394760, 2894646),
	COAL(10392696, 3682590),
	FUEL(10252096, 16761856),
	WATER(2568911, 7973065),
	MILK(14145991, 16777215),
	FRUIT(8202548, 14372706),
	SEED(3428147, 7457902),
	ALCOHOL(4293921, 14604622),
	STONE(9211025, 13027020),
	REDSTONE(16422550, 15077392),
	RESIN(16762703, 13208064),
	IC2ENERGY(15332623, 2143177),
	IRON(3552564, 11038808),
	GOLD(3552564, 15125515),
	COPPER(3552564, 13722376),
	TIN(3552564, 12431805),
	SILVER(3552564, 14408667),
	BRONZE,
	URANIUM(2031360, 4303667),
	CLAY(7034426, 11583702),
	OLD(4535060, 11769444),
	FUNGAL(7234891, 2856003),
	CREOSOTE(10256652, 12429911),
	LATEX(5854529, 11051653),
	ACIDIC(3441987, 1374014),
	VENOMOUS(8198269, 16724991),
	SLIME(3884860, 8442245),
	BLAZE(16738816, 16763904),
	COFFEE(5519389, 11763531),
	GLACIAL(5146503, 13366002),
	MINT,
	CITRUS,
	PEAT,
	SHADOW(0, 3545141),
	LEAD(3552564, 10125468),
	BRASS,
	ELECTRUM,
	ZINC(3552564, 15592447),
	TITANIUM(3552564, 11578083),
	TUNGSTEN(3552564, 1249812),
	STEEL,
	IRIDIUM,
	PLATINUM(3552564, 10125468),
	LAPIS(3552564, 4009179),
	SODALITE,
	PYRITE,
	BAUXITE,
	CINNABAR,
	SPHALERITE,
	EMERALD(3552564, 1900291),
	RUBY(3552564, 14024704),
	SAPPHIRE(3552564, 673791),
	OLIVINE,
	DIAMOND(3552564, 8371706),
	RED(13388876, 16711680),
	YELLOW(15066419, 16768256),
	BLUE(10072818, 8959),
	GREEN(6717235, 39168),
	BLACK(1644825, 5723991),
	WHITE(14079702, 16777215),
	BROWN(8349260, 6042895),
	ORANGE(15905331, 16751872),
	CYAN(5020082, 65509),
	PURPLE(11691749, 11403519),
	GRAY(5000268, 12237498),
	LIGHTBLUE(10072818, 40447),
	PINK(15905484, 16744671),
	LIMEGREEN(8375321, 65288),
	MAGENTA(15040472, 16711884),
	LIGHTGRAY(10066329, 13224393),
	NICKEL(3552564, 16768764),
	INVAR,
	GLOWSTONE(10919006, 14730249),
	SALTPETER(10919006, 14730249),
	PULP,
	MULCH,
	COMPOST(4338440, 7036475),
	SAWDUST(12561009, 15913854),
	CERTUS(13029631, 3755363),
	ENDERPEARL(3446662, 206368),
	YELLORIUM(2564173, 14019840),
	CYANITE(2564173, 34541),
	BLUTONIUM(2564173, 1769702);

	int[] colour;
	public Map<ItemStack, Float> products;
	boolean active;
	public boolean deprecated;

	private EnumHoneyComb() {
		this(16777215, 16777215);
		this.active = false;
		this.deprecated = true;
	}

	private EnumHoneyComb(final int colour, final int colour2) {
		this.colour = new int[0];
		this.products = new LinkedHashMap<ItemStack, Float>();
		this.active = true;
		this.deprecated = false;
		this.colour = new int[] { colour, colour2 };
	}

	public void addRecipe() {
		RecipeManagers.centrifugeManager.addRecipe(20, this.get(1), this.products);
	}

	@Override
	public boolean isActive() {
		return this.active;
	}

	public static EnumHoneyComb get(final ItemStack itemStack) {
		final int i = itemStack.getItemDamage();
		if (i >= 0 && i < values().length) {
			return values()[i];
		}
		return values()[0];
	}

	@Override
	public ItemStack get(final int count) {
		return new ItemStack(ExtraBees.comb, count, this.ordinal());
	}

	@Override
	public String getName(final ItemStack itemStack) {
		return ExtraBees.proxy.localise("item.comb." + this.name().toLowerCase());
	}

	public boolean addProduct(final ItemStack item, final Float chance) {
		if (item == null) {
			return false;
		}
		this.products.put(item.copy(), chance);
		return true;
	}

	public void tryAddProduct(final ItemStack item, final Float chance) {
		this.active = this.addProduct(item, chance);
	}

	public void tryAddProduct(final String oreDict, final Float chance) {
		if (!OreDictionary.getOres(oreDict).isEmpty()) {
			this.tryAddProduct(OreDictionary.getOres(oreDict).get(0), chance);
		}
		else {
			this.active = false;
		}
	}

	public void tryAddProduct(final IItemEnum type, final Float chance) {
		this.tryAddProduct(type.get(1), chance);
		this.active = (this.active && type.isActive());
	}

	public void copyProducts(final EnumHoneyComb comb) {
		this.products.putAll(comb.products);
	}
}
