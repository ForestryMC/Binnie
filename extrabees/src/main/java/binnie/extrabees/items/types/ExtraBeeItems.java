package binnie.extrabees.items.types;

import binnie.extrabees.modules.ModuleCore;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nullable;
import java.util.List;

public enum ExtraBeeItems implements IEBItemMiscProvider {

	SCENTED_GEAR("Scented Gear", "scented_gear"),
	DIAMOND_SHARD("Diamond Fragment", "diamond_shard") {
		@Override
		protected void init() {
			setGem("Diamond");
		}
	},
	EMERALD_SHARD("Emerald Fragment", "emerald_shard") {
		@Override
		protected void init() {
			setGem("Emerald");
		}
	},
	RUBY_SHARD("Ruby Fragment", "ruby_shard") {
		@Override
		protected void init() {
			setGem("Ruby");
		}
	},
	SAPPHIRE_SHARD("Sapphire Fragment", "sapphire_shard") {
		@Override
		protected void init() {
			setGem("Sapphire");
		}
	},
	LAPIS_SHARD("Lapis Fragment", "lapis_shard"),
	IRON_DUST("Iron Grains", "iron_dust") {
		@Override
		protected void init() {
			setMetal("Iron");
		}
	},
	GOLD_DUST("Gold Grains", "gold_dust") {
		@Override
		protected void init() {
			setMetal("Gold");
		}
	},
	SILVER_DUST("Silver Grains", "silver_dust") {
		@Override
		protected void init() {
			setMetal("Silver");
		}
	},
	PLATINUM_DUST("Platinum Grains", "platinum_dust") {
		@Override
		protected void init() {
			setMetal("Platinum");
		}
	},
	COPPER_DUST("Copper Grains", "copper_dust") {
		@Override
		protected void init() {
			setMetal("Copper");
		}
	},
	TIN_DUST("Tin Grains", "tin_dust") {
		@Override
		protected void init() {
			setMetal("Tin");
		}
	},
	NICKEL_DUST("Nickel Grains", "nickel_dust") {
		@Override
		protected void init() {
			setMetal("Nickel");
		}
	},
	LEAD_DUST("Lead Grains", "lead_dust") {
		@Override
		protected void init() {
			setMetal("Lead");
		}
	},
	ZINC_DUST("Zinc Grains", "zinc_dust") {
		@Override
		protected void init() {
			setMetal("Zinc");
		}
	},
	TITANIUM_DUST("Titanium Grains", "titanium_dust") {
		@Override
		protected void init() {
			setMetal("Titanium");
		}
	},
	TUNGSTEN_DUST("Tungsten Grains", "tungsten_dust") {
		@Override
		protected void init() {
			setMetal("Tungsten");
		}
	},
	URANIUM_DUST("Radioactive Fragments", "radioactive_dust"),
	COAL_DUST("Coal Grains", "coal_dust") {
		@Override
		protected void init() {
			setMetal("Coal");
		}
	},
	RED_DYE("Red Dye", "dye_red"),
	YELLOW_DYE("Yellow Dye", "dye_yellow"),
	BLUE_DYE("Blue Dye", "dye_blue"),
	GREEN_DYE("Green Dye", "dye_green"),
	WHITE_DYE("White Dye", "dye_white"),
	BLACK_DYE("Black Dye", "dye_black"),
	BROWN_DYE("Brown Dye", "dye_brown"),
	CLAY_DUST("Clay Dust", "clay_dust"),
	YELLORIUM_DUST("Yellorium Grains", "yellorium_dust") {
		@Override
		protected void init() {
			setMetal("Yellorium");
		}
	},
	BLUTONIUM_DUST("Blutonium Grains", "blutonium_dust") {
		@Override
		protected void init() {
			setMetal("Blutonium");
		}
	},
	CYANITE_DUST("Cyanite Grains", "cyanite_dust") {
		@Override
		protected void init() {
			setMetal("Cyanite");
		}
	};

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
		init();
	}

	protected void setGem(final String string) {
		this.gemString = string;
	}

	protected void setMetal(final String string) {
		this.metalString = string;
	}

	@Override
	public boolean isActive() {
		if (this.metalString != null) {
			NonNullList<ItemStack> ingots = OreDictionary.getOres("ingot" + this.metalString);
			NonNullList<ItemStack> dust = OreDictionary.getOres("dust" + this.metalString);
			return !ingots.isEmpty() || !dust.isEmpty() || this == ExtraBeeItems.COAL_DUST;
		}
		NonNullList<ItemStack> gems = OreDictionary.getOres("gem" + this.gemString);
		return this.gemString == null || !gems.isEmpty();
	}

	protected void init() {
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(final List<String> tooltip) {
	}

	@Override
	public String getName(final ItemStack itemStack) {
		return this.name;
	}

	@Override
	public ItemStack get(final int amount) {
		return new ItemStack(ModuleCore.itemMisc, amount, this.ordinal());
	}

	@Override
	public String getModelPath() {
		return modelPath;
	}

}
