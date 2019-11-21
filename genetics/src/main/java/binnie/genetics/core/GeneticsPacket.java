package binnie.genetics.core;

import binnie.core.BinnieCore;
import binnie.core.network.IPacketID;
import binnie.core.network.packet.MessageBinnie;
import binnie.core.network.packet.MessageNBT;
import binnie.genetics.genetics.GeneTracker;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public enum GeneticsPacket implements IPacketID {
	GENE_TRACKER_SYNC;

	@Override
	public void onMessage(MessageBinnie message, MessageContext context) {
		if (this != GeneticsPacket.GENE_TRACKER_SYNC || context.side != Side.CLIENT) {
			return;
		}
		MessageNBT packet = new MessageNBT(message);
		EntityPlayer player = BinnieCore.getBinnieProxy().getPlayer();
		GeneTracker tracker = GeneTracker.getTracker(BinnieCore.getBinnieProxy().getWorld(), player.getGameProfile());
		tracker.readFromNBT(packet.getTagCompound());
	}
}
