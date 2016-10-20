// 
// Decompiled by Procyon v0.5.30
// 

package binnie.botany.api;

import forestry.api.genetics.IAlleleSpecies;

public interface IAlleleFlowerSpecies extends IAlleleSpecies
{
	IFlowerType getType();

	EnumAcidity getPH();

	EnumMoisture getMoisture();
}
