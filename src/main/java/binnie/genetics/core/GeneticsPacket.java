package binnie.genetics.core;

import net.minecraft.entity.player.EntityPlayer;

import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

import binnie.core.BinnieCore;
import binnie.core.network.IPacketID;
import binnie.core.network.packet.MessageBinnie;
import binnie.core.network.packet.MessageNBT;
import binnie.genetics.genetics.GeneTracker;

public enum GeneticsPacket implements IPacketID {
	GeneTrackerSync;

	@Override
	public void onMessage(final MessageBinnie message, final MessageContext context) {
		if (this == GeneticsPacket.GeneTrackerSync && context.side == Side.CLIENT) {
			final MessageNBT packet = new MessageNBT(message);
			final EntityPlayer player = BinnieCore.getBinnieProxy().getPlayer();
			GeneTracker tracker = GeneTracker.getTracker(BinnieCore.getBinnieProxy().getWorld(), player.getGameProfile());
			if (tracker != null) {
				tracker.readFromNBT(packet.getTagCompound());
			}
		}
	}
}
