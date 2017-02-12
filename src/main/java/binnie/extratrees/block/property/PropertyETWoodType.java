package binnie.extratrees.block.property;

import binnie.extratrees.block.EnumETLog;
import binnie.extratrees.block.PlankType;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import forestry.arboriculture.blocks.PropertyWoodType;

import java.util.Collection;

public class PropertyETWoodType extends PropertyWoodType<EnumETLog> {

	public static PropertyETWoodType[] create(String name, int variantsPerBlock) {
		return create(name, variantsPerBlock, false);
	}

	public static PropertyETWoodType[] create(String name, int variantsPerBlock, boolean isLog) {
		int length;
		if (isLog) {
			length = EnumETLog.VALUES.length;
		} else {
			length = PlankType.ExtraTreePlanks.VALUES.length;
		}
		final int variantCount = (int) Math.ceil((float) length / variantsPerBlock);
		PropertyETWoodType[] variants = new PropertyETWoodType[variantCount];
		for (int variantNumber = 0; variantNumber < variantCount; variantNumber++) {
			ETWoodTypePredicate filter = new ETWoodTypePredicate(variantNumber, variantsPerBlock, isLog);
			Collection<EnumETLog> allowedValues = Collections2.filter(Lists.newArrayList(EnumETLog.class.getEnumConstants()), filter);
			variants[variantNumber] = new PropertyETWoodType(name, EnumETLog.class, allowedValues);
		}
		return variants;
	}

	protected PropertyETWoodType(String name, Class<EnumETLog> valueClass, Collection<EnumETLog> allowedValues) {
		super(name, valueClass, allowedValues);
	}
}
