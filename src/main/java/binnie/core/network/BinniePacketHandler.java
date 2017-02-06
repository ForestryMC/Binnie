package binnie.core.network;

import binnie.core.AbstractMod;
import binnie.core.network.packet.MessageBinnie;
import com.google.common.base.Preconditions;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public abstract class BinniePacketHandler implements IMessageHandler<MessageBinnie, IMessage> {
	@Nullable
	private IPacketProvider provider;

	public BinniePacketHandler(final AbstractMod mod) {
		this.setProvider(mod);
	}

	public void setProvider(final IPacketProvider provider) {
		this.provider = provider;
	}

	private IPacketProvider getProvider() {
		Preconditions.checkState(provider != null, "provider has not been set.");
		return provider;
	}

	@Override
	@Nullable
	public IMessage onMessage(final MessageBinnie message, final MessageContext ctx) {
		final int packetId = message.id;
		for (final IPacketID id : this.getProvider().getPacketIDs()) {
			if (id.ordinal() == packetId) {
				if (ctx.side == Side.CLIENT) {
					onClientMessage(id, message, ctx);
				} else {
					onServerMessage(id, message, ctx);
				}
				return null;
			}
		}
		return null;
	}

	@SideOnly(Side.CLIENT)
	private static void onClientMessage(final IPacketID id, final MessageBinnie message, final MessageContext ctx) {
		checkThreadAndEnqueue(id, message, ctx, Minecraft.getMinecraft());
	}

	private static void onServerMessage(final IPacketID id, final MessageBinnie message, final MessageContext ctx) {
		EntityPlayerMP player = ctx.getServerHandler().playerEntity;
		checkThreadAndEnqueue(id, message, ctx, player.getServerWorld());
	}

	private static void checkThreadAndEnqueue(final IPacketID id, final MessageBinnie message, final MessageContext ctx, IThreadListener threadListener) {
		if (!threadListener.isCallingFromMinecraftThread()) {
			threadListener.addScheduledTask(() -> id.onMessage(message, ctx));
		}
	}
}
