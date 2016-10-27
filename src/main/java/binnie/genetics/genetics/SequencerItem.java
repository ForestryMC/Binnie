package binnie.genetics.genetics;

import binnie.genetics.api.IGene;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class SequencerItem extends GeneItem {
	public int sequenced;
	public boolean analysed;

	public SequencerItem(final ItemStack stack) {
		super(stack);
	}

	public SequencerItem(final IGene gene) {
		super(gene);
		this.sequenced = 0;
		this.analysed = false;
	}

	@Override
	public void readFromNBT(final NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.sequenced = nbt.getByte("seq");
		this.analysed = nbt.getBoolean("ana");
	}

	@Override
	public NBTTagCompound writeToNBT(final NBTTagCompound nbt2) {
		NBTTagCompound nbt = super.writeToNBT(nbt2);
		nbt.setByte("seq", (byte) this.sequenced);
		nbt.setBoolean("ana", this.analysed);
		return nbt;
	}
}
