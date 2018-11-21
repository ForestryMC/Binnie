package binnie.core.item;

import com.google.common.base.Preconditions;

import javax.annotation.Nullable;

import net.minecraft.item.Item;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.core.IItemModelRegister;
import forestry.api.core.IModelManager;

import binnie.core.features.FeatureItem;
import binnie.core.features.IFeatureObject;

public class ItemCore extends Item implements IItemModelRegister, IFeatureObject<FeatureItem<ItemCore>> {

	@Nullable
	private FeatureItem<ItemCore> feature;

	public ItemCore(String registryName) {
		setRegistryName(registryName);
		setUnlocalizedName(registryName);
	}

	@Override
	public void init(FeatureItem<ItemCore> feature) {
		this.feature = feature;
	}

	public FeatureItem<ItemCore> getFeature() {
		Preconditions.checkNotNull(feature, "Tried to get feature to early or on an item that was not registered with the feature system.");
		return feature;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerModel(Item item, IModelManager manager) {
		manager.registerItemModel(item, 0);
	}
}
