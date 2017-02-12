package binnie.extratrees.block;

import binnie.extratrees.api.IDesignMaterial;
import forestry.api.arboriculture.IWoodType;

public interface IPlankType extends IDesignMaterial {
	//	IIcon getIcon();
	String getPlankTextureName();

	String getDescription();

	IWoodType getWoodType();
}
