package binnie.extratrees.modules;

import binnie.design.DesignHelper;
import binnie.design.items.ItemDesign;
import com.google.common.collect.ImmutableSet;

import java.util.Set;

import net.minecraftforge.common.MinecraftForge;

import binnie.core.Constants;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.api.CarpentryManager;
import binnie.design.api.IDesignSystem;
import binnie.core.modules.ExtraTreesModuleUIDs;
import binnie.core.modules.BinnieModule;
import binnie.core.modules.Module;
import binnie.extratrees.blocks.BlockCarpentry;
import binnie.extratrees.blocks.BlockCarpentryPanel;
import binnie.extratrees.blocks.BlockStainedDesign;
import binnie.extratrees.carpentry.CarpentryInterface;
import binnie.design.blocks.DesignBlock;

@BinnieModule(moduleID = ExtraTreesModuleUIDs.CARPENTRY, moduleContainerID = Constants.EXTRA_TREES_MOD_ID, name = "Carpentry", unlocalizedDescription = "extratrees.module.carpentry")
public class ModuleCarpentry implements Module {

	static {
		CarpentryManager.carpentryInterface = new CarpentryInterface();
	}

	public static BlockCarpentry blockCarpentry;
	public static BlockCarpentry blockPanel;
	public static BlockStainedDesign blockStained;

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

	@Override
	public Set<String> getDependencyUids() {
		return ImmutableSet.of(ExtraTreesModuleUIDs.MACHINES);
	}
}
