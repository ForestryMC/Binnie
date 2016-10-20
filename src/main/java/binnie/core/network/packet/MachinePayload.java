package binnie.core.network.packet;

import java.util.ArrayList;
import java.util.List;

public class MachinePayload {
    private List<Integer> intPayload;
    private List<Float> floatPayload;
    private List<String> stringPayload;
    private int id;

    public MachinePayload(final int id) {
        this.intPayload = new ArrayList<Integer>();
        this.floatPayload = new ArrayList<Float>();
        this.stringPayload = new ArrayList<String>();
        this.id = 0;
        this.id = id;
        this.intPayload.clear();
        this.floatPayload.clear();
        this.stringPayload.clear();
    }

    public MachinePayload() {
        this.intPayload = new ArrayList<Integer>();
        this.floatPayload = new ArrayList<Float>();
        this.stringPayload = new ArrayList<String>();
        this.id = 0;
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

    public void append(final MachinePayload other) {
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

    public int getID() {
        return this.id;
    }

    public void setID(final int readInt) {
        this.id = readInt;
    }
}
