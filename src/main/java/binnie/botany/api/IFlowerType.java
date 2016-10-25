package binnie.botany.api;

import javax.annotation.Nonnull;

import forestry.api.core.IModelManager;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IFlowerType<I extends IFlowerType> extends Comparable<I> {
	
	@SideOnly(Side.CLIENT)
	void registerModels(Item item, IModelManager manager, EnumFlowerStage type);

	@Nonnull
	@SideOnly(Side.CLIENT)
	ModelResourceLocation getModel(EnumFlowerStage type, boolean flowered, int section);

	String getName();
	
    int getID();

    int getSections();

    int ordinal();
}
