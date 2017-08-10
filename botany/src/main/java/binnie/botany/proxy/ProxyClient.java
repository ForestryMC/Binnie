package binnie.botany.proxy;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.Constants;
import binnie.core.models.ModelManager;

@SideOnly(Side.CLIENT)
public class ProxyClient extends Proxy implements IBotanyProxy {
	public static ModelManager modelManager = new ModelManager(Constants.BOTANY_MOD_ID);

	@Override
	public Item registerItem(Item item) {
		modelManager.registerItemClient(item);
		return super.registerItem(item);
	}

	@Override
	public Block registerBlock(Block block) {
		modelManager.registerBlockClient(block);
		return super.registerBlock(block);
	}

	@Override
	public void registerModels() {
		modelManager.registerModels();
	}

	@Override
	public void registerItemAndBlockColors() {
		modelManager.registerItemAndBlockColors();
	}
}
