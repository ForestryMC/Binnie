package binnie.extratrees.modules.features;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import binnie.core.Constants;
import binnie.core.features.FeatureType;
import binnie.core.features.IBlockFeature;
import binnie.core.features.IFeatureConstructor;
import binnie.core.features.RegisterFeatureEvent;
import binnie.core.modules.ExtraTreesModuleUIDs;
import binnie.design.blocks.BlockDesign;
import binnie.design.items.ItemDesign;
import binnie.extratrees.blocks.BlockCarpentry;
import binnie.extratrees.blocks.BlockCarpentryPanel;
import binnie.extratrees.blocks.BlockHops;
import binnie.extratrees.blocks.BlockStainedDesign;

@Mod.EventBusSubscriber(modid = Constants.EXTRA_TREES_MOD_ID)
public enum ExtraTreesBlocks implements IBlockFeature<Block, ItemBlock> {
	HOPS("hops", BlockHops::new, false),
	CARPENTRY("carpentry", () -> new BlockCarpentry("carpentry"), true) {
		@Override
		public String getModuleId() {
			return ExtraTreesModuleUIDs.CARPENTRY;
		}
	},
	PANEL("carpentryPanel", BlockCarpentryPanel::new, true) {
		@Override
		public String getModuleId() {
			return ExtraTreesModuleUIDs.CARPENTRY;
		}
	},
	STAINED("stainedGlass", BlockStainedDesign::new, true) {
		@Override
		public String getModuleId() {
			return ExtraTreesModuleUIDs.CARPENTRY;
		}
	};

	private final String identifier;
	private final IFeatureConstructor<Block> constructorBlock;
	@Nullable
	private final IFeatureConstructor<ItemBlock> constructorItem;
	@Nullable
	private Block block;
	@Nullable
	private ItemBlock item;

	ExtraTreesBlocks(String identifier, IFeatureConstructor<Block> constructorBlock, boolean carpentry) {
		this.identifier = identifier;
		this.constructorBlock = constructorBlock;
		if (carpentry) {
			this.constructorItem = () -> new ItemDesign((BlockDesign) block);
		} else {
			this.constructorItem = null;
		}
	}

	ExtraTreesBlocks(String identifier, IFeatureConstructor<Block> constructorBlock, IFeatureConstructor<ItemBlock> constructorItem) {
		this.identifier = identifier;
		this.constructorBlock = constructorBlock;
		this.constructorItem = constructorItem;
	}

	@SubscribeEvent
	public static void registerFeatures(RegisterFeatureEvent event) {
		event.register(ExtraTreesBlocks.class);
	}

	@Override
	public boolean hasItem() {
		return item != null;
	}

	@Override
	@Nullable
	public ItemBlock getItem() {
		return item;
	}

	@Override
	public boolean hasBlock() {
		return block != null;
	}

	@Override
	@Nullable
	public Block getBlock() {
		return block;
	}

	@Override
	public String getIdentifier() {
		return identifier;
	}

	@Override
	public void setBlock(@Nullable Block block) {
		this.block = block;
	}

	@Override
	public void setItem(@Nullable ItemBlock item) {
		this.item = item;
	}

	@Override
	public IFeatureConstructor<Block> getConstructor() {
		return constructorBlock;
	}

	@Override
	@Nullable
	public IFeatureConstructor<ItemBlock> getItemConstructor() {
		return constructorItem;
	}

	@Override
	public FeatureType getType() {
		return FeatureType.BLOCK;
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
