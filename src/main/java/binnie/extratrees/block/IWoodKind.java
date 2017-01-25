package binnie.extratrees.block;

import forestry.api.arboriculture.WoodBlockKind;
import net.minecraft.block.properties.PropertyEnum;

public interface IWoodKind {
	PropertyEnum<EnumETLog> getVariant();
	EnumETLog getWoodType(int meta);
	WoodBlockKind getWoodKind();
}
