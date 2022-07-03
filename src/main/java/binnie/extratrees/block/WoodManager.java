package binnie.extratrees.block;

import binnie.core.block.TileEntityMetadata;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.api.CarpentryManager;
import binnie.extratrees.api.IDesignMaterial;
import binnie.extratrees.block.decor.FenceDescription;
import binnie.extratrees.block.decor.FenceType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class WoodManager {
    public static IPlankType getPlankType(int index) {
        IDesignMaterial wood = CarpentryManager.carpentryInterface.getWoodMaterial(index);
        if (wood instanceof IPlankType) {
            return (IPlankType) wood;
        }
        return PlankType.ExtraTreePlanks.Fir;
    }

    public static int getPlankTypeIndex(IPlankType type) {
        int index = CarpentryManager.carpentryInterface.getCarpentryWoodIndex(type);
        return (index < 0) ? 0 : index;
    }

    public static FenceType getFenceType(ItemStack stack) {
        FenceDescription desc = getFenceDescription(stack);
        return (desc == null) ? null : desc.getFenceType();
    }

    public static FenceDescription getFenceDescription(ItemStack stack) {
        if (stack == null) {
            return null;
        }

        if (stack.getItem() == Item.getItemFromBlock(ExtraTrees.blockMultiFence)) {
            int damage = TileEntityMetadata.getItemDamage(stack);
            return getFenceDescription(damage);
        }

        for (IPlankType type : getAllPlankTypes()) {
            if (type instanceof IFenceProvider) {
                ItemStack f = ((IFenceProvider) type).getFence();
                if (f != null && ItemStack.areItemStacksEqual(stack, f)) {
                    return new FenceDescription(new FenceType(0), type, type);
                }
            }
        }
        return null;
    }

    public static FenceDescription getFenceDescription(int meta) {
        return new FenceDescription(meta);
    }

    public static FenceType getFenceType(int meta) {
        return getFenceDescription(meta).getFenceType();
    }

    public static ItemStack getGate(IPlankType plank) {
        if (plank == PlankType.VanillaPlanks.OAK) {
            return new ItemStack(Blocks.fence_gate);
        }
        return TileEntityMetadata.getItemStack(ExtraTrees.blockGate, getPlankTypeIndex(plank));
    }

    public static ItemStack getFence(IPlankType plank, FenceType type, int amount) {
        return getFence(plank, plank, type, amount);
    }

    public static ItemStack getFence(IPlankType plank, IPlankType plank2, FenceType type, int amount) {
        if (plank instanceof IFenceProvider && plank == plank2 && type.isPlain()) {
            ItemStack original = ((IFenceProvider) plank).getFence();
            if (original != null) {
                original.stackSize = amount;
                return original;
            }
        }

        int ord = type.ordinal();
        int i = getPlankTypeIndex(plank) + 256 * ord;
        ItemStack stack =
                TileEntityMetadata.getItemStack(ExtraTrees.blockMultiFence, i + 65536 * getPlankTypeIndex(plank2));
        stack.stackSize = amount;
        return stack;
    }

    public static ItemStack getDoor(IPlankType plank, DoorType type) {
        return TileEntityMetadata.getItemStack(ExtraTrees.blockDoor, type.ordinal() * 256 + getPlankTypeIndex(plank));
    }

    public static List<IPlankType> getAllPlankTypes() {
        List<IPlankType> list = new ArrayList<>();
        Collections.addAll(list, PlankType.ExtraTreePlanks.values());
        Collections.addAll(list, PlankType.ForestryPlanks.values());
        for (IPlankType type : PlankType.ExtraBiomesPlank.values()) {
            if (type.getStack() != null) {
                list.add(type);
            }
        }

        Collections.addAll(list, PlankType.VanillaPlanks.values());
        return list;
    }

    public static IPlankType get(ItemStack species) {
        for (IPlankType type : getAllPlankTypes()) {
            if (type.getStack() != null && type.getStack().isItemEqual(species)) {
                return type;
            }
        }
        return null;
    }
}
