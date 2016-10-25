package binnie.extratrees.proxy;

import binnie.Constants;
import binnie.core.models.ModelManager;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;

public class ProxyClient extends Proxy implements IExtraTreeProxy {
	public static ModelManager modelManager = new ModelManager(Constants.EXTRA_TREES_MOD_ID);
	
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
            rl = new ResourceLocation(Constants.EXTRA_TREES_MOD_ID, name);
        }

        @Override
        protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
            return new ModelResourceLocation(rl, this.getPropertyString(state.getProperties()));
        }
    }
    
    public static ModelManager getModelManager() {
		return modelManager;
	}
    
	@Override
	public Item registerItem(Item item) {
		getModelManager().registerItemClient(item);
		return super.registerItem(item);
	}
	
	@Override
	public Block registerBlock(Block block) {
		getModelManager().registerBlockClient(block);
		return super.registerBlock(block);
	}
	
	@Override
	public void registerModels() {
		getModelManager().registerModels();
	}
	
	@Override
	public void registerItemAndBlockColors() {
		getModelManager().registerItemAndBlockColors();
	}

}
