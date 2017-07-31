package binnie.core.machines.errors;

public interface IErrorStateDefinition {
	String getDescription();
	
	String getName();

	String getUID();
	
	EnumErrorType getType();
}
