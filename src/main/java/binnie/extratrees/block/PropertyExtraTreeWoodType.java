package binnie.extratrees.block;

import java.util.Collection;

import javax.annotation.Nonnull;

import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

import forestry.arboriculture.blocks.WoodTypePredicate;
import forestry.arboriculture.blocks.property.PropertyWoodType;

public class PropertyExtraTreeWoodType extends PropertyWoodType<EnumExtraTreeLog> {
	public static PropertyExtraTreeWoodType[] create(@Nonnull String name, int variantsPerBlock) {
		final int variantCount = (int) Math.ceil((float) EnumExtraTreeLog.VALUES.length / variantsPerBlock);
		PropertyExtraTreeWoodType[] variants = new PropertyExtraTreeWoodType[variantCount];
		for (int variantNumber = 0; variantNumber < variantCount; variantNumber++) {
			WoodTypePredicate filter = new WoodTypePredicate(variantNumber, variantsPerBlock);
			Collection<EnumExtraTreeLog> allowedValues = Collections2.filter(Lists.newArrayList(EnumExtraTreeLog.class.getEnumConstants()), filter);
			variants[variantNumber] = new PropertyExtraTreeWoodType(name, EnumExtraTreeLog.class, allowedValues);
		}
		return variants;
	}

	protected PropertyExtraTreeWoodType(String name, Class<EnumExtraTreeLog> valueClass, Collection<EnumExtraTreeLog> allowedValues) {
		super(name, valueClass, allowedValues);
	}
}
