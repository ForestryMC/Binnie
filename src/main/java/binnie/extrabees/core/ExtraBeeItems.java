package binnie.extrabees.core;

import binnie.Binnie;
import binnie.core.Mods;
import binnie.core.item.IItemMiscProvider;
import binnie.extrabees.ExtraBees;
import forestry.api.recipes.RecipeManagers;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nullable;
import java.util.List;

public enum ExtraBeeItems implements IItemMiscProvider {
	ScentedGear("Scented Gear", "scented_gear"),
	DiamondShard("Diamond Fragment", "diamond_shard"),
	EmeraldShard("Emerald Fragment", "emerald_shard"),
	RubyShard("Ruby Fragment", "ruby_shard"),
	SapphireShard("Sapphire Fragment", "sapphire_shard"),
	LapisShard("Lapis Fragment", "lapis_shard"),
	IronDust("Iron Grains", "iron_dust"),
	GoldDust("Gold Grains", "gold_dust"),
	SilverDust("Silver Grains", "silver_dust"),
	PlatinumDust("Platinum Grains", "platinum_dust"),
	CopperDust("Copper Grains", "copper_dust"),
	TinDust("Tin Grains", "tin_dust"),
	NickelDust("Nickel Grains", "nickel_dust"),
	LeadDust("Lead Grains", "lead_dust"),
	ZincDust("Zinc Grains", "zinc_dust"),
	TitaniumDust("Titanium Grains", "titanium_dust"),
	TungstenDust("Tungsten Grains", "tungsten_dust"),
	UraniumDust("Radioactive Fragments", "radioactive_dust"),
	CoalDust("Coal Grains", "coal_dust"),
	RedDye("Red Dye", "dye_red"),
	YellowDye("Yellow Dye", "dye_yellow"),
	BlueDye("Blue Dye", "dye_blue"),
	GreenDye("Green Dye", "dye_green"),
	WhiteDye("White Dye", "dye_white"),
	BlackDye("Black Dye", "dye_black"),
	BrownDye("Brown Dye", "dye_brown"),
	ClayDust("Clay Dust", "clay_dust"),
	YelloriumDust("Yellorium Grains", "yellorium_dust"),
	BlutoniumDust("Blutonium Grains", "blutonium_dust"),
	CyaniteDust("Cyanite Grains", "cyanite_dust");

	String name;
	String modelPath;
	@Nullable
	String metalString;
	@Nullable
	String gemString;

	public static void init() {
		OreDictionary.registerOre("dyeRed", ExtraBeeItems.RedDye.get(1));
		OreDictionary.registerOre("dyeYellow", ExtraBeeItems.YellowDye.get(1));
		OreDictionary.registerOre("dyeBlue", ExtraBeeItems.BlueDye.get(1));
		OreDictionary.registerOre("dyeGreen", ExtraBeeItems.GreenDye.get(1));
		OreDictionary.registerOre("dyeBlack", ExtraBeeItems.BlackDye.get(1));
		OreDictionary.registerOre("dyeWhite", ExtraBeeItems.WhiteDye.get(1));
		OreDictionary.registerOre("dyeBrown", ExtraBeeItems.BrownDye.get(1));

		OreDictionary.registerOre("gearWood", ExtraBeeItems.ScentedGear.get(1));
	}

