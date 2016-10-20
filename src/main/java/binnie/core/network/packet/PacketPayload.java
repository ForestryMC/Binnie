package binnie.core.network.packet;

import binnie.core.network.INetworkedEntity;

import java.util.ArrayList;
import java.util.List;

public class PacketPayload {
    public List<Integer> intPayload;
    public List<Float> floatPayload;
    public List<String> stringPayload;

    public PacketPayload() {
        this.intPayload = new ArrayList<Integer>();
        this.floatPayload = new ArrayList<Float>();
        this.stringPayload = new ArrayList<String>();
        this.intPayload.clear();
        this.floatPayload.clear();
        this.stringPayload.clear();
    }

    public PacketPayload(final INetworkedEntity tile) {
        this();
        tile.writeToPacket(this);
    }

    public void addInteger(final int a) {
        this.intPayload.add(a);
    }

    public void addFloat(final float a) {
        this.floatPayload.add(a);
    }

    public void addString(final String a) {
        this.stringPayload.add(a);
    }

    public int getInteger() {
        return this.intPayload.remove(0);
    }

    public float getFloat() {
        return this.floatPayload.remove(0);
    }

    public String getString() {
        return this.stringPayload.remove(0);
    }

    public void append(final PacketPayload other) {
        if (other == null) {
            return;
        }
        this.intPayload.addAll(other.intPayload);
        this.floatPayload.addAll(other.floatPayload);
        this.stringPayload.addAll(other.stringPayload);
    }

    public boolean isEmpty() {
        return this.intPayload.isEmpty() && this.floatPayload.isEmpty() && this.stringPayload.isEmpty();
    }
}
