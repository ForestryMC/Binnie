package binnie.extrabees.circuit;

import forestry.api.circuits.ICircuitSocketType;

public enum BinnieCircuitSocketType implements ICircuitSocketType {

	STIMULATOR("binnie.extrabees.stimulator");

	private final String uid;

	BinnieCircuitSocketType(String uid) {
		this.uid = uid;
	}

	@Override
	public String getUid() {
		return uid;
	}

	@Override
	public boolean equals(ICircuitSocketType socketType) {
		return uid.equals(socketType.getUid());
	}

}
