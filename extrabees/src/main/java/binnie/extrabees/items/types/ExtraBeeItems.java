package binnie.extrabees.items.types;

import javax.annotation.Nullable;
import java.util.List;

import net.minecraft.item.ItemStack;

import net.minecraftforge.oredict.OreDictionary;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.extrabees.ExtraBees;

public enum ExtraBeeItems implements IEBItemMiscProvider {

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

	public final String name;
	public final String modelPath;
	@Nullable
	public String metalString;
	@Nullable
	public String gemString;

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

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(final List<String> tooltip) {
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

}
