package binnie.core.network;

import binnie.core.network.packet.PacketPayload;

public interface INetworkedEntity {
    void writeToPacket(PacketPayload payload);

    void readFromPacket(PacketPayload payload);
}
