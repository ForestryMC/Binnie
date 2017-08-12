package binnie.core.item;

import net.minecraft.item.Item;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.core.IItemModelRegister;
import forestry.api.core.IModelManager;

public class ItemCore extends Item implements IItemModelRegister {

	public ItemCore(String registryName) {
		setRegistryName(registryName);
		setUnlocalizedName(registryName);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerModel(Item item, IModelManager manager) {
		manager.registerItemModel(item, 0);
	}
}
