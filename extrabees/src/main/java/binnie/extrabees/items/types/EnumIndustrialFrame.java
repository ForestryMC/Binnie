package binnie.extrabees.items.types;

import javax.annotation.Nullable;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeModifier;

public enum EnumIndustrialFrame implements IBeeModifier {

	EMPTY("Empty", 5, 0),
	LIGHT("Glowstone Lighting", 2, 4){
		@Override
		protected void init() {
			lighted = true;
		}
	},
	RAIN("Rain Shielding", 2, 4){
		@Override
		protected void init() {
			rain = true;
		}
	},
	SUNLIGHT("Sunlight Simulator", 4, 8){
		@Override
		protected void init() {
			lighted = true;
			sunlight = true;
		}
	},
	SOUL("Low Grade Mutagen", 5, 15){
		@Override
		protected void init() {
			mutationMod = 1.3f;
		}
	},
	URANIUM("High Grade Mutagen", 10, 50){
		@Override
		protected void init() {
			mutationMod = 2.0f;
		}
	},
	CAGE("Meshed Restrainer", 3, 12){
		@Override
		protected void init() {
			territoryMod = 0.4f;
		}
	},
	FREEDOM("Territory Extension", 3, 16){
		@Override
		protected void init() {
			territoryMod = 1.4f;
		}
	},
	HONEY("Honey Amplifier", 4, 12){
		@Override
		protected void init() {
			productionMod = 1.4f;
		}
	},
	JELLY("Gelatin Amplifier", 8, 36){
		@Override
		protected void init() {
			productionMod = 1.8f;
		}
	},
	LEAF("Pollinator MK I", 3, 15){
		@Override
		protected void init() {
			floweringMod = 1.4f;
		}
	},
	POLLEN("Pollinator MK II", 7, 25){
		@Override
		protected void init() {
			floweringMod = 2.0f;
		}
	},
	CLAY("Lifespan Extensor", 2, 10){
		@Override
		protected void init() {
			lifespanMod = 1.4f;
		}
	},
	EMERALD("Eon Simulator", 7, 20){
		@Override
		protected void init() {
			lifespanMod = 2.0f;
		}
	},
	NETHER_STAR("Immortality Gate", 12, 50){
		@Override
		protected void init() {
			lifespanMod = 20.0f;
		}
	},
	POISON("Mortality Inhibitor", 8, 18){
		@Override
		protected void init() {
			lifespanMod = 0.5f;
		}
	};

	private final String name;
	protected float territoryMod;
	protected float mutationMod;
	protected float lifespanMod;
	protected float productionMod;
	protected float floweringMod;
	protected boolean lighted;
	protected boolean sunlight;
	protected boolean rain;
	private final int wearMod;
	private final int power;

	EnumIndustrialFrame(final String name, final int wear, final int power) {
		this.territoryMod = 1.0f;
		this.mutationMod = 1.0f;
		this.lifespanMod = 1.0f;
		this.productionMod = 1.0f;
		this.floweringMod = 1.0f;
		this.lighted = false;
		this.sunlight = false;
		this.rain = false;
		this.name = name;
		this.wearMod = wear;
		this.power = power;
		init();
	}

	protected void init(){

	}

	public static ItemStack getItemStack(final Item item, final EnumIndustrialFrame frame) {
		final ItemStack stack = new ItemStack(item);
		final NBTTagCompound nbt = new NBTTagCompound();
		nbt.setInteger("frame", frame.ordinal());
		stack.setTagCompound(nbt);
		return stack;
	}

	@Override
	public float getTerritoryModifier(final IBeeGenome genome, final float currentModifier) {
		return this.territoryMod;
	}

	@Override
	public float getMutationModifier(final IBeeGenome genome, final IBeeGenome mate, final float currentModifier) {
		return this.mutationMod;
	}

	@Override
	public float getLifespanModifier(final IBeeGenome genome, @Nullable final IBeeGenome mate, final float currentModifier) {
		return this.lifespanMod;
	}

	@Override
	public float getProductionModifier(final IBeeGenome genome, final float currentModifier) {
		return this.productionMod;
	}

	@Override
	public float getFloweringModifier(final IBeeGenome genome, final float currentModifier) {
		return this.floweringMod;
	}

	@Override
	public boolean isSealed() {
		return this.rain;
	}

	@Override
	public boolean isSelfLighted() {
		return this.lighted;
	}

	@Override
	public boolean isSunlightSimulated() {
		return this.sunlight;
	}

	@Override
	public boolean isHellish() {
		return false;
	}

	public String getName() {
		return this.name;
	}

	public int getWearModifier() {
		return this.wearMod;
	}

	public int getPowerUsage() {
		return this.power;
	}

	@Override
	public float getGeneticDecay(final IBeeGenome genome, final float currentModifier) {
		return 1.0f;
	}

}
