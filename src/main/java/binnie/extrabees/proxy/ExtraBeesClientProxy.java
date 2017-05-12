package binnie.extrabees.proxy;

import forestry.core.models.ModelManager;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;

import javax.annotation.Nonnull;

/**
 * Created by Elec332 on 12-5-2017.
 */
public class ExtraBeesClientProxy extends ExtraBeesCommonProxy {

	private static final ModelManager modelManager = ModelManager.getInstance();

	@Override
	public Block registerBlock(Block block) {
		modelManager.registerBlockClient(block);
		return super.registerBlock(block);
	}

	@Override
	public Item registerItem(Item item) {
		modelManager.registerItemClient(item);
		return super.registerItem(item);
	}

	@Override
	@SuppressWarnings("all")
	public void registerModel(@Nonnull Item item, int meta) {
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}
}
