package binnie.core.models;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.IRegistry;
import net.minecraft.world.IBlockAccess;

import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.PerspectiveMapWrapper;
import net.minecraftforge.client.model.SimpleModelState;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.core.ForestryAPI;
import forestry.api.core.IItemModelRegister;
import forestry.api.core.IModelManager;
import forestry.api.core.ISpriteRegister;
import forestry.api.core.IStateMapperRegister;
import forestry.core.blocks.IColoredBlock;
import forestry.core.items.IColoredItem;
import forestry.core.models.BlockModelEntry;
import forestry.core.utils.ModelUtil;

@SideOnly(Side.CLIENT)
public class ModelManager implements IModelManager {

	private static final List<BlockModelEntry> customBlockModels = new ArrayList<>();
	private static final List<ISpriteRegister> spriteRegister = new ArrayList<>();
	private static IModelState defaultFenceState;
	private final String modID;
	private final List<IItemModelRegister> itemModelRegisters = new ArrayList<>();
	private final List<IStateMapperRegister> stateMapperRegisters = new ArrayList<>();
	private final List<IColoredBlock> blockColorList = new ArrayList<>();
	private final List<IColoredItem> itemColorList = new ArrayList<>();

	public ModelManager(String modID) {
		this.modID = modID;
	}

	public static IModelState getDefaultFenceState() {
		return defaultFenceState;
	}

	@SideOnly(Side.CLIENT)
	public static void reloadSprites() {
		for (ISpriteRegister spriteRegister : spriteRegister) {
			if (spriteRegister != null) {
				spriteRegister.registerSprites(ForestryAPI.textureManager);
			}
		}
	}

	public static void registerCustomModels(ModelBakeEvent event) {
		IRegistry<ModelResourceLocation, IBakedModel> registry = event.getModelRegistry();
		for (final BlockModelEntry entry : customBlockModels) {
			registry.putObject(entry.blockModelLocation, entry.model);
			if (entry.itemModelLocation != null) {
				registry.putObject(entry.itemModelLocation, entry.model);
			}
		}

		IModelState defaultBlockState = ModelUtil.loadModelState(new ResourceLocation("minecraft:models/block/block"));
		IModelState defaultFenceState = ModelUtil.loadModelState(new ResourceLocation("minecraft:models/block/fence_inventory"));
		ModelManager.defaultFenceState = mergeStates(defaultBlockState, defaultFenceState);
	}

	private static IModelState mergeStates(IModelState state, IModelState secondaryState) {
		Map<TransformType, TRSRTransformation> tMap = Maps.newHashMap();
		TRSRTransformation guiTransformation = secondaryState.apply(Optional.of(TransformType.GUI)).get();
		tMap.putAll(PerspectiveMapWrapper.getTransforms(state));
		tMap.put(TransformType.GUI, guiTransformation);
		return new SimpleModelState(ImmutableMap.copyOf(tMap));
	}

	public static void registerCustomBlockModel(BlockModelEntry index) {
		customBlockModels.add(index);
	}

	@Override
	public void registerItemModel(Item item, int meta, String identifier) {
		ModelLoader.setCustomModelResourceLocation(item, meta, getModelLocation(identifier));
	}

	@Override
	public void registerItemModel(Item item, int meta, String modID, String identifier) {
		ModelLoader.setCustomModelResourceLocation(item, meta, getModelLocation(modID, identifier));
	}

	@Override
	public void registerItemModel(Item item, int meta) {
		ModelLoader.setCustomModelResourceLocation(item, meta, getModelLocation(item));
	}

	@Override
	public void registerItemModel(Item item, ItemMeshDefinition definition) {
		ModelLoader.setCustomMeshDefinition(item, definition);
	}

	@Override
	public ModelResourceLocation getModelLocation(Item item) {
		String itemName = item.getRegistryName().getPath();
		return getModelLocation(itemName);
	}

	@Override
	public ModelResourceLocation getModelLocation(String identifier) {
		return getModelLocation(modID, identifier);
	}

	@Override
	public ModelResourceLocation getModelLocation(String modID, String identifier) {
		return new ModelResourceLocation(new ResourceLocation(modID, identifier), "inventory");
	}

	@SideOnly(Side.CLIENT)
	public void registerBlockClient(Block block) {
		if (block instanceof IItemModelRegister) {
			itemModelRegisters.add((IItemModelRegister) block);
		}
		if (block instanceof IStateMapperRegister) {
			stateMapperRegisters.add((IStateMapperRegister) block);
		}
		if (block instanceof IColoredBlock) {
			blockColorList.add((IColoredBlock) block);
		}
		if (block instanceof ISpriteRegister) {
			spriteRegister.add((ISpriteRegister) block);
		}
	}

	@SideOnly(Side.CLIENT)
	public void registerItemClient(Item item) {
		if (item instanceof IItemModelRegister) {
			itemModelRegisters.add((IItemModelRegister) item);
		}
		if (item instanceof IColoredItem) {
			itemColorList.add((IColoredItem) item);
		}
		if (item instanceof ISpriteRegister) {
			spriteRegister.add((ISpriteRegister) item);
		}
	}

	@SideOnly(Side.CLIENT)
	public void registerModels() {
		for (IItemModelRegister itemModelRegister : itemModelRegisters) {
			Item item = null;
			if (itemModelRegister instanceof Block) {
				item = Item.getItemFromBlock((Block) itemModelRegister);
			} else if (itemModelRegister instanceof Item) {
				item = (Item) itemModelRegister;
			}

			if (item != null) {
				itemModelRegister.registerModel(item, this);
			}
		}

		for (IStateMapperRegister stateMapperRegister : stateMapperRegisters) {
			stateMapperRegister.registerStateMapper();
		}
	}

	@SideOnly(Side.CLIENT)
	public void registerItemAndBlockColors() {
		Minecraft minecraft = Minecraft.getMinecraft();

		BlockColors blockColors = minecraft.getBlockColors();
		for (IColoredBlock blockColor : blockColorList) {
			if (blockColor instanceof Block) {
				blockColors.registerBlockColorHandler(ColoredBlockBlockColor.INSTANCE, (Block) blockColor);
			}
		}

		ItemColors itemColors = minecraft.getItemColors();
		for (IColoredItem itemColor : itemColorList) {
			if (itemColor instanceof Item) {
				itemColors.registerItemColorHandler(ColoredItemItemColor.INSTANCE, (Item) itemColor);
			}
		}
	}

	@SideOnly(Side.CLIENT)
	private static class ColoredItemItemColor implements IItemColor {
		public static final ColoredItemItemColor INSTANCE = new ColoredItemItemColor();

		private ColoredItemItemColor() {

		}

		@Override
		public int colorMultiplier(ItemStack stack, int tintIndex) {
			Item item = stack.getItem();
			if (item instanceof IColoredItem) {
				return ((IColoredItem) item).getColorFromItemstack(stack, tintIndex);
			}
			return 0xffffff;
		}
	}

	@SideOnly(Side.CLIENT)
	private static class ColoredBlockBlockColor implements IBlockColor {
		public static final ColoredBlockBlockColor INSTANCE = new ColoredBlockBlockColor();

		private ColoredBlockBlockColor() {

		}

		@Override
		public int colorMultiplier(IBlockState state, @Nullable IBlockAccess worldIn, @Nullable BlockPos pos, int tintIndex) {
			Block block = state.getBlock();
			if (block instanceof IColoredBlock) {
				return ((IColoredBlock) block).colorMultiplier(state, worldIn, pos, tintIndex);
			}
			return 0xffffff;
		}
	}
}