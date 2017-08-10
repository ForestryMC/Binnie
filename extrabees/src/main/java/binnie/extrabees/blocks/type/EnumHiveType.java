package binnie.extrabees.blocks.type;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.util.IStringSerializable;

import forestry.api.apiculture.IHiveDrop;

public enum EnumHiveType implements IStringSerializable {

	Water,
	Rock,
	Nether,
	Marble;

	public List<IHiveDrop> drops;

	EnumHiveType() {
		this.drops = new ArrayList<>();
	}

	@Override
	public String getName() {
		return this.name().toLowerCase();
	}

}
