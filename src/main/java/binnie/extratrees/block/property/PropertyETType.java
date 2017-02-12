package binnie.extratrees.block.property;

import binnie.extratrees.genetics.ETTreeDefinition;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import net.minecraft.block.properties.PropertyEnum;

import java.util.Collection;

public class PropertyETType extends PropertyEnum<ETTreeDefinition> {
	public static int getBlockCount(int variantsPerBlock) {
		return (int) Math.ceil((float) ETTreeDefinition.VALUES.length / variantsPerBlock);
	}

	public static PropertyETType create(String name, int blockNumber, int variantsPerBlock) {
		TreeTypePredicate filter = new TreeTypePredicate(blockNumber, variantsPerBlock);
		Collection<ETTreeDefinition> allowedValues = Collections2.filter(Lists.newArrayList(ETTreeDefinition.class.getEnumConstants()), filter);
		return new PropertyETType(name, ETTreeDefinition.class, allowedValues);
	}

	protected PropertyETType(String name, Class<ETTreeDefinition> valueClass, Collection<ETTreeDefinition> allowedValues) {
		super(name, valueClass, allowedValues);
	}


}