package binnie.core.models;

import binnie.Constants;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;

public class DefaultStateMapper extends StateMapperBase {

	private String modelName;
	
	public DefaultStateMapper(String modelName) {
		this.modelName = modelName;
	}
	
	@Override
	protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
		return new ModelResourceLocation(Constants.BOTANY_MOD_ID + ":" + modelName, "normal");
	}

}
