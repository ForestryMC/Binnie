package binnie.core.network;

public interface IPacketProvider {
    String getChannel();

    IPacketID[] getPacketIDs();
}
