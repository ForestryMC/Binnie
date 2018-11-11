package binnie.extrabees.proxy;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.IRegistry;
import net.minecraft.world.World;

import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.model.ModelLoader;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.core.models.BlankModel;

import binnie.core.Constants;
import binnie.core.models.ModelManager;
import binnie.extrabees.modules.ModuleCore;

@SideOnly(Side.CLIENT)
public class ProxyClient extends ProxyCommon {

	private static final ModelManager MODEL_MANAGER = new ModelManager(Constants.EXTRA_BEES_MOD_ID);

	public static ModelManager getModelManager() {
		return MODEL_MANAGER;
	}

	private static IBakedModel bakeModelFor(Block block) {
		return new BakedModelForBlock(block);
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

	@Override
	public void registerModel(@Nonnull Item item, int meta) {
		ResourceLocation registryName = item.getRegistryName();
		Preconditions.checkNotNull(registryName);
		ModelResourceLocation inventory = new ModelResourceLocation(registryName, "inventory");
		ModelLoader.setCustomModelResourceLocation(item, meta, inventory);
	}

	@SubscribeEvent
	public void onModelsBaked(ModelBakeEvent event) {
		IRegistry<ModelResourceLocation, IBakedModel> registry = event.getModelRegistry();
		registerItemBlockLink(new ResourceLocation(Constants.EXTRA_BEES_MOD_ID, "ectoplasm"), ModuleCore.ectoplasm, registry);
		registerItemBlockLink(new ResourceLocation(Constants.EXTRA_BEES_MOD_ID, "hive"), ModuleCore.hive, registry);
	}

	private void registerItemBlockLink(ResourceLocation item, Block block, IRegistry<ModelResourceLocation, IBakedModel> registry) {
		registry.putObject(new ModelResourceLocation(item, "inventory"), bakeModelFor(block));
	}

	private static class BakedModelForBlock extends BlankModel {

		private final Block block;

		public BakedModelForBlock(Block block) {
			this.block = block;
		}

		@Override
		protected ItemOverrideList createOverrides() {
			return new BlockItemOverrideList(block);
		}

		private static class BlockItemOverrideList extends ItemOverrideList {
			private final Block block;

			public BlockItemOverrideList(Block block) {
				super(ImmutableList.of());
				this.block = block;
			}

			@Override
			@SuppressWarnings("deprecation")
			public IBakedModel handleItemState(IBakedModel originalModel, ItemStack stack, @Nullable World world, @Nullable EntityLivingBase entity) {
				Minecraft minecraft = Minecraft.getMinecraft();
				BlockRendererDispatcher blockRendererDispatcher = minecraft.getBlockRendererDispatcher();
				BlockModelShapes blockModelShapes = blockRendererDispatcher.getBlockModelShapes();
				IBlockState stateFromMeta = block.getStateFromMeta(stack.getMetadata());
				return blockModelShapes.getModelForState(stateFromMeta);
			}
		}
	}
}
