package binnie.core.network.packet;

import java.util.ArrayList;
import java.util.List;

public class MachinePayload {
    private List<Integer> intPayload;
    private List<Float> floatPayload;
    private List<String> stringPayload;
    private int id;

    public MachinePayload(int id) {
        intPayload = new ArrayList<>();
        floatPayload = new ArrayList<>();
        stringPayload = new ArrayList<>();
        this.id = id;
    }

    public MachinePayload() {
        intPayload = new ArrayList<>();
        floatPayload = new ArrayList<>();
        stringPayload = new ArrayList<>();
        id = 0;
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

    public void append(MachinePayload other) {
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

    public int getID() {
        return id;
    }

    public void setID(int readInt) {
        id = readInt;
    }
}
