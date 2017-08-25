package binnie.core.machines.errors;

import javax.annotation.Nullable;

import binnie.Constants;
import binnie.core.util.I18N;

public enum CoreErrorCode implements IErrorStateDefinition {
	UNKNOWN("unknown"),
	NO_ITEM("no.item", EnumErrorType.ITEM),
	NO_SPACE("no.space", EnumErrorType.ITEM),
	NO_SPACE_TANK("no.space.tank", CoreErrorCode.NO_SPACE),
	INVALID_ITEM("invalid.item", EnumErrorType.ITEM),
	INSUFFICIENT_POWER("insufficient.power", EnumErrorType.POWER),
	TANK_FULL("tank.full", EnumErrorType.TANK),
	TANK_EMPTY("tank.empty", EnumErrorType.TANK),
	TANK_DIFFRENT_FLUID("tank.different", CoreErrorCode.NO_SPACE_TANK),
	INSUFFICIENT_LIQUID("insufficient.liquid", EnumErrorType.TANK),
	NO_RECIPE("no.recipe"),
	INVALID_RECIPE("invalid.recipe"),
	//Buildcraft
	TASK_CANCELLED("task.cancelled"),
	TASK_PAUSED("task.paused");
	
	String name;
	@Nullable
	IErrorStateDefinition parent;
	@Nullable
	EnumErrorType type;
	
	CoreErrorCode(String name) {
		this(name, null, EnumErrorType.NONE);
	}
	
	CoreErrorCode(String name, EnumErrorType type) {
		this(name, null, type);
	}
	
	CoreErrorCode(String name, IErrorStateDefinition parent) {
		this(name, parent, EnumErrorType.NONE);
	}
	
	CoreErrorCode(String name, IErrorStateDefinition parent, EnumErrorType type) {
		this.name = name;
		this.parent = parent;
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
		return Constants.CORE_MOD_ID + ":" + name;
	}

	@Override
	@Nullable
	public EnumErrorType getType() {
		if(parent != null){
			return parent.getType();
		}
		return type;
	}
}
