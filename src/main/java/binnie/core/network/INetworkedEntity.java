package binnie.core.network;

import binnie.core.network.packet.*;

public interface INetworkedEntity {
	void writeToPacket(PacketPayload payload);

	void readFromPacket(PacketPayload payload);
}
