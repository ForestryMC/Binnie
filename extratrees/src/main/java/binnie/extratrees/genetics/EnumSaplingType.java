package binnie.extratrees.genetics;

import forestry.api.arboriculture.EnumGermlingType;
import forestry.api.arboriculture.IGermlingModelProvider;
import forestry.api.core.IModelManager;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.awt.Color;

public enum EnumSaplingType {
	Default,
	Jungle,
	Conifer,
	Fruit,
	Poplar,
	Palm,
	Shrub;

	public IGermlingModelProvider getGermlingModelProvider(Color leaf, Color wood) {
		return new DefaultGermlingModelProvider(this, this, leaf, wood);
	}

	private static class DefaultGermlingModelProvider implements IGermlingModelProvider {
		private ModelResourceLocation germlingModel;
		private ModelResourceLocation pollenModel;
		private final EnumSaplingType saplingType;
		private final Color leaf;
		private final Color wood;
		private final EnumSaplingType enumSaplingType;

		public DefaultGermlingModelProvider(EnumSaplingType enumSaplingType, EnumSaplingType saplingType, Color leaf, Color wood) {
			this.saplingType = saplingType;
			this.wood = wood;
			this.leaf = leaf;
			this.enumSaplingType = enumSaplingType;
		}

		@SideOnly(Side.CLIENT)
		@Override
		public void registerModels(Item item, IModelManager manager, EnumGermlingType type) {
			if (type == EnumGermlingType.SAPLING) {
				germlingModel = manager.getModelLocation("extratrees", "saplings/tree_" + enumSaplingType.name());
				ModelBakery.registerItemVariants(item, new ResourceLocation("extratrees", "saplings/tree_" + enumSaplingType.name()));
			}
			if (type == EnumGermlingType.POLLEN) {
				pollenModel = manager.getModelLocation("pollen");
				ModelBakery.registerItemVariants(item, new ResourceLocation("forestry:pollen"));
			}
		}

		@Override
		public ModelResourceLocation getModel(EnumGermlingType type) {
			if (type == EnumGermlingType.SAPLING) {
				return germlingModel;
			} else if (type == EnumGermlingType.POLLEN) {
				return pollenModel;
			} else {
				return germlingModel;
			}
		}

		@Override
		public int getSpriteColor(EnumGermlingType type, int renderPass) {
			return type == EnumGermlingType.SAPLING && renderPass == 0 ? wood.getRGB() : leaf.getRGB();
		}
	}
}
