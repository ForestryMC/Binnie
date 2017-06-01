package binnie.core.models;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.util.ResourceLocation;

public class DefaultStateMapper extends StateMapperBase {

	private ResourceLocation resourceLocation;

	public DefaultStateMapper(ResourceLocation resourceLocation) {
		this.resourceLocation = resourceLocation;
	}

	@Override
	protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
		return new ModelResourceLocation(resourceLocation, "normal");
	}
}
