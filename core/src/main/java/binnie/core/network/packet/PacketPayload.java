package binnie.core.network.packet;

import java.util.ArrayList;
import java.util.List;

public class PacketPayload {
	private List<Integer> intPayload;
	private List<Float> floatPayload;
	private List<String> stringPayload;

	public PacketPayload() {
		this.intPayload = new ArrayList<>();
		this.floatPayload = new ArrayList<>();
		this.stringPayload = new ArrayList<>();
		this.intPayload.clear();
		this.floatPayload.clear();
		this.stringPayload.clear();
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

	public List<Integer> getIntPayload() {
		return intPayload;
	}

	public List<Float> getFloatPayload() {
		return floatPayload;
	}

	public List<String> getStringPayload() {
		return stringPayload;
	}
}
