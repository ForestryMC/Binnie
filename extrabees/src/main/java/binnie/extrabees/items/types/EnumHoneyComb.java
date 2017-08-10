package binnie.extrabees.items.types;

import binnie.core.Mods;
import com.google.common.base.MoreObjects;

import javax.annotation.Nullable;
import java.util.LinkedHashMap;
import java.util.Map;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import net.minecraftforge.oredict.OreDictionary;

import forestry.api.recipes.RecipeManagers;

import binnie.core.util.I18N;
import binnie.extrabees.ExtraBees;
import binnie.extrabees.utils.Utils;

public enum EnumHoneyComb implements IEBEnumItem {

	BARREN(7564356, 12762791){
		@Override
		protected void addSubtypes(ItemStack beeswax, ItemStack honeyDrop) {
			addProduct(beeswax, 1.00f);
			addProduct(honeyDrop, 0.50f);
		}
	},
	ROTTEN(4084257, 11652233){
		@Override
		protected void addSubtypes(ItemStack beeswax, ItemStack honeyDrop) {
			addProduct(beeswax, 0.20f);
			addProduct(honeyDrop, 0.20f);
			addProduct(new ItemStack(Items.ROTTEN_FLESH, 1, 0), 0.80f);
		}
	},
	BONE(12895407, 14606017){
		@Override
		protected void addSubtypes(ItemStack beeswax, ItemStack honeyDrop) {
			addProduct(beeswax, 0.20f);
			addProduct(honeyDrop, 0.20f);
			addProduct(new ItemStack(Items.DYE, 1, 15), 0.80f);
		}
	},
	OIL(394760, 2894646){
		@Override
		protected void addSubtypes(ItemStack beeswax, ItemStack honeyDrop) {
			tryAddProduct(EnumPropolis.OIL, 0.60f);
			addProduct(honeyDrop, 0.75f);
		}
	},
	COAL(10392696, 3682590){
		@Override
		protected void addSubtypes(ItemStack beeswax, ItemStack honeyDrop) {
			addProduct(beeswax, 0.80f);
			addProduct(honeyDrop, 0.75f);
			tryAddProduct(ExtraBeeItems.CoalDust, 1.00f);
		}
	},
	FUEL(10252096, 16761856){
		@Override
		protected void addSubtypes(ItemStack beeswax, ItemStack honeyDrop) {
			tryAddProduct(EnumPropolis.FUEL, 0.60f);
			addProduct(honeyDrop, 0.50f);
		}
	},
	WATER(2568911, 7973065){
		@Override
		protected void addSubtypes(ItemStack beeswax, ItemStack honeyDrop) {
			tryAddProduct(EnumPropolis.WATER, 1.00f);
			addProduct(honeyDrop, 0.90f);
		}
	},
	MILK(14145991, 16777215){
		@Override
		protected void addSubtypes(ItemStack beeswax, ItemStack honeyDrop) {
			tryAddProduct(EnumHoneyDrop.MILK, 1.00f);
			addProduct(honeyDrop, 0.90f);
		}
	},
	FRUIT(8202548, 14372706){
		@Override
		protected void addSubtypes(ItemStack beeswax, ItemStack honeyDrop) {
			tryAddProduct(EnumHoneyDrop.APPLE, 1.00f);
			addProduct(honeyDrop, 0.90f);
		}
	},
	SEED(3428147, 7457902){
		@Override
		protected void addSubtypes(ItemStack beeswax, ItemStack honeyDrop) {
			tryAddProduct(EnumHoneyDrop.SEED, 1.00f);
			addProduct(honeyDrop, 0.90f);
		}
	},
	ALCOHOL(4293921, 14604622){
		@Override
		protected void addSubtypes(ItemStack beeswax, ItemStack honeyDrop) {
			tryAddProduct(EnumHoneyDrop.ALCOHOL, 1.00f);
			addProduct(honeyDrop, 0.90f);
		}
	},
	STONE(9211025, 13027020){
		@Override
		protected void addSubtypes(ItemStack beeswax, ItemStack honeyDrop) {
			addProduct(beeswax, 0.50f);
			addProduct(honeyDrop, 0.25f);
		}
	},
	REDSTONE(16422550, 15077392){
		@Override
		protected void addSubtypes(ItemStack beeswax, ItemStack honeyDrop) {
			addProduct(beeswax, 0.80f);
			addProduct(new ItemStack(Items.REDSTONE, 1, 0), 1.00f);
			addProduct(honeyDrop, 0.50f);
		}
	},
	RESIN(16762703, 13208064){
		@Override
		protected void addSubtypes(ItemStack beeswax, ItemStack honeyDrop) {
			addProduct(beeswax, 1.00f);
			tryAddProduct(Utils.getIC2Item("itemHarz"), 1.00f);
			tryAddProduct(Utils.getIC2Item("itemHarz"), 0.50f);
		}
	},
	IC2ENERGY(15332623, 2143177){
		@Override
		protected void addSubtypes(ItemStack beeswax, ItemStack honeyDrop) {
			addProduct(beeswax, 0.80f);
			addProduct(new ItemStack(Items.REDSTONE, 1, 0), 0.75f);
			tryAddProduct(EnumHoneyDrop.ENERGY, 1.00f);
		}
	},
	IRON(3552564, 11038808){
		@Override
		protected void addSubtypes(ItemStack beeswax, ItemStack honeyDrop) {
			copyProducts(EnumHoneyComb.STONE);
			tryAddProduct(ExtraBeeItems.IronDust, 1.00f);
		}
	},
	GOLD(3552564, 15125515){
		@Override
		protected void addSubtypes(ItemStack beeswax, ItemStack honeyDrop) {
			copyProducts(EnumHoneyComb.STONE);
			tryAddProduct(ExtraBeeItems.GoldDust, 1.00f);
		}
	},
	COPPER(3552564, 13722376){
		@Override
		protected void addSubtypes(ItemStack beeswax, ItemStack honeyDrop) {
			copyProducts(EnumHoneyComb.STONE);
			tryAddProduct(ExtraBeeItems.CopperDust, 1.00f);
		}
	},
	TIN(3552564, 12431805){
		@Override
		protected void addSubtypes(ItemStack beeswax, ItemStack honeyDrop) {
			copyProducts(EnumHoneyComb.STONE);
			tryAddProduct(ExtraBeeItems.TinDust, 1.00f);
		}
	},
	SILVER(3552564, 14408667){
		@Override
		protected void addSubtypes(ItemStack beeswax, ItemStack honeyDrop) {
			copyProducts(EnumHoneyComb.STONE);
			tryAddProduct(ExtraBeeItems.SilverDust, 1.00f);
		}
	},
	BRONZE,
	URANIUM(2031360, 4303667){
		@Override
		protected void addSubtypes(ItemStack beeswax, ItemStack honeyDrop) {
			copyProducts(EnumHoneyComb.STONE);
			tryAddProduct("crushedUranium", 0.50f);
		}
	},
	CLAY(7034426, 11583702){
		@Override
		protected void addSubtypes(ItemStack beeswax, ItemStack honeyDrop) {
			addProduct(beeswax, 0.25f);
			addProduct(honeyDrop, 0.80f);
			addProduct(new ItemStack(Items.CLAY_BALL), 0.80f);
		}
	},
	OLD(4535060, 11769444){
		@Override
		protected void addSubtypes(ItemStack beeswax, ItemStack honeyDrop) {
			addProduct(beeswax, 1.00f);
			addProduct(honeyDrop, 0.90f);
		}
	},
	FUNGAL(7234891, 2856003){
		@Override
		protected void addSubtypes(ItemStack beeswax, ItemStack honeyDrop) {
			addProduct(beeswax, 0.90f);
			addProduct(new ItemStack(Blocks.BROWN_MUSHROOM_BLOCK, 1, 0), 1.00f);
			addProduct(new ItemStack(Blocks.RED_MUSHROOM_BLOCK, 1, 0), 0.75f);
		}
	},
	CREOSOTE(10256652, 12429911){
		@Override
		protected void addSubtypes(ItemStack beeswax, ItemStack honeyDrop) {
			tryAddProduct(EnumPropolis.CREOSOTE, 0.70f);
			addProduct(honeyDrop, 0.50f);
		}
	},
	LATEX(5854529, 11051653){
		@Override
		protected void addSubtypes(ItemStack beeswax, ItemStack honeyDrop) {
			addProduct(honeyDrop, 0.50f);
			addProduct(beeswax, 0.85f);
			tryAddProduct("itemRubber", 1.00f);
		}
	},
	ACIDIC(3441987, 1374014){
		@Override
		protected void addSubtypes(ItemStack beeswax, ItemStack honeyDrop) {
			addProduct(beeswax, 0.80f);
			tryAddProduct(EnumHoneyDrop.ACID, 0.50f);
			tryAddProduct("dustSulfur", 0.75f);
		}
	},
	VENOMOUS(8198269, 16724991){
		@Override
		protected void addSubtypes(ItemStack beeswax, ItemStack honeyDrop) {
			addProduct(beeswax, 0.80f);
			tryAddProduct(EnumHoneyDrop.POISON, 0.80f);
		}
	},
	SLIME(3884860, 8442245){
		@Override
		protected void addSubtypes(ItemStack beeswax, ItemStack honeyDrop) {
			addProduct(beeswax, 1.00f);
			addProduct(honeyDrop, 0.75f);
			addProduct(new ItemStack(Items.SLIME_BALL, 1, 0), 0.75f);
		}
	},
	BLAZE(16738816, 16763904){
		@Override
		protected void addSubtypes(ItemStack beeswax, ItemStack honeyDrop) {
			addProduct(beeswax, 0.75f);
			addProduct(new ItemStack(Items.BLAZE_POWDER, 1, 0), 1.00f);
		}
	},
	COFFEE(5519389, 11763531){
		@Override
		protected void addSubtypes(ItemStack beeswax, ItemStack honeyDrop) {
			addProduct(beeswax, 0.90f);
			addProduct(honeyDrop, 0.75f);
			tryAddProduct(Utils.getIC2Item("itemCofeePowder"), 0.75f);
		}
	},
	GLACIAL(5146503, 13366002){
		@Override
		protected void addSubtypes(ItemStack beeswax, ItemStack honeyDrop) {
			tryAddProduct(EnumHoneyDrop.ICE, 0.80f);
			addProduct(honeyDrop, 0.75f);
		}
	},
	MINT,
	CITRUS,
	PEAT,
	SHADOW(0, 3545141){
		@Override
		protected void addSubtypes(ItemStack beeswax, ItemStack honeyDrop) {
			addProduct(honeyDrop, 0.50f);
			tryAddProduct("dustObsidian", 0.75f);
		}
	},
	LEAD(3552564, 10125468){
		@Override
		protected void addSubtypes(ItemStack beeswax, ItemStack honeyDrop) {
			copyProducts(EnumHoneyComb.STONE);
			tryAddProduct(ExtraBeeItems.LeadDust, 1.00f);
		}
	},
	BRASS,
	ELECTRUM,
	ZINC(3552564, 15592447){
		@Override
		protected void addSubtypes(ItemStack beeswax, ItemStack honeyDrop) {
			copyProducts(EnumHoneyComb.STONE);
			tryAddProduct(ExtraBeeItems.ZincDust, 1.00f);
		}
	},
	TITANIUM(3552564, 11578083){
		@Override
		protected void addSubtypes(ItemStack beeswax, ItemStack honeyDrop) {
			copyProducts(EnumHoneyComb.STONE);
			tryAddProduct(ExtraBeeItems.TitaniumDust, 1.00f);
		}
	},
	TUNGSTEN(3552564, 1249812){
		@Override
		protected void addSubtypes(ItemStack beeswax, ItemStack honeyDrop) {
			copyProducts(EnumHoneyComb.STONE);
			tryAddProduct(ExtraBeeItems.TungstenDust, 1.00f);
		}
	},
	STEEL,
	IRIDIUM,
	PLATINUM(3552564, 10125468){
		@Override
		protected void addSubtypes(ItemStack beeswax, ItemStack honeyDrop) {
			copyProducts(EnumHoneyComb.STONE);
			tryAddProduct(ExtraBeeItems.PlatinumDust, 1.00f);
		}
	},
	LAPIS(3552564, 4009179){
		@Override
		protected void addSubtypes(ItemStack beeswax, ItemStack honeyDrop) {
			copyProducts(EnumHoneyComb.STONE);
			addProduct(new ItemStack(Items.DYE, 6, 4), 1.00f);
		}
	},
	/* Tech Reborn*/
	SODALITE(3552564, 1396717){
		@Override
		protected void addSubtypes(ItemStack beeswax, ItemStack honeyDrop) {
			tryAddProduct("dustSmallSodalite", 1.00f);
			tryAddProduct("dustSmallAluminum", 1.00f);
			copyProducts(EnumHoneyComb.STONE);
		}
	},
	PYRITE(3552564, 14919481){
		@Override
		protected void addSubtypes(ItemStack beeswax, ItemStack honeyDrop) {
			tryAddProduct("dustSmallPyrite", 1.00f);
			tryAddProduct("dustSmallIron", 1.00f);
			copyProducts(EnumHoneyComb.STONE);
		}
	},
	BAUXITE(3552564, 10249472){
		@Override
		protected void addSubtypes(ItemStack beeswax, ItemStack honeyDrop) {
			tryAddProduct("dustSmallBauxite", 1.00f);
			tryAddProduct("dustSmallAluminum", 1.00f);
			copyProducts(EnumHoneyComb.STONE);
		}
	},
	CINNABAR(3552564, 4665867){
		@Override
		protected void addSubtypes(ItemStack beeswax, ItemStack honeyDrop) {
			tryAddProduct("dustSmallCinnabar", 1.00f);
			addProduct(new ItemStack(Items.REDSTONE), 0.05f);
			copyProducts(EnumHoneyComb.STONE);
		}
	},
	SPHALERITE(3552564, 14406941){
		@Override
		protected void addSubtypes(ItemStack beeswax, ItemStack honeyDrop) {
			tryAddProduct("dustSmallSphalerite", 1.00f);
			tryAddProduct("dustSmallZinc", 1.00f);
			copyProducts(EnumHoneyComb.STONE);
		}
	},
	/* Gems */
	EMERALD(3552564, 1900291){
		@Override
		protected void addSubtypes(ItemStack beeswax, ItemStack honeyDrop) {
			copyProducts(EnumHoneyComb.STONE);
			tryAddProduct(ExtraBeeItems.EmeraldShard, 1.00f);
		}
	},
	RUBY(3552564, 14024704){
		@Override
		protected void addSubtypes(ItemStack beeswax, ItemStack honeyDrop) {
			copyProducts(EnumHoneyComb.STONE);
			tryAddProduct(ExtraBeeItems.RubyShard, 1.00f);
		}
	},
	SAPPHIRE(3552564, 673791){
		@Override
		protected void addSubtypes(ItemStack beeswax, ItemStack honeyDrop) {
			copyProducts(EnumHoneyComb.STONE);
			tryAddProduct(ExtraBeeItems.SapphireShard, 1.00f);
		}
	},
	OLIVINE,
	DIAMOND(3552564, 8371706){
		@Override
		protected void addSubtypes(ItemStack beeswax, ItemStack honeyDrop) {
			copyProducts(EnumHoneyComb.STONE);
			tryAddProduct(ExtraBeeItems.DiamondShard, 1.00f);
		}
	},
	/* Dyes */
	RED(13388876, 16711680){
		@Override
		protected void addSubtypes(ItemStack beeswax, ItemStack honeyDrop) {
			addDyeSubtypes(beeswax, honeyDrop);
		}
	},
	YELLOW(15066419, 16768256){
		@Override
		protected void addSubtypes(ItemStack beeswax, ItemStack honeyDrop) {
			addDyeSubtypes(beeswax, honeyDrop);
		}
	},
	BLUE(10072818, 8959){
		@Override
		protected void addSubtypes(ItemStack beeswax, ItemStack honeyDrop) {
			addDyeSubtypes(beeswax, honeyDrop);
		}
	},
	GREEN(6717235, 39168){
		@Override
		protected void addSubtypes(ItemStack beeswax, ItemStack honeyDrop) {
			addDyeSubtypes(beeswax, honeyDrop);
		}
	},
	BLACK(1644825, 5723991){
		@Override
		protected void addSubtypes(ItemStack beeswax, ItemStack honeyDrop) {
			addDyeSubtypes(beeswax, honeyDrop);
		}
	},
	WHITE(14079702, 16777215){
		@Override
		protected void addSubtypes(ItemStack beeswax, ItemStack honeyDrop) {
			addDyeSubtypes(beeswax, honeyDrop);
		}
	},
	BROWN(8349260, 6042895){
		@Override
		protected void addSubtypes(ItemStack beeswax, ItemStack honeyDrop) {
			addDyeSubtypes(beeswax, honeyDrop);
		}
	},
	ORANGE(15905331, 16751872){
		@Override
		protected void addSubtypes(ItemStack beeswax, ItemStack honeyDrop) {
			addDyeSubtypes(beeswax, honeyDrop);
		}
	},
	CYAN(5020082, 65509){
		@Override
		protected void addSubtypes(ItemStack beeswax, ItemStack honeyDrop) {
			addDyeSubtypes(beeswax, honeyDrop);
		}
	},
	PURPLE(11691749, 11403519){
		@Override
		protected void addSubtypes(ItemStack beeswax, ItemStack honeyDrop) {
			addDyeSubtypes(beeswax, honeyDrop);
		}
	},
	GRAY(5000268, 12237498){
		@Override
		protected void addSubtypes(ItemStack beeswax, ItemStack honeyDrop) {
			addDyeSubtypes(beeswax, honeyDrop);
		}
	},
	LIGHTBLUE(10072818, 40447){
		@Override
		protected void addSubtypes(ItemStack beeswax, ItemStack honeyDrop) {
			addDyeSubtypes(beeswax, honeyDrop);
		}
	},
	PINK(15905484, 16744671){
		@Override
		protected void addSubtypes(ItemStack beeswax, ItemStack honeyDrop) {
			addDyeSubtypes(beeswax, honeyDrop);
		}
	},
	LIMEGREEN(8375321, 65288){
		@Override
		protected void addSubtypes(ItemStack beeswax, ItemStack honeyDrop) {
			addDyeSubtypes(beeswax, honeyDrop);
		}
	},
	MAGENTA(15040472, 16711884){
		@Override
		protected void addSubtypes(ItemStack beeswax, ItemStack honeyDrop) {
			addDyeSubtypes(beeswax, honeyDrop);
		}
	},
	LIGHTGRAY(10066329, 13224393){
		@Override
		protected void addSubtypes(ItemStack beeswax, ItemStack honeyDrop) {
			addDyeSubtypes(beeswax, honeyDrop);
		}
	},
	NICKEL(3552564, 16768764){
		@Override
		protected void addSubtypes(ItemStack beeswax, ItemStack honeyDrop) {
			copyProducts(EnumHoneyComb.STONE);
			tryAddProduct(ExtraBeeItems.NickelDust, 1.00f);
		}
	},
	INVAR,
	GLOWSTONE(10919006, 14730249){
		@Override
		protected void addSubtypes(ItemStack beeswax, ItemStack honeyDrop) {
			addProduct(honeyDrop, 0.25f);
			addProduct(new ItemStack(Items.GLOWSTONE_DUST), 1.00f);
		}
	},
	SALTPETER(10919006, 14730249){
		@Override
		protected void addSubtypes(ItemStack beeswax, ItemStack honeyDrop) {
			addProduct(honeyDrop, 0.25f);
			tryAddProduct("dustSaltpeter", 1.00f);
		}
	},
	PULP,
	MULCH,
	COMPOST(4338440, 7036475){
		@Override
		protected void addSubtypes(ItemStack beeswax, ItemStack honeyDrop) {
			addProduct(honeyDrop, 0.25f);
			ItemStack compost = Mods.Forestry.stack("fertilizer_bio");
			tryAddProduct(compost, 1.00f);
		}
	},
	SAWDUST(12561009, 15913854){
		@Override
		protected void addSubtypes(ItemStack beeswax, ItemStack honeyDrop) {
			SAWDUST.addProduct(honeyDrop, 0.25f);
			SAWDUST.tryAddProduct("dustSawdust", 1.00f);
			if(!isActive()) {
				tryAddProduct("sawdust", 1.00f);
			}
		}
	},
	CERTUS(13029631, 3755363){
		@Override
		protected void addSubtypes(ItemStack beeswax, ItemStack honeyDrop) {
			addProduct(honeyDrop, 0.25f);
			addProduct(new ItemStack(Items.QUARTZ), 0.25f);
			tryAddProduct("dustCertusQuartz", 0.20f);
		}
	},
	ENDERPEARL(3446662, 206368){
		@Override
		protected void addSubtypes(ItemStack beeswax, ItemStack honeyDrop) {
			addProduct(honeyDrop, 0.25f);
			tryAddProduct("dustEnderPearl", 0.25f);
		}
	},
	YELLORIUM(2564173, 14019840){
		@Override
		protected void addSubtypes(ItemStack beeswax, ItemStack honeyDrop) {
			copyProducts(EnumHoneyComb.STONE);
			tryAddProduct(ExtraBeeItems.YelloriumDust, 0.25f);
		}
	},
	CYANITE(2564173, 34541){
		@Override
		protected void addSubtypes(ItemStack beeswax, ItemStack honeyDrop) {
			copyProducts(EnumHoneyComb.STONE);
			tryAddProduct(ExtraBeeItems.CyaniteDust, 0.25f);
		}
	},
	BLUTONIUM(2564173, 1769702){
		@Override
		protected void addSubtypes(ItemStack beeswax, ItemStack honeyDrop) {
			copyProducts(EnumHoneyComb.STONE);
			tryAddProduct(ExtraBeeItems.BlutoniumDust, 0.25f);
		}
	};