	public static void postInit() {
		final ItemStack lapisShard = ExtraBeeItems.LapisShard.get(1);
		GameRegistry.addShapelessRecipe(new ItemStack(Items.DYE, 1, 4), lapisShard, lapisShard, lapisShard, lapisShard);
		for (final ExtraBeeItems item : values()) {
			if (item.metalString != null) {
				ItemStack dust = null;
				ItemStack ingot = null;
				if (!OreDictionary.getOres("ingot" + item.metalString).isEmpty()) {
					ingot = OreDictionary.getOres("ingot" + item.metalString).get(0).copy();
				}
				if (!OreDictionary.getOres("dust" + item.metalString).isEmpty()) {
					dust = OreDictionary.getOres("dust" + item.metalString).get(0).copy();
				}
				final ItemStack input = item.get(1);
				if (dust != null) {
					GameRegistry.addShapelessRecipe(dust, input, input, input, input);
				} else if (ingot != null) {
					GameRegistry.addShapelessRecipe(ingot, input, input, input, input, input, input, input, input, input);
				} else if (item == ExtraBeeItems.CoalDust) {
					GameRegistry.addShapelessRecipe(new ItemStack(Items.COAL), input, input, input, input);
				}
			} else if (item.gemString != null) {
				ItemStack gem = null;
				if (!OreDictionary.getOres("gem" + item.gemString).isEmpty()) {
					gem = OreDictionary.getOres("gem" + item.gemString).get(0);
				}
				final ItemStack input2 = item.get(1);
				if (gem != null) {
					GameRegistry.addShapelessRecipe(gem.copy(), input2, input2, input2, input2, input2, input2, input2, input2, input2);
				}
			}
		}
		Item woodGear = null;
		try {
			woodGear = (Item) Class.forName("buildcraft.BuildCraftCore").getField("woodenGearItem").get(null);
		} catch (Exception ex) {
		}
		ItemStack gear = new ItemStack(Blocks.PLANKS, 1);
		if (woodGear != null) {
			gear = new ItemStack(woodGear, 1);
		}
		RecipeManagers.carpenterManager.addRecipe(100, Binnie.LIQUID.getFluidStack("for.honey", 500), ItemStack.EMPTY, ExtraBeeItems.ScentedGear.get(1), " j ", "bgb", " p ", 'j', Mods.Forestry.item("royal_jelly"), 'b', Mods.Forestry.item("beeswax"), 'p', Mods.Forestry.item("pollen"), 'g', gear);
	}

	ExtraBeeItems(String name, String modelPath) {
		this.metalString = null;
		this.gemString = null;
		this.name = name;
		this.modelPath = modelPath;
	}

	private void setGem(final String string) {
		this.gemString = string;
	}

	private void setMetal(final String string) {
		this.metalString = string;
	}

	@Override
	public boolean isActive() {
		if (this.metalString != null) {
			return !OreDictionary.getOres("ingot" + this.metalString).isEmpty() || !OreDictionary.getOres("dust" + this.metalString).isEmpty() || this == ExtraBeeItems.CoalDust;
		}
		return this.gemString == null || !OreDictionary.getOres("gem" + this.gemString).isEmpty();
	}

//	@Override
//	public IIcon getIcon(final ItemStack stack) {
//		return this.icon;
//	}
//
//	@SideOnly(Side.CLIENT)
//	@Override
//	public void registerIcons(final IIconRegister register) {
//		this.icon = ExtraBees.proxy.getIcon(register, "misc/" + this.iconPath);
//	}

	@Override
	public void addInformation(final List<String> par3List) {
	}

	@Override
	public String getName(final ItemStack stack) {
		return this.name;
	}

	@Override
	public ItemStack get(final int i) {
		return new ItemStack(ExtraBees.itemMisc, i, this.ordinal());
	}

	@Override
	public String getModelPath() {
		return modelPath;
	}

	static {
		ExtraBeeItems.TinDust.setMetal("Tin");
		ExtraBeeItems.ZincDust.setMetal("Zinc");
		ExtraBeeItems.CopperDust.setMetal("Copper");
		ExtraBeeItems.IronDust.setMetal("Iron");
		ExtraBeeItems.NickelDust.setMetal("Nickel");
		ExtraBeeItems.LeadDust.setMetal("Lead");
		ExtraBeeItems.SilverDust.setMetal("Silver");
		ExtraBeeItems.GoldDust.setMetal("Gold");
		ExtraBeeItems.PlatinumDust.setMetal("Platinum");
		ExtraBeeItems.TungstenDust.setMetal("Tungsten");
		ExtraBeeItems.TitaniumDust.setMetal("Titanium");
		ExtraBeeItems.CoalDust.setMetal("Coal");
		ExtraBeeItems.YelloriumDust.setMetal("Yellorium");
		ExtraBeeItems.BlutoniumDust.setMetal("Blutonium");
		ExtraBeeItems.CyaniteDust.setMetal("Cyanite");
		ExtraBeeItems.DiamondShard.setGem("Diamond");
		ExtraBeeItems.EmeraldShard.setGem("Emerald");
		ExtraBeeItems.RubyShard.setGem("Ruby");
		ExtraBeeItems.SapphireShard.setGem("Sapphire");
	}
}
