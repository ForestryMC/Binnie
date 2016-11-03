package binnie.extratrees.block;

import net.minecraft.block.properties.PropertyEnum;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class PropertyEnumWoodType extends PropertyEnum<EnumExtraTreeLog> {
	int group;

	private PropertyEnumWoodType(Collection<EnumExtraTreeLog> allowedValues, int group) {
		super("wood_type", EnumExtraTreeLog.class, allowedValues);
		this.group = group;
	}

	public static PropertyEnumWoodType[] create(int variants_per_block){
		int groups = (int) Math.ceil(EnumExtraTreeLog.values().length / (float) variants_per_block);
		PropertyEnumWoodType[] result = new PropertyEnumWoodType[groups];
		for (int i = 0; i < groups; i++) {
			int finalI = i;
			List allowed = Arrays.stream(EnumExtraTreeLog.values()).filter(l -> l.getMetadata() >= finalI * variants_per_block && l.getMetadata() < finalI * variants_per_block + variants_per_block).collect(Collectors.toList());
			result[i] = new PropertyEnumWoodType(allowed, i);
		}
		return result;
	}

	public EnumExtraTreeLog getFirstValue(){
		return getAllowedValues().iterator().next();
	}

	public int getGroup() {
		return group;
	}
}
