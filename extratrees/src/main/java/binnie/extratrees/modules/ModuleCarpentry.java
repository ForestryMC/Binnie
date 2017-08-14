package binnie.extratrees.modules;

import com.google.common.collect.ImmutableSet;

import java.util.Set;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

import net.minecraftforge.common.MinecraftForge;

import binnie.core.Constants;
import binnie.core.api.block.TileEntityMetadata;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.api.CarpentryManager;
import binnie.extratrees.api.IDesign;
import binnie.extratrees.api.IDesignMaterial;
import binnie.extratrees.api.IDesignSystem;
import binnie.core.modules.ExtraTreesModuleUIDs;
import binnie.core.modules.BinnieModule;
import binnie.core.modules.Module;
import binnie.extratrees.blocks.BlockCarpentry;
import binnie.extratrees.blocks.BlockCarpentryPanel;
import binnie.extratrees.blocks.BlockDesign;
import binnie.extratrees.blocks.BlockStainedDesign;
import binnie.extratrees.carpentry.CarpentryInterface;
import binnie.extratrees.carpentry.DesignBlock;
import binnie.extratrees.carpentry.EnumDesign;
import binnie.extratrees.items.ItemDesign;

@BinnieModule(moduleID = ExtraTreesModuleUIDs.CARPENTRY, moduleContainerID = Constants.EXTRA_TREES_MOD_ID, name = "Carpentry", unlocalizedDescription = "extratrees.module.carpentry")
public class ModuleCarpentry extends Module {

	static {
		CarpentryManager.carpentryInterface = new CarpentryInterface();
	}

	public static BlockCarpentry blockCarpentry;
	public static BlockCarpentry blockPanel;
	public static BlockStainedDesign blockStained;

	public static ItemStack getItemStack(final BlockDesign block, final IDesignMaterial type1, final IDesignMaterial type2, final IDesign design) {
		return getItemStack(block, block.getDesignSystem().getMaterialIndex(type1), block.getDesignSystem().getMaterialIndex(type2), CarpentryManager.carpentryInterface.getDesignIndex(design));
	}

	public static ItemStack getItemStack(final BlockDesign block, final int type1, final int type2, final int design) {
		return TileEntityMetadata.getItemStack(block, getMetadata(type1, type2, design, 0, EnumFacing.UP.ordinal()));
	}

	public static ItemStack getItemStack(final BlockDesign blockC, final DesignBlock block) {
		return getItemStack(blockC, block.getPrimaryMaterial(), block.getSecondaryMaterial(), block.getDesign());
	}

	public static int getMetadata(final int plank1, final int plank2, final int design, final int rotation, final int facing) {
		return plank1 + (plank2 << 8) + (design << 16) + (rotation << 26) + (facing << 28);
	}

	public static DesignBlock getDesignBlock(final IDesignSystem system, final int meta) {
		final int plankID1 = meta & 0xFF;
		final int plankID2 = meta >> 8 & 0xFF;
		final int tile = meta >> 16 & 0x3FF;
		final int rotation = meta >> 26 & 0x3;
		final int axis = meta >> 28 & 0x7;
		final IDesignMaterial type1 = system.getMaterial(plankID1);
		final IDesignMaterial type2 = system.getMaterial(plankID2);
		final IDesign type3 = CarpentryManager.carpentryInterface.getDesign(tile);
		return new DesignBlock(system, type1, type2, type3, rotation, EnumFacing.VALUES[axis]);
	}

	public static DesignBlock getCarpentryPanel(final IDesignSystem system, final int meta) {
		final DesignBlock block = getDesignBlock(system, meta);
		block.setPanel();
		return block;
	}

	public static int getBlockMetadata(final IDesignSystem system, final DesignBlock block) {
		final int plank1 = system.getMaterialIndex(block.getPrimaryMaterial());
		final int plank2 = system.getMaterialIndex(block.getSecondaryMaterial());
		final int design = CarpentryManager.carpentryInterface.getDesignIndex(block.getDesign());
		final int rotation = block.getRotation();
		final int facing = block.getFacing().ordinal();
		return getMetadata(plank1, plank2, design, rotation, facing);
	}

	public static int getItemMetadata(final IDesignSystem system, final DesignBlock block) {
		final int plank1 = system.getMaterialIndex(block.getPrimaryMaterial());
		final int plank2 = system.getMaterialIndex(block.getSecondaryMaterial());
		final int design = CarpentryManager.carpentryInterface.getDesignIndex(block.getDesign());
		return getMetadata(plank1, plank2, design, 0, EnumFacing.UP.ordinal());
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
	public void postInit() {
		for (final EnumDesign design : EnumDesign.values()) {
			CarpentryManager.carpentryInterface.registerDesign(design.ordinal(), design);
		}
	}

	@Override
	public Set<String> getDependencyUids() {
		return ImmutableSet.of(ExtraTreesModuleUIDs.MACHINES);
	}
}
