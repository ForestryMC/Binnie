package binnie.extrabees.apiary;

import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeModifier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

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

	protected String name;
	protected float territoryMod;
	protected float mutationMod;
	protected float lifespanMod;
	protected float productionMod;
	protected float floweringMod;
	protected boolean lighted;
	protected boolean sunlight;
	protected boolean rain;
	protected int wearMod;
	protected int power;

	IndustrialFrame(String name, int wear, int power) {
		this.name = name;
		this.power = power;
		territoryMod = 1.0f;
		mutationMod = 1.0f;
		lifespanMod = 1.0f;
		productionMod = 1.0f;
		floweringMod = 1.0f;
		lighted = false;
		sunlight = false;
		rain = false;
		wearMod = wear;
	}

	public static ItemStack getItemStack(Item item, IndustrialFrame frame) {
		ItemStack stack = new ItemStack(item);
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setInteger("frame", frame.ordinal());
		stack.setTagCompound(nbt);
		return stack;
	}

	@Override
	public float getTerritoryModifier(IBeeGenome genome, float currentModifier) {
		return territoryMod;
	}

	@Override
	public float getMutationModifier(IBeeGenome genome, IBeeGenome mate, float currentModifier) {
		return mutationMod;
	}

	@Override
	public float getLifespanModifier(IBeeGenome genome, IBeeGenome mate, float currentModifier) {
		return lifespanMod;
	}

	@Override
	public float getProductionModifier(IBeeGenome genome, float currentModifier) {
		return productionMod;
	}

	@Override
	public float getFloweringModifier(IBeeGenome genome, float currentModifier) {
		return floweringMod;
	}

	@Override
	public boolean isSealed() {
		return rain;
	}

	@Override
	public boolean isSelfLighted() {
		return lighted;
	}

	@Override
	public boolean isSunlightSimulated() {
		return sunlight;
	}

	@Override
	public boolean isHellish() {
		return false;
	}

	public Object getName() {
		return name;
	}

	// TODO unused method?
	public int getWearModifier() {
		return wearMod;
	}

	// TODO unused method?
	public int getPowerUsage() {
		return power;
	}

	@Override
	public float getGeneticDecay(IBeeGenome genome, float currentModifier) {
		return 1.0f;
	}
}
