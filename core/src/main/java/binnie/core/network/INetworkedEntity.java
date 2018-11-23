package binnie.core.network;

import binnie.core.network.packet.PacketPayload;

public interface INetworkedEntity {
	void writeToPacket(PacketPayload p0);

	void readFromPacket(PacketPayload p0);
}
