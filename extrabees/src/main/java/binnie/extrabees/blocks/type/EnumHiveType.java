package binnie.extrabees.blocks.type;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.util.IStringSerializable;

import forestry.api.apiculture.IHiveDrop;

public enum EnumHiveType implements IStringSerializable {
	WATER,
	ROCK,
	NETHER,
	MARBLE;

	List<IHiveDrop> drops;

	EnumHiveType() {
		this.drops = new ArrayList<>();
	}

	public void addDrops(IHiveDrop... drops) {
		this.drops.addAll(Arrays.asList(drops));
	}

	public List<IHiveDrop> getDrops() {
		return drops;
	}

	public int getMeta() {
		return ordinal();
	}

	public static EnumHiveType getHiveTypeForMeta(int meta) {
		if (meta < 0 || meta >= values().length) {
			return ROCK;
		}
		return values()[meta];
	}

	@Override
	public String getName() {
		return this.name().toLowerCase();
	}

}