	public int[] colour;
	public Map<ItemStack, Float> products;
	public boolean active;
	public boolean deprecated;

	EnumHoneyComb() {
		this(16777215, 16777215);
		this.active = false;
		this.deprecated = true;
	}

	EnumHoneyComb(final int colour, final int colour2) {
		this.colour = new int[0];
		this.products = new LinkedHashMap<>();
		this.active = true;
		this.deprecated = false;
		this.colour = new int[]{colour, colour2};
	}

	public static EnumHoneyComb get(final ItemStack itemStack) {
		final int i = itemStack.getItemDamage();
		if (i >= 0 && i < values().length) {
			return values()[i];
		}
		return values()[0];
	}

	protected void addSubtypes(ItemStack beeswax, ItemStack honeyDrop){
	}

	protected void addDyeSubtypes(ItemStack beeswax, ItemStack honeyDrop){
		addProduct(honeyDrop, 0.80f);
		addProduct(beeswax, 0.80f);
		int index = ordinal() - EnumHoneyComb.RED.ordinal();
		EnumHoneyDrop drop = EnumHoneyDrop.values()[EnumHoneyDrop.RED.ordinal() + index];
		int[] dyeMetas = {1, 11, 4, 2, 0, 15, 3, 14, 6, 5, 8, 12, 9, 10, 13, 7};
		int meta = dyeMetas[index];
		ItemStack dye = new ItemStack(Items.DYE, 1, meta);
		switch (meta) {
			case 0: {
				dye = ExtraBeeItems.BlackDye.get(1);
				break;
			}
			case 1: {
				dye = ExtraBeeItems.RedDye.get(1);
				break;
			}
			case 2: {
				dye = ExtraBeeItems.GreenDye.get(1);
				break;
			}
			case 3: {
				dye = ExtraBeeItems.BrownDye.get(1);
				break;
			}
			case 4: {
				dye = ExtraBeeItems.BlueDye.get(1);
				break;
			}
			case 11: {
				dye = ExtraBeeItems.YellowDye.get(1);
				break;
			}
			case 15: {
				dye = ExtraBeeItems.WhiteDye.get(1);
				break;
			}
		}
		addProduct(drop.get(1), 1.00f);
		drop.addRemnant(dye);
	}

