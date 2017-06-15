package binnie.extrabees.proxy;

import com.google.common.collect.ImmutableList;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
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

import forestry.core.models.BlankModel;

import binnie.extrabees.ExtraBees;
import binnie.extrabees.items.IItemModelProvider;
import binnie.extrabees.utils.ExtraBeesResourceLocation;

public class ExtraBeesClientProxy extends ExtraBeesCommonProxy {

	private static IBakedModel bakeModelFor(Block block) {
		return new BlankModel() {

			@Override
			protected ItemOverrideList createOverrides() {
				return new ItemOverrideList(ImmutableList.of()) {

					@Override
					@SuppressWarnings("deprecation")
					public IBakedModel handleItemState(IBakedModel originalModel, ItemStack stack, @Nullable World world, @Nullable EntityLivingBase entity) {
						return Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getModelForState(block.getStateFromMeta(stack.getMetadata()));
					}
				};
			}
		};
	}
	
	@Override
	public Item registerItem(Item item) {
		if(item instanceof IItemModelProvider){
			((IItemModelProvider) item).registerModel(item);
		}
		return super.registerItem(item);
	}
	
	@Override
	@SuppressWarnings("all")
	public void registerModel(@Nonnull Item item, int meta) {
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}
	
	@SubscribeEvent
	public void onModelsBaked(ModelBakeEvent event) {
		IRegistry<ModelResourceLocation, IBakedModel> registry = event.getModelRegistry();
		registerItemBlockLink(new ExtraBeesResourceLocation("alveary"), ExtraBees.alveary, registry);
		registerItemBlockLink(new ExtraBeesResourceLocation("ectoplasm"), ExtraBees.ectoplasm, registry);
		registerItemBlockLink(new ExtraBeesResourceLocation("hive"), ExtraBees.hive, registry);
	}

	private void registerItemBlockLink(ResourceLocation item, Block block, IRegistry<ModelResourceLocation, IBakedModel> registry) {
		registry.putObject(new ModelResourceLocation(item, "inventory"), bakeModelFor(block));
	}
}
