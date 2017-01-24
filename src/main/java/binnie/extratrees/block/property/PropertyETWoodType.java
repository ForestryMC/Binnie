package binnie.extratrees.block.property;

import java.util.Collection;

import javax.annotation.Nonnull;

import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

import binnie.extratrees.block.EnumExtraTreeLog;
import binnie.extratrees.block.PlankType;
import forestry.arboriculture.blocks.property.PropertyWoodType;

public class PropertyETWoodType extends PropertyWoodType<EnumExtraTreeLog> {
	
	public static PropertyETWoodType[] create(@Nonnull String name, int variantsPerBlock) {
		return create(name, variantsPerBlock, false);
	}
	public static PropertyETWoodType[] create(@Nonnull String name, int variantsPerBlock, boolean isLog) {
		int length;
		if(isLog){
			length = EnumExtraTreeLog.VALUES.length;
		}else{
			length = PlankType.ExtraTreePlanks.VALUES.length;
		}
		final int variantCount = (int) Math.ceil((float) length / variantsPerBlock);
		PropertyETWoodType[] variants = new PropertyETWoodType[variantCount];
		for (int variantNumber = 0; variantNumber < variantCount; variantNumber++) {
			ETWoodTypePredicate filter = new ETWoodTypePredicate(variantNumber, variantsPerBlock, isLog);
			Collection<EnumExtraTreeLog> allowedValues = Collections2.filter(Lists.newArrayList(EnumExtraTreeLog.class.getEnumConstants()), filter);
			variants[variantNumber] = new PropertyETWoodType(name, EnumExtraTreeLog.class, allowedValues);
		}
		return variants;
	}

	protected PropertyETWoodType(String name, Class<EnumExtraTreeLog> valueClass, Collection<EnumExtraTreeLog> allowedValues) {
		super(name, valueClass, allowedValues);
	}
}
