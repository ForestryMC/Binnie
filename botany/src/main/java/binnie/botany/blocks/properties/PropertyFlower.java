package binnie.botany.blocks.properties;

import com.google.common.base.Optional;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;

import net.minecraft.block.properties.PropertyHelper;

import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAllele;

import binnie.botany.api.genetics.IAlleleFlowerSpecies;
import binnie.botany.api.genetics.IFlowerType;
import binnie.core.Constants;

public class PropertyFlower<I extends IFlowerType<I>> extends PropertyHelper<I> {
	public PropertyFlower(String name, Class<I> flowerTypeClass) {
		super(name, flowerTypeClass);
	}

	@Override
	public List<I> getAllowedValues() {
		List<I> flowerTypes = new ArrayList<>();
		for (IAllele allele : AlleleManager.alleleRegistry.getRegisteredAlleles().values()) {
			if (allele instanceof IAlleleFlowerSpecies) {
				IFlowerType flowerType = ((IAlleleFlowerSpecies) allele).getType();
				if (!flowerTypes.contains(flowerType)) {
					flowerTypes.add((I) flowerType);
				}
			}
		}
		return flowerTypes;
	}

	/**
	 * Get the name for the given value.
	 */
	@Override
	public String getName(IFlowerType value) {
		return value.getName().toLowerCase(Locale.ENGLISH);
	}

	@Override
	public Optional<I> parseValue(String value) {
		IAllele allele = AlleleManager.alleleRegistry.getAllele(Constants.BOTANY_MOD_ID + ".flower" + StringUtils.capitalize(value));
		if (allele instanceof IAlleleFlowerSpecies) {
			IAlleleFlowerSpecies alleleValue = (IAlleleFlowerSpecies) allele;
			return Optional.of((I) alleleValue.getType());
		}
		return Optional.absent();
	}
}