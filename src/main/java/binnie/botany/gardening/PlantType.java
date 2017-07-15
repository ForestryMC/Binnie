package binnie.botany.gardening;

import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;

import binnie.botany.Botany;

public enum PlantType implements IStringSerializable {
	WEEDS("weeds", true),
	WEEDS_LONG("weeds_long", true),
	WEEDS_VERY_LONG("weeds_very_long", true),
	DEAD_FLOWER("dead_flower"),
	DECAYING_FLOWER("decaying_flower");
	
	String name;
	boolean isWeed;
	
	PlantType(String name) {
		this(name, false);
	}
	
	PlantType(String name, boolean isWeed) {
		this.name = name;
		this.isWeed = isWeed;
	}
	
	public static PlantType get(int id) {
		return values()[id % values().length];
	}
	
	public ItemStack get() {
		return new ItemStack(Botany.plant, 1, ordinal());
	}
	
	public boolean isWeed() {
		return isWeed;
	}
	
	@Override
	public String getName() {
		return name;
	}
}
