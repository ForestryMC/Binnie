package binnie.core.modules.features;

import binnie.core.BinnieCore;
import binnie.core.Constants;
import binnie.core.features.FeatureItem;
import binnie.core.features.FeatureProvider;
import binnie.core.features.IFeatureRegistry;
import binnie.core.item.ItemFieldKit;
import binnie.core.item.ItemGenesis;
import binnie.core.modules.BinnieCoreModuleUIDs;

@FeatureProvider(containerId = Constants.CORE_MOD_ID, moduleID = BinnieCoreModuleUIDs.CORE)
public class BinnieItems {
	public static final FeatureItem<ItemGenesis> GENESIS = features().createItem("genesis", ItemGenesis::new);
	public static final FeatureItem<ItemFieldKit> FIELD_KIT = features().createItem("field_kit", ItemFieldKit::new);

	private static IFeatureRegistry features() {
		return BinnieCore.instance.registry(BinnieCoreModuleUIDs.CORE);
	}

	private BinnieItems() {
	}
}
