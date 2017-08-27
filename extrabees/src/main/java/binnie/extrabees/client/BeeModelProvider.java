package binnie.extrabees.client;

import java.util.Locale;

import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.apiculture.BeeManager;
import forestry.api.apiculture.EnumBeeType;
import forestry.api.apiculture.IBeeModelProvider;
import forestry.api.core.IModelManager;

public class BeeModelProvider implements IBeeModelProvider {
	public static final BeeModelProvider instance = new BeeModelProvider();

	@SideOnly(Side.CLIENT)
	private static final ModelResourceLocation[] models = new ModelResourceLocation[EnumBeeType.values().length];

	@Override
	@SideOnly(Side.CLIENT)
	public void registerModels(Item item, IModelManager manager) {
		String beeIconDir = "bees/default/";
		EnumBeeType beeType = BeeManager.beeRoot.getType(new ItemStack(item));
		if (beeType != null) {
			String beeTypeNameBase = beeIconDir + beeType.toString().toLowerCase(Locale.ENGLISH);

			models[beeType.ordinal()] = getModelLocation("forestry", beeTypeNameBase);
			ModelBakery.registerItemVariants(item, new ResourceLocation("forestry:" + beeTypeNameBase));
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ModelResourceLocation getModel(EnumBeeType type) {
		return models[type.ordinal()];
	}

	public ModelResourceLocation getModelLocation(String modID, String identifier) {
		return new ModelResourceLocation(modID + ":" + identifier, "inventory");
	}
}
