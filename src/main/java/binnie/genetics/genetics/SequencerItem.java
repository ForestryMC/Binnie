package binnie.genetics.genetics;

import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import net.minecraftforge.common.util.Constants;

import binnie.core.genetics.Gene;
import binnie.genetics.api.IGene;

public class SequencerItem extends GeneItem {
	public int sequenced;
	public boolean analysed;

	public SequencerItem(final IGene gene) {
		super(gene);
		this.sequenced = 0;
		this.analysed = false;
	}

	@Nullable
	public static SequencerItem create(final ItemStack itemStack) {
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
	public NBTTagCompound writeToNBT(final NBTTagCompound nbt2) {
		NBTTagCompound nbt = super.writeToNBT(nbt2);
		nbt.setByte("seq", (byte) this.sequenced);
		nbt.setBoolean("ana", this.analysed);
		return nbt;
	}
}
