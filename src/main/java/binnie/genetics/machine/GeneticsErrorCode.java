package binnie.genetics.machine;

import javax.annotation.Nullable;

import binnie.core.machines.power.CoreErrorCode;
import binnie.core.machines.power.EnumErrorType;
import binnie.core.machines.power.IErrorStateDefinition;
import binnie.core.util.I18N;

public enum GeneticsErrorCode implements IErrorStateDefinition {
	NO_OWNER("no.owner"),
	NO_INDIVIDUAL("no.individual", CoreErrorCode.NO_ITEM),
	//Acclimatiser
	ACCLIMATISER_NO_ITEM("acclimatiser.no.item", CoreErrorCode.NO_ITEM),
	ACCLIMATISER_CAN_NOT_WORK(".acclimatiser.can.not.acclimatise", CoreErrorCode.INVALID_ITEM),
	//Analyser
	ANALYSER_NO_ITEM("analyser.no.item", CoreErrorCode.NO_ITEM),
	ANALYSER_ALREADY_ANALYSED("analyser.already.analysed", EnumErrorType.ITEM),
	ANALYSER_INSUFFICIENT_DYE("analyser.insufficient.dye", EnumErrorType.ITEM),
	//Genepool
	GENEPOOL_INSUFFICIENT_ETHANOL("genepool.insufficient.ethanol", CoreErrorCode.INSUFFICIENT_LIQUID),
	GENEPOOL_INSUFFICIENT_ENZYME("genepool.insufficient.enzyme", CoreErrorCode.NO_ITEM),
	//Incubator
	INCUBATOR_INSUFFICIENT_LIQUID("incubator.insufficient.liquid");
	
	String name;
	@Nullable
	IErrorStateDefinition parent;
	@Nullable
	EnumErrorType type;
	
	GeneticsErrorCode(String name) {
		this(name, null, EnumErrorType.NONE);
	}
	
	GeneticsErrorCode(String name, EnumErrorType type) {
		this(name, null, type);
	}
	
	GeneticsErrorCode(String name, IErrorStateDefinition parent) {
		this(name, parent, EnumErrorType.NONE);
	}
	
	GeneticsErrorCode(String name, IErrorStateDefinition parent, EnumErrorType type) {
		this.name = name;
		this.parent = parent;
	}
	
	public String getDescription(){
		return I18N.localise("genetics.errors." + name + ".desc");
	}
	
	public String getName(){
		if(parent != null){
			return parent.getName();
		}
		return  I18N.localise("genetics.errors." + name + ".name");
	}
	
	@Override
	public EnumErrorType getType() {
		if(parent != null){
			return parent.getType();
		}
		return type;
	}
}
