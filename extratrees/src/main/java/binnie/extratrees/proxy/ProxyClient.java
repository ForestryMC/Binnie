package binnie.extratrees.proxy;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.client.model.ModelLoader;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.arboriculture.ModuleArboriculture;
import forestry.core.models.BlockModelEntry;

import binnie.core.Constants;
import binnie.core.models.ModelManager;
import binnie.extratrees.blocks.BlockETDecorativeLeaves;
import binnie.extratrees.blocks.BlockETDefaultLeaves;
import binnie.extratrees.blocks.BlockETDefaultLeavesFruit;
import binnie.extratrees.blocks.wood.BlockETSlab;
import binnie.extratrees.models.ModelDefaultETLeaves;
import binnie.extratrees.models.ModelDefaultETLeavesFruit;
import binnie.extratrees.models.ModelETDecorativeLeaves;
import binnie.extratrees.modules.ModuleWood;

@SideOnly(Side.CLIENT)
public class ProxyClient extends Proxy implements IExtraTreeProxy {
	public static final ModelManager modelManager = new ModelManager(Constants.EXTRA_TREES_MOD_ID);

	public static ModelManager getModelManager() {
		return modelManager;
	}

	@Override
	public void init() {
		//ForestryAPI.textureManager.registerIconProvider(FruitSprite.Average);
	}

	private static void registerBlockModel(final BlockModelEntry index) {
		ModelManager.registerCustomBlockModel(index);
		if (index.addStateMapper) {
			StateMapperBase ignoreState = new BlockModeStateMapper(index);
			ModelLoader.setCustomStateMapper(index.block, ignoreState);
		}
	}

	@Override
	public void setCustomStateMapper(String name, Block block) {
		ModelLoader.setCustomStateMapper(block, new CustomMapper(name));
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
		for (BlockETDecorativeLeaves leaves : ModuleWood.leavesDecorative) {
			String resourceName = leaves.getRegistryName().toString();
			ModelResourceLocation blockModelLocation = new ModelResourceLocation(resourceName);
			ModelResourceLocation itemModeLocation = new ModelResourceLocation(resourceName, "inventory");
			BlockModelEntry blockModelIndex = new BlockModelEntry(blockModelLocation, itemModeLocation, new ModelETDecorativeLeaves(), leaves);
			registerBlockModel(blockModelIndex);
		}

		for (BlockETDefaultLeaves leaves : ModuleWood.leavesDefault) {
			String resourceName = leaves.getRegistryName().toString();
			ModelResourceLocation blockModelLocation = new ModelResourceLocation(resourceName);
			ModelResourceLocation itemModeLocation = new ModelResourceLocation(resourceName, "inventory");
			BlockModelEntry blockModelIndex = new BlockModelEntry(blockModelLocation, itemModeLocation, new ModelDefaultETLeaves(), leaves);
			registerBlockModel(blockModelIndex);
		}

		for (BlockETDefaultLeavesFruit leaves : ModuleWood.leavesDefaultFruit) {
			String resourceName = leaves.getRegistryName().toString();
			ModelResourceLocation blockModelLocation = new ModelResourceLocation(resourceName);
			ModelResourceLocation itemModeLocation = new ModelResourceLocation(resourceName, "inventory");
			BlockModelEntry blockModelIndex = new BlockModelEntry(blockModelLocation, itemModeLocation, new ModelDefaultETLeavesFruit(), leaves);
			registerBlockModel(blockModelIndex);
		}

		for (BlockETSlab slab : ModuleWood.slabsDouble) {
			ModuleArboriculture.proxy.registerWoodModel(slab, true);
		}
		for (BlockETSlab slab : ModuleWood.slabsDoubleFireproof) {
			ModuleArboriculture.proxy.registerWoodModel(slab, true);
		}
		modelManager.registerModels();
	}

	@Override
	public void registerItemAndBlockColors() {
		getModelManager().registerItemAndBlockColors();
	}

	static class CustomMapper extends StateMapperBase {
		private final ResourceLocation rl;

		public CustomMapper(String name) {
			rl = new ResourceLocation(Constants.EXTRA_TREES_MOD_ID, name);
		}

		@Override
		protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
			return new ModelResourceLocation(rl, this.getPropertyString(state.getProperties()));
		}
	}

	private static class BlockModeStateMapper extends StateMapperBase {
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
