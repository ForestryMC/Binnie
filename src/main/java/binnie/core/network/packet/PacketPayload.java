package binnie.core.network.packet;

import binnie.core.network.INetworkedEntity;
import java.util.ArrayList;
import java.util.List;

public class PacketPayload {
    public List<Integer> intPayload;
    public List<Float> floatPayload;
    public List<String> stringPayload;

    public PacketPayload() {
        intPayload = new ArrayList<>();
        floatPayload = new ArrayList<>();
        stringPayload = new ArrayList<>();
    }

    // TODO unused constructor?
    public PacketPayload(INetworkedEntity tile) {
        this();
        tile.writeToPacket(this);
    }

    public void addInteger(int a) {
        intPayload.add(a);
    }

    public void addFloat(float a) {
        floatPayload.add(a);
    }

    public void addString(String a) {
        stringPayload.add(a);
    }

    public int getInteger() {
        return intPayload.remove(0);
    }

    public float getFloat() {
        return floatPayload.remove(0);
    }

    public String getString() {
        return stringPayload.remove(0);
    }

    // TODO unused method
    public void append(PacketPayload other) {
        if (other == null) {
            return;
        }
        intPayload.addAll(other.intPayload);
        floatPayload.addAll(other.floatPayload);
        stringPayload.addAll(other.stringPayload);
    }

    public boolean isEmpty() {
        return intPayload.isEmpty() && floatPayload.isEmpty() && stringPayload.isEmpty();
    }
}
