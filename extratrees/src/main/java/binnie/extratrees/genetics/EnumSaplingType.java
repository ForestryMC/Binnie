package binnie.extratrees.genetics;

import java.awt.Color;

import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.arboriculture.EnumGermlingType;
import forestry.api.arboriculture.IGermlingModelProvider;
import forestry.api.core.IModelManager;

public enum EnumSaplingType {
	Default,
	Jungle,
	Conifer,
	Fruit,
	Poplar,
	Palm,
	Shrub;

	public IGermlingModelProvider getGermlingModelProvider(Color leaf, Color wood) {
		return new DefaultGermlingModelProvider(this, leaf, wood);
	}

	private class DefaultGermlingModelProvider implements IGermlingModelProvider {
		private ModelResourceLocation germlingModel;
		private ModelResourceLocation pollenModel;
		private EnumSaplingType saplingType;
		private Color leaf;
		private Color wood;

		public DefaultGermlingModelProvider(EnumSaplingType saplingType, Color leaf, Color wood) {
			this.saplingType = saplingType;
			this.wood = wood;
			this.leaf = leaf;
		}

		@SideOnly(Side.CLIENT)
		@Override
		public void registerModels(Item item, IModelManager manager, EnumGermlingType type) {
			if (type == EnumGermlingType.SAPLING) {
				germlingModel = manager.getModelLocation("extratrees", "saplings/tree_" + name());
				ModelBakery.registerItemVariants(item, new ResourceLocation("extratrees", "saplings/tree_" + name()));
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
