package binnie.core.network;

import binnie.Constants;
import binnie.core.AbstractMod;
import binnie.core.network.packet.MessageBinnie;
import com.google.common.base.Preconditions;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public abstract class BinniePacketHandler implements IMessageHandler<MessageBinnie, IMessage> {
	@SuppressWarnings({"unused", "NullableProblems"})
	@SidedProxy(modId = Constants.CORE_MOD_ID)
	private static MessageProxy messageProxy;

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
				messageProxy.onMessage(id, message, ctx);
				return null;
			}
		}
		return null;
	}

	private static abstract class MessageProxy {
		public abstract void onMessage(final IPacketID id, final MessageBinnie message, final MessageContext ctx);

		protected static void checkThreadAndEnqueue(final IPacketID id, final MessageBinnie message, final MessageContext ctx, IThreadListener threadListener) {
			if (!threadListener.isCallingFromMinecraftThread()) {
				threadListener.addScheduledTask(() -> id.onMessage(message, ctx));
			}
		}
	}

	@SuppressWarnings("unused")
	@SideOnly(Side.CLIENT)
	public static class ClientProxy extends MessageProxy {
		@Override
		public void onMessage(IPacketID id, MessageBinnie message, MessageContext ctx) {
			checkThreadAndEnqueue(id, message, ctx, Minecraft.getMinecraft());
		}
	}

	@SuppressWarnings("unused")
	public static class ServerProxy extends MessageProxy {
		@Override
		public void onMessage(IPacketID id, MessageBinnie message, MessageContext ctx) {
			EntityPlayerMP player = ctx.getServerHandler().playerEntity;
			checkThreadAndEnqueue(id, message, ctx, player.getServerWorld());
		}
	}
}
