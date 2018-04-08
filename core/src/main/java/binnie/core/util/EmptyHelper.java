package binnie.core.util;

import binnie.core.api.genetics.IGene;
import binnie.core.gui.IBinnieGUID;
import binnie.core.gui.minecraft.CustomSlot;
import binnie.core.machines.power.TankInfo;
import binnie.core.network.IPacketID;
import forestry.api.genetics.IChromosomeType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.IFluidTank;

public final class EmptyHelper {
    private EmptyHelper() {}

    public static final IPacketID[] PACKET_IDS_EMPTY = new IPacketID[0];

    public static final IBinnieGUID[] BINNIE_GUIDS_EMPTY = new IBinnieGUID[0];

    public static final Class<?>[] CLASS_ARRAY_EMPTY =  new Class[0];

    public static final IChromosomeType[] CHROMOSOME_TYPES_EMPTY = new IChromosomeType[0];

    public static final CustomSlot[] CUSTOM_SLOTS_EMPTY = new CustomSlot[0];

    public static final int[] INT_ARRAY_EMPTY = new int[0];

    public static final TankInfo[] TANK_INFO_EMPTY = new TankInfo[0];

    public static final IFluidTank[] FLUID_TANKS_EMPTY = new IFluidTank[0];

    public static final Integer[] DEFAULT_TABS_EMPTY = new Integer[0];

    public static final IProperty[] PROPERTIES_EMPTY = new IProperty[0];

    public static final ItemStack[] ITEM_STACKS_EMPTY = new ItemStack[0];

    public static final IGene[] GENES_EMPTY = new IGene[0];
}
