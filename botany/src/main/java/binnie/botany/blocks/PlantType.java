package binnie.botany.blocks;

import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;

import binnie.botany.modules.ModuleGardening;

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

	public static PlantType getType(int meta){
		if(meta >= values().length){
			meta = values().length-1;
		}
		return values()[meta];
	}

	public ItemStack get() {
		return new ItemStack(ModuleGardening.plant, 1, ordinal());
	}

	public boolean isWeed() {
		return isWeed;
	}

	@Override
	public String getName() {
		return name;
	}
}
