package binnie.extratrees.modules.features;

import binnie.core.Constants;
import binnie.core.features.FeatureBlock;
import binnie.core.features.FeatureProvider;
import binnie.core.features.IFeatureRegistry;
import binnie.core.modules.ExtraTreesModuleUIDs;
import binnie.design.items.ItemDesign;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.blocks.BlockCarpentry;
import binnie.extratrees.blocks.BlockCarpentryPanel;
import binnie.extratrees.blocks.BlockStainedDesign;

@FeatureProvider(containerId = Constants.EXTRA_TREES_MOD_ID, moduleID = ExtraTreesModuleUIDs.CARPENTRY)
public class CarpentryBlocks {
	public static final FeatureBlock<BlockCarpentry, ItemDesign> CARPENTRY = features().createBlock("carpentry", () -> new BlockCarpentry("carpentry"), ItemDesign::new);
	public static final FeatureBlock<BlockCarpentryPanel, ItemDesign> PANEL = features().createBlock("carpentryPanel", BlockCarpentryPanel::new, ItemDesign::new);
	public static final FeatureBlock<BlockStainedDesign, ItemDesign> STAINED = features().createBlock("stainedGlass", BlockStainedDesign::new, ItemDesign::new);

	private static IFeatureRegistry features() {
		return ExtraTrees.instance.registry(ExtraTreesModuleUIDs.CARPENTRY);
	}

	private CarpentryBlocks() {
	}

}
