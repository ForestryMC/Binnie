package binnie.extrabees.circuit;

import binnie.extrabees.ExtraBees;
import forestry.api.circuits.ChipsetManager;
import forestry.api.circuits.ICircuitLayout;
import forestry.api.circuits.ICircuitSocketType;

import binnie.core.util.I18N;

public class BinnieCircuitLayout implements ICircuitLayout {

	private final String uid;
	private final ICircuitSocketType socketType;

	public BinnieCircuitLayout(final String uid, final ICircuitSocketType socketType) {
		this.uid = uid;
		this.socketType = socketType;
		ChipsetManager.circuitRegistry.registerLayout(this);
	}

	@Override
	public String getUID() {
		return "binnie.circuitLayout" + this.uid;
	}

	@Override
	public String getName() {
		return I18N.localise(String.format("%s.circuit.layout.%s",
				ExtraBees.MODID,
				this.uid.toLowerCase()));
	}

	@Override
	public String getUsage() {
		return I18N.localise(String.format("%s.circuit.layout.%s.usage",
				ExtraBees.MODID,
				this.uid.toLowerCase()));
	}

	@Override
	public ICircuitSocketType getSocketType() {
		return this.socketType;
	}
}
