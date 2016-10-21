package binnie.core.item;

import forestry.api.core.IItemModelRegister;
import forestry.api.core.IModelManager;
import net.minecraft.item.Item;

public class ItemCore extends Item implements IItemModelRegister{
	
	public ItemCore(String registryName) {
		setRegistryName(getRegistryName());
	}
	
	@Override
	public void registerModel(Item item, IModelManager manager) {
		manager.registerItemModel(item, 0);
	}

}
