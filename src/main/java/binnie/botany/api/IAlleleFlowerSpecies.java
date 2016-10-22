package binnie.botany.api;

import javax.annotation.Nonnull;

import forestry.api.core.IModelManager;
import forestry.api.genetics.IAlleleSpecies;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IAlleleFlowerSpecies extends IAlleleSpecies {
    IFlowerType getType();

    EnumAcidity getPH();

    EnumMoisture getMoisture();
	
	@SideOnly(Side.CLIENT)
	int getFlowerColour(EnumFlowerStage type, int renderPass);

	@SideOnly(Side.CLIENT)
	@Nonnull
	ModelResourceLocation getFlowerModel(EnumFlowerStage type);
	
	@SideOnly(Side.CLIENT)
	void registerModels(Item item, IModelManager manager, EnumFlowerStage type);
}
