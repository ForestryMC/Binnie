package binnie.core.machines.errors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import binnie.core.ModId;
import binnie.core.machines.inventory.Validator;
import binnie.core.util.I18N;

public interface IErrorStateDefinition {
	String getDescription();

	/**
	 * Called on errors that point to slots with validators.
	 */
	default String getDescription(Collection<Validator<?>> validators) {
		List<String> validatorStrings = new ArrayList<>();
		for (Validator<?> validator : validators) {
			String validatorString = validator.getTooltip();
			if (!validatorStrings.contains(validatorString)) {
				validatorStrings.add(validatorString);
			}
		}
		if (validatorStrings.size() == 0) {
			return getDescription();
		} else {
			final String validationString;
			if (validatorStrings.size() == 1) {
				validationString = validatorStrings.get(0);
			} else {
				validationString = validatorStrings.toString();
			}
			return I18N.localise(ModId.CORE, "errors.missing.validated.slot.desc", validationString);
		}
	}
	
	String getName();

	String getUID();
	
	EnumErrorType getType();
}
