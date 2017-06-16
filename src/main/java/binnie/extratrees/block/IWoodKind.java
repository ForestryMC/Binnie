package binnie.extratrees.block;

import net.minecraft.block.properties.PropertyEnum;

import forestry.api.arboriculture.WoodBlockKind;

public interface IWoodKind {
	PropertyEnum<EnumETLog> getVariant();

	EnumETLog getWoodType(int meta);

	WoodBlockKind getWoodKind();
}
