package binnie.botany.proxy;

import binnie.core.Constants;
import binnie.core.models.ModelManager;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ProxyClient extends Proxy implements IBotanyProxy {
	public static final ModelManager MODEL_MANAGER = new ModelManager(Constants.BOTANY_MOD_ID);

	@Override
	public Item registerItem(Item item) {
		MODEL_MANAGER.registerItemClient(item);
		return super.registerItem(item);
	}

	@Override
	public Block registerBlock(Block block) {
		MODEL_MANAGER.registerBlockClient(block);
		return super.registerBlock(block);
	}

	@Override
	public void registerModels() {
		MODEL_MANAGER.registerModels();
	}

	@Override
	public void registerItemAndBlockColors() {
		MODEL_MANAGER.registerItemAndBlockColors();
	}
}
