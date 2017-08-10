package binnie.genetics.genetics;

import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import net.minecraftforge.common.util.Constants;

import binnie.core.genetics.Gene;
import binnie.api.genetics.IGene;

public class SequencerItem extends GeneItem {
	public int sequenced;
	public boolean analysed;

	public SequencerItem(IGene gene) {
		super(gene);
		sequenced = 0;
		analysed = false;
	}

	@Nullable
	public static SequencerItem create(ItemStack itemStack) {
		NBTTagCompound nbt = itemStack.getTagCompound();
		if (nbt != null &&
			nbt.hasKey("gene", Constants.NBT.TAG_COMPOUND) &&
			nbt.hasKey("seq", Constants.NBT.TAG_BYTE) &&
			nbt.hasKey("ana")
			) {
			NBTTagCompound geneNbt = nbt.getCompoundTag("gene");
			Gene gene = Gene.create(geneNbt);
			SequencerItem sequencerItem = new SequencerItem(gene);
			sequencerItem.sequenced = nbt.getByte("seq");
			sequencerItem.analysed = nbt.getBoolean("ana");
			return sequencerItem;
		}
		return null;
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt2) {
		NBTTagCompound nbt = super.writeToNBT(nbt2);
		nbt.setByte("seq", (byte) sequenced);
		nbt.setBoolean("ana", analysed);
		return nbt;
	}
}
