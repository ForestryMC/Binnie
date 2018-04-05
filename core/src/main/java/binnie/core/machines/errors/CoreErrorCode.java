package binnie.core.machines.errors;

import javax.annotation.Nullable;

import binnie.core.Constants;
import binnie.core.util.I18N;

public enum CoreErrorCode implements IErrorStateDefinition {
	UNKNOWN("unknown"),
	NO_ITEM("no.item", EnumErrorType.ITEM),
	NO_SPACE("no.space", EnumErrorType.ITEM),
	NO_SPACE_TANK("no.space.tank", EnumErrorType.TANK),
	INVALID_ITEM("invalid.item", EnumErrorType.ITEM),
	INSUFFICIENT_POWER("insufficient.power", EnumErrorType.POWER),
	TANK_FULL("tank.full", EnumErrorType.TANK),
	TANK_EMPTY("tank.empty", EnumErrorType.TANK),
	TANK_DIFFRENT_FLUID("tank.different", EnumErrorType.TANK),
	INSUFFICIENT_LIQUID("insufficient.liquid", EnumErrorType.TANK),
	NO_RECIPE("no.recipe"),
	INVALID_RECIPE("invalid.recipe"),
	//Buildcraft
	TASK_CANCELLED("task.cancelled"),
	TASK_PAUSED("task.paused");
	
	private final String name;
	private final EnumErrorType type;
	
	CoreErrorCode(String name) {
		this(name, EnumErrorType.NONE);
	}
	
	CoreErrorCode(String name, EnumErrorType type) {
		this.name = name;
		this.type = type;
	}
	
	public String getDescription(){
		return I18N.localise("binniecore.errors." + name + ".desc");
	}
	
	public String getName(){
		return  I18N.localise("binniecore.errors." + name + ".name");
	}

	@Override
	public String getUID() {
		return Constants.CORE_MOD_ID + ':' + name;
	}

	@Override
	@Nullable
	public EnumErrorType getType() {
		return type;
	}
}
