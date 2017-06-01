package binnie.extrabees.items.types;

import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeModifier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import javax.annotation.Nullable;

public enum IndustrialFrame implements IBeeModifier {

	Empty("Empty", 5, 0),
	Light("Glowstone Lighting", 2, 4),
	Rain("Rain Shielding", 2, 4),
	Sunlight("Sunlight Simulator", 4, 8),
	Soul("Low Grade Mutagen", 5, 15),
	Uranium("High Grade Mutagen", 10, 50),
	Cage("Meshed Restrainer", 3, 12),
	Freedom("Territory Extension", 3, 16),
	Honey("Honey Amplifier", 4, 12),
	Jelly("Gelatin Amplifier", 8, 36),
	Leaf("Pollinator MK I", 3, 15),
	Pollen("Pollinator MK II", 7, 25),
	Clay("Lifespan Extensor", 2, 10),
	Emerald("Eon Simulator", 7, 20),
	NetherStar("Immortality Gate", 12, 50),
	Poison("Mortality Inhibitor", 8, 18);

	static {
		IndustrialFrame.Light.lighted = true;
		IndustrialFrame.Rain.rain = true;
		IndustrialFrame.Sunlight.lighted = true;
		IndustrialFrame.Sunlight.sunlight = true;
		IndustrialFrame.Soul.mutationMod = 1.3f;
		IndustrialFrame.Uranium.mutationMod = 2.0f;
		IndustrialFrame.Cage.territoryMod = 0.4f;
		IndustrialFrame.Freedom.territoryMod = 1.4f;
		IndustrialFrame.Honey.productionMod = 1.4f;
		IndustrialFrame.Jelly.productionMod = 1.8f;
		IndustrialFrame.Leaf.floweringMod = 1.4f;
		IndustrialFrame.Pollen.floweringMod = 2.0f;
		IndustrialFrame.Clay.lifespanMod = 1.4f;
		IndustrialFrame.Emerald.lifespanMod = 2.0f;
		IndustrialFrame.NetherStar.lifespanMod = 20.0f;
		IndustrialFrame.Poison.lifespanMod = 0.5f;
	}

	String name;
	float territoryMod;
	float mutationMod;
	float lifespanMod;
	float productionMod;
	float floweringMod;
	boolean lighted;
	boolean sunlight;
	boolean rain;
	int wearMod;
	int power;

	IndustrialFrame(final String name, final int wear, final int power) {
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
	}

	public static ItemStack getItemStack(final Item item, final IndustrialFrame frame) {
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
