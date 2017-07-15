package binnie.botany.api;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.core.IModelManager;
import forestry.api.genetics.IAlleleProperty;
import forestry.api.genetics.IAlleleSpecies;

public interface IAlleleFlowerSpecies extends IAlleleSpecies, IAlleleProperty<IAlleleFlowerSpecies> {
	IFlowerType getType();

	EnumAcidity getPH();

	EnumMoisture getMoisture();

	@SideOnly(Side.CLIENT)
	int getFlowerColor(EnumFlowerStage type, int renderPass);

	@SideOnly(Side.CLIENT)
	ModelResourceLocation getFlowerModel(EnumFlowerStage type, boolean flowered);

	@SideOnly(Side.CLIENT)
	void registerModels(Item item, IModelManager manager, EnumFlowerStage type);
}
