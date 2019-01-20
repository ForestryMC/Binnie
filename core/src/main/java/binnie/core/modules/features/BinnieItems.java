package binnie.core.modules.features;

import javax.annotation.Nullable;

import net.minecraft.item.Item;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import binnie.core.Constants;
import binnie.core.features.FeatureType;
import binnie.core.features.IFeatureConstructor;
import binnie.core.features.IItemFeature;
import binnie.core.features.RegisterFeatureEvent;
import binnie.core.item.ItemFieldKit;
import binnie.core.item.ItemGenesis;
import binnie.core.modules.BinnieCoreModuleUIDs;

@Mod.EventBusSubscriber(modid = Constants.CORE_MOD_ID)
public enum BinnieItems implements IItemFeature<Item, Item> {
	GENESIS("genesis", ItemGenesis::new),
	FIELD_KIT("field_kit", ItemFieldKit::new);

	private final String identifier;
	private final IFeatureConstructor<Item> constructor;
	@Nullable
	private Item item;

	BinnieItems(String identifier, IFeatureConstructor<Item> supplier) {
		this.identifier = identifier;
		this.constructor = supplier;
	}

	@SubscribeEvent
	public static void registerFeatures(RegisterFeatureEvent event) {
		event.register(BinnieItems.class);
	}

	@Override
	public boolean hasItem() {
		return item != null;
	}

	@Override
	@Nullable
	public Item getItem() {
		return item;
	}

	@Override
	public String getIdentifier() {
		return identifier;
	}

	@Override
	public void setItem(@Nullable Item item) {
		this.item = item;
	}

	@Override
	public IFeatureConstructor<Item> getConstructor() {
		return constructor;
	}

	@Override
	public FeatureType getType() {
		return FeatureType.ITEM;
	}

	@Override
	public String getModId() {
		return Constants.CORE_MOD_ID;
	}

	@Override
	public String getModuleId() {
		return BinnieCoreModuleUIDs.CORE;
	}
}
