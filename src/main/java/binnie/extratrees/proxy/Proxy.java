package binnie.extratrees.proxy;

import javax.annotation.Nonnull;

import binnie.core.proxy.BinnieModProxy;
import forestry.core.models.BlockModelEntry;
import forestry.core.models.ModelEntry;
import forestry.core.models.ModelManager;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraftforge.client.model.ModelLoader;

public class Proxy extends BinnieModProxy implements IExtraTreeProxy {
	@Override
	public void setCustomStateMapper(String name, Block block) {

	}

}
