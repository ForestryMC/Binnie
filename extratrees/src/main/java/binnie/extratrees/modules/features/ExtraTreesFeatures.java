package binnie.extratrees.modules.features;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;

import forestry.api.core.Tabs;

import binnie.core.Constants;
import binnie.core.features.FeatureBlock;
import binnie.core.features.FeatureItem;
import binnie.core.features.FeatureProvider;
import binnie.core.features.IFeatureRegistry;
import binnie.core.item.ItemMisc;
import binnie.core.modules.ExtraTreesModuleUIDs;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.blocks.BlockHops;
import binnie.extratrees.items.ExtraTreeItems;
import binnie.extratrees.items.ItemETFood;
import binnie.extratrees.items.ItemHammer;
import binnie.extratrees.items.ItemHops;

@FeatureProvider(containerId = Constants.EXTRA_TREES_MOD_ID, moduleID = ExtraTreesModuleUIDs.CORE)
public class ExtraTreesFeatures {

	public static final FeatureItem<ItemMisc<ExtraTreeItems>> MISC = features().createItem("misc", () -> new ItemMisc<>(Tabs.tabArboriculture, ExtraTreeItems.values()));
	public static final FeatureItem<ItemETFood> FOOD = features().createItem("food", ItemETFood::new);
	public static final FeatureItem<ItemHammer> HAMMER = features().createItem("hammer", () -> new ItemHammer(false));
	public static final FeatureItem<ItemHammer> DURABLE_HAMMER = features().createItem("durable_hammer", () -> new ItemHammer(true));

	public static final FeatureBlock<BlockHops, ItemBlock> HOPS = features().createBlock("hops", BlockHops::new);
	public static final FeatureItem<ItemHops> HOPS_ITEM = features().createItem("hops", () -> new ItemHops(HOPS.block(), Blocks.FARMLAND));

	private static IFeatureRegistry features() {
		return ExtraTrees.instance.registry(ExtraTreesModuleUIDs.CORE);
	}

	private ExtraTreesFeatures() {
	}
}
