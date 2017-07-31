package binnie.core.machines.errors;

public enum EnumErrorType {
	NONE,
	ITEM,
	TANK,
	POWER;
	
	public boolean isItemError() {
		return this == EnumErrorType.ITEM;
	}
	
	public boolean isTankError() {
		return this == EnumErrorType.TANK;
	}
	
	public boolean isPowerError() {
		return this == EnumErrorType.POWER;
	}
}
