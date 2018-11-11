package binnie.extrabees.items.types;

import javax.annotation.Nullable;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import net.minecraftforge.oredict.OreDictionary;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.item.IItemMiscProvider;
import binnie.core.util.I18N;
import binnie.extrabees.modules.ModuleCore;

public enum ExtraBeeItems implements IItemMiscProvider {

	SCENTED_GEAR("scented_gear"),
	DIAMOND_SHARD("diamond_shard") {
		@Override
		protected void init() {
			setGem("Diamond");
		}
	},
	EMERALD_SHARD("emerald_shard") {
		@Override
		protected void init() {
			setGem("Emerald");
		}
	},
	RUBY_SHARD("ruby_shard") {
		@Override
		protected void init() {
			setGem("Ruby");
		}
	},
	SAPPHIRE_SHARD("sapphire_shard") {
		@Override
		protected void init() {
			setGem("Sapphire");
		}
	},
	LAPIS_SHARD("lapis_shard"),
	IRON_DUST("iron_dust") {
		@Override
		protected void init() {
			setMetal("Iron");
		}
	},
	GOLD_DUST("gold_dust") {
		@Override
		protected void init() {
			setMetal("Gold");
		}
	},
	SILVER_DUST("silver_dust") {
		@Override
		protected void init() {
			setMetal("Silver");
		}
	},
	PLATINUM_DUST("platinum_dust") {
		@Override
		protected void init() {
			setMetal("Platinum");
		}
	},
	COPPER_DUST("copper_dust") {
		@Override
		protected void init() {
			setMetal("Copper");
		}
	},
	TIN_DUST("tin_dust") {
		@Override
		protected void init() {
			setMetal("Tin");
		}
	},
	NICKEL_DUST("nickel_dust") {
		@Override
		protected void init() {
			setMetal("Nickel");
		}
	},
	LEAD_DUST("lead_dust") {
		@Override
		protected void init() {
			setMetal("Lead");
		}
	},
	ZINC_DUST("zinc_dust") {
		@Override
		protected void init() {
			setMetal("Zinc");
		}
	},
	TITANIUM_DUST("titanium_dust") {
		@Override
		protected void init() {
			setMetal("Titanium");
		}
	},
	TUNGSTEN_DUST("tungsten_dust") {
		@Override
		protected void init() {
			setMetal("Tungsten");
		}
	},
	URANIUM_DUST("radioactive_dust"),
	COAL_DUST("coal_dust") {
		@Override
		protected void init() {
			setMetal("Coal");
		}
	},
	RED_DYE("dye_red"),
	YELLOW_DYE("dye_yellow"),
	BLUE_DYE("dye_blue"),
	GREEN_DYE("dye_green"),
	WHITE_DYE("dye_white"),
	BLACK_DYE("dye_black"),
	BROWN_DYE("dye_brown"),
	CLAY_DUST("clay_dust"),
	YELLORIUM_DUST("yellorium_dust") {
		@Override
		protected void init() {
			setMetal("Yellorium");
		}
	},
	BLUTONIUM_DUST("blutonium_dust") {
		@Override
		protected void init() {
			setMetal("Blutonium");
		}
	},
	CYANITE_DUST("cyanite_dust") {
		@Override
		protected void init() {
			setMetal("Cyanite");
		}
	};

	public final String identifier;
	@Nullable
	public String metalString;
	@Nullable
	public String gemString;

	ExtraBeeItems(String identifier) {
		this.identifier = identifier;
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
	public String getDisplayName(final ItemStack itemStack) {
		return I18N.localise("extrabees.item.misc." + this.identifier);
	}

	@Override
	public ItemStack get(final int amount) {
		return new ItemStack(ModuleCore.itemMisc, amount, this.ordinal());
	}

	@Override
	public String getModelPath() {
		return identifier;
	}

}
