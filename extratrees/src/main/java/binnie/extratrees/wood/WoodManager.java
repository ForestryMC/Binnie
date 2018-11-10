package binnie.extratrees.wood;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import forestry.api.arboriculture.IWoodType;
import forestry.api.arboriculture.TreeManager;
import forestry.api.arboriculture.WoodBlockKind;
import forestry.arboriculture.IWoodTyped;
import forestry.core.utils.Translator;

import binnie.core.block.TileEntityMetadata;
import binnie.design.api.IDesignMaterial;
import binnie.extratrees.api.CarpentryManager;
import binnie.extratrees.blocks.decor.FenceDescription;
import binnie.extratrees.blocks.decor.FenceType;
import binnie.extratrees.modules.ModuleWood;
import binnie.extratrees.wood.planks.ExtraTreePlanks;
import binnie.extratrees.wood.planks.ForestryPlanks;
import binnie.extratrees.wood.planks.IPlankType;
import binnie.extratrees.wood.planks.VanillaPlanks;

public class WoodManager {
	private static List<IPlankType> PLANK_TYPES;
	private static Map<IPlankType, ItemStack> PLANKS_STACKS;

	public static String getDisplayName(IWoodTyped wood, IWoodType woodType) {
		WoodBlockKind blockKind = wood.getBlockKind();

		String displayName;

		if (woodType instanceof EnumETLog || woodType instanceof EnumShrubLog) {
			String customUnlocalizedName = "tile.et." + blockKind + "." + woodType + ".name";
			if (Translator.canTranslateToLocal(customUnlocalizedName)) {
				displayName = Translator.translateToLocal(customUnlocalizedName);
			} else {
				String woodGrammar = Translator.translateToLocal("for." + blockKind + ".grammar");
				String woodTypeName = Translator.translateToLocal("et.trees.woodType." + woodType);

				displayName = woodGrammar.replaceAll("%TYPE", woodTypeName);
			}
		} else {
			throw new IllegalArgumentException("Unknown wood type: " + woodType);
		}

		if (wood.isFireproof()) {
			displayName = Translator.translateToLocalFormatted("tile.for.fireproof", displayName);
		}

		return displayName;
	}

	public static IPlankType getPlankType(final int index) {
		final IDesignMaterial wood = CarpentryManager.carpentryInterface.getWoodMaterial(index);
		if (wood instanceof IPlankType) {
			return (IPlankType) wood;
		}
		return ExtraTreePlanks.Fir;
	}

	public static int getPlankTypeIndex(final IPlankType type) {
		final int index = CarpentryManager.carpentryInterface.getCarpentryWoodIndex(type);
		return (index < 0) ? 0 : index;
	}

	@Nullable
	public static FenceType getFenceType(final ItemStack stack) {
		final FenceDescription desc = getFenceDescription(stack);
		return (desc == null) ? null : desc.getFenceType();
	}

	@Nullable
	public static FenceDescription getFenceDescription(final ItemStack stack) {
		if (stack.getItem() == Item.getItemFromBlock(ModuleWood.blockMultiFence)) {
			final int damage = TileEntityMetadata.getItemDamage(stack);
			return getFenceDescription(damage);
		}
		//old default recipes for the wood fences
		/*for (final IPlankType type : getAllPlankTypes()) {
			if (type instanceof IFenceProvider) {
				final ItemStack f = ((IFenceProvider) type).getFence();
				if (ItemStack.areItemStacksEqual(stack, f)) {
					return new FenceDescription(new FenceType(0), type, type);
				}
			}
		}*/
		return null;
	}

	public static FenceDescription getFenceDescription(final int meta) {
		return new FenceDescription(meta);
	}

	public static FenceType getFenceType(final int meta) {
		return getFenceDescription(meta).getFenceType();
	}

	public static ItemStack getGate(final IPlankType plank) {
		if (plank == VanillaPlanks.OAK) {
			return new ItemStack(Blocks.OAK_FENCE_GATE);
		}
		return TreeManager.woodAccess.getStack(plank.getWoodType(), WoodBlockKind.FENCE_GATE, false);
	}

	public static ItemStack getDoor(final IPlankType plank) {
		if (plank == VanillaPlanks.OAK) {
			return new ItemStack(Items.OAK_DOOR);
		} else if (plank == VanillaPlanks.BIRCH) {
			return new ItemStack(Items.BIRCH_DOOR);
		} else if (plank == VanillaPlanks.SPRUCE) {
			return new ItemStack(Items.SPRUCE_DOOR);
		} else if (plank == VanillaPlanks.BIG_OAK) {
			return new ItemStack(Items.DARK_OAK_DOOR);
		} else if (plank == VanillaPlanks.JUNGLE) {
			return new ItemStack(Items.JUNGLE_DOOR);
		} else if (plank == VanillaPlanks.ACACIA) {
			return new ItemStack(Items.ACACIA_DOOR);
		}
		return TreeManager.woodAccess.getStack(plank.getWoodType(), WoodBlockKind.DOOR, false);
	}

	public static ItemStack getFence(final IPlankType plank, final FenceType type, final int amount) {
		return getFence(plank, plank, type, amount);
	}

	public static ItemStack getFence(final IPlankType plank, final IPlankType plank2, final FenceType type, final int amount) {
		//old default recipes for the wood fences
		/*if (plank instanceof IFenceProvider && plank == plank2 && type.isPlain()) {
			final ItemStack original = ((IFenceProvider) plank).getFence();
			if (!original.isEmpty()) {
				original.setCount(amount);
				return original;
			}
		}*/
		final int ord = type.ordinal();
		final int i = getPlankTypeIndex(plank) + 256 * ord;
		final ItemStack stack = TileEntityMetadata.getItemStack(ModuleWood.blockMultiFence, i + 65536 * getPlankTypeIndex(plank2));
		stack.setCount(amount);
		return stack;
	}

	public static List<IPlankType> getAllPlankTypes() {
		if (PLANK_TYPES == null) {
			PLANK_TYPES = new ArrayList<>();
			Collections.addAll(PLANK_TYPES, ExtraTreePlanks.VALUES);
			Collections.addAll(PLANK_TYPES, ForestryPlanks.values());
			Collections.addAll(PLANK_TYPES, VanillaPlanks.values());
			//TODO: extrabiomes 1.12
			/*for (final IPlankType type : ExtraBiomesPlank.values()) {
				if (type.getStack() != null) {
					list.add(type);
				}
			}*/
		}
		return PLANK_TYPES;
	}

	public static Map<IPlankType, ItemStack> getAllPlankStacks() {
		if (PLANKS_STACKS == null) {
			PLANKS_STACKS = new HashMap<>();
			for (IPlankType type : getAllPlankTypes()) {
				PLANKS_STACKS.put(type, type.getStack(false));
			}
		}
		return PLANKS_STACKS;
	}

	public static Collection<ItemStack> getAllPlankStacks(IPlankType type) {
		Map<IPlankType, ItemStack> planks = new HashMap<>(getAllPlankStacks());
		planks.remove(type);
		return planks.values();
	}

	@Nullable
	public static IPlankType getPlankType(ItemStack itemStack) {
		for (IPlankType type : getAllPlankTypes()) {
			if (type.getStack(false).isItemEqual(itemStack)) {
				return type;
			}
		}
		return null;
	}
}
