package binnie.design.gui;

import javax.annotation.Nullable;

import binnie.core.Constants;
import binnie.core.machines.errors.CoreErrorCode;
import binnie.core.machines.errors.EnumErrorType;
import binnie.core.machines.errors.IErrorStateDefinition;
import binnie.core.util.I18N;
import net.minecraft.util.ResourceLocation;

public enum DesignErrorCode implements IErrorStateDefinition {
	DESIGNER_NO_ADHESIVE("designer.no.adhesive", CoreErrorCode.NO_ITEM);

	private final String name;
	@Nullable
	private final IErrorStateDefinition parent;
	@Nullable
	private final EnumErrorType type;

	DesignErrorCode(String name, IErrorStateDefinition parent) {
		this(name, parent, EnumErrorType.NONE);
	}

	DesignErrorCode(String name, IErrorStateDefinition parent, EnumErrorType type) {
		this.name = name;
		this.parent = parent;
		this.type = type;
	}

	@Override
	public String getUID() {
		return Constants.DESIGN_MOD_ID + ':' + name;
	}

	public String getDescription(){
		return I18N.localise(new ResourceLocation(Constants.DESIGN_MOD_ID, "errors." + name + ".desc"));
	}

	public String getName(){
		if (parent != null){
			return parent.getName();
		}
		return I18N.localise(new ResourceLocation(Constants.DESIGN_MOD_ID, "errors." + name + ".name"));
	}

	@Override
	@Nullable
	public EnumErrorType getType() {
		if (parent != null){
			return parent.getType();
		}
		return type;
	}
}
