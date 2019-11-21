package binnie.genetics.genetics;

import binnie.core.api.genetics.IGene;
import binnie.core.genetics.Gene;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;

public class SequencerItem extends GeneItem {
	private int sequenced;
	private boolean analysed;

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

	public int getSequenced() {
		return sequenced;
	}

	public void setSequenced(int sequenced) {
		this.sequenced = sequenced;
	}

	public boolean isAnalysed() {
		return analysed;
	}

	public void setAnalysed(boolean analysed) {
		this.analysed = analysed;
	}
}
