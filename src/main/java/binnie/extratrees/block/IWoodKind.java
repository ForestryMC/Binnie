package binnie.extratrees.block;

import forestry.api.arboriculture.WoodBlockKind;
import net.minecraft.block.properties.PropertyEnum;

public interface IWoodKind {
	PropertyEnum<EnumExtraTreeLog> getVariant();
	EnumExtraTreeLog getWoodType(int meta);
	WoodBlockKind getWoodKind();
}
