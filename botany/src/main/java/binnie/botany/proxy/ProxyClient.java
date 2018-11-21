package binnie.botany.proxy;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.Constants;
import binnie.core.models.ModelManager;

@SideOnly(Side.CLIENT)
public class ProxyClient extends Proxy implements IBotanyProxy {
	private static final ModelManager MODEL_MANAGER = new ModelManager(Constants.BOTANY_MOD_ID);

	public static ModelManager getModelManager() {
		return MODEL_MANAGER;
	}

	@Override
	public void onRegisterBlock(Block block) {
		getModelManager().registerBlockClient(block);
		super.onRegisterBlock(block);
	}

	@Override
	public void onRegisterItem(Item item) {
		getModelManager().registerItemClient(item);
		super.onRegisterItem(item);
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
