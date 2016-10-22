package binnie.botany.api;

import javax.annotation.Nonnull;

import forestry.api.core.IModelManager;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

//import net.minecraft.util.IIcon;

public interface IFlowerType {
//	IIcon getStem(final EnumFlowerStage p0, final boolean p1, final int p2);
//
//	IIcon getPetalIcon(final EnumFlowerStage p0, final boolean p1, final int p2);
//
//	IIcon getVariantIcon(final EnumFlowerStage p0, final boolean p1, final int p2);
	
	@SideOnly(Side.CLIENT)
	void registerModels(Item item, IModelManager manager, EnumFlowerStage type);

	@Nonnull
	ModelResourceLocation getModel(EnumFlowerStage type);

    int getID();

    int getSections();

    int ordinal();
}
