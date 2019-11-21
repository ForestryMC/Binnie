package binnie.core.util;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public class NBTUtil {

	private NBTUtil() {
	}

	public static void readFromList(NBTTagCompound compound, String listKey, Consumer<NBTTagCompound> consumer) {
		if (!compound.hasKey(listKey, 9)) {
			return;
		}
		NBTTagList tagList = compound.getTagList(listKey, 10);
		for (int i = 0; i < tagList.tagCount(); ++i) {
			consumer.accept(tagList.getCompoundTagAt(i));
		}
	}

	public static <K, V> void writeToList(NBTTagCompound compound, String listKey, Map<K, V> content, BiFunction<K, V, NBTTagCompound> consumer) {
		NBTTagList tagList = new NBTTagList();
		for (Map.Entry<K, V> entry : content.entrySet()) {
			tagList.appendTag(consumer.apply(entry.getKey(), entry.getValue()));
		}
		compound.setTag(listKey, tagList);
	}

	public static void readFromNBT(IEnergyStorage storage, String key, NBTTagCompound nbt) {
		if (nbt.hasKey(key)) {
			CapabilityEnergy.ENERGY.readNBT(storage, null, nbt.getTag(key));
		}
	}


	public static void writeToNBT(IEnergyStorage storage, String key, NBTTagCompound nbt) {
		NBTBase nbtBase = CapabilityEnergy.ENERGY.writeNBT(storage, null);
		if (nbtBase != null) {
			nbt.setTag(key, nbtBase);
		}
	}
}
