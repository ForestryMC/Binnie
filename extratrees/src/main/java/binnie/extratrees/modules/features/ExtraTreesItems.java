package binnie.extratrees.modules.features;

import javax.annotation.Nullable;

import net.minecraft.init.Blocks;
import net.minecraft.item.Item;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import forestry.api.core.Tabs;

import binnie.core.Constants;
import binnie.core.features.FeatureType;
import binnie.core.features.IFeatureConstructor;
import binnie.core.features.IItemFeature;
import binnie.core.features.RegisterFeatureEvent;
import binnie.core.item.ItemMisc;
import binnie.core.modules.ExtraTreesModuleUIDs;
import binnie.extratrees.items.ExtraTreeMiscItems;
import binnie.extratrees.items.ItemArboristDatabase;
import binnie.extratrees.items.ItemETFood;
import binnie.extratrees.items.ItemHammer;
import binnie.extratrees.items.ItemHops;
import binnie.extratrees.items.ItemMothDatabase;

@Mod.EventBusSubscriber(modid = Constants.EXTRA_TREES_MOD_ID)
public enum ExtraTreesItems implements IItemFeature<Item, Item> {
	MISC("misc", () -> new ItemMisc<>(Tabs.tabArboriculture, ExtraTreeMiscItems.values())),
	FOOD("food", ItemETFood::new),
	HAMMER("hammer", () -> new ItemHammer(false)),
	DURABLE_HAMMER("durable_hammer", () -> new ItemHammer(true)),
	HOPS_ITEM("hops", () -> new ItemHops(ExtraTreesBlocks.HOPS.block(), Blocks.FARMLAND)),
	//TODO: Rename "databaseTree" and "databaseLepi" with 1.13
	ARBORIST_DATABASE("databaseTree", ItemArboristDatabase::new) {
		@Override
		public String getModuleId() {
			return ExtraTreesModuleUIDs.TREE_DATABASE;
		}
	},
	MOTH_DATABASE("databaseLepi", ItemMothDatabase::new) {
		@Override
		public String getModuleId() {
			return ExtraTreesModuleUIDs.MOTH_DATABASE;
		}
	};

	private final String identifier;
	private final IFeatureConstructor<Item> constructor;
	@Nullable
	private Item item;

	ExtraTreesItems(String identifier, IFeatureConstructor<Item> supplier) {
		this.identifier = identifier;
		this.constructor = supplier;
	}

	@SubscribeEvent
	public static void registerFeatures(RegisterFeatureEvent event) {
		event.register(ExtraTreesItems.class);
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
		return Constants.EXTRA_TREES_MOD_ID;
	}

	@Override
	public String getModuleId() {
		return ExtraTreesModuleUIDs.CORE;
	}
}
