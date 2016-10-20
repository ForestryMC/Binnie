package binnie.core.network;

import binnie.core.network.packet.PacketPayload;

public interface INetworkedEntity {
    void writeToPacket(final PacketPayload p0);

    void readFromPacket(final PacketPayload p0);
}