	public static void addSubtypes() {
		OreDictionary.registerOre("beeComb", new ItemStack(ExtraBees.comb, 1, 32767));
		ItemStack beeswax = Mods.Forestry.stack("beeswax");
		ItemStack honeyDrop = Mods.Forestry.stack("honey_drop");
		for(EnumHoneyComb comb : values()){
			comb.addSubtypes(beeswax, honeyDrop);
		}
	}

	public void addRecipe() {
		if(isActive()) {
			RecipeManagers.centrifugeManager.addRecipe(20, this.get(1), this.products);
		}
	}

	@Override
	public boolean isActive() {
		return this.active;
	}

	@Override
	public ItemStack get(final int size) {
		return new ItemStack(ExtraBees.comb, size, this.ordinal());
	}

	@Override
	public String getName(final ItemStack stack) {
		return I18N.localise("extrabees.item.comb." + this.name().toLowerCase());
	}

	public boolean addProduct(@Nullable Item item, final Float chance) {
		return addProduct(new ItemStack(MoreObjects.firstNonNull(item, Item.getItemFromBlock(Blocks.AIR))), chance);
	}

	public boolean addProduct(final ItemStack item, final Float chance) {
		if (item.isEmpty()) {
			return false;
		}
		this.products.put(item.copy(), chance);
		return true;
	}

	public void tryAddProduct(final ItemStack item, final Float chance) {
		this.active = this.addProduct(item, chance);
	}

	public void tryAddProduct(@Nullable final Item item, final Float chance) {
		this.active = this.addProduct(item, chance);
	}

	public void tryAddProduct(final String oreDict, final Float chance) {
		if (!OreDictionary.getOres(oreDict).isEmpty()) {
			this.tryAddProduct(OreDictionary.getOres(oreDict).get(0), chance);
		} else {
			this.active = false;
		}
	}

	public void tryAddProduct(final IEBEnumItem type, final Float chance) {
		this.tryAddProduct(type.get(1), chance);
		this.active = (this.active && type.isActive());
	}

	public void copyProducts(final EnumHoneyComb comb) {
		this.products.putAll(comb.products);
	}

}
