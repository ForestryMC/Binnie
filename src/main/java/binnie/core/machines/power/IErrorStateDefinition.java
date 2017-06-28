package binnie.core.machines.power;

public interface IErrorStateDefinition {
	String getDescription();
	
	String getName();
	
	EnumErrorType getType();
}
