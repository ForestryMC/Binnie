package binnie.core.item;

import forestry.api.core.IItemModelRegister;
import forestry.api.core.IModelManager;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemCore extends Item implements IItemModelRegister {

	public ItemCore(String registryName) {
		setRegistryName(registryName);
		setTranslationKey(registryName);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerModel(Item item, IModelManager manager) {
		manager.registerItemModel(item, 0);
	}
}
