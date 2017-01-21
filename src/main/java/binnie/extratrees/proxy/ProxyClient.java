package binnie.extratrees.proxy;

import javax.annotation.Nonnull;

import binnie.Constants;
import binnie.core.models.ModelManager;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.block.BlockETDecorativeLeaves;
import binnie.extratrees.block.slab.BlockETSlab;
import binnie.extratrees.models.ModelETDecorativeLeaves;
import forestry.api.arboriculture.EnumPileType;
import forestry.arboriculture.PluginArboriculture;
import forestry.arboriculture.blocks.BlockDecorativeLeaves;
import forestry.arboriculture.blocks.slab.BlockArbSlab;
import forestry.arboriculture.models.ModelDecorativeLeaves;
import forestry.arboriculture.models.ModelLeaves;
import forestry.arboriculture.models.ModelWoodPile;
import forestry.arboriculture.models.WoodModelLoader;
import forestry.arboriculture.proxy.ProxyArboricultureClient;
import forestry.arboriculture.render.CharcoalPileRenderer;
import forestry.arboriculture.tiles.TilePile;
import forestry.core.models.BlockModelEntry;
import forestry.core.models.ModelEntry;
import forestry.core.proxy.Proxies;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class ProxyClient extends Proxy implements IExtraTreeProxy {
	public static ModelManager modelManager = new ModelManager(Constants.EXTRA_TREES_MOD_ID);

	@Override
	public void init() {
		//ForestryAPI.textureManager.registerIconProvider(FruitSprite.Average);
	}
	
	@Override
	public void registerBlockModel(@Nonnull final BlockModelEntry index) {
		getModelManager().registerCustomBlockModel(index);
		if(index.addStateMapper){
			StateMapperBase ignoreState = new BlockModeStateMapper(index);
			ModelLoader.setCustomStateMapper(index.block, ignoreState);
		}
	}
	
	@Override
	public void registerModel(@Nonnull ModelEntry index) {
		getModelManager().registerCustomModel(index);
	}

	@Override
	public void setCustomStateMapper(String name, Block block) {
		ModelLoader.setCustomStateMapper(block, new CustomMapper(name));
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
		for (BlockETDecorativeLeaves leaves : ExtraTrees.leavesDecorative) {
			String resourceName = leaves.getRegistryName().toString();
			ModelResourceLocation blockModelLocation = new ModelResourceLocation(resourceName);
			ModelResourceLocation itemModeLocation = new ModelResourceLocation(resourceName, "inventory");
			BlockModelEntry blockModelIndex = new BlockModelEntry(blockModelLocation, itemModeLocation, new ModelETDecorativeLeaves(), leaves);
			registerBlockModel(blockModelIndex);
		}
		
		for(BlockETSlab slab : ExtraTrees.slabsDouble){
			PluginArboriculture.proxy.registerWoodModel(slab, true);
		}
		for(BlockETSlab slab : ExtraTrees.slabsDoubleFireproof){
			PluginArboriculture.proxy.registerWoodModel(slab, true);
		}
		getModelManager().registerModels();
	}

	@Override
	public void registerItemAndBlockColors() {
		getModelManager().registerItemAndBlockColors();
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
	
	class BlockModeStateMapper extends StateMapperBase {
		private final BlockModelEntry index;

		public BlockModeStateMapper(BlockModelEntry index) {
			this.index = index;
		}

		@Override
		protected ModelResourceLocation getModelResourceLocation(IBlockState iBlockState) {
			return index.blockModelLocation;
		}
	}

}
