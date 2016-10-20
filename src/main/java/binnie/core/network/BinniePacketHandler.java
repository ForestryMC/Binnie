package binnie.core.network;

import binnie.core.AbstractMod;
import binnie.core.network.packet.MessageBinnie;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public abstract class BinniePacketHandler implements IMessageHandler<MessageBinnie, IMessage> {
    private IPacketProvider provider;

    public BinniePacketHandler(final AbstractMod mod) {
        this.setProvider(mod);
    }

    public void setProvider(final IPacketProvider provider) {
        this.provider = provider;
    }

    @Override
    public IMessage onMessage(final MessageBinnie message, final MessageContext ctx) {
        try {
            final int packetId = message.id;
            for (final IPacketID id : this.provider.getPacketIDs()) {
                if (id.ordinal() == packetId) {
                    id.onMessage(message, ctx);
                    return null;
                }
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return null;
    }
}
