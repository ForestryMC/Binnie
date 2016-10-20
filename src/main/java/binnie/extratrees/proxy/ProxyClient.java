package binnie.extratrees.proxy;

import binnie.extratrees.ExtraTrees;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;

public class ProxyClient extends Proxy implements IExtraTreeProxy {

    @Override
    public void init() {
        //ForestryAPI.textureManager.registerIconProvider(FruitSprite.Average);
    }


    @Override
    public void setCustomStateMapper(String name, Block block) {
        ModelLoader.setCustomStateMapper(block, new CustomMapper(name));
    }

    class CustomMapper extends StateMapperBase {
        ResourceLocation rl;

        public CustomMapper(String name) {
            rl = new ResourceLocation(ExtraTrees.MOD_ID, name);
        }

        @Override
        protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
            return new ModelResourceLocation(rl, this.getPropertyString(state.getProperties()));
        }
    }

}
