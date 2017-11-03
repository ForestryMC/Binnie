package binnie.extratrees.modules;

import net.minecraftforge.common.MinecraftForge;

import forestry.api.modules.ForestryModule;

import binnie.core.Constants;
import binnie.core.modules.BlankModule;
import binnie.core.modules.ExtraTreesModuleUIDs;
import binnie.design.DesignHelper;
import binnie.design.api.IDesignSystem;
import binnie.design.blocks.DesignBlock;
import binnie.design.items.ItemDesign;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.api.CarpentryManager;
import binnie.extratrees.blocks.BlockCarpentry;
import binnie.extratrees.blocks.BlockCarpentryPanel;
import binnie.extratrees.blocks.BlockStainedDesign;
import binnie.extratrees.carpentry.CarpentryInterface;

@ForestryModule(moduleID = ExtraTreesModuleUIDs.CARPENTRY, containerID = Constants.EXTRA_TREES_MOD_ID, name = "Carpentry", unlocalizedDescription = "extratrees.module.carpentry")
public class ModuleCarpentry extends BlankModule {

	static {
		CarpentryManager.carpentryInterface = new CarpentryInterface();
	}

	public static BlockCarpentry blockCarpentry;
	public static BlockCarpentry blockPanel;
	public static BlockStainedDesign blockStained;

	public ModuleCarpentry() {
		super(Constants.EXTRA_TREES_MOD_ID, ExtraTreesModuleUIDs.MACHINES);
	}

	public static DesignBlock getCarpentryPanel(final IDesignSystem system, final int meta) {
		final DesignBlock block = DesignHelper.getDesignBlock(system, meta);
		block.setPanel();
		return block;
	}

	@Override
	public void registerItemsAndBlocks() {
		blockCarpentry = new BlockCarpentry("carpentry");
		blockPanel = new BlockCarpentryPanel();
		blockStained = new BlockStainedDesign();
		ExtraTrees.proxy.registerBlock(blockCarpentry, new ItemDesign(blockCarpentry));
		ExtraTrees.proxy.registerBlock(blockPanel, new ItemDesign(blockPanel));
		ExtraTrees.proxy.registerBlock(blockStained, new ItemDesign(blockStained));

		MinecraftForge.EVENT_BUS.register(blockCarpentry);
	}
}
