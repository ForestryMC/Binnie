package binnie.genetics.machine.inoculator;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import forestry.api.apiculture.EnumBeeChromosome;
import forestry.api.apiculture.IBeeRoot;
import forestry.api.genetics.IAllele;

import binnie.core.api.genetics.IGene;

public class Inoculator {
	public static final int SLOT_SERUM_VIAL = 0;
	public static final int[] SLOT_SERUM_RESERVE = new int[]{1, 2};
	public static final int[] SLOT_SERUM_EXPENDED = new int[]{3, 4};
	public static final int[] SLOT_RESERVE = new int[]{5, 6, 7, 8};
	public static final int SLOT_TARGET = 9;
	public static final int[] SLOT_FINISHED = new int[]{10, 11, 12, 13};
	public static final int TANK_VEKTOR = 0;
}
