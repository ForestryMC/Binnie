package binnie.extratrees.machines;

import binnie.core.Constants;
import binnie.core.machines.errors.CoreErrorCode;
import binnie.core.machines.errors.EnumErrorType;
import binnie.core.machines.errors.IErrorStateDefinition;
import binnie.core.util.I18N;

import javax.annotation.Nullable;

public enum ExtraTreesErrorCode implements IErrorStateDefinition {
	BREWERY_NO_RECIPE("brewery.no.recipe", CoreErrorCode.NO_RECIPE),
	BREWERY_INSUFFICIENT_INGREDIENTS("brewery.insufficient.ingredients"),
	DISTILLERY_INSUFFICIENT_LIQUID("distillery.insufficient.liquid", CoreErrorCode.INSUFFICIENT_LIQUID),
	FRUITPRESS_NO_FRUIT("press.no.fruit", CoreErrorCode.NO_ITEM),
	LUMBERMILL_INSUFFICIENT_WATER("lumbermill.insufficient.water", CoreErrorCode.INSUFFICIENT_LIQUID),
	LUMBERMILL_NO_WOOD("lumbermill.no.wood", CoreErrorCode.NO_ITEM),
	LUMBERMILL_NO_SPACE_BARK("lumbermill.space.bark", CoreErrorCode.NO_SPACE),
	LUMBERMILL_NO_SPACE_PLANKS("lumbermill.space.planks", CoreErrorCode.NO_SPACE),
	LUMBERMILL_NO_SPACE_SAW_DUST("lumbermill.space.saw_dust", CoreErrorCode.NO_SPACE);

	private final String name;
	@Nullable
	private final IErrorStateDefinition parent;
	@Nullable
	private final EnumErrorType type;

	ExtraTreesErrorCode(String name) {
		this(name, null, EnumErrorType.NONE);
	}

	ExtraTreesErrorCode(String name, IErrorStateDefinition parent) {
		this(name, parent, EnumErrorType.NONE);
	}

	ExtraTreesErrorCode(String name, IErrorStateDefinition parent, EnumErrorType type) {
		this.name = name;
		this.parent = parent;
		this.type = type;
	}

	@Override
	public String getUID() {
		return Constants.EXTRA_TREES_MOD_ID + ':' + name;
	}

	public String getDescription() {
		return I18N.localise("extratrees.errors." + name + ".desc");
	}

	public String getName() {
		if (parent != null) {
			return parent.getName();
		}
		return I18N.localise("extratrees.errors." + name + ".name");
	}

	@Override
	@Nullable
	public EnumErrorType getType() {
		if (parent != null) {
			return parent.getType();
		}
		return type;
	}
}
