package binnie.genetics.machine;

import javax.annotation.Nullable;

import binnie.core.Constants;
import binnie.core.machines.errors.CoreErrorCode;
import binnie.core.machines.errors.EnumErrorType;
import binnie.core.machines.errors.ErrorStateRegistry;
import binnie.core.machines.errors.IErrorStateDefinition;
import binnie.core.util.I18N;

public enum GeneticsErrorCode implements IErrorStateDefinition {
	NO_OWNER("no.owner"),
	NO_INDIVIDUAL("no.individual", CoreErrorCode.NO_ITEM),
	NO_SERUM("no.serum", CoreErrorCode.NO_ITEM),
	INVALID_SERUM("invalid.serum", CoreErrorCode.INVALID_ITEM),
	INVALID_SERUM_MISMATCH("invalid.serum.mismatch", INVALID_SERUM),
	INVALID_SERUM_NO("invalid.serum.no.genes", INVALID_SERUM),
	DEFUNCT_SERUM("defunct.serum", CoreErrorCode.INVALID_ITEM),
	EMPTY_SERUM("empty.serum", CoreErrorCode.INVALID_ITEM),
	NO_ENZYME("no.enzyme", CoreErrorCode.NO_ITEM),
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
	INCUBATOR_INSUFFICIENT_LIQUID("incubator.insufficient.liquid", CoreErrorCode.INSUFFICIENT_LIQUID),
	//Inoculator
	INOCULATOR_INSUFFICIENT_VECTOR("insufficient.vector", CoreErrorCode.INSUFFICIENT_LIQUID),
	//Isolator
	ISOLATOR_INSUFFICIENT_ETHANOL("isolator.insufficient.ethanol", CoreErrorCode.INSUFFICIENT_LIQUID),
	ISOLATOR_NO_ROOM("isolator.no.room", CoreErrorCode.NO_SPACE),
	ISOLATOR_NO_EMPTY_SEQUENCER("isolator.no.empty.sequencer", CoreErrorCode.NO_ITEM),
	//Polymeriser
	POLYMERISER_NO_ITEM("polymeriser.no.item", CoreErrorCode.NO_ITEM),
	POLYMERISER_ITEM_FILLED("polymeriser.item.filled", CoreErrorCode.INVALID_ITEM),
	POLYMERISER_INSUFFICIENT_DNA("polymeriser.insufficient.dna", CoreErrorCode.INSUFFICIENT_LIQUID),
	POLYMERISER_INSUFFICIENT_BACTERIA("polymeriser.insufficient.bacteria", CoreErrorCode.INSUFFICIENT_LIQUID),
	//Sequencer
	SEQUENCER_INSUFFICIENT_DYE("sequencer.insufficient.dye", CoreErrorCode.NO_ITEM),
	SEQUENCER_NO_SPACE("sequencer.no.space", CoreErrorCode.NO_SPACE),
	SEQUENCER_NO_DNA("sequencer.no.dna", CoreErrorCode.NO_ITEM)
	;
	
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
		this.type = type;
		ErrorStateRegistry.registerErrorState(this);
	}

	@Override
	public String getUID() {
		return Constants.GENETICS_MOD_ID + ":" + name;
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
